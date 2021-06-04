package com.cofii2.components.javafx;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;

public class PopupMessage extends Popup{

    private Node parentNode;
    private Label lbMessage = new Label("No message");
    //---------------------------------------------------
    public void show(String message){
        lbMessage.setText(message);
        Bounds sb = parentNode.localToScreen(parentNode.getBoundsInLocal());
        show(parentNode, sb.getMaxX(), sb.getMinY());
    }
    //---------------------------------------------------
    private void init(){
        Button btnx = new Button("x");
        HBox hbox = new HBox(btnx, lbMessage);
        hbox.setSpacing(2);
        btnx.setFont(Font.font(6));

        hbox.setOnMouseDragged(e -> {
            PopupMessage.this.setX(e.getScreenX());
            PopupMessage.this.setY(e.getScreenY());
        });
        getContent().add(hbox);
    }
    //---------------------------------------------------
    public PopupMessage(Node parentNode){
        this.parentNode = parentNode;
        init();
    }
    public PopupMessage(Node parentNode, String message){
        this.parentNode = parentNode;
        lbMessage.setText(message);
        init();
    }
    //---------------------------------------------------
    public Node getParentNode() {
        return parentNode;
    }
    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
    public Label getLbMessage() {
        return lbMessage;
    }
    public void setLbMessage(Label lbMessage) {
        this.lbMessage = lbMessage;
    }

}
