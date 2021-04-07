package com.cofii2.components.javafx.piano;

public class Note {

    public static final String[] NOTE_SCALE = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
    public static final String[] WHITE_NOTE_SCALE = { "A", "B", "C", "D", "E", "F", "G"};
    
    public static final String[] BLACK_NOTE_SCALE = { "A#", "C#", "D#", "F#", "G#" };

    int index;
    String name;
    int number;

    public Note(int index, String name, int number) {
        this.index = index;
        this.name = name;
        this.number = number;
    }

}
