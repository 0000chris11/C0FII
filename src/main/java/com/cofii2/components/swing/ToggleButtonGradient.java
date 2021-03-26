/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.methods.MGraphics;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JToggleButton;

/**
 *
 * @author C0FII
 */
public class ToggleButtonGradient extends JToggleButton {

      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private Color[] colorGradient;
      private Color[] bgColorDisable = new Color[]{Color.GRAY.darker(), Color.WHITE.darker()};
      private Color fgColor;
      private String text;

      @Override
      public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();
            if (isEnabled()) {
                  if (isSelected()) {
                        g2.setPaint(new GradientPaint(0, 0,
                                colorGradient[0], w, h, colorGradient[1]));
                  } else {
                        g2.setPaint(new GradientPaint(0, 0,
                                colorGradient[0].darker(), w, h, Color.GRAY));
                  }
            } else {
                  g2.setPaint(new GradientPaint(0, 0,
                          bgColorDisable[0], w, h, bgColorDisable[1]));
            }
            g2.fillRect(0, 0, w, h);
            if (text != null) {
                  if (fgColor == null) {
                        MGraphics.drawCenteredText(g2, text, Color.BLUE.darker(), w, h);
                  }else{
                        MGraphics.drawCenteredText(g2, text, fgColor, w, h);
                  }
            }

            super.paintComponents(g);
      }

      public ToggleButtonGradient(Color[] colorGradient) {
            this.colorGradient = colorGradient;
      }

      public ToggleButtonGradient(Color[] colorGradient, String text) {
            this.colorGradient = colorGradient;
            this.text = text;
      }

      public ToggleButtonGradient(Color[] colorGradient, Color fgColor, String text) {
            this.colorGradient = colorGradient;
            this.fgColor = fgColor;
            this.text = text;
      }
}
