package com.jh.runtime;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;
import java.util.function.Consumer;

public class GameSystem {
    private static GameSystem gameSystem;
    private final GridPane beManaged;
    private final Text score;
    private int intScore;

    public static final int GAME_WIDTH = 10,
        GAME_HEIGHT = 10;

    private final Set<OpenBox> list;

    private GameSystem() {
        list = new HashSet<>();
        beManaged = new GridPane();
        score = new Text("高钒钧幼稚死了啦");


        score.setFont(new Font("宋体", 64));
        restart();
    }

    private static final Random rd = new Random();
    /**
     * 重新开始游戏
     */
    public void restart() {
        intScore = 0;
        list.clear();
        createSetList();
        flush();
        score.setText(String.valueOf(intScore));
    }
    private int level = 1; // 玩家关卡数

    public void nextLevel() {
        level++;

    }
    private void createSetList() {
        for(int i = 0;i < GAME_WIDTH;i++) { // 添加游戏元素
            for(int j = 0;j < GAME_HEIGHT;j++) {
                list.add(new OpenBox(i, j, Type.getType(rd.nextInt(Type.values().length))));
            }
        }
    }
    public boolean isGameOver() {
        List<OpenBox> set = new LinkedList<>(list);
        while(set.size() > 0) {
            OpenBox tmp = set.remove(0);
            Set<OpenBox> boxSet = getGroup(tmp, list);
            set.removeAll(boxSet);
            if(boxSet.size() >= 2) return false;
        }
        flush();
        return true;
    }

    /**
     * 得到{@link GameSystem}实例
     * @return 实例对象
     */
    public static GameSystem getInstance() {
        if(gameSystem != null) return gameSystem;
        synchronized (GameSystem.class) {
            gameSystem = new GameSystem();
        }
        return gameSystem;
    }

    /**
     * 将指定节点加入{@link GameSystem}管理
     * @param array 指定节点
     */
    public void setBlockArray(Collection<OpenBox> array) {
        list.addAll(array);
    }

    /**
     * 点击一个节点时调用
     * @param beClick 被点击的节点
     */
    public void clickNode(OpenBox beClick) {
        Set<OpenBox> group = getGroup(beClick, list);
        int n = group.size();
        if(n < 2) return;
        removeBlocks(group);

        intScore += 5*n*n;
        score.setText(intScore+"");

        beManaged.getChildren().clear();
        flush();
    }
    public Text getScore() {
        return score;
    }

    /**
     * 刷新UI界面
     */
    public void flush() {
        beManaged.getChildren().clear();
        // 添加空白标签防止空行不能显示方块
        for(int i = 0;i < GAME_HEIGHT;i++) {
            Label space = new Label(String.valueOf(i+1));
            space.setPrefHeight(Type.SIZE);
            beManaged.add(space, GAME_WIDTH, i);
        }
        list.forEach(value->{
            beManaged.add(value, value.getNode().getX(), value.getNode().getY());
        });
    }
    public GridPane getGridPane() {
        return beManaged;
    }

    /**
     * 获取指定集合中指定节点周围同色节点
     * @param openBox 指定的节点
     * @param target 指定的集合
     * @return 其周围的节点组，包括其本身
     */
    public static HashSet<OpenBox> getGroup(OpenBox openBox, Collection<OpenBox> target) {
        HashSet<OpenBox> ret = new HashSet<>();
        find(openBox, ret, target);
        return ret;
    }
    private static void find(OpenBox openBox, HashSet<OpenBox> set, Collection<OpenBox> target) {
        if(target.contains(openBox)) {
            if(!set.add(openBox)) {
                return;
            }
            int x = openBox.getNode().getX(),
                    y = openBox.getNode().getY();
            Type type = openBox.getType();
            find(new OpenBox(x-1, y, type), set, target);
            find(new OpenBox(x+1, y, type), set, target);
            find(new OpenBox(x, y-1, type), set, target);
            find(new OpenBox(x, y+1, type), set, target);
        }
    }

