/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.custom.LKCustom2;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author C0FII
 */
public class ComboBoxE extends JComboBox<String>{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      public ComboBoxE(){
            properties();
      }
      public ComboBoxE(String[] data){
            for(int a = 0; a < data.length; a++){
                  addItem(data[a]);
            }
            properties();
      }
      
      private void properties(){
            setEditable(true);
            setFont(LKCustom2.FONT_NORMAL);
            JTextField tfe = (JTextField) getEditor().getEditorComponent();
            
            CompoundBorder cb = new CompoundBorder(
                    tfe.getBorder(), BorderFactory.createEmptyBorder(2, 4, 1, 4));
            
            tfe.setBackground(LKCustom2.BK_NORMAL);
            tfe.setFont(getFont());
            tfe.setForeground(Color.WHITE);
            tfe.setCaretColor(Color.WHITE);
            tfe.setCaretPosition(0);
            tfe.setBorder(cb);
            //+++++++++++++++++++++++++++++++++
            putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
      }
}
