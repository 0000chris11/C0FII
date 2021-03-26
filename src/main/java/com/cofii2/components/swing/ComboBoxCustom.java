/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.custom.LKCustom2;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author C0FII
 */
public class ComboBoxCustom extends JComboBox<String>{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;
      private Font textFont = new Font("Dialog", Font.BOLD, 16);
      
       public ComboBoxCustom(String[] model) {
            super(model);
            setBorder(new EmptyBorder(1, 3, 1, 3));
            setBackground(LKCustom2.BK_NORMAL);
            setForeground(Color.WHITE);
            setFont(textFont);
      }
      
      public ComboBoxCustom(){
            setBorder(new EmptyBorder(1, 3, 1, 3));
            setBackground(LKCustom2.BK_NORMAL);
            setForeground(Color.WHITE);
            setFont(textFont);
      }
}
