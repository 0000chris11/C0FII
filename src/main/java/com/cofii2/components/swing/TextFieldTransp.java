/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JTextField;

/**
 *
 * @author C0FII
 */
public class TextFieldTransp extends JTextField{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void paintComponent(Graphics g){
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
      }
      
      public TextFieldTransp(){
            setOpaque(false);
            setBackground(new Color(51, 51, 51, 40));
            Font f = new Font("Dialog", Font.BOLD, 16);
            setFont(f);
            setForeground(Color.WHITE);
      }
}
