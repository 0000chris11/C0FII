/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Class dealing with pre-set methods for cell on
 * JTables or JTrees
 * 
 * @author C0FII
 */
public class MCell {
      
      private MCell() {
            throw new IllegalStateException("Private Constructor");
      }
      //+++++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Method use to detect a checkBox node
       * and a default node diference
       * 
       * @param anEvent how the anEvent ocurred
       * @param tree JTree display
       * @return isCellEditable (true or false)
       */
      public static boolean isCellEditableForCheckBoxAndDefaults(EventObject anEvent, 
              JTree tree) {
            boolean returnValue = false;

            if (anEvent instanceof MouseEvent) {
                  MouseEvent mouseEvent = (MouseEvent) anEvent;
                  TreePath path = tree.getPathForLocation(mouseEvent.getX(),
                          mouseEvent.getY());
                  if (path != null) {
                        Object ob = path.getLastPathComponent();
                        if (ob instanceof DefaultMutableTreeNode) {
                              DefaultMutableTreeNode editedNode = (DefaultMutableTreeNode) ob;
                              Object userObject = editedNode.getUserObject();
                              if (userObject instanceof JCheckBox) {
                                    Rectangle r = tree.getPathBounds(path);
                                    int x = mouseEvent.getX() - r.x;

                                    JCheckBox checkbox = (JCheckBox) userObject;

                                    String text = checkbox.getText();
                                    checkbox.setText("");

                                    returnValue
                                            = userObject instanceof JCheckBox
                                            && x > 0
                                            && x < checkbox.getPreferredSize().width;

                                    checkbox.setText(text);
                              } else {
                                    returnValue = false;

                              }
                        }
                  }
            }
            System.out.println("isCellEditableForCheckBoxAndDefaults: " + returnValue);
            return returnValue;
      }
      /**
       * Method use to only detect input that produce
       * characters and default way to acces a cell
       * (Example: by double clicking on it)
       * 
       * @param anEvent how the anEvent ocurred
       * @param isCellEditable default value (super.isCelEditable)
       * @return isCellEditable (true or false)
       */
      public static boolean getCharacterInputandDefault(
              EventObject anEvent, boolean isCellEditable) {
            boolean returnValue;
            if (anEvent instanceof KeyEvent) {
                  KeyEvent e = (KeyEvent) anEvent;
                  int kc = e.getKeyCode();
                  if (kc == 0 || kc == KeyEvent.VK_WINDOWS) {
                        returnValue = false;
                  } else {
                        returnValue = true;
                  }

            } else {
                  returnValue = isCellEditable;
            }
            return returnValue;
      }
}
