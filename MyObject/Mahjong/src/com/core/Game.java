package com.core;

import com.depend.Card;
import com.depend.type.CardType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * 一局游戏的实例
 */
public final class Game {
    private Player[] players;
    private Player landlord;

    public Game(Player[] players) {
        this.players = players;
        licensingCards();
    }
    private void licensingCards() {
        ArrayList<Card> cards = Licensing.getShuffledCards();
        R:while(true)
            for(Player player : players) {
                if(cards.size() > 3) // 如果牌堆数目大于3就抓牌
                    player.draw(cards.remove(0));
                else if(landlord == null) {
                    landlord = landlord(); // 叫地主
                }
                else {
                    if(cards.isEmpty())
                        break R;
                    landlord.draw(cards.remove(0));
                }
            }
        play();
    }

    private ArrayList<TreeSet<Card>> record = new ArrayList<>();
    /**
     * 开始打牌
     * @return 最先出完牌的玩家
     */
    private Player play() {
        TreeSet<Card> last = null;
        CardType lastType = null;
        while(true) {
            for(int i = 0;i < players.length;) {
                if(players[i] != landlord)
                    continue;
                TreeSet<Card> play = players[i].play(last);
                CardType ct = CardType.forType(play);
                if(!(lastType.equals(ct) && ct != null))
                    continue;
                lastType = ct;
                // 自加
                i++;
            }
        }
    }
    private Player landlord() {
        int maxScore = 0;
        Player maxScorePlayer = null;
        while(true)
            for(Player player : players) {
                int playerScore = player.score();
                if(playerScore > maxScore) {
                    maxScore = playerScore;
                    maxScorePlayer = player;
                }
                if (playerScore >= 3)
                    return maxScorePlayer;
            }
    }
}
