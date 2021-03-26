/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.myAClasses;

import com.cofii2.myInterfaces.SerializationExceptionAction;
import java.awt.LayoutManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Class containing custom methods that aligns component using only GroupLayout
 * (for now)
 *
 * @author C0FII
 */
public class MLayout {

      private LayoutManager LY;
      private int Limit;
      private static SerializationExceptionAction SEA = null;

      private static GroupLayout.ParallelGroup[] phs;//HORIZONTAL GROUP = COL NUM
      private static GroupLayout.SequentialGroup sv;//VERTICAL GROUP = ROW LENGHT DETERMINE BY ->rows<-
      private static GroupLayout.SequentialGroup sgh;//SUB HORIZONTAL GROUP

      private static int HEIGHT = 27;

      /**
       * Construct default LayoutManager with limit rows
       *
       * @param ly LayoutManager to use (Only GroupLayout works for now)
       * @param limit How many rows of components
       */
      public MLayout(LayoutManager ly, int limit) {
            LY = ly;
            Limit = limit;
      }

      /**
       * Customize GroupLayout; the first 4 component on the first row and the
       * last one below the 2nd component
       *
       * @param C1 1st column (Labels)
       * @param C2 2nd column TextFields
       * @param C3 2nd column bound to previous param
       * @param C4 3nd column (Buttons)
       * @param C5 4th column (List)
       */
      public void fourAndOneSequence(JComponent[] C1, JComponent[] C2,
              JComponent[] C3, JComponent[] C4, JComponent[] C5) {
            if (LY instanceof GroupLayout) {
                  GroupLayout gl = (GroupLayout) LY;

                  gl.setAutoCreateGaps(true);
                  gl.setAutoCreateContainerGaps(true);

                  GroupLayout.ParallelGroup pgh1 = gl.createParallelGroup(GroupLayout.Alignment.LEADING, true);
                  GroupLayout.ParallelGroup pgh2 = gl.createParallelGroup(GroupLayout.Alignment.LEADING, true);
                  GroupLayout.ParallelGroup pgh3 = gl.createParallelGroup(GroupLayout.Alignment.LEADING, true);

                  GroupLayout.SequentialGroup sgv1 = gl.createSequentialGroup();
                  //SequentialGroup sgh1 = layout.createSequentialGroup();
                  int LST_PREF = 200;
                  int TF_PREF = 150;
                  int CON_PREF = 50;

                  for (int a = 0; a < Limit; a++) {
                        pgh1.addComponent(C1[a]);
                        pgh1.addGap(0);
                        //++++++++++++++++++++++++++
                        pgh2.addGroup(gl.createSequentialGroup()
                                .addComponent(C2[a], TF_PREF, TF_PREF, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(C3[a], CON_PREF, CON_PREF, CON_PREF));
                        pgh2.addComponent(C4[a], LST_PREF, LST_PREF, Short.MAX_VALUE);
                        //++++++++++++++++++++++++++
                        pgh3.addComponent(C5[a]);
                        pgh3.addGap(0);
                        //=========================================
                        sgv1.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(C1[a])
                                .addComponent(C2[a])
                                .addComponent(C3[a])
                                .addComponent(C5[a]));
                        sgv1.addComponent(C4[a]);
                  }
                  //++++++++++++++++++++++++++++++++
                  //++++++++++++++++++++++++++++++++
                  gl.setHorizontalGroup(
                          gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                                  .addGroup(gl.createSequentialGroup()
                                          .addGroup(pgh1)
                                          .addGroup(pgh2)
                                          .addGroup(pgh3)));

                  gl.setVerticalGroup(
                          gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                                  .addGroup(sgv1));
            }
      }

      /**
       * LineSequence like a table containg row and columns
       *
       * @param gl GroupLayout for aligning
       * @param components array of components (rows, columns)
       */
      public static void lineSequence(GroupLayout gl, JComponent[][] components) {
            defaultLineSequence(gl, components);
      }

      public static void lineSequence(GroupLayout gl, JLabel[] headers, JComponent[][] components) {
            JComponent[][] comps = getDoubleComponents(components, headers);
            defaultLineSequence(gl, comps);
      }

