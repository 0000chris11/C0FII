package com.cofii2.components.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MessageWindow extends Stage {

    public static final String DEFAULT_STYLE = "-fx-background-color: #100c49; -fx-text-fill: white";

    private Node parentNode;
    private Label lbMessage = new Label();

    // -----------------------------------------------------
    private void init() {
        initStyle(StageStyle.UNDECORATED);
        setAlwaysOnTop(true);
        lbMessage.setStyle(DEFAULT_STYLE);

        setScene(new Scene(lbMessage, -1, -1));
    }

    public MessageWindow(String message) {
        lbMessage.setText(message);
        init();
    }

    public MessageWindow() {
        lbMessage.setText("No message");
        init();
    }

    // -----------------------------------------------------
    public void show(Duration duration) {
        if (isShowing()) {
            // System.out.println("##### HIDING isShowing");
            hide();
        }

        Bounds sl = parentNode.localToScreen(parentNode.getBoundsInLocal());
        setX(sl.getMaxX());
        setY(sl.getMinY());
        
        if (!isShowing()) {
            show();
            parentNode.requestFocus();
        }
        new Timeline(new KeyFrame(duration, e -> {
            // System.out.println("##### HIDING Timeline (" + getWidth() + ", " +
            // getHeight() + ")");
            hide();
        })).play();
    }

    // -----------------------------------------------------
    // -----------------------------------------------------
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
