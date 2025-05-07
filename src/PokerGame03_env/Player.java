package PokerGame03_env;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    
    // プレイヤーID、所持チップ、手札のカード
    private int id;
    private int chips;
    private List<Card> handCards;
    
    // プレイヤーの初期化
    public Player(int id, int chips) {
        handCards = new ArrayList<Card>();
        this.id = id;
        this.chips = chips;
    }
    
    // チップをポットにビットする
    public void bit(Pot pot, int bit) {
        pot.add(bit);
        chips -= bit;
    }
    
    // プレイヤーの手札を文字列として取得（カードの表示形式）
    public String handToString() {
        return handCards.stream()
            .map(Card::toString)
            .collect(Collectors.joining(", "));
    }
    
    // プレイヤーの手札にカードを追加
    public void addHand(Card card) {
        handCards.add(card);
    }
    
    // プレイヤーの手札を取得
    public List<Card> getHand(){
        return handCards;
    }
    
    // プレイヤーIDを取得
    public int getId() {
        return id;
    }

    // プレイヤーの所持チップを取得
    public int getChips() {
        return chips;
    }

    // プレイヤーの所持チップを設定
    public void setChips(int chips) {
        this.chips = chips;
    }

    // コール（最小レイズ額をポットにビット）
    public void call(Game game) {
        bit(game.getPot(), game.getMinimumRaiseAmount());
    }

    // レイズ（指定した額をポットにビット）
    public void raise(Game game, int raiseAmount) {
        bit(game.getPot(), raiseAmount);
    }
}

// プレイヤーの行動（コール・レイズ・ドロップ）を表す
class PlayerAction {
    public int action;   // 0:コール, 1:レイズ, 2:ドロップ
    public int raiseAmount;  // レイズ額
}
