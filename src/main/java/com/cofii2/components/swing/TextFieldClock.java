/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author C0FII
 */
public class TextFieldClock extends JPanel{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;
      
      private transient Clock c = new Clock();
      private JTextField tf = new TextFieldCustom();
      private JTextField md = c.getMD();
      private JTextField mu = c.getMU();
      private JLabel lb2 = c.getLB2();
      private JTextField sd = c.getSD();
      private JTextField su = c.getSU();
      
      public TextFieldClock(){
            setBackground(Color.BLACK);
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            
            add(tf);
            add(md);
            add(mu);
            add(lb2);
            add(sd);
            add(su);
            
      }
      
      public JTextField getTF(){
            return tf;
      }
      public JTextField getMD(){
            return md;
      }
      public JTextField getMU(){
            return mu;
      }
      public JLabel getLB2(){
            return lb2;
      }
      public JTextField getSD(){
            return sd;
      }
      public JTextField getSU(){
            return su;
      }
}
