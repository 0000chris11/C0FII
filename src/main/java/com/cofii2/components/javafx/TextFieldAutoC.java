package com.cofii2.components.javafx;

import java.util.Arrays;
import java.util.List;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class TextFieldAutoC extends VBox {

    public static final int STARTS_WITH = 0;
    private int searchOption = STARTS_WITH;

    private TextField tf = new TextField();
    private ListView<String> lv = new ListView<>();
    private String[] lvOriginalElements;

    private String noElementsOption = "No distinct elements";

    // --------------------------------------------------
    private void tfKeyReleased(KeyEvent e) {
        if (e.getCode().isNavigationKey()) {
            int selected = lv.getSelectionModel().getSelectedIndex();
            int length = lv.getItems().size();
            if (e.getCode() == KeyCode.DOWN && selected < length - 1) {
                lv.getSelectionModel().select(++selected);
            } else if (e.getCode() == KeyCode.UP && selected > 0) {
                lv.getSelectionModel().select(--selected);
            }

        } else if ((e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.SPACE)
                && lvOriginalElements.length > 0) {
            lv.getItems().clear();
            lv.getItems().addAll(lvOriginalElements);
            if (!lv.getItems().get(0).equals(noElementsOption)) {
                System.out.println("\nSEARCH FUNCTION STARTS");

                String text = tf.getText().toLowerCase();
                if (!text.isEmpty()) {
                    if (text.contains("; ")) {
                        text = text.substring(text.lastIndexOf("; ") + 2, text.length() - 1);
                        System.out.println("\ttext ; : " + text);
                    }

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
                }

            }
        }
    }

    // --------------------------------------------------
    private void init() {
        tf.setOnKeyReleased(this::tfKeyReleased);
        lv.getItems().addAll(lvOriginalElements);
        lv.setMaxHeight(160);
        getChildren().addAll(tf, lv);
        setMaxWidth(Short.MAX_VALUE);
        //setMaxHeight(400);
    }

    public TextFieldAutoC(String[] lvOriginalElements) {
        this.lvOriginalElements = lvOriginalElements;
        init();

    }

    public TextFieldAutoC(List<String> lvOriginalElements) {
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

}
