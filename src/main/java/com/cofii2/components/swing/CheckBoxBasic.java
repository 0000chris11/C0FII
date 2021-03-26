/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JCheckBox;

/**
 *
 * @author C0FII
 */
public class CheckBoxBasic extends JCheckBox{
      /**
       *
       */
      private static final long serialVersionUID = 1L;
      private Color color = new Color(255, 0, 0, 20);
      
      @Override
      public void paintComponent(Graphics g) {

            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
      }

      public CheckBoxBasic() {
            setOpaque(false);
            setBackground(color);
      }
      public CheckBoxBasic(String text){
            super(text);
            setOpaque(false);
            setBackground(color);
      }
      public CheckBoxBasic(String text, Color color){
            super(text);
            setOpaque(false);
            this.color = color;
            setBackground(color);
      }
}
