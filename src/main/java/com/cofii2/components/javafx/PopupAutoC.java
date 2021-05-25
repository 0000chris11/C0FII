package com.cofii2.components.javafx;

import javafx.geometry.Bounds;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;

public class PopupAutoC extends Popup{
    
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
    //--------------------------------------------
    private void showPopup(){
        Bounds sb = tfParent.localToScreen(tfParent.getBoundsInLocal());
        if(sideOption == BOTTOM_SIDE){
            show(tfParent, sb.getMinX(), sb.getMaxY());
        }else if(sideOption == RIGHT_SIDE){
            show(tfParent, sb.getMaxX(), sb.getMinY());
        }
        
    }
    
    private String getAfterTag(String text) {
        if (!text.isEmpty() && text.contains("; ")) {
            text = text.substring(text.lastIndexOf("; ") + 2, text.length());
                //System.out.println("\ttext ; : " + text);
        }
        //System.out.println("getAfterTag TEXT: " + text);
        return text;
    }

    private void listScrollControl(KeyEvent e){
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

        }
    }
    
    private void search(){
        //lv.setVisible(true);
        showPopup();
        lv.getItems().clear();
        lv.getItems().addAll(lvOriginalItems);
        if (!lv.getItems().get(0).equals(noItemsOption)) {
            //System.out.println("\nSEARCH FUNCTION STARTS");

            String text = tfParent.getText().toLowerCase();
            text = getAfterTag(text);

            if (!text.isEmpty()) {
                for (int a = 0; a < lv.getItems().size(); a++) {//REMOVE ITEMS
                    String item = lv.getItems().get(a);
                    String itemFiltered = item.toLowerCase();
                    if (searchOption == STARTS_WITH) {
                        if (!itemFiltered.startsWith(text)) {
                            lv.getItems().remove(item);
                            a--;
                        }
                    }else if(searchOption == CONTAINTS){
                        if(!itemFiltered.contains(text)){
                            lv.getItems().remove(item);
                            a--;
                        }
                    }
                }
                if (lv.getItems().isEmpty()) {
                    hide();
                }
            }else{
                hide();
            }

        }
    }

    private void tfParentKeyReleased(KeyEvent e){
        if(lvOriginalItems != null){
            listScrollControl(e);

            if ((e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.SPACE)
                    && lvOriginalItems.length > 0) {
                        search();
                    }
        }
    }
    private void tfParentFocusesProperty(boolean newV){
        if (!newV || !lv.isFocused()) {
            //lv.setVisible(false);
            hide();
            //setParentHeight(closeHeight);
        }
    }
    private void lvSelectionListener(String newValue){
        String text = tfParent.getText();
        if (!tfParent.isFocused()) {
            if (text.contains("; ")) {
                // String text = getAfterTag(tf.getText());
                String[] split = text.split("; ");
                text = "";
                for (int a = 0; a < split.length - 1; a++) {
                    text += split[a] + "; ";
                }
                text += newValue;
                tfParent.setText(text);
            } else {
                tfParent.setText(newValue);
            }
        }
    }
    //--------------------------------------------
    public void init(){
        getContent().add(lv);

        tfParent.addEventHandler(KeyEvent.KEY_RELEASED, this::tfParentKeyReleased);
        tfParent.focusedProperty().addListener((obs, oldV, newV) -> tfParentFocusesProperty(newV));
        lv.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> lvSelectionListener(newV));

        lv.setPrefHeight(200);
        lv.setPrefWidth(-1);

        setWidth(-1);
        setHeight(-1);
    }
    public PopupAutoC(TextField tfParent){
        this.tfParent = tfParent;
        init();
    }
    public PopupAutoC(TextField tfParent, String... lvOriginalItems){
        this.tfParent = tfParent;
        this.lvOriginalItems = lvOriginalItems;
        lv.getItems().addAll(lvOriginalItems);
        init();
    }
    //--------------------------------------------

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
