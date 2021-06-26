package com.cofii2.textControl.javafx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;

/**
 * Class that adds text control to 1 or more TextField (UNTESTED)
 */
public class TextMatchControl {

    public static final int MATCH_ANY = 0;
    public static final int IF_TRUE = 1;
    // -----------------------------------------------
    private IMatch imatch;

    private TextField[] tfs;

    /**
     * if true the patter always be the first 'if'; the parent per say
     */
    private boolean patterFirst = true;
    private Map<String[], Integer> arraysList = new HashMap<>();
    private Map<Pattern, Integer> patternsList = new HashMap<>();

    private ITextFilter itext;

    private boolean match = false;

    // -----------------------------------------------
    public void addMatch(String... array) {
        arraysList.put(array, MATCH_ANY);
    }

    public void addMatch(int searchOption, String... array) {
        if (searchOption == 0) {
            arraysList.put(array, searchOption);
        } else {
            throw new IllegalArgumentException("C0FII: Only MATCH_ANY (0) allowed");
        }
    }

    public void addPattern(Pattern pattern, int option) {
        if (option == 1) {
            patternsList.put(pattern, option);
        } else {
            throw new IllegalArgumentException("C0FII: Only IF_TRUE (1) allowed");
        }
    }

    public void addSingleTextFilter(ITextFilter itext) {
        this.itext = itext;
    }

    // -----------------------------------------------
    private void matchListFunction(String text) {
        System.out.println("text: " + text);
        arraysList.forEach((a, m) -> {
            if (m == MATCH_ANY) {
                match = Arrays.asList(a).stream().anyMatch(element -> {
                    System.out.println("\t" + element);
                    return text.equalsIgnoreCase(element);
                });
            }
        });
    }

    private void tfsTextProperty(String newValue) {
        if (itext != null) {
            newValue = itext.getFilteredText(newValue);
        }

        final String text = newValue;
        if (!patternsList.isEmpty() && patterFirst) {
            patternsList.forEach((a, m) -> {
                if (m == IF_TRUE && a.matcher(text).matches()) {
                    matchListFunction(text);
                }
            });
        } else {
            matchListFunction(text);
        }

        // ------------------------------------------------------
        imatch.getMatch(match);
    }

    // -----------------------------------------------
    private void init() {
        Arrays.asList(tfs)
                .forEach(e -> e.textProperty().addListener((obs, oldValue, newValue) -> tfsTextProperty(newValue)));
    }

    // -----------------------------------------------
    public TextMatchControl(IMatch imatch, TextField... tfs) {
        this.imatch = imatch;
        this.tfs = tfs;
        init();
    }
    // -----------------------------------------------
}
