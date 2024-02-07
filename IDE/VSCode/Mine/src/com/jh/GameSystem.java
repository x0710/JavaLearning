package com.jh;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class GameSystem {
    private static GameSystem instance = new GameSystem();
    private Random random;
    private final MineGame game;

    private GameSystem() {
        random = new Random();
        game = new MineGame();
    }

    public static GameSystem getInstance() {
        return instance;
    }

    public void restart(int width, int height, int mineAmount) {
        if(width*height<=mineAmount) throw new AmountException("The number of mines is too much!");
        HashSet<Cell> cells = summonMines(width, height, mineAmount);
        for(int i = 0;i < width;i++) {
            for(int j = 0;j < height;j++) {
                Cell w = new Cell(i, j, State.NONE, false);
                cells.add(w);
            }
        }
        game.setMines(cells);
    }
    private HashSet<Cell> summonMines(int width, int height, int mineAmount) {
        HashSet<Cell> add = new HashSet<>();
        for(int i = 0;i < mineAmount;i++) {
            Cell a;
            do{
               int x, y;
                x = random.nextInt(width);
                y = random.nextInt(height);
                a = new Cell(x, y, State.FLAG, true);
            }
            while(!add.add(a));
        }
        return add;
    }
    public void showStage() {
        game.show();
    }
    public HashSet<Cell> getRoundCells(Cell target) {
        return getRound(game.getMines(), target);
    }
    /**
     * 获取target周围的Cell（不含target）
     * @param source
     * @param target
     * @return
     */
    public static HashSet<Cell> getRound(Collection<Cell> source, Cell target) {
        HashSet<Cell> set = new HashSet<>();
        getRound(source, target, set);
        set.remove(target);
        return set;
    }
    /**
     * 获取target周围的Cell（含target）
     * @param source 在此集合搜索
     * @param target 集合中以其为中心搜索
     * @param result 搜索到的结果集
     */
    private static void getRound(Collection<Cell> source, Cell target, HashSet<Cell> result) {
        if(source.contains(target)) {
            if(result.add(target)) {
                getRound(source, new Cell(target.getX()-1, target.getY()-1, null, false), result);
                getRound(source, new Cell(target.getX()+1, target.getY()-1, null, false), result);
                getRound(source, new Cell(target.getX()-1, target.getY()+1, null, false), result);
                getRound(source, new Cell(target.getX()+1, target.getY()+1, null, false), result);

                getRound(source, new Cell(target.getX()-1, target.getY(), null, false), result);
                getRound(source, new Cell(target.getX(), target.getY()-1, null, false), result);
                getRound(source, new Cell(target.getX()+1, target.getY(), null, false), result);
                getRound(source, new Cell(target.getX(), target.getY()+1, null, false), result);
            }

        }
    }
}

class AmountException extends RuntimeException {
    public AmountException() {}
    public AmountException(String message) {
        super(message);
    }
}