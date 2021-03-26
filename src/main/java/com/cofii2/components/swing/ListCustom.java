/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.custom.LKCustom2;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;

/**
 *
 * @author C0FII
 */
public class ListCustom extends JList<String>{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private Font textFont = LKCustom2.FONT_NORMAL;
      private Color bgColor = LKCustom2.BK_NORMAL;
      private Color fgColor = Color.WHITE;

      public ListCustom() {
            setBackground(bgColor);
            setForeground(fgColor);
            setFont(textFont);
            
            setFixedCellWidth(240);
            setSelectionBackground(new Color(0,0,90));
            setSelectionForeground(Color.WHITE);
            
            setBorder(LKCustom2.BR_LINEMARIGIN_FOCUS_OFF);
      }
}
