/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

/**
 *
 * @author C0FII
 */
public class MGraphics {
      
      private MGraphics(){
            throw new IllegalStateException("Private Constructor");
      }
      
       public static void drawCenteredText(Graphics2D g2, String text, int w, int h){
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            
            FontMetrics FM = g2.getFontMetrics();
            
            Font F = new Font("Dialog", Font.BOLD, 15);
            FontRenderContext frc = g2.getFontRenderContext();
            TextLayout tl = new TextLayout(text, F, frc);
            Shape outline = tl.getOutline(null);
            
            int x = (w - (int) outline.getBounds().getWidth()) / 2;
            int y = (FM.getAscent() + (h - (FM.getAscent() + FM.getDescent())) / 2);
            //+++++++++++++++++++++++++++++++++++++++++++++++++++
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g2.setColor(new Color(204, 0, 0, 220));
            g2.setFont(F);
            g2.drawString(text, x, y);
      }
       
       public static void drawCenteredText(Graphics2D g2, String text, Color color, int w, int h){
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            
            FontMetrics FM = g2.getFontMetrics();
            
            Font F = new Font("Dialog", Font.BOLD, 15);
            FontRenderContext frc = g2.getFontRenderContext();
            TextLayout tl = new TextLayout(text, F, frc);
            Shape outline = tl.getOutline(null);
            
            int x = (w - (int) outline.getBounds().getWidth()) / 2;
            int y = (FM.getAscent() + (h - (FM.getAscent() + FM.getDescent())) / 2);
            //+++++++++++++++++++++++++++++++++++++++++++++++++++
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g2.setColor(color);
            g2.setFont(F);
            g2.drawString(text, x, y);
      }
      
      /**
       * UNTESTED. Draw a String with outline in the center of a Component
       * 
       * @param g2 Component's Graphics
       * @param text text to draw with outline
       * @param w Component's width
       * @param h Component's height
       */
      public static void drawCenteredTextWithOutline(Graphics2D g2, String text, int w, int h){
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            
            FontMetrics FM = g2.getFontMetrics();
            
            Font F = new Font("Dialog", Font.BOLD, 15);
            FontRenderContext frc = g2.getFontRenderContext();
            TextLayout tl = new TextLayout(text, F, frc);
            Shape outline = tl.getOutline(null);
            
            int x = (w - (int) outline.getBounds().getWidth()) / 2;
            int y = (FM.getAscent() + (h - (FM.getAscent() + FM.getDescent())) / 2);
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            g2.setColor(new Color(0, 0, 0, 255));
            g2.translate(x, y);
            g2.draw(outline);
            g2.translate(-x, -y);
            //+++++++++++++++++++++++++++++++++++++++++++++++++++
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g2.setColor(new Color(204, 0, 0, 220));
            g2.setFont(F);
            g2.drawString(text, x, y);
      }
      
      public static void drawCenteredTextWithOutline(Graphics2D g2, String text, Color color, int w, int h){
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            
            FontMetrics FM = g2.getFontMetrics();
            
            Font F = new Font("Dialog", Font.BOLD, 15);
            FontRenderContext frc = g2.getFontRenderContext();
            TextLayout tl = new TextLayout(text, F, frc);
            Shape outline = tl.getOutline(null);
            
            int x = (w - (int) outline.getBounds().getWidth()) / 2;
            int y = (FM.getAscent() + (h - (FM.getAscent() + FM.getDescent())) / 2);
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            g2.setColor(new Color(0, 0, 0, 255));
            g2.translate(x, y);
            g2.draw(outline);
            g2.translate(-x, -y);
            //+++++++++++++++++++++++++++++++++++++++++++++++++++
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g2.setColor(color);
            g2.setFont(F);
            g2.drawString(text, x, y);
      }
}
