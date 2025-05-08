package PokerGame.dev02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SystemMain {
    private static final int ENTRY_BIT_NUM = 1000;
    
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("ゲームを開始します。プレイ人数を入力してください。＞");
        int playerNum = Integer.parseInt(br.readLine());
        
        System.out.print("プレイヤー１ は 初回ビット数を入力してください。＞");
        int firstBit = Integer.parseInt(br.readLine());
        
        Game game = new Game();
        if (!game.initialize(playerNum)) {
            System.out.println("プレイヤー人数が不足しています。ゲームを終了します。");
            return;
        }

        if (!game.start(firstBit, ENTRY_BIT_NUM)) {
            System.out.println("参加者のチップが不足しています。ゲームを終了します。");
            return;
        }

        while (true) {
            boolean isRaise = false;

            for (int i = 0; i < playerNum; i++) {
                Player player = game.getPlayer(i);
                if (player.getChips() <= 0) continue;

                System.out.printf("プレイヤー%d は行動を選択してください\n[0]コール\n[1]レイズ\n[2]ドロップ\n", i + 1);
                System.out.printf("----\n所持チップ数：%d\n現在のビット額：%d\n----\n＞", player.getChips(), game.getBit());
                int selectedAction = Integer.parseInt(br.readLine());

                int raiseBit = 0;
                if (selectedAction == 1) {
                    isRaise = true;
                    while (true) {
                        System.out.print("レイズする額を入力してください。＞");
                        raiseBit = Integer.parseInt(br.readLine());
                        if (raiseBit <= game.getBit()) {
                            System.out.printf("エラー：現在のビット額 %d より小さいです。\n", game.getBit());
                            continue;
                        }
                        break;
                    }
                }
                
                // 行動を実行
                game.actionUpdate(i, selectedAction, raiseBit);
            }

            // レイズが行われた場合は、もう一巡
            if (isRaise) continue;

            // 手札配布と表示
            game.dealCards();
            for (int i = 0; i < playerNum; i++) {
                Player player = game.getPlayer(i);
                if (player.getChips() <= 0) continue;

                System.out.printf("プレイヤー%dの手札:\n", i + 1);
                for (int j = 0; j < player.getHand().size(); j++) {
                    Card card = player.getHand().get(j);
                    System.out.printf("[%d]%s%d ", j, card.getSuit().getSymbol(), card.getNumber());
                }
                System.out.println();

                System.out.print("捨てたいカードの番号を空白区切りで入力（例: 0 2 4）。何も捨てないならEnter：＞");
                List<Integer> indices = new ArrayList<>();
                if (!br.readLine().trim().isEmpty())
                    for (String s : br.readLine().trim().split(" ")) indices.add(Integer.parseInt(s));
                
                player.discardAndDraw(indices, game.stockCards);
            }

            
            Player winner = game.judgeWinner();
            System.out.printf("プレイヤー%dが勝利しました！\n", winner.getId() + 1);
            winner.setChips(winner.getChips() + game.getPot());
            game.setPot(0);

            // 終了条件（全プレイヤーのうち1人以外がチップ0）
            int activePlayers = 0;
            for (int i = 0; i < playerNum; i++) {
                if (game.getPlayer(i).getChips() > 0) activePlayers++;
            }
            if (activePlayers <= 1) {
                System.out.println("ゲーム終了。1人のプレイヤーが残りました。");
                break;
            }
        }

        System.out.println("ゲームを終了します。");
    }
}
