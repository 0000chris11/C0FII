package com.cofii2.components.javafx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

public class SceneZoom {

    private Scene scene;

    private ScrollPane scGP;
    private ScrollBar hBar;
    private ScrollBar vBar;

    private double rightPadding = 0;
    private double bottomPadding = 0;

    private DoubleProperty doubleProperty;
    private Region parent;

    // ------------------------------------------
    private void sceneKeyListener(KeyEvent e) {
        if (e.isControlDown()) {
            double newValue = doubleProperty.getValue();
            if (e.getCode() == KeyCode.PLUS) {
                newValue += 0.01;
            } else if (e.getCode() == KeyCode.MINUS && newValue > 1.0) {
                newValue -= 0.01;
            }
            doubleProperty.setValue(newValue);

        }
    }

    private void hbarVisiblePropertyChange(ObservableValue<? extends Boolean> observable, boolean oldValue,
            boolean newValue) {
        if (newValue) {
            double height = scene.getWindow().getHeight() + hBar.getHeight();
            if (parent.getHeight() >= height) {
                System.out.println("HEIGHT CHANGE");
                // scene.getWindow().setHeight(height);
                bottomPadding = hBar.getHeight();
            } else {
                bottomPadding = 0;
                //parent.setPadding(Insets.EMPTY);
            }
        }else{
            bottomPadding = 0;
        }

        parent.setPadding(new Insets(0, rightPadding, bottomPadding, 0));

    }

    private void vbarVisiblePropertyChange(ObservableValue<? extends Boolean> observable, boolean oldValue,
            boolean newValue) {
        if (newValue) {
            double width = scene.getWindow().getWidth() + vBar.getWidth();
            if (parent.getWidth() < width) {
                System.out.println("HORIZONTAL CHANGE");
                // scene.getWindow().setWidth(scene.getWindow().getWidth() + vBar.getWidth() +
                // 4);
                rightPadding = vBar.getWidth();
            }else{
                rightPadding = 0;
            }

        }else{
            rightPadding = 0;
        }

        parent.setPadding(new Insets(0, rightPadding, bottomPadding, 0));
    }

    private void scrollPaneSkinPropertyChange(ObservableValue<? extends Skin<?>> observable, Skin<?> oldValue,
            Skin<?> newValue) {

        scGP.skinProperty().removeListener(this::scrollPaneSkinPropertyChange);

        for (Node n : scGP.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.HORIZONTAL)) {
                    hBar = bar;
                } else if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    vBar = bar;
                }
            }
        }

        hBar.visibleProperty().addListener(this::hbarVisiblePropertyChange);
        vBar.visibleProperty().addListener(this::vbarVisiblePropertyChange);
    }

    // ------------------------------------------
    public SceneZoom(Node content, DoubleProperty doubleProperty) {
        this.doubleProperty = doubleProperty;
        ZoomingPane zp = new ZoomingPane(content);
        zp.zoomFactorProperty().bind(doubleProperty);
        Group gp = new Group(zp);

        scGP = new ScrollPane(gp);

        if (scGP.getSkin() == null) {
            scGP.skinProperty().addListener(this::scrollPaneSkinPropertyChange);
        }
        // System.out.println(scGP.getSkin() != null ? "SCROLLPANE SKIN" : "SCROLLPANE
        // NOT SKIN");

        scene = new Scene(scGP);
    }

    // ------------------------------------------
    public void setParent(Region parent) {
        parent.prefHeightProperty().bind(scene.heightProperty().subtract(2.0));
        parent.prefWidthProperty().bind(scene.widthProperty().subtract(2.0));
        scene.setOnKeyReleased(this::sceneKeyListener);

        this.parent = parent;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
