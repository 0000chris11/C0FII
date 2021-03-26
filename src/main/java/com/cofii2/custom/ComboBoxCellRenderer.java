/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.custom;

import com.cofii2.stores.StringBoolean;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author C0FII
 */
public class ComboBoxCellRenderer extends BasicComboBoxRenderer {

      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private transient ArrayList<StringBoolean> listSB = new ArrayList<>();


      public static final Color fgColor = Color.WHITE;
      public static final Color foregroundSelected = Color.WHITE;
      public static final Color foregroundDisable = Color.GRAY;
      public static final Color backgroundDisable = new Color(124, 6, 6);

      public ComboBoxCellRenderer(JComboBox<String> cb) {
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder());

            for (int a = 0; a < cb.getItemCount(); a++) {
                  listSB.add(new StringBoolean(cb.getModel().getElementAt(a), true));
            }
      }

      public List<StringBoolean> getDisablesItems() {
            return listSB;
      }

      public void set(String value, boolean bool) {
            for (StringBoolean x : listSB) {
                  if (x.string.equals(value)) {
                        x.setBoolean(bool);
                  }
            }
      }

      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String text = value.toString();
            c.setText(text);
            for (StringBoolean x : listSB) {
                  if (text.equals(x.string) && !x.bool) {
                        if (isSelected) {
                              c.setBackground(backgroundDisable);
                        } else {
                              c.setBackground(LKCustom2.BK_NORMAL);
                        }
                        c.setForeground(foregroundDisable);
                        break;
                  } else {
                        if(isSelected){
                              c.setBackground(LKCustom2.BK_DIST2);
                        }else{
                              c.setBackground(LKCustom2.BK_NORMAL);
                        }
                        c.setForeground(fgColor);

                  }
            }
            return c;
      }

}
