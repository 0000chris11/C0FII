package com.cofii2.textControl.javafx;

@FunctionalInterface
public interface ITextFilter {
    public String getFilteredText(String text);
}
