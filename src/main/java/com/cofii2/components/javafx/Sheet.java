package com.cofii2.components.javafx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import java.awt.image.BufferedImage;

public class Sheet extends Group {

    private int lineSpacing = 12;
    private int lineWidth = 100;
    private Color stroke = Color.BLACK;

    private Line[] lines = new Line[5];
    private NoteType noteType;

    // ----------------------------------------------
    public void addNote(NoteType noteType) {
        this.noteType = noteType;

        double positionY = noteType.getPositionY();

        double centerY = lineSpacing * positionY;

        double centerX = (double) lineWidth / 2;
        double radiusY = (double) lineSpacing / 2;
        double radiusX = radiusY + (radiusY / 5);

        // ADDING NOTE
        Ellipse ellipse = new Ellipse(centerX, centerY, radiusX, radiusY);
        if (noteType.getNoteType() == NoteType.WHOLE_NOTE) {
            ellipse.setFill(null);
            ellipse.setStroke(stroke);
        }
        if (noteType.getSemitoneType() != null) {
            ImageView imageView = null;
            if (noteType.getSemitoneType().equals("#")) {
                // BufferedImage BufferedImage = ImageIO.read();
                // InputStream inputStream =
                double width = radiusX * 2;
                double height = radiusY * 2;
                double x = (centerX + radiusX + lineSpacing) - width;
                double y = centerY - radiusY;
                
                Image image = new Image(getClass().getResource("/resources/sharp.png").toString(), width, height, true,true);
                imageView = new ImageView(image);
                
                ellipse.setCenterX(ellipse.getCenterX() - width);
                imageView.setX(x);
                imageView.setY(y);
            }

            getChildren().addAll(ellipse, imageView);
        }else{
            getChildren().add(ellipse);
        }

        
    }

    public String getNote() {
        return noteType.getNoteName();
    }

    // ----------------------------------------------
    public Sheet() {
        // ADDING LINES
        int y = 0;
        for (int a = 0; a < 5; a++) {
            lines[a] = new Line(0, y, lineWidth, y);
            lines[a].setStroke(stroke);

            y += lineSpacing;
        }

        getChildren().addAll(lines);
    }

    // ----------------------------------------------
    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Color getStroke() {
        return stroke;
    }

    public void setStroke(Color stroke) {
        this.stroke = stroke;
    }
}
