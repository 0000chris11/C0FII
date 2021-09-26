package com.cofii2.components.javafx.popup;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableMap;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private static final Color FILL_DEFAULT = Color.WHITE;
    private static final Color FILL_TRUE = Color.GREEN;
    private static final Color FILL_FALSE = Color.RED;

    private Node parentNode;
    private TextFlow textFlow = new TextFlow();
    private ObservableMap<String, Boolean> map = FXCollections.observableHashMap();
    private HBox headerBar;

    // -------------------------------------
    /**
     * Shows popup only if the parentNode is not null
     */
    public void showPopup() {
        if (parentNode != null) {
            if (parentNode.getScene().getWindow().isShowing()) {
                Bounds sb = parentNode.localToScreen(parentNode.getBoundsInLocal());
                show(parentNode, sb.getMinX(), sb.getMinY());
            }
        }
    }

    // ------------------------------------
    private void elementsListener(Change<? extends String, ? extends Boolean> change) {
        String key = change.getKey();
        boolean valueAdded = change.getValueAdded();

        if (!key.equals(concat)) {
            if (!change.wasRemoved() && change.wasAdded()) {// ADDING-------------------------------
                if (((Text) textFlow.getChildren().get(0)).getText().equals(noElementsWord)) {
                    textFlow.getChildren().clear();
                }

                Text textKey;
                if (map.isEmpty()) {
                    textKey = new Text(key + concat);
                } else {
                    textKey = new Text("\n" + key + concat);
                }
                textKey.setFill(FILL_DEFAULT);

                Text textV = new Text(valueAdded ? valueTrue : valueFalse);
                textV.setFill(valueAdded ? FILL_TRUE : FILL_FALSE);
                textFlow.getChildren().addAll(textKey, textV);

            } else if (change.wasRemoved() && change.wasAdded()) {// UPDATING-------------------------------
                // IDK HOW TO GET THE REMOVED KEY
                Text textValue = new Text((valueAdded ? valueTrue : valueFalse) + "\n");
                textValue.setFill(valueAdded ? FILL_TRUE : FILL_FALSE);
                // ---------------------------
                Text textK = (Text) textFlow.getChildren().stream().filter(e -> {
                    String element = ((Text) e).getText().replace(concat, "");
                    element = element.replaceAll("[\\n\\t]", "");

                    if (element.equals(key)) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst().orElse(null);
                // ---------------------------
                if (textK != null) {
                    int index = textFlow.getChildren().indexOf(textK) + 1;
                    textFlow.getChildren().set(index, textValue);

                }
                
                Text lasText = (Text) textFlow.getChildren().get(textFlow.getChildren().size() - 1);
                lasText.setText(lasText.getText().replace("\n", ""));
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
            map.forEach((s, b) -> {
                Text textKey = new Text(s + ": ");
                textKey.setFill(Color.WHITE);

                Text textValue = new Text((b.booleanValue() ? valueTrue : valueFalse) + "\n");
                textValue.setFill(b.booleanValue() ? Color.GREEN : Color.RED);

                textFlow.getChildren().addAll(textKey, textValue);
                });
                Text lasText = (Text) textFlow.getChildren().get(textFlow.getChildren().size() - 1);
                lasText.setText(lasText.getText().replace("\n", ""));
        }
        map.addListener(this::elementsListener);
        // TOP-------------------------------
        Region region = new Region();
        Button btnX = new Button("x");
        btnX.setOnAction(e -> hide());
        btnX.setFont(Font.font(6));
        btnX.setMaxWidth(8);
        btnX.setMaxHeight(8);
        // region.prefHeightProperty().bind(btnX.prefHeightProperty());
        // region.prefWidthProperty().bind(btnX.prefWidthProperty());
        headerBar = new HBox(region, btnX);
        headerBar.setOnMouseDragged(e -> {
            PopupKV.this.setX(e.getScreenX());
            PopupKV.this.setY(e.getScreenY());
        });
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox.setHgrow(headerBar, Priority.NEVER);

        getContent().add(new VBox(headerBar, textFlow));
    }

    // ------------------------------------
    public PopupKV() {
        init();
    }

    public PopupKV(Node parentNode) {
        this.parentNode = parentNode;
        init();
    }

    public PopupKV(ObservableMap<String, Boolean> map) {
        this.map = map;
        init();
    }

    public PopupKV(Node parentNode, ObservableMap<String, Boolean> map) {
        this.parentNode = parentNode;
        this.map = map;
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

    public HBox getHeaderBar() {
        return headerBar;
    }

    public void setHeaderBar(HBox headerBar) {
        this.headerBar = headerBar;
    }

}