    /**
     * 移除指定的节点
     * @param blocks 指定的节点
     */
    public void removeBlocks(Collection<OpenBox> blocks) {
        blocks.forEach(this::removeBlock);
        for(int i = GAME_WIDTH-1;i >= 0;i--) {
            if(getBlocks(i).size() <= 0) {
                for(int j = i+1;j < GAME_WIDTH;j++) {
                    Set<OpenBox> right = getBlocks(j);
                    Set<OpenBox> newRight = moveLeft(right);
                    // 非游戏中的操作，不能调用this.removeBlocks()
                    list.removeAll(right);
                    list.addAll(newRight);
                }

            }
        }
    }

    /**
     * 得到某列所有的节点
     * @param column 指定的列下标
     * @return 该列的所有节点
     */
    public Set<OpenBox> getBlocks(int column) {
        Set<OpenBox> ret = new HashSet<>();
        for(int i = GAME_HEIGHT-1;i >= 0;i--) {
            OpenBox add = getOpenBox(column, i);
            if(add != null) ret.add(add);
        }
        return ret;
    }
    /**
     * 移除指定的节点
     * @param block 指定的节点
     */
    public void removeBlock(OpenBox block) {
        Set<OpenBox> upSet = getUpNodes(block);
        Set<OpenBox> newUpSet = moveDown(upSet);
        list.remove(block);
        list.removeAll(upSet);
        list.addAll(newUpSet);
    }

    /**
     * 通过一对(x,y)获取在list中存在的{@link OpenBox}
     * @param x 对应的x
     * @param y 对应的y
     * @return 在list中对应的{@link OpenBox}，null如果不存在
     */
    public OpenBox getOpenBox(int x, int y) {
        for(Type type : Type.values()) {
            OpenBox openBox1 = new OpenBox(x, y, type);
            if(list.contains(openBox1)) {
                return openBox1;
            }
        }
        return null;
    }

    /**
     * 移除一列最上端的节点
     * @param columnIndex 该节点的列
     */
    public void removeYTop(int columnIndex) {
        for(int i = 0;i < GAME_HEIGHT;i++) {
            OpenBox beMoved = new OpenBox(columnIndex, i, null);
            if(list.remove(beMoved)) {
                if(beManaged != null)
                    this.beManaged.getChildren().remove(beMoved);
                return;
            }
        }
    }

    /**
     * 获得某节点的列，上面的节点
     * @param openBox 指定的节点
     * @return 该节点面的点
     */
    public Set<OpenBox> getUpNodes(OpenBox openBox) {
        Set<OpenBox> set = new HashSet<>();

        int x = openBox.getNode().getX();
        for(int i = 0;i < openBox.getNode().getY();i++) {
            for(Type type : Type.values()) {
                OpenBox openBox1 = new OpenBox(x, i, type);
                if(list.contains(openBox1)) {
                    set.add(openBox1);
                    break;
                }
            }

        }
        return set;
    }

    /**
     * 将节点的纵坐标+1
     * @param set 要重置的节点集
     * @return 重置后的节点集
     */
    public static Set<OpenBox> moveDown(Set<OpenBox> set) {
        Set<OpenBox> ret = new HashSet<>();
        set.forEach(value->{
            ret.add(new OpenBox(value.getNode().getX(), value.getNode().getY()+1, value.getType()));
        });
        return ret;
    }
    /**
     * 将节点的横坐标-1
     * @param set 要重置的节点集
     * @return 重置后的节点集
     */
    public static Set<OpenBox> moveLeft(Set<OpenBox> set) {
        Set<OpenBox> ret = new HashSet<>();
        set.forEach(value->{
            ret.add(new OpenBox(value.getNode().getX()-1, value.getNode().getY(), value.getType()));
        });
        return ret;
    }
}
