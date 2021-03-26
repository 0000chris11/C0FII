/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.custom;

import com.cofii2.components.swing.LabelX;
import com.cofii2.myInterfaces.Paint;
import com.cofii2.stores.STColor;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 1st Custom CellRenderer. Black background, 1st column gray
 *
 * @author C0FII
 */
public class TableCellRenderer extends DefaultTableCellRenderer {

      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private transient ArrayList<STColor> listSTC = new ArrayList<>();
      private Color defaultForeground = Color.WHITE;

      /**
       * Set the Foreground of each cell
       *
       * @param color foreground color
       */
      public void setDefaultForeground(Color color) {
            defaultForeground = color;
      }
      
      public Color getDefaultForeground(){
            return defaultForeground;
      }

      public void addWordChoice(STColor st) {
            listSTC.add(st);
      }

      private DefaultTableCellRenderer getDefault(Object value, Component c, boolean isSelected, int column) {
            setBorder(new EmptyBorder(1, 4, 1, 4));
            c.setFont(new Font("Dialog", Font.PLAIN, 20));

            if (!listSTC.isEmpty()) {
                  for (STColor x : listSTC) {
                        if (x.string.equals(value.toString())) {
                              c.setForeground(x.color);
                        } else {
                              c.setForeground(defaultForeground);
                        }
                  }
            } else {
                  c.setForeground(defaultForeground);
            }

            if (!isSelected) {
                  if (column == 0) {
                        c.setBackground(LKCustom2.BK_NORMAL);
                  } else if (column == 1) {
                        c.setBackground(Color.BLACK);
                  }
            }

            return this;
      }

      //++++++++++++++++++++++++++++++++++++++++++++
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            JComponent returnValue = null;
            if (value instanceof JComboBox) {
                  returnValue = (JComboBox<?>) value;
            } else if (value instanceof String) {
                  returnValue = getDefault(value, c, isSelected, column);
            } else if (value instanceof JLabel) {
                  if (isSelected) {
                        returnValue = new LabelX(new Paint() {
                              @Override
                              public void paintBefore(Graphics g, Object ob) {
                                    g.setColor(LKCustom2.BK_DIST2);
                                    JLabel lb = (JLabel) ob;
                                    g.fillRect(0, 0, lb.getWidth(), lb.getHeight());

                              }

                              @Override
                              public void paintAfter(Graphics g, Object ob) {
                                    throw new UnsupportedOperationException();
                              }
                        });
                  } else {
                        returnValue = (LabelX) value;
                  }

            }

            return returnValue;
      }
}
