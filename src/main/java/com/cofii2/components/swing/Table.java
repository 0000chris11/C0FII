package com.cofii2.components.swing;


import com.cofii2.custom.LKCustom2;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author C0FII
 */
public class Table extends JTable{
      
      /**
       *
       */
      private static final long serialVersionUID = 1L;

      public Table(){
            setBackground(Color.BLACK);
            setFont(LKCustom2.FONT_TABLE);
            setRowHeight(23);
            setAutoCreateRowSorter(true);
            setGridColor(Color.GRAY);
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setRowSelectionAllowed(true);
            setSelectionBackground(LKCustom2.BK_DIST2);
      }
}
