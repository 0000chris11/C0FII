package com.cofii2.components.javafx.piano;

/**
 * @Author C0FII
 */
public interface KeyAction{

    public void whiteKeyPressedAction(String wkpName);
    public void blackKeyPressedAction(String bkpName);

    public void whiteKeyReleasedAction(String wkrName);
    public void blackKeyReleasedAction(String bkrName);
}