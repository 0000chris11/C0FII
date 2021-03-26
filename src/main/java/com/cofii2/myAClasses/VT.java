/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myAClasses;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 *
 * @author C0FII
 */
public class VT extends JFrame{
      public VT(int layout){
            setDefaultCloseOperation(2);
            setTitle("Test Window");
            setLayout(new BoxLayout(getContentPane(), layout));
      }
}
