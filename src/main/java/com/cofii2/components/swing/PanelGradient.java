/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author C0FII
 */
public class PanelGradient extends JPanel{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private Color[] colors;
      private static boolean tdeleted = false;
      /**
       * Untested method; suppose to have delete-state where the graphics draw a X onn the panel
       * 
       * @param b draw a X or not
       */
      public static void setTDeleted(boolean b){
            tdeleted = b;
      }
      
      public PanelGradient(Color[] colors){
            this.colors = colors;
      }

      @Override
      public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(
                    0, 0, colors[0],
                    getWidth(), getHeight(),
                    colors[1]));
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            //TABLE DELETED STATE
            if(tdeleted){
                  g2.setStroke(new BasicStroke(3));
                  g2.setColor(Color.RED);
                  g2.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
            }
            super.paintComponents(g);
      }
}
