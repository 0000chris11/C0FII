package com.cofii2.components.javafx.popup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.util.Callback;

/**
 * Class that extends a popup. Use for the display of a dropdown popup of the
 * elements given.
 * 
 * @author C0FII
 */
public class PopupAutoC extends Popup {

    // SEARC OPTIONS---------------------------------
    public static final int STARTS_WITH = 0;
    public static final int CONTAINTS = 1;
    private IntegerProperty searchOption = new SimpleIntegerProperty(STARTS_WITH);
    // SHOW OPTIONS---------------------------------
    public static final int WHEN_KEY_RELEASED = 0;
    public static final int WHEN_FOCUS = 1;
    private IntegerProperty showOption = new SimpleIntegerProperty(WHEN_KEY_RELEASED);

    private boolean showWhenFocus = false;
    // SHOW WHERE OPTIONS----------------------------------
    public static final int BOTTOM_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    private int showWhereOption = BOTTOM_SIDE;

    private String noItemsOption = "No distinct elements";
    private String tagSeparator = "; ";

    private static final String[] DISABLE_ITEM_STYLE_REMOVE = { "list-cell:filled:selected:focused",
            "list-cell:filled:selected", "list-cell:even", "list-cell:odd", "list-cell:filled:hover" };
    private static final String[] DISABLE_ITEM_STYLE_ADD = { "disableItem:filled:selected:focused",
            "disableItem:filled:selected", "disableItem:even", "disableItem:odd", "disableItem:filled:hover" };
    // ---------------------------------------------
    // private TextField tfParent;
    private ObjectProperty<TextField> tfParent = new SimpleObjectProperty<>(null);
    private ListView<String> lv = new ListView<>();
    private ListViewSkin<?> skin;
    private VirtualFlow<?> vf;
    private List<String> lvOriginalItems = new ArrayList<>();
    private List<String> noSearchableItems = new ArrayList<>();
    private List<String> disableItems = new ArrayList<>();

    private EventHandler<KeyEvent> tfParentKeyReleasedListener = this::tfParentKeyReleased;
    private ChangeListener<Boolean> tfParentFocusListener = (obs, oldV, newV) -> tfParentFocusedProperty(newV);

    private ChangeListener<TextField> tfParentChangeListener = (obs, oldValue, newValue) -> {
        if (newValue != null) {
            tfParentInit();
        }
    };

    // QOL-------------------------------------
    private String tagToSearchFor(String text) {
        if (!text.isEmpty() && text.contains(tagSeparator)) {
            String[] split = text.split(tagSeparator);
            if (split.length > 1) {
                text = split[split.length - 1];
            } else {
                text = "";
            }
            // System.out.println("\ttext ; : " + text);
        }
        // System.out.println("getAfterTag TEXT: " + text);
        return text;
    }

    private void setToLast(String newValue) {
        StringBuilder text = new StringBuilder(
                tfParent.getValue().getText() != null ? tfParent.getValue().getText() : "");
        if (text.toString().contains(tagSeparator)) {
            // String text = getAfterTag(tf.getText());
            Matcher matcher = Pattern.compile(tagSeparator).matcher(text);
            int tagCount = 0;
            while (matcher.find()) {
                tagCount++;
            }
            String[] split = text.toString().split(tagSeparator);
            text = new StringBuilder();
            for (int a = 0; a < tagCount; a++) {
                text.append(split[a]).append(tagSeparator);
            }
            text.append(newValue);
            tfParent.getValue().setText(text.toString());
        } else {
            tfParent.getValue().setText(newValue);
        }
    }

    // INTEGER PROPERTIES-----------------------
    private void showOptionChangeListener(Number newValue) {
        int option = newValue.intValue();
        if (option == WHEN_FOCUS) {
            showWhenFocus = true;
        } else {
            showWhenFocus = false;
        }
    }

    // LISTENERS FUNC--------------------------------------------
    private void showPopup() {
        if (tfParent.getValue().getScene().getWindow().isShowing()) {
            Bounds sb = tfParent.getValue().localToScreen(tfParent.getValue().getBoundsInLocal());
            if (showWhereOption == BOTTOM_SIDE) {
                show(tfParent.getValue(), sb.getMinX(), sb.getMaxY());
            } else if (showWhereOption == RIGHT_SIDE) {
                show(tfParent.getValue(), sb.getMaxX(), sb.getMinY());
            }
        }

    }

