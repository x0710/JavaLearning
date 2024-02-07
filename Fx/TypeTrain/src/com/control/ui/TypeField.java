package com.control.ui;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TypeField extends FlowPane {
    public static final Color CORRECT = Color.valueOf("#87CEFA");
    public static final Color ERROR = Color.valueOf("#DC143C");
    public static final Color UNTYPE = Color.valueOf("#000");
    public static final Font TYPE_FONT = Font.font(20);

    private TypeBar parent;

    private HBox show;
    private TextField input;

    /**
     * @param val 显示文字
     */
    public TypeField(String val) {
        this(val, null);
    }
    public TypeField(String val, TypeBar parent) {
        this.parent = parent;
        setOrientation(Orientation.VERTICAL);
        // setVgap(5);
        show = new HBox();
        input = new TextField();
        show.setPrefHeight(20);
        show.setBorder(new Border(new BorderStroke(Color.AQUA, BorderStrokeStyle.DASHED, new CornerRadii(5), new BorderWidths(10), Insets.EMPTY)));
        setTypingText(val);
        show.setAlignment(Pos.CENTER);
        input.setPrefHeight(40);
        input.setPrefWidth(show.getWidth());
        input.setFont(TYPE_FONT);
        // input.focusedProperty().addListener((obs, oldValue, newValue)->{
        //     if(newValue) {
        //         // input.setBorder(null);
        //         // input.setBorder(new Border(new BorderStroke(Color.LIGHTPINK, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(2), Insets.EMPTY)));
        //     }
        //     else {
        //         input.setBorder(new Border(new BorderStroke(Color.LIGHTGREEN, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(2), Insets.EMPTY)));
        //     }
        // });
        
        // input.setText("12345678901234567890123456789012345678901234567890123456789012345");
        
        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int changed = newValue.length()-oldValue.length();
                final int index = newValue.length();
                if(changed > 0) { // 输入
                    char typed = newValue.charAt(newValue.length()-1);
                    Text c = (Text)show.getChildren().get(index-1);
                    if(typed==c.getText().toCharArray()[0]) {
                        typedListenerList.forEach(action->action.invalidated(new SimpleBooleanProperty(true)));
                        c.setFill(CORRECT);
                    }
                    else {
                        typedListenerList.forEach(action->action.invalidated(new SimpleBooleanProperty(false)));
                        c.setFill(ERROR);
                    }
                }
                else { // 删除
                    Text c = (Text)show.getChildren().get(index);
                    // index = newValue.length();
                    deletedListenerList.forEach(action->action.invalidated(new SimpleBooleanProperty(CORRECT.equals(c.getFill()))));
                    c.setFill(UNTYPE);
                }
                // System.out.println("在下标为"+index+"已经设置颜色为red");
                if(parent != null && index >= show.getChildren().size()) {
                    parent.nextInput();
                    // System.out.println("换行！");
                }
            }
        });
        getChildren().addAll(show, input);
        setAlignment(Pos.CENTER);
    }

    public void setTypingText(String text) {
        int sub = text.length()-show.getChildren().size();
        for(int i = 0;i < sub;i++) {
            Text add = new Text();
            add.setFont(TYPE_FONT);
            show.getChildren().add(add);
        }
        // show.getChildren().clear();
        // for(char c:text.toCharArray()) {
        //     show.getChildren().add(new Text(String.valueOf(c)));
        // }
        char[] chars = text.toCharArray();
        for(int i = 0;i < chars.length;i++) {
            Text textC = (Text)show.getChildren().get(i);
            textC.setText(String.valueOf(chars[i]));
            textC.setFill(UNTYPE);
        }
    }
    public void clearInputText() {
        input.clear();
    }

    public void requestFocus() {
        input.requestFocus();
    }

    private ObservableList<InvalidationListener> typedListenerList = FXCollections.observableArrayList();
    private ObservableList<InvalidationListener> deletedListenerList = FXCollections.observableArrayList();
    
    public ObservableList<InvalidationListener> onTypedCorrect() {
        return this.typedListenerList;
    }
    public ObservableList<InvalidationListener> onDeleted() {
        return this.deletedListenerList;
    }
    /**
     * 拆分一个长文章
     * @param text 要拆分的文章
     * @param count 以这个数目的字数为一个文段进行拆分
     * @return 拆分后的结果
     */
    public static ArrayList<String> subPassage(String text, final int count) {
        String[] texts = text.split("(?:\\r?\\n)+");
        ArrayList<String> lines = new ArrayList<>();
        for(int i = 0;texts.length>i;i++) {
            if(texts[i].length()>count) {// 拆分句子
                for(int index = 0;index < texts[i].length();) {
                    String line;
                    try {
                        line = texts[i].substring(index, index+count);
                    }
                    catch(StringIndexOutOfBoundsException e) {
                        line = texts[i].substring(index, texts[i].length()-1);
                        lines.add(line);
                        break;
                    }
                    index += count;
                    lines.add(line);
                }
            }
            else {
                lines.add(texts[i]);
            }
        }
        return lines;
    }
    public void setInputable(boolean value) {
        input.setDisable(!value);
    }
}
