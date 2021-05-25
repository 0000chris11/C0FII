package com.cofii2.components.javafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import com.cofii2.methods.MList;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ToggleGroupD<T extends ToggleButton> {

    private boolean countVisible = false;

    private final List<T> toggleGroup = new ArrayList<>();

    // ----------------------------------------------
    private void groupAction(ActionEvent e) {
        T btn = (T) e.getSource();

        if (btn.isSelected()) {
            for (int b = 0; b < toggleGroup.size(); b++) {
                if (b != toggleGroup.indexOf(btn)) {
                    if (!countVisible) {
                        if (!toggleGroup.get(b).isVisible()) {
                            toggleGroup.get(b).setSelected(false);
                        }
                    }
                    if (toggleGroup.get(b).isSelected()) {
                        toggleGroup.get(b).setSelected(false);
                    }
                }
            }
        }

    }

    // ----------------------------------------------
    public ToggleGroupD(T... elements) {
        toggleGroup.addAll(Arrays.asList(elements));
        toggleGroup.forEach(e -> e.addEventHandler(ActionEvent.ACTION, this::groupAction));
    }
    // ----------------------------------------------

    public boolean getCountVisible() {
        return countVisible;
    }

    public void setCountVisible(boolean countVisible) {
        this.countVisible = countVisible;
    }

}
