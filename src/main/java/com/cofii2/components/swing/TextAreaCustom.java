/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.custom.LKCustom2;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;

/**
 *
 * @author C0FII
 */
public class TextAreaCustom extends JTextArea{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private Font textFont = LKCustom2.FONT_NORMAL;
      private Color bgColor = LKCustom2.BK_NORMAL;
      private Color fgColor = Color.WHITE;
      
      public TextAreaCustom() {
            config();
      }
      public TextAreaCustom(String text){
            config();
            setText(text);
      }
      
      private void config(){
            setBackground(bgColor);
            setForeground(fgColor);
            
            setBorder(LKCustom2.BR_LINEMARIGIN_FOCUS_OFF);
            //+++++++++++++++++++++++++++++++++++++++
            setFont(textFont);
            setEnabled(true);
            setFocusable(true);
            setEditable(true);
            setCaretColor(Color.WHITE);
      }
}
