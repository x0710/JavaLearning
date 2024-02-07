package com.control.ui;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class TypeBar extends VBox{
    private final ObservableList<TypeField> fields = FXCollections.observableArrayList();
    private List<String> contents;

    private Iterator<String> contentsIterator;
    private int inputerIndex;
    
    private SimpleIntegerProperty corrects, incorrects, typed;
    private int allWords;

    public TypeBar(List<String> contents, int amount) {
        setTypeContent(contents);
        incorrects = new SimpleIntegerProperty(0);
        typed = new SimpleIntegerProperty(0);
        corrects = new SimpleIntegerProperty(0);
        
        setTypeFieldAmount(amount);
        // corrects.addListener((obs, oldV, newV)->typed.set(newV.intValue()+incorrects.get()));
        // incorrects.addListener((obs, oldV, newV)->typed.set(newV.intValue()+corrects.get()));
        
        getChildren().addAll(fields);
        setAlignment(Pos.CENTER);
        ((TypeField)fields.get(0)).setInputable(true);
    }
    public void setTypeFieldAmount(int amount) {
        fields.clear();
        for(int i = 0;i < amount;i++) {
            String text;
            if(contentsIterator.hasNext()) text = contentsIterator.next();
            else text = "";
            TypeField added = new TypeField(text, this);
            added.setInputable(false);
            added.onDeleted().add(value->{
                if(((SimpleBooleanProperty)value).get()) corrects.set(corrects.get()-1);
                else incorrects.set(incorrects.get()-1);
                typed.set(corrects.get()+incorrects.get());
            });
            added.onTypedCorrect().add(value->{
                if(((SimpleBooleanProperty)value).get()) corrects.set(corrects.get()+1);
                else incorrects.set(incorrects.get()+1);
                typed.set(corrects.get()+incorrects.get());
            });
            // added.onTypedCorrect().add(System.out::println);
            fields.add(added);
        }
    }
    public void setTypeContent(List<String> contents) {
        this.contents = contents;
        this.contentsIterator = this.contents.iterator();
        this.allWords = computeWordsLength(contents);
    }
    private void nextPage() {
        for(int i = 0;i < fields.size();i++){
            TypeField field = fields.get(i);
            if(contentsIterator.hasNext()) {
                field.setTypingText(contentsIterator.next());
            }
            else { // 打字完毕无可显示文字
                field.setTypingText("");
            }
        }
    }
    public SimpleIntegerProperty onCorrects() {
        return corrects;
    }
    public SimpleIntegerProperty onIncorrects() {
        return incorrects;
    }
    public SimpleIntegerProperty onTyped() {
        return typed;
    }
    public static int computeWordsLength(Collection<String> value) {
        int ret = 0;
        for(Iterator<String> iterator = value.iterator();iterator.hasNext();) {
            ret += iterator.next().length();
        }
        return ret;
    }
    TypeField nextInput() {
        TypeField field = fields.get(inputerIndex);
        field.setInputable(false);
        field = fields.get(nextIndex());
        /*下条不能删除！这里掉了大坑，原来在setTypingText()里面有clear()会调用TypeField的textProperty()
          同时是在textProperty()调用的nextPage()又间接调用了setTypingText()的clear()从而导致了再次调用textProperty()死循环
         */
        field.clearInputText(); // 该行可删除，在进行setTypingText时会自动进行clear()
        if(inputerIndex == 0) {
            nextPage();
        }
        field.setInputable(true);
        field.requestFocus();
        return field;
    }
    private int nextIndex() {
        if(++inputerIndex>=fields.size()) {
            inputerIndex = 0;
        }
        return inputerIndex;
    }
    public int getAllWords() {
        return allWords;
    }
}
