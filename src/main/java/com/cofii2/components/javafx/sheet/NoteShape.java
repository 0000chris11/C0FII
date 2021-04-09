package com.cofii2.components.javafx.sheet;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

public class NoteShape <T extends Shape> {
    
    private T shape;
    private ImageView imageView;

    public NoteShape(T object, ImageView imageView){
        this.shape = object;
        this.imageView = imageView;
    }

    public T getObject() {
        return shape;
    }

    public void setObject(T object) {
        this.shape = object;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    
}
