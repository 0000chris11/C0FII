package com.cofii2.components.javafx.popup;

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

    public static final int STARTS_WITH = 0;
    public static final int CONTAINTS = 1;
    private int searchOption = STARTS_WITH;

    public static final int BOTTOM_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    private int sideOption = BOTTOM_SIDE;

    private String noItemsOption = "No distinct elements";

    private TextField tfParent;
    private ListView<String> lv = new ListView<>();
    private ListViewSkin<?> skin;
    private VirtualFlow<?> vf;
    private String[] lvOriginalItems;

    // QOL-------------------------------------
    private String getAfterTag(String text) {
        if (!text.isEmpty() && text.contains("; ")) {
            text = text.substring(text.lastIndexOf("; ") + 2, text.length());
            // System.out.println("\ttext ; : " + text);
        }
        // System.out.println("getAfterTag TEXT: " + text);
        return text;
    }

    private void setAfterTag(String newValue) {
        StringBuilder text = new StringBuilder(tfParent.getText());
        if (text.toString().contains("; ")) {
            // String text = getAfterTag(tf.getText());
            String[] split = text.toString().split("; ");
            text = new StringBuilder();
            for (int a = 0; a < split.length - 1; a++) {
                text.append(split[a]).append("; ");
            }
            text.append(newValue);
            tfParent.setText(text.toString());
        } else {
            tfParent.setText(newValue);
        }
    }

    // LISTENERS FUNC--------------------------------------------
    private void showPopup() {
        Bounds sb = tfParent.localToScreen(tfParent.getBoundsInLocal());
        if (sideOption == BOTTOM_SIDE) {
            show(tfParent, sb.getMinX(), sb.getMaxY());
        } else if (sideOption == RIGHT_SIDE) {
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
            if(lv.getSelectionModel().isEmpty()){
                showPopup();
            }

            tfParent.setText(lv.getSelectionModel().getSelectedItem());
            tfParent.positionCaret(tfParent.getText() != null ? tfParent.getText().length() : 0);
            if (e.getCode() == KeyCode.DOWN) {
                lv.getSelectionModel().select(++selected);
            } else if (e.getCode() == KeyCode.UP) {
                lv.getSelectionModel().select(--selected);
            }

            tfParent.setText(lv.getSelectionModel().getSelectedItem());
        }
    }

    private void search(KeyEvent e) {
        // lv.setVisible(true);
        showPopup();
        if (e.getCode() != KeyCode.DOWN) {
            lv.getItems().clear();
            lv.getItems().addAll(lvOriginalItems);
            if (!lv.getItems().get(0).equals(noItemsOption)) {
                // System.out.println("\nSEARCH FUNCTION STARTS");

                String text = tfParent.getText();
                text = getAfterTag(text);

                if (!tfParent.getText().isEmpty()) {
                    text = text.toLowerCase();
                    for (int a = 0; a < lv.getItems().size(); a++) {// REMOVE ITEMS
                        String item = lv.getItems().get(a);
                        String itemFiltered = item.toLowerCase();
                        if (searchOption == STARTS_WITH) {
                            if (!itemFiltered.startsWith(text)) {
                                lv.getItems().remove(item);
                                a--;
                            }
                        } else if (searchOption == CONTAINTS && !itemFiltered.contains(text)) {
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
    }

    private void tfParentKeyReleased(KeyEvent e) {
        if (tfParent != null && lvOriginalItems != null) {
            // listScrollControl(e);
            listScrollControlSimpleWay(e);

            if ((e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.SPACE
                    /*|| e.getCode() == KeyCode.DOWN*/) && lvOriginalItems.length > 0) {
                /*
                 * if (e.getCode() == KeyCode.DOWN && lv.getSelectionModel().isEmpty()) {
                 * lv.getSelectionModel().select(0);
                 * tfParent.setText(lv.getSelectionModel().getSelectedItem()); }
                 */
                search(e);
            }

        }
    }

    private void tfParentFocusedProperty(boolean newValue) {
        if (tfParent != null && (!newValue || !lv.isFocused())) {// CAN'T HIDE IF LV IS FOCUSED
            hide();
        }
    }

    private void lvSelectionListener(String oldValue, String newValue) {
        System.out.println("\noldValue: " + oldValue);
        System.out.println("newValue: " + newValue);
        if (tfParent != null && !tfParent.isFocused()) {
            setAfterTag(newValue);

        }
    }

    private void lvFocusedProperty(boolean newValue) {
        if (tfParent != null && (!newValue || !tfParent.isFocused())) {// CAN'T HIDE IF TFPARENT IS FOCUSED
            hide();
        }
    }

    // INIT--------------------------------------------
    private void tfParentInit() {
        tfParent.addEventHandler(KeyEvent.KEY_RELEASED, this::tfParentKeyReleased);
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
        /*
         * final EventDispatcher originalED = getEventDispatcher();
         * setEventDispatcher((event, tail) -> { if (event.getEventType() ==
         * KeyEvent.KEY_RELEASED && ((KeyEvent) event).getCode() == KeyCode.ENTER) {
         * tfParent.positionCaret(tfParent.getText().length()); return null; //
         * returning null indicates the event was consumed } return
         * originalED.dispatchEvent(event, tail); });
         */
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

    public PopupAutoC() {
        init();
    }

    public PopupAutoC(TextField tfParent) {
        this.tfParent = tfParent;
        init();
    }

    public PopupAutoC(TextField tfParent, String... lvOriginalItems) {
        this.tfParent = tfParent;
        this.lvOriginalItems = lvOriginalItems;
        lv.getItems().addAll(lvOriginalItems);
        init();
    }

    // GET & SETTERS--------------------------------------------
    public int getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(int searchOption) {
        this.searchOption = searchOption;
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

    public String[] getLvOriginalItems() {
        return lvOriginalItems;
    }

    public void setLvOriginalItems(String[] lvOriginalItems) {
        this.lvOriginalItems = lvOriginalItems;
    }

    public ListView<String> getLv() {
        return lv;
    }

    public void setLv(ListView<String> lv) {
        this.lv = lv;
    }

}