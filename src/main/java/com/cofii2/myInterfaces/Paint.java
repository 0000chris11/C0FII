/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myInterfaces;

import java.awt.Graphics;

/**
 *
 * @author COFII
 */
public interface Paint {
      
      public void paintBefore(Graphics g, Object ob);
      public void paintAfter(Graphics g, Object ob);
      
}
