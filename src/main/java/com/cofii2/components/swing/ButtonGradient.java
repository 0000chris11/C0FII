/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.custom.LKCustom2;
import com.cofii2.methods.MGraphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

/**
 *
 * @author C0FII
 */
public class ButtonGradient extends JButton{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;
      private String text;
      Font textFont = new Font("Dialog", Font.BOLD, 15);
      transient GradientPaint[] gradientP = new GradientPaint[3];
      private Color fgColor;
      private Color[] bgColorDisable = new Color[]{Color.GRAY.darker(), Color.WHITE.darker()};
      
      Color normal1 = LKCustom2.GP_WHITE_AND_CYAN[0];
      Color normal2 = LKCustom2.GP_WHITE_AND_CYAN[1];
      
      Color pressed1 = LKCustom2.GP_WHITE_AND_CYAN[0];
      Color pressed2 = LKCustom2.GP_WHITE_AND_CYAN[1].darker();
      
      Color disable1 = LKCustom2.GP_WHITE_AND_CYAN[0].darker();
      Color disable2 = LKCustom2.GP_WHITE_AND_CYAN[1].darker();

      @Override
      public void paintComponent(Graphics g) {
            int w = getWidth();
            int h = getHeight();

            Graphics2D g2 = (Graphics2D) g;
            if (getModel().isEnabled()) {
                  if (getModel().isPressed()) {
                        g2.setPaint(new GradientPaint(0, 0, pressed1, w, h, pressed2));
                  } else {
                        g2.setPaint(new GradientPaint(0, 0, normal1, w, h, normal2));
                  }
            } else {
                  g2.setPaint(new GradientPaint(0, 0, bgColorDisable[0], w, h, bgColorDisable[1]));
            }
            
            g2.fillRect(0, 0, w, h);
            //++++++++++++++++++++++++++++++
            if (text != null) {
                  if(fgColor != null){
                        MGraphics.drawCenteredTextWithOutline(g2, text, fgColor, w, h);
                  }else{
                        MGraphics.drawCenteredTextWithOutline(g2, text, w, h);
                  }
                  
            }
            
            super.paintComponents(g);
      }
      //+++++++++++++++++++++++++++++++++++++++++++++++
      public ButtonGradient(){
            this.text = null;
      }
      
      public ButtonGradient(String text) {
            this.text = text;
      }
      
      public ButtonGradient(Color[] colorGradient){
            this.normal1 = colorGradient[0];
            this.normal2 = colorGradient[1];
            
            this.pressed1 = colorGradient[0];
            this.pressed1 = colorGradient[1].darker();
      }
      
      public ButtonGradient(String text, Color[] colorGradient) {
            this.text = text;
            this.normal1 = colorGradient[0];
            this.normal2 = colorGradient[1];
            
            this.pressed1 = colorGradient[0];
            this.pressed1 = colorGradient[1].darker();
      }
      
      public ButtonGradient(String text, Color fgColor) {
            this.text = text;
            this.fgColor = fgColor;
      }
      
      public ButtonGradient(String text, Color[] colorGradient, Color fgColor) {
            this.text = text;
            this.fgColor = fgColor;
            
            this.normal1 = colorGradient[0];
            this.normal2 = colorGradient[1];
            
            this.pressed1 = colorGradient[0];
            this.pressed1 = colorGradient[1].darker();
      }
      //++++++++++++++++++++++++++++++++++++++++++++++++

      
}
