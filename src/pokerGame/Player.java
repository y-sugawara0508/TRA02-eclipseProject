package pokerGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private int id;
    private int chips;
    private boolean isDrop;
    private List<Card> hand = new ArrayList<>();

    private static final int FIRST_CHIPS_NUM = 10000;

    public Player(int id) {
        setId(id);
        setChips(FIRST_CHIPS_NUM);
    }

    public void bit(int bitChips, Game game) {
        game.addPot(bitChips);
        game.setBit(bitChips);
        this.chips -= bitChips;
    }

    public void call(Game game) {
        bit(game.getBit(), game);
    }

    public void raise(int bitChips, Game game) {
        bit(bitChips, game);
    }

    public void drop() {
        this.isDrop = true;
    }

    public void setHand(List<Card> cards) {
        hand.clear();
        hand.addAll(cards);
    }

    public List<Card> getHand() {
        return hand;
    }

    public void discardAndDraw(List<Integer> indices, List<Card> stock) {
        Collections.sort(indices, (a, b) -> b - a);
        for (int index : indices) {
            if (index >= 0 && index < hand.size()) {
                hand.remove(index);
            }
        }
        while (hand.size() < 5 && !stock.isEmpty()) {
            hand.add(stock.remove(0));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }
}