      public static void defaultLineSequence(GroupLayout gl, JComponent[][] components) {
            int colLength = components[0].length;
            int rowLength = components.length;
            phs = new GroupLayout.ParallelGroup[colLength];
            for (int a = 0; a < colLength; a++) {
                  phs[a] = gl.createParallelGroup(GroupLayout.Alignment.LEADING, true);
            }
            defaultGroupConfig(gl, colLength, false);
            //HORIZONTAL +++++++++++++++++++++++++++++

            for (int row = 0; row < rowLength; row++) {
                  for (int col = 0; col < colLength; col++) {
                        phs[col].addComponent(components[row][col]);
                  }
            }
            //System.out.println("PHS LENGTH: " + phs.length);
            //System.out.println("SGH: " + sgh.toString());
            for (int a = 0; a < colLength; a++) {
                  sgh.addGroup(phs[a]);
            }
            //VERTICAL +++++++++++++++++++++++++++++++
            for (int a = 0; a < rowLength; a++) {
                  GroupLayout.ParallelGroup pg = gl.createParallelGroup(GroupLayout.Alignment.LEADING, false);
                  for (int b = 0; b < colLength; b++) {
                        pg.addComponent(components[a][b], HEIGHT, HEIGHT, HEIGHT);
                  }
                  sv.addGroup(pg);
            }

            defaultAssignment(gl);
      }

      /**
       * lineSequenceYClone copys the 1st array of component creating multiples
       * rows
       *
       * @param gl GroupLayout needed for aligning
       * @param components row of components to copy
       * @param rows how many rows
       * @return double array of components in result of the arrengment
       */
      public static JComponent[][] lineSequenceYClone(GroupLayout gl,
              JComponent[] components, int rows) {
            JComponent[][] compList = null;

            gl.setAutoCreateGaps(true);
            gl.setAutoCreateContainerGaps(true);

            GroupLayout.ParallelGroup[] phs = new GroupLayout.ParallelGroup[components.length];
            GroupLayout.SequentialGroup sv = gl.createSequentialGroup();
            compList = new JComponent[components.length][rows];

            for (int a = 0; a < components.length; a++) {
                  phs[a] = gl.createParallelGroup(GroupLayout.Alignment.LEADING, true);
            }

            GroupLayout.SequentialGroup sgh = gl.createSequentialGroup();

            for (int a = 0; a < components.length; a++) {
                  for (int b = 0; b < rows; b++) {
                        //compList[a][b] = cloneSwingComponent(components[a]);
                        compList[a][b] = SerializationUtils.clone(components[a]);
                  }
            }

            for (int a = 0; a < components.length; a++) {
                  for (int b = 0; b < rows; b++) {
                        phs[a].addComponent(compList[a][b]);
                        GroupLayout.ParallelGroup pg = gl.createParallelGroup(GroupLayout.Alignment.LEADING, false);
                        for (int c = 0; c < components.length; c++) {
                              pg.addComponent(compList[c][b], HEIGHT, HEIGHT, HEIGHT);
                        }
                        sv.addGroup(pg);
                  }
                  sgh.addGroup(phs[a]);
            }
            //+++++++++++++++++++++++++++++++++++++++++++++++++
            gl.setHorizontalGroup(
                    gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(sgh));

            gl.setVerticalGroup(
                    gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(sv));

            return compList;
      }

      /**
       * lineSequenceYClone; 1st the headers and copys the giving components to
       * 2nd and forth
       *
       * @param gl GroupLayout needed for aligning
       * @param headers JLabel for columns name
       * @param components row of components to copy
       * @param rows how many rows
       * @return double array of components in result of the arrengment
       * excluding headers
       */
      public static JComponent[][] lineSequenceYClone(GroupLayout gl,
              JLabel[] headers, JComponent[] components, int rows) {
            int cols = components.length;
            rows++;//+1 FOR HEADERS
            defaultGroupConfig(gl, cols, true);
            //ADD COMPONENTS TO DOUBLE ARRAY==============================
            JComponent[][] compList = getDoubleComponents(components, headers, rows);
            //HORIZONTAL CONFIG
            for (int a = 0; a < rows; a++) {
                  for (int b = 0; b < cols; b++) {
                        phs[b].addComponent(compList[a][b]);//ADDING COLUMNS
                  }
            }
            for (int a = 0; a < components.length; a++) {
                  sgh.addGroup(phs[a]);
            }

            //VERTICAL CONFIG
            for (int a = 0; a < rows; a++) {
                  GroupLayout.ParallelGroup pgv = gl.createParallelGroup(GroupLayout.Alignment.LEADING, false);
                  for (int b = 0; b < cols; b++) {
                        pgv.addComponent(compList[a][b],
                                HEIGHT, HEIGHT, HEIGHT);
                  }
                  sv.addGroup(pgv);
            }
            //+++++++++++++++++++++++++++++++++++++++++++++++++
            defaultAssignment(gl);

            return compList;
      }

