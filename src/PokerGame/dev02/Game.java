package PokerGame.dev02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    List<Card> stockCards;
    private List<Player> players;
    private int pot;
    private int bit;
    private boolean isFirst;
    private static final int PLAYER_MIN_COUNT = 3;

    public boolean initialize(int playerNum) {
        stockCards = new ArrayList<>();
        for (Suit suit : Suit.values())
            for (int j = 1; j <= Card.MAX_CARD_NUM; j++) stockCards.add(new Card(suit, j));
        Collections.shuffle(stockCards);

        players = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            players.add(new Player(i));
        }

        return players.size() >= PLAYER_MIN_COUNT;
    }

    public boolean start(int firstBit, int entryBit) {
        for (Player player : players) {
            if (player.getChips() < entryBit + firstBit) return false;
            player.bit(entryBit, this);
        }

        players.get(0).bit(firstBit, this);
        isFirst = true;
        return true;
    }

    public boolean actionUpdate(int playerId, int selectedAction, int raiseBit) {
        switch (selectedAction) {
            case 0: getPlayer(playerId).call(this); break;
            case 1: getPlayer(playerId).raise(raiseBit, this); break;
            case 2:
            default: getPlayer(playerId).drop(); break;
        }
        return true;
    }

    public void dealCards() {
        for (Player player : players) {
            List<Card> hand = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (!stockCards.isEmpty()) {
                    hand.add(stockCards.remove(0));
                }
            }
            player.setHand(hand);
        }
    }

    public Player judgeWinner() {
        Player winner = null;
        int max = -1;

        for (Player player : players) {
            if (player.getChips() == 0 || player.getHand().isEmpty()) continue;

            for (Card card : player.getHand()) {
                if (card.getNumber() > max) {
                    max = card.getNumber();
                    winner = player;
                }
            }
        }

        return winner;
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void addPot(int pot) {
        this.pot += pot;
    }

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }
}
