package com.cofii2.components.javafx;

import com.cofii2.stores.CC;

import javafx.scene.control.Tooltip;

public class TooltipCustom extends Tooltip{
    
    @Override
    protected void showPopup() {
        System.out.println(CC.CYAN + "TooltipCustom SHOW" + CC.RESET);
        hide();
        //super.show();
    }
}
