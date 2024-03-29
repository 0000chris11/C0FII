/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author C0FII
 */
public class MImage {

      private static int option = 1;

      public static final int DRAW_IMAGE = 1;
      public static final int DRAW_CIRCLE = 2;
      public static final int DRAW_CIRCLES = 3;

      private static Color color = Color.WHITE;
      private static Color[] colors = {Color.WHITE, Color.GRAY};

      private MImage() {
            throw new IllegalStateException("Private Constructor");
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++
      public static void setImageIcon(JButton btn, String url) {
            ImageIcon ic = new ImageIcon(url);
            Icon i = ic;
            btn.setIcon(i);
      }

      public static ImageIcon resizeIcon(String path, int w, int h) {
            BufferedImage bimage;
            int cl = colors.length;
            
            if (option != DRAW_CIRCLES) {
                  bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            }else{
                  bimage = new BufferedImage((w * cl) + 2, h, BufferedImage.TYPE_INT_ARGB);
            }
            Graphics2D g2 = bimage.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            ImageIcon ii = new ImageIcon(path);
            if (option == DRAW_IMAGE) {
                  g2.drawImage(ii.getImage(), 0, 0, w, h, null);
            } else if (option == DRAW_CIRCLE) {
                  g2.setColor(color);
                  g2.fillOval(0, 0, w, h);
            } else if (option == DRAW_CIRCLES) {
                  int x = 0;
                  int y = 0;
                  for (int a = 0; a < colors.length; a++) {
                        g2.setColor(colors[a]);
                        g2.fillOval(x, y, w, h);
                        x += w + 2;
                        //y += h + 2;

                  }
            }
            //System.out.println("------------------------Icon2 Width: " + icon2.getIconWidth() + "\tHeight: " + icon2.getIconHeight());
            return new ImageIcon(bimage);
            //++++++++++++++++++++++++++++++++++++++++++++++++++
      }

      public static ImageIcon resizeIcon(ImageIcon ii, int w, int h) {
            BufferedImage bimage;
            int cl = colors.length;
            
            if (option != DRAW_CIRCLES) {
                  bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            }else{
                  bimage = new BufferedImage((w * cl) + 2, h, BufferedImage.TYPE_INT_ARGB);
            }
            Graphics2D g2 = bimage.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            if (option == DRAW_IMAGE) {
                  g2.drawImage(ii.getImage(), 0, 0, w, h, null);
            } else if (option == DRAW_CIRCLE) {
                  g2.setColor(color);
                  g2.fillOval(0, 0, w, h);
            } else if (option == DRAW_CIRCLES) {
                  int x = 0;
                  int y = 0;
                  for (int a = 0; a < colors.length; a++) {
                        g2.setColor(colors[a]);
                        g2.fillOval(x, y, w, h);
                        x += w + 2;
                        //y += h + 2;

                  }
            }
            //System.out.println("------------------------Icon2 Width: " + icon2.getIconWidth() + "\tHeight: " + icon2.getIconHeight());
            return new ImageIcon(bimage);
            //++++++++++++++++++++++++++++++++++++++++++++++++++
      }

      public static ImageIcon resizeIcon(ImageIcon ii, Dimension newDimension) {
            int w = newDimension.width;
            int h = newDimension.height;

            BufferedImage bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bimage.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            if (option == DRAW_IMAGE) {
                  g2.drawImage(ii.getImage(), 0, 0, w, h, null);
            } else if (option == DRAW_CIRCLE) {
                  g2.setColor(color);
                  g2.fillOval(0, 0, w, h);
            }

            return new ImageIcon(bimage);
      }

      public static BufferedImage changeImageColorOfOneColor(File file, Color color) {
            BufferedImage bimage = null;
            try {
                  bimage = ImageIO.read(file);

                  int w = bimage.getWidth();
                  int h = bimage.getHeight();

                  for (int a = 0; a < h; a++) {
                        for (int b = 0; b < w; b++) {
                              int rgb = bimage.getRGB(b, a);
                              //System.out.println("RGB on [" + rgb + "]");
                              if (rgb != 0) {
                                    //PAINTING ONLY WHEN THERE IS COLOR
                                    bimage.setRGB(b, a, color.getRGB());
                              }
                        }
                  }
            } catch (IOException ex) {
                  ex.printStackTrace();
            }
            return bimage;
      }

      public static Dimension getScaledDownHeightAR(int w, int h, int newHeight) {
            int width = w;
            int height = h;
            int newWidth = width;

            if (height > newHeight) {//SCALE DOWN
                  newWidth = (newHeight * width) / height;
            }
            return new Dimension(newWidth, newHeight);
      }

      public static Dimension getScaledDownHeightAR(Dimension dimension, int newHeight) {
            int width = dimension.width;
            int height = dimension.height;
            int newWidth = width;

            if (height > newHeight) {//SCALE DOWN
                  newWidth = (newHeight * width) / height;
            }
            return new Dimension(newWidth, newHeight);
      }

      public static Dimension getScaledDownWidthAR(int w, int h, int newWidth) {
            int width = w;
            int height = h;
            int newHeight = height;

            if (width > newWidth) {//SCALE DOWN
                  newHeight = (newWidth * height) / width;
            }
            return new Dimension(newHeight, newWidth);
      }

      public static Dimension getScaledDownWidthAR(Dimension dimension, int newWidth) {
            int width = dimension.width;
            int height = dimension.height;
            int newHeight = height;

            if (width > newWidth) {//SCALE DOWN
                  newHeight = (newWidth * height) / width;
            }
            return new Dimension(newHeight, newWidth);
      }

      public static Dimension getScaledDownDimensionAR(Dimension dimension, Dimension newDimension) {
            int width = dimension.width;
            int height = dimension.height;
            int newHeight = newDimension.height;
            int newWidth = newDimension.width;

            if (width > newWidth) {
                  newHeight = (newWidth * height) / width;
            }

            if (height > newHeight) {
                  newWidth = (newHeight * width) / height;
            }

            return new Dimension(newHeight, newWidth);
      }

      public static Dimension getScaledDownDimensionAR(int w, int h, int nw, int nh) {
            int width = w;
            int height = h;
            int newHeight = nw;
            int newWidth = nh;

            if (width > newWidth) {
                  newHeight = (newWidth * height) / width;
            }

            if (height > newHeight) {
                  newWidth = (newHeight * width) / height;
            }

            return new Dimension(newHeight, newWidth);
      }

      public static Dimension getListIconsAverage(Icon[] list) {
            double sumWidth = 0;
            double sumHeight = 0;
            for (int a = 0; a < list.length; a++) {
                  sumWidth += list[a].getIconWidth();
                  sumHeight += list[a].getIconHeight();
            }
            double averageWidth = sumWidth / list.length;
            double averageHeight = sumHeight / list.length;

            return new Dimension((int) averageWidth, (int) averageHeight);
      }

      //+++++++++++++++++++++++++++++++++++++++++++++++
      public static void setOption(int option) {
            MImage.option = option;
      }

      public static void setColor(Color color) {
            MImage.color = color;
      }

      public static void setColors(Color[] colors) {
            MImage.colors = colors;
      }
}
