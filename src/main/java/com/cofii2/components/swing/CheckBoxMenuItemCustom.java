/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JCheckBoxMenuItem;

/**
 *
 * @author C0FII
 */
public class CheckBoxMenuItemCustom extends JCheckBoxMenuItem{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;
      private Color bgColor = Color.BLACK;
      private Color fgColor = Color.WHITE;
      private Font textFont = new Font("Dialog", Font.PLAIN, 12);
      
      public CheckBoxMenuItemCustom(String text){
            super(text);
            setOpaque(true);
            setBackground(bgColor);
            setForeground(fgColor);
            setFont(textFont);
      }
      
       public void setBGColor(Color bgColor){
            this.bgColor = bgColor;
            setBackground(bgColor);
      }
      
      public void setFGColor(Color fgColor){
            this.fgColor = fgColor;
            setForeground(fgColor);
      }
      
      public void setCustomFont(Font font){
            this.textFont = font;
            setFont(font);
      }
}
