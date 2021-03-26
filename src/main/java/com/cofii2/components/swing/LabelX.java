/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.myInterfaces.Paint;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author C0FII
 */
public class LabelX extends JLabel{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      private transient Paint p = null;
      
      public LabelX(){
            
      }
      public LabelX(Paint p){
            this.p = p;
      }
      
      @Override
      protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            if(p != null){
                  p.paintBefore(g, this);
            }
            
            g.setColor(Color.RED);
            int w = getWidth();
            int h = getHeight();
            g.drawLine(10, h / 2, w - 10, h / 2);
            
            if(p != null){
                  p.paintAfter(g, this);
            }
      }

}
