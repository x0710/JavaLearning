package com.jh;

import javafx.scene.image.Image;

public enum State {
    NONE("./face.jpg"), FLAG("./flag.jpg"), UNKNOW("./bomb.jpg"), KNOW("./luncecy.png");

    private Image icon;
    private State(String path) {
        if(path != null)
           icon = new Image(path);
    }
    public State getNext() {
        if(this == NONE) return FLAG;
        if(this == FLAG) return UNKNOW;
        if(this == UNKNOW) return NONE;
        return KNOW;
    }
    public Image getIcon() {
        return icon;
    }
}
