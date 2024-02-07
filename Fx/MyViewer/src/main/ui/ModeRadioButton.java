package main.ui;

import static com.connection.Mode.*;

import com.connection.Mode;

import javafx.scene.control.RadioButton;

public class ModeRadioButton extends RadioButton{
    private Mode mode;

    public ModeRadioButton(Mode mode) {
        this(mode.name());
        this.mode = mode;
    }
    public ModeRadioButton(String text) {
        super(text);
        mode = Mode.valueOf(text);
    }
    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
    
}
