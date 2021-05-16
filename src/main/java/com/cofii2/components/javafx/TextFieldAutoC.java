package com.cofii2.components.javafx;

import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Class for Text AutoCompletion using a TextField and a ListView-String inside
 * of VBox
 * 
 * @author C0FII
 */
public class TextFieldAutoC extends VBox {

    public static final int STARTS_WITH = 0;
    private int searchOption = STARTS_WITH;

    private double closeHeight = 30;
    private double openHeight = 180;

    private int index;

    private TextField tf = new TextField();
    private ListView<String> lv = new ListView<>();
    private ListViewSkin<?> skin;
    private VirtualFlow<?> vf;
    private String[] lvOriginalElements;

    private String noElementsOption = "No distinct elements";

    // LISTENERS --------------------------------------------------
    private void tfKeyReleased(KeyEvent e) {
        if (lvOriginalElements != null) {
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

                int firstVC = vf.getFirstVisibleCell().getIndex();
                int lastVC = vf.getLastVisibleCell().getIndex();
                if (selected >= lastVC || selected <= firstVC) {
                    //System.out.println("SCROLLING");
                    lv.scrollTo(selected);
                }

            } else if ((e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.SPACE)
                    && lvOriginalElements.length > 0) {
                        search();
            } else if (e.getCode() == KeyCode.ENTER) {
                // tf.setText(lv.getSelectionModel().getSelectedItem());
                setFullTagString(tf.getText(), lv.getSelectionModel().getSelectedItem());
                lv.setVisible(false);
                //setPrefHeight(closeHeight);
                //setParentHeight(closeHeight);
                
            }
        }
    }
    private void search(){
        lv.setVisible(true);
        //setParentHeight(openHeight);
        //setPrefHeight(openHeight);

        lv.getItems().clear();
        lv.getItems().addAll(lvOriginalElements);
        if (!lv.getItems().get(0).equals(noElementsOption)) {
            //System.out.println("\nSEARCH FUNCTION STARTS");

            String text = tf.getText().toLowerCase();
            text = getAfterTag(text);

            if (!text.isEmpty()) {
                for (int a = 0; a < lv.getItems().size(); a++) {
                    String item = lv.getItems().get(a);
                    String itemFiltered = item.toLowerCase();
                    if (searchOption == STARTS_WITH) {
                        if (!itemFiltered.startsWith(text)) {
                            lv.getItems().remove(item);
                            a--;
                        }
                    }
                }
                if (lv.getItems().isEmpty()) {
                    lv.setVisible(false);
                    //setPrefHeight(closeHeight);
                }
            }else{
                lv.setVisible(false);
                //setPrefHeight(closeHeight);
            }

        }
    }
    private void listViewSelection(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        //System.out.println("NEW VALUE: " + newValue);
        String text = tf.getText();
        if (!tf.isFocused()) {
            if (text.contains("; ")) {
                // String text = getAfterTag(tf.getText());
                String[] split = text.split("; ");
                text = "";
                for (int a = 0; a < split.length - 1; a++) {
                    text += split[a] + "; ";
                }
                text += newValue;
                tf.setText(text);
            } else {
                tf.setText(newValue);
            }
        }
    }

    private void vboxFocus(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (!newValue && !lv.isFocused()) {
            lv.setVisible(false);
            setParentHeight(closeHeight);
        }
    }

    // --------------------------------------------------------------
    private String getAfterTag(String text) {
        if (!text.isEmpty() && text.contains("; ")) {
            text = text.substring(text.lastIndexOf("; ") + 2, text.length());
                //System.out.println("\ttext ; : " + text);
        }
        //System.out.println("getAfterTag TEXT: " + text);
        return text;
    }

    private void setFullTagString(String text, String newValue) {
        if (text.contains("; ")) {
            // String text = getAfterTag(tf.getText());
            String[] split = text.split("; ");
            text = "";
            for (int a = 0; a < split.length - 1; a++) {
                text += split[a] + "; ";
            }
            text += newValue;
            tf.setText(text);
        } else {
            tf.setText(newValue);
        }
    }

    private void setParentHeight(double value) {
        /*
        Parent p = getParent();
        if (p instanceof GridPane) {
            GridPane gp = (GridPane) p;
            gp.getRowConstraints().get(index).setPrefHeight(value);
        }
        */
    }
    private double getParentHeight(){
        /*
        Parent p = getParent();
        if (p instanceof GridPane) {
            GridPane gp = (GridPane) p;
            return gp.getRowConstraints().get(index).getPrefHeight();
        }else{
            return -1;
        }
        */
        return 0;
    }

    // --------------------------------------------------
    private void init() {
        tf.setOnKeyReleased(this::tfKeyReleased);
        tf.focusedProperty().addListener(this::vboxFocus);
        //tf.setStyle("-fx-background-color: #035850; -fx-text-fill: white");
        tf.setPrefWidth(-1);
        
        lv.setVisible(false);
        lv.getSelectionModel().selectedItemProperty().addListener(this::listViewSelection);
        lv.setPrefHeight(openHeight - closeHeight);
        lv.setPrefWidth(-1);
        lv.setMaxHeight(openHeight - closeHeight);
        lv.managedProperty().bind(lv.visibleProperty());

        if (lvOriginalElements != null) {
            lv.getItems().addAll(lvOriginalElements);
        }

        getChildren().addAll(tf, lv);  

        setVgrow(tf, Priority.NEVER);
        setVgrow(lv, Priority.ALWAYS);

        //setPrefHeight(openHeight + closeHeight);
        //setPrefHeight(closeHeight);
        setPrefHeight(-1);
        setPrefWidth(-1);
        setMaxHeight(Short.MAX_VALUE);
        
        // setPrefHeight(-1);
       
    }

    /**
     * Construct a new TextFieldAutoC. Supported only for a GridPane Parent
     * 
     * @param index determines the row in wich this element it is
     */
    public TextFieldAutoC(int index) {
        this.index = index;
        init();

    }

    /**
     * Construct a new TextFieldAutoC. Supported only for a GridPane Parent
     * 
     * @param index              determines the row in wich this element it is
     * @param lvOriginalElements the base of the array to search on
     */
    public TextFieldAutoC(int index, String[] lvOriginalElements) {
        this.index = index;
        this.lvOriginalElements = lvOriginalElements;
        init();

    }

    /**
     * Construct a new TextFieldAutoC. Supported only for a GridPane Parent
     * 
     * @param index              determines the row in wich this element it is
     * @param lvOriginalElements the base of the List to search on
     */
    public TextFieldAutoC(int index, List<String> lvOriginalElements) {
        this.index = index;
        this.lvOriginalElements = lvOriginalElements.toArray(new String[lvOriginalElements.size()]);
        init();
    }

    // --------------------------------------------------
    public TextField getTf() {
        return tf;
    }

    public void setTf(TextField tf) {
        this.tf = tf;
    }

    public ListView<String> getLv() {
        return lv;
    }

    public void setLv(ListView<String> lv) {
        this.lv = lv;
    }

    public String[] getLvOriginalElements() {
        return lvOriginalElements;
    }

    public void setLvOriginalElements(String[] lvOriginalElements) {
        this.lvOriginalElements = lvOriginalElements;
    }

    public String getNoElementsOption() {
        return noElementsOption;
    }

    public void setNoElementsOption(String noElementsOption) {
        this.noElementsOption = noElementsOption;
    }

    public int getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(int searchOption) {
        this.searchOption = searchOption;
    }

    public double getCloseHeight() {
        return closeHeight;
    }

    public void setCloseHeight(double closeHeight) {
        this.closeHeight = closeHeight;
    }

    public double getOpenHeight() {
        return openHeight;
    }

    public void setOpenHeight(double openHeight) {
        this.openHeight = openHeight;
    }

}
