/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import com.cofii2.myInterfaces.SingleSelectionIAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;

/**
 *
 * @author C0FII
 */
public class SingleSelectionAction <T extends AbstractButton> implements ActionListener{

      private SingleSelectionIAction SA;
      private T[] Array;
      
      public SingleSelectionAction(T[] arrayButtons, SingleSelectionIAction sa){
            Array = arrayButtons;
            SA = sa;
      }
      
      @Override
      public void actionPerformed(ActionEvent e) {
            int num = MComp.getSelectedOnAButtonGroup(e, Array) - 1;//- 1 (INDEX)
            //System.out.println("TEST SingleSelectionAction FOR DEFALTS Button Group num: " + (num + 1));
            SA.action(e, num);
            
      }
      
}
