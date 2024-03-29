package com.cofii2.components.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LabelStatus extends HBox {

    // LB POSITION----------------------------------
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private int lbPos = LEFT;
    // ---------------------------------------------
    private String defaultText = "Waiting for action";
    private Color defaultColor = Color.WHITE;

    // private boolean autoClose = false;

    private Label lbStatus = new Label(defaultText);
    private Button btnCloseStatus = new Button("X");

    // ---------------------------------------------------
    public void setText(String text, Color color) {
        lbStatus.setText(text);
        lbStatus.setTextFill(color);
    }

    public void setText(String text, Color color, Duration duration) {
        lbStatus.setText(text);
        lbStatus.setTextFill(color);

        new Timeline(new KeyFrame(duration, e -> reset())).play();
    }

    // ---------------------------------------------------
    public void reset() {
        lbStatus.setText(defaultText != null ? defaultText : "");
        lbStatus.setTextFill(defaultColor);

        btnCloseStatus.setVisible(false);
    }

    // ---------------------------------------------------
    private void init() {
        btnCloseStatus.managedProperty().bind(btnCloseStatus.visibleProperty());

        Region region = new Region();
        setHgrow(lbStatus, Priority.ALWAYS);
        setHgrow(region, Priority.ALWAYS);
        setHgrow(btnCloseStatus, Priority.NEVER);

        btnCloseStatus.setFont(Font.font(6));

        // IF THE LABEL IS NOT THE DEFAULT COLOR -> btnCloseStatus IS SET TO VISIBLE
        lbStatus.textFillProperty().addListener(
                (obs, oldValue, newValue) -> btnCloseStatus.setVisible(!newValue.equals((Paint) defaultColor)));
        btnCloseStatus.setOnAction(e -> reset());

        if (lbPos == LEFT) {
            getChildren().addAll(lbStatus, region, btnCloseStatus);
        } else if(lbPos == RIGHT){
            getChildren().addAll(region, lbStatus, btnCloseStatus);
        }
    }

    // ---------------------------------------------------
    public LabelStatus() {
        init();
    }

    public LabelStatus(String defautlText) {
        this.defaultText = defautlText;
        init();
    }

    public LabelStatus(String defautlText, int lbPos) {
        this.defaultText = defautlText;
        this.lbPos = lbPos;
        init();
    }
    // ---------------------------------------------------

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Label getLbStatus() {
        return lbStatus;
    }

    public void setLbStatus(Label lbStatus) {
        this.lbStatus = lbStatus;
    }

    public Button getBtnCloseStatus() {
        return btnCloseStatus;
    }

    public void setBtnCloseStatus(Button btnCloseStatus) {
        this.btnCloseStatus = btnCloseStatus;
    }

    public int getLbPos() {
        return lbPos;
    }

    public void setLbPos(int lbPos) {
        this.lbPos = lbPos;
    }

}
