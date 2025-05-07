package re_pokerGame;

public class Pot {
    
    // ポットに入っているチップの総額
    private int chips;
    
    // ポットの初期化（チップを0に設定）
    public void initialize() {
        chips = 0;
    }
    
    // チップをポットに追加する
    public void add(int bit) {
        chips += bit;
    }

    // ポットの総チップ数を取得する
    public int getChips() {
        return chips;
    }
}
