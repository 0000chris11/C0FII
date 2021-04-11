package com.cofii2.components.javafx.sheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.awt.image.BufferedImage;

public class Sheet extends Group {

    private double lineSpacing = 12;
    private double notePadding = lineSpacing * 2;

    private int lineWidth = 100;
    private Color stroke = Color.BLACK;

    private Line[] lines = new Line[5];
    private NoteType noteType;

    private List<NoteType> noteList = new ArrayList<>();
    private List<NoteShape<Shape>> noteShapes = new ArrayList<>();
    // ----------------------------------------------
    public void addNote(NoteType noteType) {
        this.noteType = noteType;

        double positionY = noteType.getPositionY();

        double centerY = lineSpacing * positionY;
        double centerX = (double) lineWidth / 2;

        if (!noteList.isEmpty()) {
            //noteShapes.get(0).getObject()
        }

        double radiusY = lineSpacing / 2;
        double radiusX = radiusY + (radiusY / 5);

        // ADDING NOTE
        Ellipse ellipse = new Ellipse(centerX, centerY, radiusX, radiusY);
        if (noteType.getNoteType() == NoteType.WHOLE_NOTE) {
            ellipse.setFill(null);
            ellipse.setStroke(stroke);
        }
        if (noteType.getSemitoneType() != null) {
            ImageView imageView = null;
            Image image = null;

            double width = radiusX * 2;
            double height = radiusY * 2;
            height += height / 5;

            // SHARP, FLAT OR EXCEPTION
            if (noteType.getSemitoneType().equals("#")) {
                image = new Image(getClass().getResource("/resources/sharp.png").toString(), width, height, true, true);
            } else if (noteType.getSemitoneType().equalsIgnoreCase("b")) {
                image = new Image(getClass().getResource("/resources/flat.png").toString(), width, height, true, true);
            } else {
                throw new IllegalArgumentException("Wrong note name");
            }

            imageView = new ImageView(image);
            double x = ellipse.getCenterX() + radiusX + (radiusX / 5);
            double y = centerY - radiusY;

            imageView.setX(x);
            imageView.setY(y - (height / 5));

            getChildren().addAll(ellipse, imageView);
            noteList.add(noteType);
            noteShapes.add(new NoteShape<>(ellipse, imageView));
        } else {
            getChildren().add(ellipse);
            noteList.add(noteType);
            noteShapes.add(new NoteShape<>(ellipse, null));
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
    public double getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(double lineSpacing) {
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
