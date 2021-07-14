package com.cofii2.components.javafx.popup;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PopupMenu extends ContextMenu{
    
    private String[] elements;
    //---------------------------------
    public void addAction(int index, EventHandler<ActionEvent> actionEvent){
        getItems().get(index).setOnAction(actionEvent);
    }
    //---------------------------------
    private void init(){
        int[] indexs = {0};
        Arrays.asList(elements).forEach(e -> {
            MenuItem item = new MenuItem(e);
            item.setId(Integer.toString(indexs[0]++));
            getItems().add(item);
        });
    }
    //---------------------------------
    public PopupMenu(String... elements){
        this.elements = elements;
        init();
    }
}
