package com.cofii2.components.javafx.drag;

import javafx.scene.Node;

public class Draggable {

    private double sceneXOriginal;
    private double sceneYOriginal;

    private double nodeXPressed;
    private double nodeYPressed;

    private double sceneX;
    private double sceneY;

    private DragAction dragAction;
    private boolean isInBackComponent;

    public Draggable(Node node, Node targetNode) {
        node.setPickOnBounds(false);
        sceneXOriginal = node.getBoundsInParent().getMinX();
        sceneYOriginal = node.getBoundsInParent().getMinY();
        // LISTENERS ==================================================
        node.setOnMousePressed(e -> {
            // MOVE LOGIC ----------------------------------------
            nodeXPressed = e.getX();
            nodeYPressed = e.getY();

            sceneX = node.getBoundsInParent().getMinX();
            sceneY = node.getBoundsInParent().getMinY();

        });

        node.setOnMouseDragged(e -> {
            // MOVE LOGIC ---------------------------------------
            sceneX = node.getBoundsInParent().getMinX();
            sceneY = node.getBoundsInParent().getMinY();

            double nodeX = e.getX();
            double nodeY = e.getY();

            node.setLayoutX((sceneX + nodeX) - nodeXPressed);
            node.setLayoutY((sceneY + nodeY) - nodeYPressed);

            isInBackComponent = targetNode.contains(targetNode.sceneToLocal(e.getSceneX(), e.getSceneY()));

            if (dragAction != null) {
                if (isInBackComponent) {
                    dragAction.dragAction(DragEnum.OVER_DROP_TARGET);
                } else {
                    dragAction.dragAction(DragEnum.OUTSIDE_OF_DROP_TARGET);
                }
            }
        });

        node.setOnMouseReleased(e -> {
            if (isInBackComponent) {
                if (dragAction != null) {
                    dragAction.dragAction(DragEnum.DROP_ON_TARGET_SUCCESS);
                }
            } else {
                node.setLayoutX(sceneXOriginal);
                node.setLayoutY(sceneYOriginal);
            }
        });
        /*
         * node.setOnDragDetected(e -> { // Dragboard db =
         * node.startDragAndDrop(TransferMode.ANY); node.startFullDrag(); //
         * ClipboardContent content = new ClipboardContent(); //
         * content.putString("Hello!"); // db.setContent(content); /* Dragboard db =
         * node.startDragAndDrop(TransferMode.ANY); ClipboardContent content = new
         * ClipboardContent(); content.putString("Hello!"); db.setContent(content);
         * 
         * // -----------------------------------------------------
         * 
         * });
         */
        /*
         * if (targetNode != null) { targetNode.setOnMouseDragEntered(e -> { ((Shape)
         * targetNode).setFill(Color.BLUE.darker()); });
         * targetNode.setOnMouseDragExited(e -> { ((Shape)
         * targetNode).setFill(Color.rgb(0, 0, 0, 0.4)); }); }
         */
    }
    // GETTERS & SETTERS ================================================
    public DragAction getDragAction() {
        return dragAction;
    }

    public void setDragAction(DragAction dragAction) {
        this.dragAction = dragAction;
    }

}
