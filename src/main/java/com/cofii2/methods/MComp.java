/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import com.cofii2.components.swing.TextFieldDCellEditor;
import com.cofii2.custom.TableCellRenderer;
import com.cofii2.custom.TableDCellRenderer;
import com.cofii2.myAClasses.LimitN;
import com.cofii2.myAbsClasses.ATextAction;
import com.cofii2.myListeners.MPlaceH;
import com.cofii2.stores.CC;
import com.cofii2.stores.IntObject;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author C0FII
 */
public class MComp {

      private static Logger logger = Logger.getLogger(MComp.class.getName());

      private MComp() {
            throw new IllegalStateException("Private Constructor");
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Print Size, minimumSize, preferredSize and maxSize of the given
       * component
       *
       * @param jc component to print info about
       */
      public static void printComponentSizeInfo(JComponent jc) {
            System.out.println(jc.getName() + " (" + jc.getClass().toString() + ")");
            System.out.println("\tSize: " + jc.getSize());
            System.out.println("\tMin Size: " + jc.getMinimumSize());
            System.out.println("\tPreffered Size: " + jc.getPreferredSize());
            System.out.println("\tMax Size: " + jc.getMaximumSize());
      }

      /**
       * Print ComboBox and the editor background and foreground
       *
       * @param cb ComboBox to print info about
       */
      public static void printComboBoxColorInfo(JComboBox<?> cb) {
            String name = cb.getClass().toString();
            if (cb.getName() != null) {
                  name = cb.getName();
            }
            System.out.println("\n" + name);
            System.out.println("\tBackground: " + cb.getBackground());
            System.out.println("\tForeground: " + cb.getForeground());
            System.out.println("\tEditor Background: " + cb.getEditor().getEditorComponent().getBackground());
            System.out.println("\tEditor Foreground: " + cb.getEditor().getEditorComponent().getForeground());

      }

      public static void logComponentSizeInfo(JComponent jc) {
            logger.info(jc.getName());
            logger.info("\tSize: " + jc.getSize());
            logger.info("\tMin Size: " + jc.getMinimumSize());
            logger.info("\tPreffered Size: " + jc.getPreferredSize());
            logger.info("\tMax Size: " + jc.getMaximumSize());
      }

      /**
       * Pint the names of each component in array
       *
       * @param jc Components to print the name
       */
      public static void printComponentsName(JComponent[] jc) {
            int count = 1;
            for (JComponent x : jc) {
                  System.out.println(count++ + ": " + x.getName());
            }
      }

      public static void printComponentsName(Component[] jc) {
            int count = 1;
            for (Component x : jc) {
                  System.out.println(count++ + ": " + x.getName());
            }
      }

      /**
       * Print table's Model, CellRenederer (0,0) and CellEditor
       *
       * @param table Table to print info about
       */
      public static void printTableInfo(JTable table) {
            String name = getName(table);
            System.out.println(name);
            getClassName("\tTable Model: ", table.getModel().getClass().toString());
            getClassName("\tTable Default Cell Renderer: ", table.getDefaultRenderer(String.class).getClass().toString());
            System.out.println("\tTable Cell Renderer");
            for (int c = 0; c < table.getColumnCount(); c++) {
                  for (int r = 0; r < table.getRowCount(); r++) {
                        getClassName("\t\t" + r + ", " + c + ": ", table.getCellRenderer(r, c).getClass().toString());
                  }
            }
            getClassName("\tTable Default Cell Editor: ", table.getDefaultEditor(String.class).getClass().toString());

            if (table.getCellEditor() != null) {
                  getClassName("\tTable Cell Editor: ", table.getCellEditor().getClass().toString());
            } else {
                  getClassName("\tTable Cell Editor: null", "");
            }
            System.out.println("\tTable Cell Editor");
            for (int c = 0; c < table.getColumnCount(); c++) {
                  for (int r = 0; r < table.getRowCount(); r++) {
                        getClassName("\t\t" + r + ", " + c + ": ", table.getCellEditor(r, c).getClass().toString());
                  }
            }

            getClassName("\tTable ColumnModel: ", table.getColumnModel().getClass().toString());

      }

      public static void printTableInfo(JTable table, int rowLimit) {
            String name = getName(table);
            System.out.println(name);
            getClassName("\tTable Model: ", table.getModel().getClass().toString());
            getClassName("\tTable Default Cell Renderer: ", table.getDefaultRenderer(String.class).getClass().toString());
            System.out.println("\tTable Cell Renderer");
            for (int c = 0; c < table.getColumnCount(); c++) {
                  for (int r = 0; r < table.getRowCount(); r++) {
                        if (r < rowLimit) {
                              getClassName("\t\t" + r + ", " + c + ": ", table.getCellRenderer(r, c).getClass().toString());
                        }
                  }
            }
            getClassName("\tTable Default Cell Editor: ", table.getDefaultEditor(String.class).getClass().toString());

            if (table.getCellEditor() != null) {
                  getClassName("\tTable Cell Editor: ", table.getCellEditor().getClass().toString());
            } else {
                  getClassName("\tTable Cell Editor: null", "");
            }
            System.out.println("\tTable Cell Editor");
            for (int c = 0; c < table.getColumnCount(); c++) {
                  for (int r = 0; r < table.getRowCount(); r++) {
                        if (r < rowLimit) {
                              getClassName("\t\t" + r + ", " + c + ": ", table.getCellEditor(r, c).getClass().toString());
                        }
                  }
            }

            getClassName("\tTable ColumnModel: ", table.getColumnModel().getClass().toString());

      }

      public static void printItems(JMenu menu) {
            System.out.println("\n" + menu.getText());
            for (int a = 0; a < menu.getItemCount(); a++) {
                  System.out.println("\t" + (a + 1) + ": " + menu.getItem(a).getText());
            }
      }

      //----------------------------------------------------------------------------------
      private static String getName(JComponent jc) {
            String returnValue = null;
            if (jc.getName() != null) {
                  returnValue = jc.getName();
            } else {
                  returnValue = jc.getClass().getName();
            }
            return returnValue;
      }

      private static void getClassName(String print, String text) {
            if (text.contains("javax.swing")) {
                  System.out.println(print + text);
            } else {
                  System.out.println(CC.CYAN + print + text + CC.RESET);
            }
      }

      //++++++++++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Get Index of the child component
       *
       * @param component child component to get the index from
       * @return index of the child component
       */
      public static int getComponentIndex(Component component) {
            Container c = component.getParent();
            for (int a = 0; a < c.getComponentCount(); a++) {
                  if (c.getComponent(a) == component) {
                        return a;
                  }
            }
            return -1;
      }

      /**
       * Set the a label to the center of a container (in an null layout)
       *
       * @param LB label to place at the center
       * @param JC Container to place the component on to
       */
      public static void setLabelToCenter(JLabel LB, JComponent JC) {
            FontMetrics FM = LB.getFontMetrics(LB.getFont());

            int x = (JC.getWidth() - (int) LB.getWidth()) / 2;
            int y = (FM.getAscent()
                    + (JC.getHeight() - (FM.getAscent() + FM.getDescent())) / 2);

            LB.setLocation(x, y);
      }

      public static void setLabelToXCenter(JLabel LB, int y, JComponent JC) {
            int x = (JC.getWidth() - (int) LB.getWidth()) / 2;
            LB.setLocation(x, y);
      }

      public static void setLabelToXCenter(JLabel LB, int y, JFrame JC) {
            int x = (JC.getWidth() - (int) LB.getWidth()) / 2;
            LB.setLocation(x, y);
      }

      public static void setComponentFitOnJFrame(JComponent jc, JFrame jf) {
            jc.setBounds(2, 2,
                    jf.getWidth() - 21,
                    jf.getHeight() - 44);

            System.out.println("JP Bounds: " + jc.getBounds());
      }

      public static void setFrameToCenterOfScreen(JFrame JF) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            JF.setLocation(dim.width / 2 - JF.getSize().width / 2, dim.height / 2 - JF.getSize().height / 2);
      }

