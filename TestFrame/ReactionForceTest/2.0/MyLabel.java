import java.awt.*;

public class MyLabel extends Label {
    Status s;
    String setVal;

    public MyLabel() {
        s = Status.INITIAL;
        setVal = "speed: " + "null" + "        ";
    }
    public void setText(String str, Status t) {
        s = t;
        setVal = "Speed: ".concat(str).concat("ms") + "        ";
        super.setText(setVal + t);
    }
    public void setNextStatus() {
        Status temp;
        switch(s) {
            case RESULTS:
                temp = Status.PROHIBITION;
                break;
            case PROHIBITION:
                temp = Status.ALLOWABLE;
                break;
            case ALLOWABLE:
                temp = Status.RESULTS;
                break;
            default:
                temp = Status.INITIAL;
                break;
        }
        setStatus(temp);
    }
    public void setStatus(Status s) {
        super.setText(setVal + s);
    }
}