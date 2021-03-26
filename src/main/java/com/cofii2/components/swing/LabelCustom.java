/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author C0FII
 */
public class LabelCustom extends JLabel{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private Font textFont = new Font("Dialog", Font.BOLD, 16);
      private Color fgColor = Color.WHITE;
      
       public LabelCustom(){
            defaultConfig();
      }
      public LabelCustom(String text){
            super(text);
            defaultConfig();
      }
      public LabelCustom(int constants){
            super("", constants);
      }
      public LabelCustom(String text, int constants){
            super(text, constants);
            defaultConfig();
      }
      
      private void defaultConfig(){
            setForeground(fgColor);
            setFont(textFont);
      }
}
