package main.ui;

import java.util.ArrayList;

import javafx.scene.layout.VBox;

public class ColumnContainer extends VBox {
    public ColumnContainer() {

    }
    public String getTypeByIndex(int index) {
        return ((ColumnType)getChildren().get(index)).getTypeOther();
    }
    public void addColumn(ColumnType c) {
        getChildren().add(c);
    }
    public String toSQL() {
        StringBuffer sb = new StringBuffer("(");
        ArrayList<String> pk = new ArrayList<>();
        getChildren().forEach(action->{
            ColumnType ct = ((ColumnType)action);
            if(ct.isPK()) pk.add(ct.getColumnName());
            sb.append(ct.getColumnName()).append(" ").append(ct.getTypeOther()).append(", ");
        });
        if(pk.isEmpty()) {
            sb.setLength(sb.length()-2);
        }
        else {
            for(String pkCol:pk) {
                sb.append("PRIMARY KEY(").append(pkCol).append(", ");
            }
            sb.setLength(sb.length()-2);
            sb.append(")");
        }
        sb.append(")");
        return sb.toString();
    }
    // CREATE TABLE tb_name(id int(2), name char(5), sex enum('m','w'), primary key(id, sex))
}
