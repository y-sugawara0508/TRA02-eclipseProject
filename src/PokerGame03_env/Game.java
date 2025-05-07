package PokerGame03_env;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    // ゲームに参加する最低人数
    private static final int PLAYER_MIN_COUNT = 3;

    // フィールド（カードのデッキ、プレイヤーリスト、ポットなど）
    private List<Card> stockCards;
    private List<Player> players;
    private List<Player> table;
    private boolean isEnd;
    private Pot pot;

    // 最小レイズ額
    private int minimumRaiseAmount;

    // ゲームの初期化（カードのデッキをシャッフルし、プレイヤーにカードを配る）
    public Game() {
        stockCards = new ArrayList<Card>();
        for (Suit suit : Suit.values())
            for (int i = 1; i <= Card.MAX_CARD_NUM; i++) stockCards.add(new Card(suit, i));
        
        players = new ArrayList<Player>();
        pot = new Pot();
    }

    // ゲームの開始とプレイヤーの初期化
    public boolean initialize(int playerNum, int firstChips) {
        // プレイヤーの生成
        for (int i = 1; i <= playerNum; i++) players.add(new Player(i, firstChips));
        
        // プレイヤーテーブルに追加
        table = new ArrayList<Player>();
        for (Player p : players) table.add(p);
        
        // カードをシャッフル
        Collections.shuffle(stockCards);
        
        // 各プレイヤーに手札を配る
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                if (!stockCards.isEmpty()) player.addHand(stockCards.remove(0));
            }
        }

        return players.size() >= PLAYER_MIN_COUNT;
    }

    // ゲーム開始時に各プレイヤーが参加費を払う
    public boolean start(int entryBit) {
        pot.initialize();
        for (Player player : table) {
            if (player.getChips() >= entryBit) player.bit(pot, entryBit);
            else return false;
        }
        return true;
    }

    // ランダムに選ばれたプレイヤーが1000チップを払う
    public boolean firstBit(int firstBit) {
        Random random = new Random();
        Player selectedPlayer = table.get(random.nextInt(table.size()));

        if (selectedPlayer.getChips() >= firstBit) selectedPlayer.bit(pot, firstBit);
        else return false;
        return true;
    }

    // プレイヤーの行動（コール・レイズ・ドロップ）を処理する
    public boolean playerAction(List<PlayerAction> actionToPlayer) {
        boolean isRaise = false;
        List<Player> currentPlayers = new ArrayList<>(table);

        for (int i = 0; i < currentPlayers.size(); i++) {
            Player player = currentPlayers.get(i);
            PlayerAction action = actionToPlayer.get(i);

            switch (action.action) {
                case 0: player.call(this); break;  // コールの場合、最小レイズ額をビット
                case 1: player.raise(this, action.raiseAmount); isRaise = true; break;  // レイズの場合、指定額をビット
                case 2: drop(player); break;  // ドロップの場合、テーブルからプレイヤーを削除
            }
        }

        return isRaise || table.size() <= 2;  // レイズがあった場合またはプレイヤーが2人以下になった場合終了
    }

 // プレイヤーが手札から最大2枚を捨て、同じ枚数だけ山札から引く
    public void playerDiscard(List<PlayerAction> actionToPlayer) {
        for (int i = 0; i < table.size(); i++) {
            Player player = table.get(i);
            System.out.printf("プレイヤー%dの現在の手札: %s\n", player.getId(), player.handToString());

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("捨てるカードのインデックス（0～4、カンマ区切り、最大2枚まで）を入力してください。何も入力しない場合はスキップ＞");
                String line = reader.readLine().trim();

                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    List<Integer> indexesToDiscard = new ArrayList<>();
                    for (String part : parts) {
                        int idx = Integer.parseInt(part.trim());
                        if (idx >= 0 && idx < 5) indexesToDiscard.add(idx);
                    }

                    // インデックスの降順に並べ替えてから削除（後ろから消さないとバグる）
                    Collections.sort(indexesToDiscard, Collections.reverseOrder());

                    for (int idx : indexesToDiscard) {
                        player.getHand().remove(idx);
                    }

                    // 山札から捨てた分だけカードを引く
                    for (int j = 0; j < indexesToDiscard.size(); j++) {
                        if (!stockCards.isEmpty()) {
                            player.addHand(stockCards.remove(0));
                        }
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("入力エラーが発生しました。スキップします。");
            }

            System.out.printf("プレイヤー%dの新しい手札: %s\n", player.getId(), player.handToString());
        }
    }
    
    // チップが0以下のプレイヤーがいる場合
    public boolean hasPlayerWithNoChips() {
        for (Player p : table) if (p.getChips() <= 0) return true;
        return false;
    }

    // プレイヤーがドロップした場合、テーブルから削除
    public void drop(Player player) {
        table.remove(player);
    }

    // ポットを取得
    public Pot getPot() {
        return pot;
    }

    // 最小レイズ額を取得
    public int getMinimumRaiseAmount() {
        return minimumRaiseAmount;
    }

    // 最小レイズ額を設定
    public void setMinimumRaiseAmount(int amount) {
        this.minimumRaiseAmount = amount;
    }

    // ゲームが終了したかどうか
    public boolean isEnd() {
        return isEnd;
    }

    // ゲーム終了フラグを設定
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    // プレイヤーリストを取得
    public List<Player> getPlayers() {
        return players;
    }

    // テーブルにいるプレイヤーリストを取得
    public List<Player> getTable() {
        return table;
    }

    // 最もチップが少ないプレイヤーを取得
    public Player getPoorestPlayer() {
        Player poorestPlayer = null;
        for (Player player : players)
            if (poorestPlayer == null || player.getChips() < poorestPlayer.getChips()) poorestPlayer = player;
        
        return poorestPlayer;
    }

    // 最もチップが多いプレイヤーを取得
    public Player getRichestPlayer() {
        Player richestPlayer = null;
        for (Player player : players)
            if (richestPlayer == null || player.getChips() > richestPlayer.getChips()) richestPlayer = player;
        
        return richestPlayer;
    }

    // 勝者を評価する（強い手を持つプレイヤーが勝者）
    public Player evaluateWinner() {
        Player winner = table.get(0);
        int highestHandValue = evaluateHand(winner);
        
        for (Player player : table) {
            int playerHandValue = evaluateHand(player);
            if (playerHandValue > highestHandValue) {
                winner = player;
                highestHandValue = playerHandValue;
            }
        }
        return winner;
    }

    // プレイヤーの手札の強さを評価する（単純にカード番号の合計で判定）
    private int evaluateHand(Player player) {
        int handValue = 0;
        for (Card card : player.getHand()) {
            handValue += card.getNumber();
        }
        return handValue;
    }
}
