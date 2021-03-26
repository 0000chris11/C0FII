/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.stores;

import com.cofii2.methods.MComp;
import com.cofii2.methods.MList;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author C9FII
 */
public class MArrays {

      private ArrayList<Object[]> list = new ArrayList<Object[]>();

      public void addArray(Object[] array) {
            list.add(array);
      }

      public Object[][] getDoubleArray() {
            return MList.getDoubleArrayFromList(list);
      }

      public JComponent[][] getDoubleArrayComponents(boolean changeName) {
            boolean same = MList.isTheLengthTheSameForAll(list);
            JComponent[][] components = null;
            if (same) {
                  int row = list.get(0).length;
                  int col = list.size();
                  components = new JComponent[row][col];
                  for (int r = 0; r < row; r++) {
                        for (int c = 0; c < col; c++) {
                              Object ob = list.get(c)[r];
                              if (!changeName) {
                                    components[r][c] = (JComponent) ob;
                              }else{
                                    if(ob instanceof JLabel){
                                          JLabel lb = (JLabel) ob;
                                          String text = lb.getText();
                                          int index = MComp.getLastDigitCharsCountAtEnd(text);
                                          
                                          text.replace(Integer.toString(index), Integer.toString(++index));
                                          components[r][c] = lb;
                                    }else{
                                          components[r][c] = (JComponent) ob;
                                    }
                              }
                        }
                  }
            } else {
                  throw new IllegalArgumentException("All arrays need to be the same size");
            }
            return components;
      }
}
