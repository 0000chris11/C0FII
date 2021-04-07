package com.cofii2.components.javafx;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class Sheet extends Group {

    private int lineSpacing = 12;
    private int lineWidth = 100;
    private Color stroke = Color.BLACK;

    private Line[] lines = new Line[5];

    // ----------------------------------------------
    public void addNote(NoteType noteType) {
        double positionY = noteType.getPositionY();
        
        double centerY = lineSpacing * positionY;
        double centerX = (double) lineWidth / 2;
        double radiusY = (double) lineSpacing / 2;
        double radiusX = radiusY + (radiusY / 5);

        Ellipse ellipse = new Ellipse(centerX, centerY, radiusX, radiusY);

        if (noteType.getNoteType() == NoteType.WHOLE_NOTE) {
            ellipse.setFill(null);
            ellipse.setStroke(stroke);
        }

        getChildren().add(ellipse);
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