    // ---------------------------------------------------------
    private void listScrollControl(KeyEvent e) {
        if (skin == null) {
            skin = new ListViewSkin<>(lv);
            vf = (VirtualFlow<?>) skin.getChildren().get(0);
        }

        if (e.getCode().isNavigationKey()) {
            int selected = lv.getSelectionModel().getSelectedIndex();
            int length = lv.getItems().size();
            if (e.getCode() == KeyCode.DOWN && selected < length - 1) {
                lv.getSelectionModel().select(++selected);
            } else if (e.getCode() == KeyCode.UP && selected > 0) {
                lv.getSelectionModel().select(--selected);
            }

            int firstVC = vf.getFirstVisibleCell().getIndex();// NULL ERROR
            int lastVC = vf.getLastVisibleCell().getIndex();
            if (selected >= lastVC || selected <= firstVC) {
                // System.out.println("SCROLLING");
                lv.scrollTo(selected);
            }

        }
    }

    private void listScrollControlSimpleWay(KeyEvent e) {
        int selected = lv.getSelectionModel().getSelectedIndex();
        if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.UP) {
            if (lv.getSelectionModel().isEmpty() && !lv.isFocused()) {
                showPopup();
            }

            // tfParent.setText(lv.getSelectionModel().getSelectedItem());
            if (!lv.isFocused()) {
                if (e.getCode() == KeyCode.DOWN) {
                    lv.getSelectionModel().select(++selected);
                } else if (e.getCode() == KeyCode.UP) {
                    lv.getSelectionModel().select(--selected);
                }
            }
            // tfParent.setText(lv.getSelectionModel().getSelectedItem());
            // setAfterTag(lv.getSelectionModel().getSelectedItem());
        }
    }

    private void search(KeyEvent e) {
        // lv.setVisible(true);
        showPopup();
        // if (e.getCode() != KeyCode.DOWN) {
        lv.getItems().clear();
        lv.getItems().addAll(lvOriginalItems);
        if (!lv.getItems().isEmpty()) {
            if (!lv.getItems().get(0).equals(noItemsOption)) {
                // System.out.println("\nSEARCH FUNCTION STARTS");

                String text = tfParent.getValue().getText();
                text = tagToSearchFor(text);

                if (!tfParent.getValue().getText().isEmpty()) {
                    text = text.toLowerCase();
                    for (int a = 0; a < lv.getItems().size(); a++) {// REMOVE ITEMS
                        String item = lv.getItems().get(a);
                        String itemFiltered = item.toLowerCase();
                        if (searchOption.intValue() == STARTS_WITH) {
                            if (!itemFiltered.startsWith(text)) {
                                lv.getItems().remove(item);
                                a--;
                            }
                        } else if (searchOption.intValue() == CONTAINTS && !itemFiltered.contains(text)) {
                            lv.getItems().remove(item);
                            a--;
                        }
                    }
                    if (lv.getItems().isEmpty()) {
                        hide();
                    }
                } else {
                    hide();
                }

            }
        }
        // }
    }

    private void tfParentKeyReleased(KeyEvent e) {
        if (tfParent.getValue() != null && lvOriginalItems != null) {
            // listScrollControl(e);
            listScrollControlSimpleWay(e);
            if ((e.getCode().isLetterKey() || e.getCode().isDigitKey() || e.getCode() == KeyCode.BACK_SPACE
                    || e.getCode() == KeyCode.SPACE)) {
                search(e);
            }

        }
    }

    private void tfParentOnMouseClicked(MouseEvent e) {
        if (tfParent.getValue() != null) {
            // WHEN THERE IS NOTHING SELECTED & WHEN NOT ALL ITEMS ON THE LIST ARE
            // NOT-SEARCHABLE
            /*
             * boolean itemMatch = lv.getItems().stream().anyMatch(item ->
             * !item.equals(tfParent.getValue().getText()) &&
             * noSearchableItems.stream().noneMatch(noS -> item.equals(noS)));
             */

            if (!lv.isFocused() && !showWhenFocus) {// CAN'T HIDE IF LV IS FOCUSED
                hide();
                // WHEN SHOW-WHEN-FOCUS OPTION && WHEN LIST IS NOT EMPTY
            } else if (showWhenFocus && !lv.getItems().isEmpty()/* && itemMatch */) {
                showPopup();
            }
        }
    }

    private void tfParentFocusedProperty(boolean focused) {
        if (tfParent.getValue() != null) {
            // WHEN THERE IS NOTHING SELECTED & WHEN NOT ALL ITEMS ON THE LIST ARE
            // NOT-SEARCHABLE
            boolean itemMatch = lv.getItems().stream().anyMatch(item -> !item.equals(tfParent.getValue().getText())
                    && noSearchableItems.stream().noneMatch(noS -> item.equals(noS)));

            if (!focused || (!lv.isFocused() && !showWhenFocus)) {// CAN'T HIDE IF LV IS FOCUSED
                hide();
                // WHEN FOCUS & WHEN SHOW-WHEN-FOCUS OPTION && WHEN LIST IS NOT EMPTY &
            } else if (focused && showWhenFocus && !lv.getItems().isEmpty() && itemMatch) {
                showPopup();
            }
        }
    }

    // LV----------------
    private void lvSelectionListener(String oldValue, String newValue) {
        if (tfParent.getValue() != null && newValue != null
                && noSearchableItems.stream().noneMatch(s -> s.equals(newValue))) {
            setToLast(newValue);
            tfParent.getValue()
                    .positionCaret(tfParent.getValue().getText() != null ? tfParent.getValue().getText().length() : 0);

        }
    }

    private void lvFocusedProperty(boolean newValue) {
        if (tfParent.getValue() != null && (!newValue || !tfParent.getValue().isFocused())) {// CAN'T HIDE IF TFPARENT
                                                                                             // IS FOCUSED
            hide();
        }
    }

    private void lvItemsAddedOrRemovedListener(Change<? extends String> c) {
        while (c.next()) {
            if (c.wasAdded() || c.wasRemoved()) {
                double lvHeight = lv.getFixedCellSize() * lv.getItems().size();
                lv.setMaxHeight(lvHeight);
            }
        }
    }

    private void lvMouseClicked(MouseEvent e) {
        hide();
    }

    // INIT--------------------------------------------
    private void tfParentInit() {
        tfParent.getValue().removeEventHandler(KeyEvent.KEY_RELEASED, tfParentKeyReleasedListener);
        tfParent.getValue().setOnMouseClicked(null);
        tfParent.getValue().focusedProperty().removeListener(tfParentFocusListener);

        tfParent.getValue().addEventHandler(KeyEvent.KEY_RELEASED, tfParentKeyReleasedListener);

        tfParent.getValue().setOnMouseClicked(this::tfParentOnMouseClicked);
        tfParent.getValue().focusedProperty().addListener(tfParentFocusListener);

        if (tfParent.getValue().getScene() != null) {
            tfParent.getValue().getScene().getWindow().xProperty().addListener((obs, oldV, newV) -> {
                if (tfParent.getValue() != null) {
                    Bounds bounds = tfParent.getValue().localToScene(tfParent.getValue().getBoundsInLocal());
                    // System.out.println("tfParent Left Insets: "+ tfParent.getInsets().getLeft());
                    double x = newV.doubleValue() /* + bounds.getMinX() */;
                    setX(x + bounds.getMinX() + tfParent.getValue().getInsets().getLeft());
                }
            });
            tfParent.getValue().getScene().getWindow().yProperty().addListener((obs, oldV, newV) -> {
                if (tfParent.getValue() != null && tfParent.getValue().getScene().getWindow() != null) {
                    Bounds bounds = tfParent.getValue().localToScene(tfParent.getValue().getBoundsInLocal());
                    double y = newV.doubleValue() /* + bounds.getMinX() */;
                    double titleHeight = tfParent.getValue().getScene().getWindow().getHeight()
                            - tfParent.getValue().getScene().getHeight();

                    setY(y + bounds.getMaxY() + titleHeight);
                }
            });

            lv.setPrefWidth(tfParent.getValue().getPrefHeight());
        }
    }

    public void init() {
        addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.END) {
                tfParent.getValue().positionCaret(tfParent.getValue().getText().length());
            } else if (e.getCode() == KeyCode.BEGIN) {// DOESN'T WORK
                tfParent.getValue().positionCaret(0);
            } else if (e.getCode() == KeyCode.LEFT) {
                tfParent.getValue().positionCaret(tfParent.getValue().getCaretPosition() - 1);
            } else if (e.getCode() == KeyCode.RIGHT) {
                tfParent.getValue().positionCaret(tfParent.getValue().getCaretPosition() + 1);
            }
        });
        // INTEGER PROPERTIES-----------------------
        showOption.addListener((obs, oldValue, newValue) -> showOptionChangeListener(newValue));
        // ---------------------------------------
        getContent().add(lv);
        /*
         * if (tfParent.getValue() != null) { tfParentInit(); }
         */

        lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setId(null);
                        setDisable(false);
                        if (!disableItems.isEmpty()) {
                            if (disableItems.stream().anyMatch(s -> s.equals(item))) {
                                setDisable(true);
                                setId("disableCell");
                            }
                            // getStyleClass().removeAll(DISABLE_ITEM_STYLE_REMOVE);
                            // getStyleClass().addAll(DISABLE_ITEM_STYLE_ADD);
                            // setTextFill(Color.RED);
                            // getStyleClass().forEach(System.out::println);
                        }

                        applyCss();
                        setText(item);
                    }

                };
            }
        });

        lv.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> lvSelectionListener(oldV, newV));
        lv.focusedProperty().addListener((obs, oldV, newV) -> lvFocusedProperty(newV));
        lv.setOnMouseClicked(this::lvMouseClicked);

        lv.setFixedCellSize(24);
        lv.getItems().addListener(this::lvItemsAddedOrRemovedListener);
        // lv.getItems().addListener(this::lvItemsAddedOrRemovedListener);

        lv.setFocusTraversable(false);

        lv.setPrefHeight(200);

        setOnHidden(e -> lv.getSelectionModel().clearSelection());

        setWidth(-1);
        setHeight(-1);
    }

    // CONSTRUCTORS--------------------------------
    public PopupAutoC() {
        this.tfParent.addListener(tfParentChangeListener);
        init();
    }

    public PopupAutoC(TextField tfParent) {
        this.tfParent.addListener(tfParentChangeListener);

        this.tfParent.setValue(tfParent);
        init();
    }

    public PopupAutoC(TextField tfParent, String... lvOriginalItems) {
        this.tfParent.addListener(tfParentChangeListener);

        this.tfParent.setValue(tfParent);
        this.lvOriginalItems.addAll(Arrays.asList(lvOriginalItems));
        lv.getItems().addAll(lvOriginalItems);
        init();
    }

    // NEW UNTESTED--------------------------------
    public void clearItems() {
        lvOriginalItems.clear();
        lv.getItems().clear();
    }

    public void addItem(String item) {
        lvOriginalItems.add(item);
        lv.getItems().add(item);
    }

    public void addAllItems(String... items) {
        lvOriginalItems.clear();
        lv.getItems().clear();

        List<String> list = Arrays.asList(items);
        lvOriginalItems.addAll(list);
        lv.getItems().addAll(list);
    }

    public void addAllItems(List<String> list) {
        lvOriginalItems.clear();
        lv.getItems().clear();

        lvOriginalItems.addAll(list);
        lv.getItems().addAll(list);
    }

    // GET & SETTERS--------------------------------------------
    public int getSearchOption() {
        return searchOption.intValue();
    }

    public void setSearchOption(int searchOption) {
        this.searchOption.setValue(searchOption);
    }

    public String getNoItemsOption() {
        return noItemsOption;
    }

    public void setNoItemsOption(String noItemsOption) {
        this.noItemsOption = noItemsOption;
    }

    public TextField getTfParent() {
        return tfParent.getValue();
    }

    public void setTfParent(TextField tfParent) {
        this.tfParent.setValue(tfParent);
    }

    public List<String> getLvOriginalItems() {
        return lvOriginalItems;
    }

    public void setLvOriginalItems(List<String> lvOriginalItems) {
        this.lvOriginalItems = lvOriginalItems;
    }

    public ListView<String> getLv() {
        return lv;
    }

    public void setLv(ListView<String> lv) {
        this.lv = lv;
    }

    public String getTagSeparator() {
        return tagSeparator;
    }

    public void setTagSeparator(String tagSeparator) {
        this.tagSeparator = tagSeparator;
    }

    public int getShowOption() {
        return showOption.intValue();
    }

    public void setShowOption(int showOption) {
        this.showOption.setValue(showOption);
    }

    public List<String> getNoSearchableItems() {
        return noSearchableItems;
    }

    public void setNoSearchableItems(List<String> noSearchableItems) {
        this.noSearchableItems = noSearchableItems;
    }

    public List<String> getDisableItems() {
        return disableItems;
    }

    public void setDisableItems(List<String> disableItems) {
        this.disableItems = disableItems;
    }

}
