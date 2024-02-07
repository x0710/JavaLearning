package com.tool;

public class TimeRecoder {
    private long recordFirst, recordEnd;

    public TimeRecoder() {
        setNewPoint();
    }
    public void setNewPoint() {
        recordFirst = System.currentTimeMillis();
    }
    public double getSecondToRecord() {
        recordEnd = System.currentTimeMillis();
        return (recordEnd-recordFirst)/1000d;
    }
    public int getMillionsToRecord() {
        recordEnd = System.currentTimeMillis();
        return (int)(recordEnd-recordFirst);
    }
}
