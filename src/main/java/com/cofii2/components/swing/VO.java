/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import com.cofii2.methods.MComp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author C0FII
 */
public class VO {

      public JFrame JF = new JFrame();
      private JPanel PU = new JPanel();
      private JPanel PD = new JPanel();

      private JLabel lbTitle = new LabelCustom(SwingConstants.CENTER);

      private static final Color bgColor = Color.DARK_GRAY.darker();

      public VO(String title, String question, JButton[] buttons) {
            JF.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JF.setAlwaysOnTop(true);
            JF.setResizable(false);
            JF.setTitle(title);
            JF.getContentPane().setBackground(bgColor);

            JF.getContentPane().setLayout(new BoxLayout(JF.getContentPane(), BoxLayout.Y_AXIS));
            JF.getContentPane().add(Box.createVerticalStrut(10), 0);
            JF.getContentPane().add(PU, 1);
            JF.getContentPane().add(Box.createVerticalStrut(10), 2);
            JF.getContentPane().add(PD, 3);

            PU.setBackground(bgColor);
            PD.setBackground(bgColor);

            Dimension panelsDImension = new Dimension(300, 120);
            Dimension panels2DImension = new Dimension(300, 40);
            PU.setMinimumSize(panelsDImension);
            PU.setMaximumSize(panelsDImension);
            PU.setPreferredSize(panelsDImension);
            PD.setMinimumSize(panels2DImension);
            PD.setMaximumSize(panels2DImension);
            PD.setPreferredSize(panels2DImension);

            PU.setLayout(new FlowLayout(FlowLayout.CENTER));
            PU.add(lbTitle);
            lbTitle.setForeground(Color.WHITE);
            lbTitle.setFont(new Font("Dialog", Font.BOLD, 20));
            lbTitle.setText(question);

            Dimension lbDimension = new Dimension(300, 30);
            PU.setMinimumSize(lbDimension);
            PU.setMaximumSize(lbDimension);
            PU.setPreferredSize(lbDimension);

            FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);           
            int width = (int) lbTitle.getFont().getStringBounds(lbTitle.getText(), frc).getWidth();
            System.out.println("Font Width: " + width);
            System.out.println("280");
            int c = 1;
            while (width > (300 - 40)) {
                  System.out.println("\t" + c++);
                  lbTitle.setFont(new Font("Dialog", Font.BOLD, lbTitle.getFont().getSize() - 1));
                  width = (int) lbTitle.getFont().getStringBounds(lbTitle.getText(), frc).getWidth();
            }

            PD.setLayout(new FlowLayout(FlowLayout.CENTER));
            Dimension buttonsDImension = new Dimension(80, 27);
            c = 0;
            for (int a = 0; a < buttons.length; a++) {
                  PD.add(buttons[a]);
                  buttons[a].setMinimumSize(buttonsDImension);
                  buttons[a].setMaximumSize(buttonsDImension);
                  buttons[a].setPreferredSize(buttonsDImension);

            }

            start();
      }

      private void start() {
            SwingUtilities.invokeLater(new Runnable() {
                  @Override
                  public void run() {
                        JF.setSize(300, 130);
                        MComp.setFrameToCenterOfScreen(JF);

                        JF.setVisible(true);
                  }

            });
      }
}
