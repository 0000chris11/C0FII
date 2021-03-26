/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.components.swing;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author C0FII
 */
public class Clock {
      
      private JTextField md = new TextFieldCustom("0");
      private JTextField mu = new TextFieldCustom("0");
      private JLabel lb2 = new LabelCustom(":", SwingConstants.CENTER);
      private JTextField sd = new TextFieldCustom("0");
      private JTextField su = new TextFieldCustom("0");
      
      public Clock(){
            
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
