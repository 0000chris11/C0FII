package com.cofii2.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class Custom {
    // OLD WAY-----------------------------------------------------
    public static String getOldDist(int length, ToggleButton... btns) {
        StringBuilder dist = new StringBuilder("NONE");
        boolean distPresent = false;
        int distCount = 0;
        for (int a = 0; a < length; a++) {
            if (btns[a].isSelected()) {
                if (!distPresent) {
                    dist.delete(0, dist.length());
                    dist.append("X0: ");
                    distPresent = true;
                }
                dist.deleteCharAt(1);
                dist.insert(1, Integer.toString(++distCount));
                dist.append(Integer.toString(a + 1) + "_");
            }
        }
        if (distCount > 0) {// DELETE LAST '_'
            dist.deleteCharAt(dist.length() - 1);
        }
        return dist.toString();
    }

    public static String getOldImageC(int length, ToggleButton... btns) {
        StringBuilder imageC = new StringBuilder("NONE");
        for (int a = 0; a < length; a++) {
            if (btns[a].isSelected()) {
                imageC.delete(0, imageC.length());
                imageC.append("C" + (a + 1));
            }
        }
        return imageC.toString();
    }

    public static String getImageCPath(int length, TextField tf, ToggleButton... btns) {
        StringBuilder imageCPath = new StringBuilder("NONE");
        for (int a = 0; a < length; a++) {
            if (btns[a].isSelected()) {
                imageCPath.delete(0, imageCPath.length());
                imageCPath.append(tf.getText());
            }
        }
        return imageCPath.toString();
    }

    public static int[] getSelectedDist(String dist) {
        final int[] dists;
        if (!dist.equals("NONE")) {
            String[] split = dist.split("[ ]\\d");
            dists = new int[split.length];

            int[] indexs = { 0 };
            Arrays.asList(split).forEach(s -> dists[indexs[0]++] = Integer.parseInt(s.trim()));
        } else {
            dists = new int[0];
        }
        return dists;
    }

    public static String getDist(String[] dist) {
        StringBuilder returnValue;
        int[] indexs = { 0 };
        Arrays.asList(dist).forEach(s -> {
            if (s.equals("Yes")) {
                indexs[0]++;
            }
        });
        if (indexs[0] > 0) {
            returnValue = new StringBuilder("X").append(indexs[0]).append(":");
            Arrays.asList(indexs).forEach(i -> returnValue.append(" " + i + ","));
            returnValue.deleteCharAt(returnValue.length() - 1);//TEST
        } else {
            returnValue = new StringBuilder("NONE");
        }
        return returnValue.toString();
    }

    /*
     * public static int[] getSelectedDist(String[] dist){ int[] dists = new
     * int[dist.length]; Arrays.asList(dist).forEach(s -> { if(s.equals("Yes")){
     * 
     * } }); }
     */
    // ------------------------------------
    private Custom() {

    }
}