      public static void setNames(JComponent[] components, String name) {
            for (int a = 0; a < components.length; a++) {
                  components[a].setName(name + "_" + (a + 1));
            }
      }

      //++++++++++++++++++++++++++++++++++++++++++
      /**
       * Allows only one button to be selected at a time and also deselect all
       * of them
       *
       * @param <T> only object that extends AbstractButton class
       * @param e Tested with ActionEvent and ComponentEvent
       * @param array Arrray of the buttons to control a SingleSelection
       * @return index of wich one is selected; return -1 if nothing is selected
       */
      public static <T extends AbstractButton> int getSelectedOnAButtonGroup(EventObject e, T[] array) {
            int returnValue = -1;
            ArrayList<T> list = new ArrayList<T>();
            for (T x : array) {
                  if (x.isVisible()) {
                        list.add(x);
                  }
            }

            for (int a = 0; a < list.size(); a++) {
                  boolean selected = list.get(a).isSelected();
                  boolean enabled = list.get(a).isEnabled();

                  if (e instanceof ActionEvent) {
                        if (e.getSource() == list.get(a)) {//ACTION EVENT
                              returnValue = event(selected, enabled, returnValue, a, list);
                        }
                  } else {
                        returnValue = event(selected, enabled, returnValue, a, list);
                  }

            }

            return returnValue;
      }

