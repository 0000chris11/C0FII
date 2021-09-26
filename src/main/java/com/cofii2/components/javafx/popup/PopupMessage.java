package com.cofii2.components.javafx.popup;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.cofii2.components.javafx.popup.config.PPosition;
import com.sun.tools.javac.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;

public class PopupMessage extends Popup {

    private StringProperty id = new SimpleStringProperty("-1");
    private Node parentNode;

    private final VBox vbox = new VBox();
    private final ObservableSet<String> itemList;

    private int popupPosition = PPosition.RIGHT;

    /**
     * May turn off this attribute to only store data
     */
    private boolean show = true;

    // ---------------------------------------------------
    public void showPopup() {
        if (!itemList.isEmpty() && show) {
            Bounds sb = parentNode.localToScreen(parentNode.getBoundsInLocal());
            if (sb != null && parentNode.isVisible()) {
                if (popupPosition == PPosition.TOP) {
                    show(parentNode, sb.getMinX(), sb.getMinY());
                }else if(popupPosition == PPosition.RIGHT){
                    show(parentNode, sb.getMaxX(), sb.getMinY());
                }else if(popupPosition == PPosition.BOTTOM){
                    show(parentNode, sb.getMinX(), sb.getMaxY());
                }
            }
        }

    }

    // LISTENERS ----------------------------------------------
    private void idChange(String newValue) {
        Predicate<? super Node> predicate = node -> ((Label) node).getText().contains("id-");
        vbox.getChildren().stream().filter(predicate).map(lb -> {
            Label lb2 = (Label) lb;
            lb2.setText("id-" + (newValue));
            return lb2;
        }).close();
    }

    private void lbsListChange(Change<? extends String> c) {
        // while (c.next()) {
        if (c.wasAdded() || c.wasRemoved()) {
            vbox.getChildren().clear();
            if (!itemList.isEmpty()) {
                itemList.stream().filter(s -> !s.contains("id-")).forEach(s -> vbox.getChildren().add(new Label(s)));
                if (!vbox.getChildren().isEmpty()) {
                    showPopup();
                } else {
                    hide();
                }
            } else {
                hide();
            }
        }
        // }
    }

    // INIT ---------------------------------------------------
    private void init() {
        Button btnx = new Button("x");
        HBox hbox = new HBox(btnx, vbox);
        hbox.setSpacing(2);
        btnx.setFont(Font.font(6));

        btnx.setOnAction(e -> hide());
        hbox.setOnMouseDragged(e -> {
            PopupMessage.this.setX(e.getScreenX());
            PopupMessage.this.setY(e.getScreenY());
        });
        getContent().add(hbox);
    }

    // ---------------------------------------------------
    public PopupMessage(String id, Node parentNode) {
        // lbs.addListener((ListChangeListener<? super String>)(c -> lbsListChange(c)));
        itemList = FXCollections.observableSet("id-" + id);
        itemList.addListener(this::lbsListChange);

        this.id.addListener((obs, oldValue, newValue) -> idChange(newValue));
        this.id.setValue(id);

        this.parentNode = parentNode;
        init();
    }

    public PopupMessage(String id, Node parentNode, boolean show) {
        // lbs.addListener((ListChangeListener<? super String>)(c -> lbsListChange(c)));
        itemList = FXCollections.observableSet("id-" + id);
        itemList.addListener(this::lbsListChange);

        this.id.addListener((obs, oldValue, newValue) -> idChange(newValue));
        this.id.setValue(id);

        this.parentNode = parentNode;
        this.show = show;
        init();
    }

    public PopupMessage(String id, Node parentNode, String message) {
        itemList = FXCollections.observableSet("id-" + id);
        itemList.addListener(this::lbsListChange);

        this.id.addListener((obs, oldValue, newValue) -> idChange(newValue));
        this.id.setValue(id);

        this.parentNode = parentNode;
        itemList.add(message);
        init();
    }

    // ---------------------------------------------------
    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public ObservableSet<String> getItemList() {
        return itemList;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id.setValue(id);
    }

    public VBox getVbox() {
        return vbox;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getPopupPosition() {
        return popupPosition;
    }

    public void setPopupPosition(int popupPosition) {
        this.popupPosition = popupPosition;
    }

}
