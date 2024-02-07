package com.core.database;

import java.util.HashMap;

public class TableLine {
    private HashMap<String, String> data;

    public TableLine() {
        data = new HashMap<>();
    }
    public void putData(String name, String value) {
        data.put(name, value);
    }
    public String getDataByName(String name) {
        return data.get(name);
    }

}
