/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.myAbsClasses.ATextAction;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.security.InvalidParameterException;
import javax.swing.JTextField;

/**
 *
 * @author C0FII
 */
public class MPlaceH implements FocusListener{

      private String text;
      private ATextAction at;

      private Color matchFColor = Color.GRAY;
      private Color unmatchFColor = Color.WHITE;

      public MPlaceH(String text, ATextAction at) {
            this.text = text;
            this.at = at;
      }

      public MPlaceH(String text) {
            this.text = text;
            this.at = null;
      }
      //+++++++++++++++++++++++++++++++++++++++++++++++
      @Override
      public void focusGained(FocusEvent e) {
            if (e.getComponent() instanceof JTextField) {
                  JTextField tf = (JTextField) e.getComponent();
                  if (tf.getText().equalsIgnoreCase(text)) {
                        tf.setText("");
                        tf.setForeground(unmatchFColor);
                        if (at != null) {
                              at.unmatchTextAction();
                        }
                  }
            } else {
                  throw new InvalidParameterException("Only JTextField allowed");
            }
      }

      @Override
      public void focusLost(FocusEvent e) {
            if (e.getComponent() instanceof JTextField) {
                  JTextField tf = (JTextField) e.getComponent();
                  if (tf.getText().isEmpty()) {
                        tf.setText(text);
                        tf.setForeground(matchFColor);
                        if (at != null) {
                              at.matchTextAction();
                        }
                  }
            } else {
                  throw new InvalidParameterException("Only JTextField allowed");
            }
      }
      //+++++++++++++++++++++++++++++++++++++++++++++++
      public void setMatchForegroundColor(Color c) {
            matchFColor = c;
      }

      public Color getMatchForegroundColor() {
            return matchFColor;
      }

      public void setUnmatchForegroundColor(Color c) {
            unmatchFColor = c;
      }

      public Color getUnmatchForegroundColor() {
            return unmatchFColor;
      }
      
}
