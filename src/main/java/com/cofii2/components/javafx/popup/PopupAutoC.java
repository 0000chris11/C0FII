package com.cofii2.components.javafx.popup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;

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

    private TextField tfParent;
    private ListView<String> lv = new ListView<>();
    private ListViewSkin<?> skin;
    private VirtualFlow<?> vf;
    private List<String> lvOriginalItems = new ArrayList<>();

    private EventHandler<KeyEvent> tfParentKeyReleasedListener = this::tfParentKeyReleased;

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
        StringBuilder text = new StringBuilder(tfParent.getText() != null ? tfParent.getText() : "");
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
            tfParent.setText(text.toString());
        } else {
            tfParent.setText(newValue);
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
        Bounds sb = tfParent.localToScreen(tfParent.getBoundsInLocal());
        if (showWhereOption == BOTTOM_SIDE) {
            show(tfParent, sb.getMinX(), sb.getMaxY());
        } else if (showWhereOption == RIGHT_SIDE) {
            show(tfParent, sb.getMaxX(), sb.getMinY());
        }

    }

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
        if (!lv.getItems().get(0).equals(noItemsOption)) {
            // System.out.println("\nSEARCH FUNCTION STARTS");

            String text = tfParent.getText();
            text = tagToSearchFor(text);

            if (!tfParent.getText().isEmpty()) {
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
        // }
    }

    private void tfParentKeyReleased(KeyEvent e) {
        if (tfParent != null && lvOriginalItems != null) {
            // listScrollControl(e);
            listScrollControlSimpleWay(e);
            if ((e.getCode().isLetterKey() || e.getCode().isDigitKey() || e.getCode() == KeyCode.BACK_SPACE
                    || e.getCode() == KeyCode.SPACE)) {
                search(e);
            }

        }
    }

    private void tfParentFocusedProperty(boolean newValue) {
        if (tfParent != null) {
            if (!newValue || (!lv.isFocused() && !showWhenFocus)) {// CAN'T HIDE IF LV IS FOCUSED
                hide();
            } else if (newValue && showWhenFocus && !lv.getItems().isEmpty()
                    && lv.getItems().stream().noneMatch(s -> s.equals(tfParent.getText()))) {
                showPopup();
            }
        }
    }

    private void lvSelectionListener(String oldValue, String newValue) {
        if (tfParent != null && newValue != null) {
            setToLast(newValue);
            tfParent.positionCaret(tfParent.getText() != null ? tfParent.getText().length() : 0);

        }
    }

    private void lvFocusedProperty(boolean newValue) {
        if (tfParent != null && (!newValue || !tfParent.isFocused())) {// CAN'T HIDE IF TFPARENT IS FOCUSED
            hide();
        }
    }

    // INIT--------------------------------------------
    private void tfParentInit() {
        tfParent.removeEventHandler(KeyEvent.KEY_RELEASED, tfParentKeyReleasedListener);
        tfParent.addEventHandler(KeyEvent.KEY_RELEASED, tfParentKeyReleasedListener);

        tfParent.focusedProperty().addListener((obs, oldV, newV) -> tfParentFocusedProperty(newV));

        if (tfParent.getScene() != null) {
            tfParent.getScene().getWindow().xProperty().addListener((obs, oldV, newV) -> {
                if (tfParent != null) {
                    Bounds bounds = tfParent.localToScene(tfParent.getBoundsInLocal());
                    // System.out.println("tfParent Left Insets: "+ tfParent.getInsets().getLeft());
                    double x = newV.doubleValue() /* + bounds.getMinX() */;
                    setX(x + bounds.getMinX() + tfParent.getInsets().getLeft());
                }
            });
            tfParent.getScene().getWindow().yProperty().addListener((obs, oldV, newV) -> {
                if (tfParent != null && tfParent.getScene().getWindow() != null) {
                    Bounds bounds = tfParent.localToScene(tfParent.getBoundsInLocal());
                    double y = newV.doubleValue() /* + bounds.getMinX() */;
                    double titleHeight = tfParent.getScene().getWindow().getHeight() - tfParent.getScene().getHeight();

                    setY(y + bounds.getMaxY() + titleHeight);
                }
            });

            lv.setPrefWidth(tfParent.getPrefHeight());
        }
    }

    public void init() {
        addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.END) {
                tfParent.positionCaret(tfParent.getText().length());
            } else if (e.getCode() == KeyCode.BEGIN) {// DOESN'T WORK
                tfParent.positionCaret(0);
            } else if (e.getCode() == KeyCode.LEFT) {
                tfParent.positionCaret(tfParent.getCaretPosition() - 1);
            } else if (e.getCode() == KeyCode.RIGHT) {
                tfParent.positionCaret(tfParent.getCaretPosition() + 1);
            }
        });
        // INTEGER PROPERTIES-----------------------
        showOption.addListener((obs, oldValue, newValue) -> showOptionChangeListener(newValue));
        // ---------------------------------------
        getContent().add(lv);
        if (tfParent != null) {
            tfParentInit();
        }
        lv.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> lvSelectionListener(oldV, newV));
        lv.focusedProperty().addListener((obs, oldV, newV) -> lvFocusedProperty(newV));
        lv.setFocusTraversable(false);

        lv.setPrefHeight(200);

        setWidth(-1);
        setHeight(-1);
    }

    // CONSTRUCTORS--------------------------------
    public PopupAutoC() {
        init();
    }

    public PopupAutoC(TextField tfParent) {
        this.tfParent = tfParent;
        init();
    }

    public PopupAutoC(TextField tfParent, String... lvOriginalItems) {
        this.tfParent = tfParent;
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
        return tfParent;
    }

    public void setTfParent(TextField tfParent) {
        this.tfParent = tfParent;

        if (this.tfParent != null) {
            tfParentInit();
        }

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

}