      private static <T extends AbstractButton> int event(boolean selected, boolean enabled, int returnValue, int a,
              ArrayList<T> list) {
            if (selected && enabled) {
                  returnValue = a + 1;

                  for (int b = 0; b < list.size(); b++) {
                        if (b != a) {//MINUS ITSELF
                              list.get(b).setSelected(false);
                        }
                  }
            } else {//NOTHING SELECTED
                  if (selected) {
                        returnValue = a + 1;
                  }
            }
            return returnValue;
      }

      public static <T extends AbstractButton> ArrayList<Integer> getSelectedButtons(EventObject e, T[] array, boolean allowDisabled) {
            ArrayList<Integer> listN = new ArrayList<Integer>();
            ArrayList<T> list2 = new ArrayList<T>();

            for (T x : array) {
                  if (!allowDisabled) {
                        if (x.isVisible() && x.isEnabled()) {
                              list2.add(x);
                        }
                  }else{
                        if (x.isVisible()){
                              list2.add(x);
                        }
                  }
            }

            for (int a = 0; a < list2.size(); a++) {
                  if (list2.get(a).isSelected() && list2.get(a).isEnabled()) {
                        listN.add(a + 1);
                  }
            }
            return listN;
      }

      public static IntObject[] getSelectedItems(EventObject e, JComboBox[] array, boolean enabled) {
            ArrayList<JComboBox> list = new ArrayList<JComboBox>();

            for (JComboBox x : array) {
                  if (x.isVisible()) {
                        if (enabled) {
                              list.add(x);
                        } else {
                              if (x.isEnabled()) {
                                    list.add(x);
                              }
                        }
                  }
            }
            IntObject[] returnValue = new IntObject[list.size()];

            for (int a = 0; a < list.size(); a++) {
                  int index = a + 1;
                  Object object = list.get(a).getSelectedItem();
                  if (list.get(a).isEnabled()) {
                        returnValue[a] = new IntObject(index, object);
                  } else {
                        returnValue[a] = new IntObject(index, list.get(a).getItemAt(0));//FIRST DEFAULT VALUE
                  }
            }

            return returnValue;
      }

