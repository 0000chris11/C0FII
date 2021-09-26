/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.methods;

import com.cofii2.stores.StringBoolean;
import java.io.File;
import java.util.List;

/**
 *
 * @author C0FII
 */
public class MFile {

      private static final String[] fileTypes = { "jpg", "png" };

      private MFile() {
            throw new IllegalStateException("Private Constructor");
      }

      // ++++++++++++++++++++++++++++++++++++++++++++
      public static List<File> getAllSubFilesInDirectory(String directoryPath, List<File> files) {
            File directory = new File(directoryPath);
            // Get all files from a directory.
            File[] fList = directory.listFiles();
            if (fList != null)
                  for (File file : fList) {
                        if (file.isFile()) {
                              files.add(file);
                        } else if (file.isDirectory()) {
                              getAllSubFilesInDirectory(file.getAbsolutePath(), files);
                        }
                  }
            return files;
      }


      public static void printElements(File f) {
            System.out.println(f.getName());
            String[] elements = f.list();
            int c = 0;
            for (String x : elements) {
                  System.out.println("\t" + c++ + ": " + x);
            }
      }

      public static String[] getSplitFilePath(File f) {
            String path = f.getPath();
            return path.split("\\.(?=[^\\.]+$)");
      }

      public static String[] getSplitFilePath(String f) {
            return f.split("\\.(?=[^\\.]+$)");
      }

      /**
       * Testing if file Exist (OLD TEST)
       * 
       * @deprecated only for testing
       * @param file    file to test
       * @param convert idr
       * @return
       */
      public static StringBoolean doesFileExist(File file, boolean convert) {
            System.out.println("TEST doesFileExist");
            StringBoolean returnValue = null;
            boolean exist = false;

            String path = file.getPath().toLowerCase();
            System.out.println("\tfilePath: " + path);
            String fileType = path.split("\\.(?=[^\\.]+$)")[1];
            System.out.println("\tfileType: " + fileType);
            if (!file.exists()) {
                  for (String x : fileTypes) {
                        file = new File(path.replace(fileType, x));
                        if (file.exists()) {
                              if (convert) {
                                    returnValue = new StringBoolean(file.getPath(), true);
                              } else {
                                    returnValue = new StringBoolean(x, true);
                              }
                              exist = true;
                              break;
                        }
                  }
                  if (!exist) {
                        returnValue = new StringBoolean("none", false);
                  }
            } else {
                  if (convert) {
                        returnValue = new StringBoolean(path, true);
                  } else {
                        returnValue = new StringBoolean(fileType, true);
                  }
            }

            return returnValue;
      }
}
