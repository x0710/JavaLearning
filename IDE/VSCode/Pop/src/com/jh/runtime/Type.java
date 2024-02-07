package com.jh.runtime;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum Type {
    GREEN(Color.valueOf("green")), RED(Color.valueOf("red")),
    BLUE(Color.valueOf("blue")), YELLOW(Color.valueOf("yellow")), PURPLE(Color.valueOf("purple"));

    public static final int SIZE = 20;
    private final int index;
    private final Color color;
    private final WritableImage image;

    Type(Color color) {
        this.color = color;
        image = new WritableImage(SIZE, SIZE);
        PixelWriter pw = image.getPixelWriter();
        for(int i = 0;i < image.getWidth();i++) {
            for(int j = 0;j < image.getHeight();j++) {
                pw.setColor(i, j, color);
            }
        }
        index = this.ordinal();
    }
    public Image getImage() {
        return image;
    }
    public Color getColor() {
        return color;
    }
    public static Type getType(int ordinal) {
        for(Type type : Type.values()) {
            if(ordinal == type.index) return type;
        }
        return null;
    }
}
