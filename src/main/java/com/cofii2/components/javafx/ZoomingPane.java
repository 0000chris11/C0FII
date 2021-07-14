package com.cofii2.components.javafx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 * @author RupprechJo
 */
public class ZoomingPane extends Pane {

    Node content;
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);
    private Scale scale = new Scale(1, 1);

    private void zoomFactorChanged(Number newValue){
        scale.setX(newValue.doubleValue());
                scale.setY(newValue.doubleValue());
                requestLayout();
    }

    public ZoomingPane(Node content) {
        this.content = content;
        getChildren().add(content);
        content.getTransforms().add(scale);

        zoomFactor.addListener((obs, oldValue, newValue) -> zoomFactorChanged(newValue));
    }

    @Override
    protected void layoutChildren() {
        Pos pos = Pos.TOP_LEFT;
        double width = getWidth();
        double height = getHeight();
        double top = getInsets().getTop();
        double right = getInsets().getRight();
        double left = getInsets().getLeft();
        double bottom = getInsets().getBottom();
        double contentWidth = (width - left - right)/zoomFactor.get();
        double contentHeight = (height - top - bottom)/zoomFactor.get();
        layoutInArea(content, left, top,
                contentWidth, contentHeight,
                0, null,
                pos.getHpos(),
                pos.getVpos());
    }

    public final Double getZoomFactor() {
        return zoomFactor.get();
    }
    public final void setZoomFactor(Double zoomFactor) {
        this.zoomFactor.set(zoomFactor);
    }
    public final DoubleProperty zoomFactorProperty() {
        return zoomFactor;
    }
}
