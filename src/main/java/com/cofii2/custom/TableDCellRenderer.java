/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.custom;

import com.cofii2.methods.MCell;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

/**
 *
 * @author C0FII
 */
public class TableDCellRenderer extends DefaultCellEditor {

      /**
       *
       */
      private static final long serialVersionUID = 1L;

      public TableDCellRenderer(JTextField textField) {
            super(textField);
      }

      //++++++++++++++++++++++++++++++++++++++++++
      @Override
      public boolean isCellEditable(EventObject anEvent) {
            return MCell.getCharacterInputandDefault(anEvent,
                    super.isCellEditable(anEvent));
      }

}
