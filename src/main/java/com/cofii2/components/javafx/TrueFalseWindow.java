package com.cofii2.components.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrueFalseWindow extends Stage{
    
    private Label lbMessage = new Label("Yes or No?");
    private Button btnFalse = new Button("No");
    private Button btnTrue = new Button("Yes");
    //---------------------------------------------------------
    private void init(){
        HBox hbox = new HBox(btnFalse, btnTrue);
        hbox.setPadding(new Insets(8, 8, 8, 8));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(4);

        VBox vbox = new VBox(lbMessage, hbox);
        VBox.setMargin(lbMessage, new Insets(8, 8, 8, 8));
        
        setScene(new Scene(vbox));
    }
    public TrueFalseWindow(String message){
        lbMessage.setText(message);
        init();
    }
    
    public TrueFalseWindow(String message, EventHandler<ActionEvent> btnFalseAction, EventHandler<ActionEvent> btnTrueAction){
        lbMessage.setText(message);
        btnFalse.setOnAction(btnFalseAction);
        btnTrue.setOnAction(btnTrueAction);
        init();
    }
    //---------------------------------------------------------
    public Label getLbMessage() {
        return lbMessage;
    }
    public void setLbMessage(Label lbMessage) {
        this.lbMessage = lbMessage;
    }
    public Button getBtnFalse() {
        return btnFalse;
    }
    public void setBtnFalse(Button btnFalse) {
        this.btnFalse = btnFalse;
    }
    public Button getBtnTrue() {
        return btnTrue;
    }
    public void setBtnTrue(Button btnTrue) {
        this.btnTrue = btnTrue;
    }
}
