/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.custom.LKCustom2;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 *
 * @author C0FII
 */
public class TextFieldDCellEditor extends JTextField{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      public TextFieldDCellEditor(){
            Font f = new Font("Dialog", Font.PLAIN, 20);

            setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
            setBorder(LKCustom2.BR_LINEMARIGIN_FOCUS_OFF);
            setFont(new Font(f.getName(), f.getStyle(), f.getSize()));
            setMargin(new Insets(1, 2, 1, 2));
      }
}
