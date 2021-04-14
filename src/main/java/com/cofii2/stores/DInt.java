/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.stores;

/**
 *
 * @author C0FII
 */
public class DInt implements Comparable<DInt>{
      public int index1;
      public int index2;
      
      public DInt(int index1, int index2){
            this.index1 = index1;
            this.index2 = index2;
      }
      

      
      @Override
      public int compareTo(DInt o) {
            return this.index1 - o.index1;
      }



      @Override
      public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + index1;
            result = prime * result + index2;
            return result;
      }



      @Override
      public boolean equals(Object obj) {
            if (this == obj)
                  return true;
            if (obj == null)
                  return false;
            if (getClass() != obj.getClass())
                  return false;
            DInt other = (DInt) obj;
            if (index1 != other.index1)
                  return false;
            if (index2 != other.index2)
                  return false;
            return true;
      }
      
}
