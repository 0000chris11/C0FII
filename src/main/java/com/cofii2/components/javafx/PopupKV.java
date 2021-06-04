package com.cofii2.components.javafx;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.collections.MapChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;

/**
 * Extension of Popup. Class use for listing values (String, boolean) in a popup
 * 
 * @author C0FII
 */
public class PopupKV extends Popup {

    private String valueFalse = "errors";
    private String valueTrue = "correct";
    private String concat = ": ";
    private String noElementsWord = "No elements";

    private Color FILL_DEFAULT = Color.WHITE;
    private Color FILL_TRUE = Color.GREEN;
    private Color FILL_FALSE = Color.RED;

    private Node parentNode;
    private TextFlow textFlow = new TextFlow();
    private ObservableMap<String, Boolean> map = FXCollections.observableHashMap();

    // ------------------------------------
    private void elementsListener(Change<? extends String, ? extends Boolean> change) {
        String key = change.getKey();
        boolean valueAdded = change.getValueAdded();

        if (!key.contains(concat)) {
            if (!change.wasRemoved() && change.wasAdded()) {//ADDING-------------------------------
                if (((Text) textFlow.getChildren().get(0)).getText().equals(noElementsWord)) {
                    textFlow.getChildren().clear();
                }

                Text textK;
                if (map.isEmpty()) {
                    textK = new Text(key + concat);
                } else {
                    textK = new Text("\n" + key + concat);
                }
                textK.setFill(FILL_DEFAULT);

                Text textV = new Text(valueAdded ? valueTrue : valueFalse);
                textV.setFill(valueAdded ? FILL_TRUE : FILL_FALSE);
                textFlow.getChildren().addAll(textK, textV);

            } else if (change.wasRemoved() && change.wasAdded()) {//UPDATING-------------------------------
                // IDK HOW TO GET THE REMOVED KEY
                String newValue = valueAdded ? valueTrue : valueFalse;
                Text textV = new Text(newValue);
                textV.setFill(valueAdded ? FILL_TRUE : FILL_FALSE);
                //---------------------------
                Text textK = (Text) textFlow.getChildren().stream().filter(e -> {
                    String element = ((Text) e).getText().replace(concat, "");
                    element = element.replaceAll("[\\n\\t]", "");

                    if (element.equals(key)) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);
                //---------------------------
                if (textK != null) {
                    int index = textFlow.getChildren().indexOf(textK) + 1;
                    textFlow.getChildren().set(index, textV);

                }
            }
        } else {
            throw new IllegalArgumentException("C0FII: the key can have the same value as the concat attribute");
        }

    }
    // ------------------------------------
    private void init() {
        if (map.isEmpty()) {
            Text tx = new Text(noElementsWord);
            tx.setFill(FILL_DEFAULT);
            textFlow.getChildren().add(tx);
        } else {
            map.forEach((s, b) -> textFlow.getChildren().addAll(new Text(s + ": "),
                    b.booleanValue() ? new Text(valueTrue) : new Text(valueFalse)));
        }
        map.addListener(this::elementsListener);
        // TOP-------------------------------
        Region region = new Region();
        Button btnX = new Button("x");
        btnX.setOnAction(e -> hide());
        btnX.setFont(Font.font(6));
        btnX.setPrefWidth(8);
        btnX.setPrefHeight(8);
        region.prefHeightProperty().bind(btnX.prefHeightProperty());
        region.prefWidthProperty().bind(btnX.prefWidthProperty());
        HBox hbox = new HBox(region, btnX);
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox.setHgrow(hbox, Priority.NEVER);

        getContent().add(new VBox(hbox, textFlow));
    }
    // ------------------------------------
    public PopupKV() {
        init();
    }

    public PopupKV(Node parentNode) {
        this.parentNode = parentNode;
        init();
    }

    public PopupKV(ObservableMap<String, Boolean> elements) {
        this.map = elements;
        init();
    }

    public PopupKV(Node parentNode, ObservableMap<String, Boolean> elements) {
        this.parentNode = parentNode;
        this.map = elements;
        init();
    }

    // ------------------------------------
    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public ObservableMap<String, Boolean> getElements() {
        return map;
    }

    public void setElements(ObservableMap<String, Boolean> elements) {
        this.map = elements;
    }

}
