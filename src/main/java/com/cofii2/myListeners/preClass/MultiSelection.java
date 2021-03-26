/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners.preClass;

import javax.swing.AbstractButton;
import com.cofii2.myInterfaces.IMultiSelection;

/**
 *
 * @author C0FII
 */
public class MultiSelection <T extends AbstractButton>{
      
      public T[] array;
      public IMultiSelection ma;
      
      public MultiSelection(T[] array, IMultiSelection ma){
            this.array = array;
            this.ma = ma;
      }
}