      //???????????????????????????????????????????????????????????????
      public static void setActionToPL_U() {
            InputMap actionMap = (InputMap) UIManager.getDefaults().get("ScrollPane.ancestorInputMap");
            actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), new AbstractAction() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        throw new UnsupportedOperationException();
                  }
            });
            actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new AbstractAction() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                        throw new UnsupportedOperationException();
                  }
            });
      }

      //++++++++++++++++++++++++++++++++++++++++++++
      /**
       * Loop a list of components UP or Down, by pressing CTRL+DOWN or CTRL+UP
       * <b>Note</b>: method must be place on keyPressed method (of KeyListener)
       *
       * @param comp List of components to loop around
       * @param e KeyEvent needed
       * @param fixedSize number of components to switch focus
       */

      public static void setLoopComponents(JComponent[] comp, int fixedSize, KeyEvent e) {
            int max;//SIZE LOOP
            if (fixedSize <= 0) {
                  max = comp.length;
            } else {
                  max = fixedSize;
            }
            if (KeyEvent.VK_DOWN == e.getKeyCode()) {
                  if (e.isControlDown() && !e.isAltDown()) {
                        for (int a = 0; a < max; a++) {
                              if (a == max - 1) {//Last one to the First one                  
                                    //last one has the focus
                                    if (comp[a].hasFocus()) {
                                          comp[0].requestFocus();
                                    }
                              } else {
                                    if (comp[a].hasFocus()) {
                                          //System.out.println("\t\t: not the last one");
                                          comp[a + 1].requestFocus();
                                    }
                              }
                        }
                  }
            } else if (KeyEvent.VK_UP == e.getKeyCode()) {
                  if (e.isControlDown() && !e.isAltDown()) {
                        for (int a = 0; a < max; a++) {
                              if (a == 0) {//First to the Last one
                                    if (comp[a].hasFocus()) {
                                          comp[max - 1].requestFocus();
                                    }
                              } else {
                                    if (comp[a].hasFocus()) {
                                          comp[a - 1].requestFocus();
                                    }

                              }
                        }
                  }
            }
      }

      public static void setOnlyNumbersAllowed(JTextField tf, int options) {
            AbstractDocument ad = (AbstractDocument) tf.getDocument();
            ad.setDocumentFilter(new LimitN(options));
      }

      public static void setOnlyNumbersAllowed(JTextField tf, int range1, int range2) {
            AbstractDocument ad = (AbstractDocument) tf.getDocument();
            ad.setDocumentFilter(new LimitN(range1, range2));
      }

      public static void setPlaceHolderText(JTextField tf, String phText) {
            MPlaceH mp = new MPlaceH(phText);
            tf.addFocusListener(mp);
      }

      public static void setPlaceHolderText(JTextField tf, String phText, Color unMatch, Color match) {
            MPlaceH mp = new MPlaceH(phText);
            mp.setMatchForegroundColor(Color.GRAY);
            mp.setUnmatchForegroundColor(Color.WHITE);
            tf.addFocusListener(mp);
      }

      public static void setPlaceHolderText(JTextField tf, String phText, ATextAction at) {
            MPlaceH mp = new MPlaceH(phText, at);
            tf.addFocusListener(mp);
      }

      public static void setPlaceHolderText(JTextField tf, String phText, Color unMatch, Color match, ATextAction at) {
            MPlaceH mp = new MPlaceH(phText, at);
            mp.setMatchForegroundColor(Color.GRAY);
            mp.setUnmatchForegroundColor(Color.WHITE);
            tf.addFocusListener(mp);
      }

      //YET TO TEST ++++++++++++++++++
      public static void setCustomTableCellEditor(JTable JT) {
            //JT.setDefaultEditor(String.class, DTCellEditor);
            TableDCellRenderer cellEditor = new TableDCellRenderer(new TextFieldDCellEditor());
            for (int i = 0; i < JT.getColumnCount(); i++) {
                  JT.getColumnModel().getColumn(i).setCellEditor(cellEditor);
            }
      }

      public static void setCustomTableCellRenderer(JTable JT) {
            TableCellRenderer renderer = new TableCellRenderer();
            for (int i = 0; i < JT.getColumnCount(); i++) {
                  JT.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
      }

      public static void setTableRenderer(JTable JT, DefaultTableCellRenderer tcr) {
            for (int a = 0; a < JT.getColumnCount(); a++) {
                  JT.getColumnModel().getColumn(a).setCellRenderer(tcr);
            }
      }

      public static void setForeground(JTable JT) {
            for (int row = 0; row < JT.getRowCount(); row++) {
                  for (int col = 0; col < JT.getColumnCount(); col++) {
                        ((DefaultTableCellRenderer) JT.getCellRenderer(row, col)).setForeground(Color.GRAY);
                  }
            }
      }

      public static void setTableDefaultColumnWidth(JTable JT) {
            TableColumnModel tcm = JT.getColumnModel();
            if (tcm.getColumnCount() >= 1) {
                  tcm.getColumn(0).setMaxWidth(45);
                  tcm.getColumn(1).setMinWidth(300);
            }
      }

      /**
       * Count up or down if the last char of the text is an int by preesing
       * ALT+DOWN or ALT+UP
       * <b>Note</b>: method must be place on keyPressed method (of KeyListener)
       *
       * @param JC JTextComponent for input
       * @param e KeyEvent needed
       *
       * @return the result of the number counting up or down in a string
       */
      public static String getCountComponents(JTextField JC, KeyEvent e) {
            String returnValue = JC.getText();
            if (KeyEvent.VK_DOWN == e.getKeyCode() || KeyEvent.VK_UP == e.getKeyCode()) {
                  if (e.isShiftDown()) {//ALT DOESN'T WORK WITH COMBOBOXES
                        String finalText = "FINALTEX ERROR";
                        String text = JC.getText();
                        String intText = "NEWTEXT ERROR";
                        boolean areDigits = false;
                        int origLength = text.length();
                        int count = text.length();
                        while (true) {
                              //StringIndexOutOfBoundsException: String index out of range: -1
                              if (count >= 1) {
                                    if (Character.isDigit(text.charAt(count - 1))) {
                                          if (count == origLength) {
                                                intText = "";
                                          }

                                          intText = Character.toString(text.charAt(count - 1)) + intText;
                                          count--;
                                          areDigits = true;
                                    } else {
                                          break;
                                    }
                              } else {
                                    break;
                              }
                        }
                        //System.out.println("\tnewText: " + newText + " - matches: ");
                        //newText.matches("[0-9]+")
                        if (!text.isEmpty() && !intText.isEmpty() && areDigits) {
                              //System.out.println("\t\tYES");
                              int res = text.length() - count;
                              //System.out.println("\tres: " + res);
                              int newInt = Integer.parseInt(intText);
                              //System.out.println("\n\tnewInt before: " + newInt);
                              if (KeyEvent.VK_DOWN == e.getKeyCode()) {
                                    if (newInt != 0) {
                                          newInt--;
                                    }
                              } else if (KeyEvent.VK_UP == e.getKeyCode()) {
                                    newInt++;
                              }
                              //System.out.println("\tnewInt after: " + newInt);
                              finalText = text.substring(0, text.length() - res) + (newInt);
                              //System.out.println("\tfinalText: " + finalText);
                              //JC.setText(finalText);
                              //System.out.println("\tgetText: " + JC.getText());
                        } else {
                              finalText = text;
                              //System.out.println("\t\tNO");
                        }
                        if (!finalText.isEmpty()) {
                              //System.out.println("####### RETURNING FINAL TEXT: " + finalText);
                              returnValue = finalText;
                        } else {
                              //System.out.println("####### RETURNING TEXT: " + text);
                              returnValue = text;
                        }
                  }
            }
            //System.out.println("#######FINISH#########");
            return returnValue;
      }

      public static int getLastDigitCharsCountAtEnd(String text) {
            int length = text.length();
            String value = "";
            boolean areDigits = false;
            int returnValue = 0;

            while (true) {
                  if (length >= 1) {
                        if (Character.isDigit(text.charAt(length - 1))) {
                              value = Character.toString(text.charAt(length - 1)) + value;//ADDED IN REVERSE
                              length--;
                              areDigits = true;
                              //returnValue++;
                        } else {
                              if (areDigits) {
                                    returnValue = Integer.parseInt(value);
                              }
                              break;
                        }
                  } else {
                        returnValue = 0;
                        break;
                  }
            }
            return returnValue;
      }

      //+++++++++++++++++++++++++++++++++++++++++++++
      public static <T> T getCastElement(Class<T> clazz, Object element) {
            return clazz.cast(element);
      }

      /*
      public static <T> T[] getCastArray(Class<T> clazz, Object[][] elements) {
            [][] nv = new boolean[foo.length][foo[0].length];
            for (int i = 0; i < nv.length; i++) {
                  nv[i] = Arrays.copyOf(elements[i], elements[i].length);
            }
      }
       */
 /*
      public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
    try {
        return clazz.cast(o);
    } catch(ClassCastException e) {
        return null;
    }
}
       */
      public static boolean isNull(Object ob, boolean print) {
            boolean returnValue = false;
            if (ob == null) {
                  returnValue = true;
                  if (print) {
                        System.out.println("NULL OBJECT");
                  }
            } else {
                  if (print) {
                        System.out.println("NOT NULL OBJECT");
                  }
            }
            return returnValue;
      }

      public static boolean areThisItemsMatchTheSelectedItem(String[] items, JComboBox cb) {
            boolean returnValue = false;
            String selected = cb.getSelectedItem().toString();
            for (int a = 0; a < items.length; a++) {
                  if (selected.equals(items[a])) {
                        returnValue = true;
                  }
            }
            return returnValue;
      }

      public static boolean areThisItemsOnThisComboBox(String[] items, JComboBox cb) {
            boolean returnValue = false;
            for (int a = 0; a < cb.getItemCount(); a++) {
                  String item = cb.getItemAt(a).toString();
                  for (int b = 0; b < items.length; b++) {
                        if (item.equals(items[b])) {
                              returnValue = true;
                        }
                  }
            }

            return returnValue;
      }

      //+++++++++++++++++++++++++++++++++++++++++++++
      public static JTextField[] getClock(JTextField tfMD, JTextField tfMU, JTextField tfSD, JTextField tfSU) {
            JTextField[] clock = new JTextField[4];
            clock[0] = tfMD;
            clock[1] = tfMU;
            clock[2] = tfSD;
            clock[3] = tfSU;
            return clock;
      }
      //+++++++++++++++++++++++++++++++++++++++++++++

      static JTextField tf_ce = new JTextField();
      static DefaultCellEditor DTCellEditor = new DefaultCellEditor(tf_ce) {
            @Override
            public boolean isCellEditable(EventObject anEvent) {
                  return MCell.getCharacterInputandDefault(anEvent,
                          super.isCellEditable(anEvent));
            }
      };
}
