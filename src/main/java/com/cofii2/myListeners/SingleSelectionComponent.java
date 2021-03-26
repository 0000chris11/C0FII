/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myListeners;

import com.cofii2.methods.MComp;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.AbstractButton;
import com.cofii2.myInterfaces.SingleSelectionIAction;

/**
 *
 * @author C0FII
 */
public class SingleSelectionComponent <T extends AbstractButton> extends ComponentAdapter{

      private SingleSelectionIAction SA;
      private T[] Array;
      
      public SingleSelectionComponent(T[] arrayButtons, SingleSelectionIAction sa){
            Array = arrayButtons;
            SA = sa;
      }
      
      @Override
      public void componentHidden(ComponentEvent e) {
            hiddenShown(e);
      }

      @Override
      public void componentShown(ComponentEvent e) {
            hiddenShown(e);
      }
      
      
      
      private void hiddenShown(ComponentEvent e){
            int num = MComp.getSelectedOnAButtonGroup(e, Array);
            if(num != -1){
                  num = num - 1;
            }
            
            SA.action(e, num);
      }

}
