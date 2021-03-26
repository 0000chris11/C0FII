/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.mysql;

/**
 *
 * @author C0FII
 */
public class Connect {
      
      public String URLConnection;
      public String User;
      public String Password;
      
      public Connect(String URLConnection, String User, String Password){
            this.URLConnection = URLConnection;
            this.User = User;
            this.Password = Password;
      }
}