      public static void singlelineSequence(GroupLayout gl, JComponent[] components) {
            defaultGroupConfig(gl, 1, true);
            int rows = components.length;
            //HORIZONTAL++++++++++++++++++++++++++++
            for (int a = 0; a < rows; a++) {
                  phs[0].addComponent(components[a]);//ADDING SINGLE COL
            }
            sgh.addGroup(phs[0]);//ADDING SINGLE PRL GROUP
            //VERTICAL++++++++++++++++++++++++++++
            for (int a = 0; a < rows; a++) {
                  GroupLayout.ParallelGroup pgv = gl.createParallelGroup(GroupLayout.Alignment.LEADING, false);

                  if (components[a] instanceof JScrollPane) {
                        pgv.addComponent(components[a],
                                120, 120, 120);
                  } else {
                        pgv.addComponent(components[a],
                                HEIGHT, HEIGHT, HEIGHT);
                  }

                  sv.addGroup(pgv);
            }

            defaultAssignment(gl);
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      private static void defaultGroupConfig(GroupLayout gl, int cols, boolean fixLater) {
            gl.setAutoCreateGaps(true);
            gl.setAutoCreateContainerGaps(true);

            //HORIZONTAL GROUP = COL NUM
            if (fixLater) {
                  phs = new GroupLayout.ParallelGroup[cols];
            }
            //VERTICAL GROUP = ROW LENGHT DETERMINE BY ->rows<-
            sv = gl.createSequentialGroup();
            //+++++++++++++++++++++++++++++++++++++++++++++
            //SUB HORIZONTAL GROUP
            sgh = gl.createSequentialGroup();

            //SUB-SUB HORIZONTAL GROUPS (COLS)
            if (fixLater) {
                  for (int a = 0; a < cols; a++) {
                        phs[a] = gl.createParallelGroup(GroupLayout.Alignment.LEADING, true);
                  }
            }
      }

      private static void defaultAssignment(GroupLayout gl) {
            gl.setHorizontalGroup(
                    gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(sgh));

            gl.setVerticalGroup(
                    gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(sv));
      }

      private static JComponent[][] getDoubleComponents(JComponent[] components, JLabel[] headers, int rows) {
            //ADD COMPONENTS TO DOUBLE ARRAY==============================
            JComponent[][] compList = new JComponent[rows][components.length];

            for (int a = 0; a < rows; a++) {
                  for (int b = 0; b < components.length; b++) {
                        //compList[a][b] = cloneSwingComponent(components[a]);
                        if (a == 0) {
                              compList[a][b] = headers[b];
                        } else {
                              //ROW ID
                              if (components[b] instanceof JLabel) {//MAY CHANGE LATER
                                    JLabel lb = (JLabel) components[b];
                                    if (a > 1) {
                                          lb.setText(lb.getText().replace(
                                                  Integer.toString(a - 1), Integer.toString(a)));
                                    }
                                    components[b] = lb;
                              }
                              try {
                                    compList[a][b] = SerializationUtils.clone(components[b]);
                              } catch (SerializationException ex) {
                                    if (SEA != null) {
                                          compList[a][b] = SEA.getComponent(ex, components[b]);
                                    }
                              }
                        }
                  }
            }
            return compList;
      }

      private static JComponent[][] getDoubleComponents(JComponent[][] components, JLabel[] headers) {
            int row = components.length + 1;
            int col = components[0].length;

            JComponent[][] returnValue = new JComponent[row][col];
            //System.out.println("returnValue row: " + returnValue.length);
            //System.out.println("returnValue col: " + returnValue[0].length);

            //System.out.println("\nheaders col: " + headers.length);

            //System.out.println("\ncompoents row: " + components.length);
            //System.out.println("compoents col: " + components[0].length);
            for (int a = 0; a < row; a++) {
                  if (a == 0) {
                        returnValue[a] = headers;
                  } else {
                        returnValue[a] = components[a - 1];
                  }
            }
            return returnValue;
      }

      public static void setSerializationExceptionAction(SerializationExceptionAction sea) {
            SEA = sea;
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      public static int getComponentsHeight() {
            return HEIGHT;
      }

      public static void setComponentsHeight(int h) {
            HEIGHT = h;
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      private JComponent cloneSwingComponent(JComponent c) {
            try {
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  ObjectOutputStream oos = new ObjectOutputStream(baos);
                  oos.writeObject(c);
                  ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                  ObjectInputStream ois = new ObjectInputStream(bais);
                  return (JComponent) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                  ex.printStackTrace();
                  return null;
            }
      }
}
