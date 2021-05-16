/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofii2.stores;

/**
 * Class for changing the color of the JVM's terminal
 * 
 * @author C0FII
 */
public class CC {
      //ANSI
      public static final String RESET = "\u001B[0m";
      public static final String BLACK = "\u001B[30m";
      public static final String RED = "\u001B[31m";
      public static final String GREEN = "\u001B[32m";
      public static final String YELLOW = "\u001B[33m";
      public static final String BLUE = "\u001B[34m";
      public static final String PURPLE = "\u001B[35m";
      public static final String CYAN = "\u001B[36m";
      public static final String WHITE = "\u001B[37m";
      //------------------------------------------
      public static final String WHITE_SPACE = ""       /* dummy empty string for homogeneity */
      + "\\u0009" // CHARACTER TABULATION
      + "\\u000A" // LINE FEED (LF)
      + "\\u000B" // LINE TABULATION
      + "\\u000C" // FORM FEED (FF)
      + "\\u000D" // CARRIAGE RETURN (CR)
      + "\\u0020" // SPACE
      + "\\u0085" // NEXT LINE (NEL) 
      + "\\u00A0" // NO-BREAK SPACE
      + "\\u1680" // OGHAM SPACE MARK
      + "\\u180E" // MONGOLIAN VOWEL SEPARATOR
      + "\\u2000" // EN QUAD 
      + "\\u2001" // EM QUAD 
      + "\\u2002" // EN SPACE
      + "\\u2003" // EM SPACE
      + "\\u2004" // THREE-PER-EM SPACE
      + "\\u2005" // FOUR-PER-EM SPACE
      + "\\u2006" // SIX-PER-EM SPACE
      + "\\u2007" // FIGURE SPACE
      + "\\u2008" // PUNCTUATION SPACE
      + "\\u2009" // THIN SPACE
      + "\\u200A" // HAIR SPACE
      + "\\u2028" // LINE SEPARATOR
      + "\\u2029" // PARAGRAPH SEPARATOR
      + "\\u202F" // NARROW NO-BREAK SPACE
      + "\\u205F" // MEDIUM MATHEMATICAL SPACE
      + "\\u3000" // IDEOGRAPHIC SPACE
      ;        
}
