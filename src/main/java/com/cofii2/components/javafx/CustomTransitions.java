package com.cofii2.components.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class CustomTransitions {
    
    private Node node;

    private int maxTransition = 10;
    private List<Transition> transitionList = new ArrayList<>(maxTransition);
    //---------------------------------------------------------
    public CustomTransitions(Node node){
        this.node = node;
    }
    //---------------------------------------------------------
    public void addTranslateToRight(Duration duration){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(node);
        translateTransition.setDuration(duration);
        translateTransition.setFromX(-30);
        translateTransition.setToX(0);

        transitionList.add(translateTransition);
    }
    public void addFadeIn(Duration duration){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(duration);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);

        transitionList.add(fadeTransition);
    }
    public void play(){
        transitionList.forEach(Transition::play);
    }
    public void stop(){
        transitionList.forEach(Transition::stop);
    }
    //------------------------------------------
    public Node getNode() {
        return node;
    }
    public void setNode(Node node) {
        this.node = node;
    }
    public int getMaxTransition() {
        return maxTransition;
    }
    public void setMaxTransition(int maxTransition) {
        this.maxTransition = maxTransition;
    }
    public List<Transition> getTransitionList() {
        return transitionList;
    }
    public void setTransitionList(List<Transition> transitionList) {
        this.transitionList = transitionList;
    }
    
}
