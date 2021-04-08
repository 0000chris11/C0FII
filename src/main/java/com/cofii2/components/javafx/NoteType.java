package com.cofii2.components.javafx;

import java.util.HashMap;
import java.util.Map;

public class NoteType {
    
    public static final int WHOLE_NOTE = 0;
    public static final int HALF_NOTE = 1;
    public static final int QUARTER_NOTE = 2;
    public static final int EIGHTH_NOTE = 3;

    private static final Map<Character, Integer> noteScale = new HashMap<>();

    public static final String[] NOTE_SCALE = {"C", "D", "E", "F", "G", "A", "B"};
    public static final double[] POSITION_4 = {5.0, 4.5, 4.0, 3.5, 3.0, 2.5, 2.0};//4
    public static final double[] POSITION_0 = {19.0, 18.5, 18.0, 17.5, 17.0, 16.5, 16.0};//0
    //---------------------------------------------------------
    private String noteName;
    private String semitoneType;
    private int noteType;
    private double positionY;
    //---------------------------------------------------------
    private void hashMapInit(){
        noteScale.put('C', 0);
        noteScale.put('D', 1);
        noteScale.put('E', 2);
        noteScale.put('F', 3);
        noteScale.put('G', 4);
        noteScale.put('A', 5);
        noteScale.put('B', 6);

        int positionIndex = noteScale.get(noteName.charAt(0));
        int scaleNumber = Character.getNumericValue(noteName.charAt(1));

        double position = POSITION_0[positionIndex];
        positionY = position - (3.5 * scaleNumber);
    }
    //---------------------------------------------------------
    /**
     * Construct a note to be display on Sheet music
     * 
     * @param noteName name of the note (A1 to C8)
     * @param noteType must be one of the NoteType PSF variable
     */
    public NoteType(String noteName, int noteType) {
        if(noteName.contains("#") || noteName.contains("b")){
            this.noteName = Character.toString(noteName.charAt(0)) + Character.toString(noteName.charAt(2));
            this.semitoneType = Character.toString(noteName.charAt(1));
        }else{
            this.noteName = noteName;
        }
        this.noteType = noteType;

        hashMapInit();
        /*
        A3 = 6.0
        B3 = 5.5
        C4 = 5.0
        D4 = 4.5
        E4 = 4.0
        F4 = 3.5
        G4 = 3.0
        A4 = 2.5
        B4 = 2.0
        C5 = 1.5
        D5 = 1.0
        E5 = 0.5
        F5 = 0.0
        G5 = -0.5
        A5 = -1.0
        */
    }
    //---------------------------------------------------------
    public String getNoteName() {
        return noteName;
    }
    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
    public int getNoteType() {
        return noteType;
    }
    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }
    public double getPositionY() {
        return positionY;
    }
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
    public String getSemitoneType() {
        return semitoneType;
    }
    public void setSemitoneType(String semitoneType) {
        this.semitoneType = semitoneType;
    }

    
}
