package re_pokerGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameIO {
    
    // ユーザー入力を受け付けるためのBufferedReader
    private BufferedReader bufferedReader;
    
    public GameIO() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    // ユーザーから整数を入力させるメソッド
    public int readInt(String prompt) throws IOException {
        System.out.printf(prompt);
        while (true) {
            try {
                return Integer.parseInt(bufferedReader.readLine());
            } catch (NumberFormatException e) {
                System.out.print("無効な入力です。整数を入力してください。＞");
            }
        }
    }
    
    // ゲーム終了後、最終結果を表示
    public void showResult(Game game) {
        Player winner = game.evaluateWinner();
        System.out.printf("ゲームが終了しました！\n");
        System.out.printf("勝者はプレイヤー%dです！\n", winner.getId());
        
        System.out.printf("最終結果：\n");
        for (Player player : game.getPlayers()) {
            System.out.printf("プレイヤー%d 所持チップ：%d\n", player.getId(), player.getChips());
        }
    }

    // 各プレイヤーの行動を入力させるメソッド
    public List<PlayerAction> readActions(Game game) throws IOException { 
        List<PlayerAction> actions = new ArrayList<>();
        
        for (Player player : game.getTable()) {
            PlayerAction playerAction = new PlayerAction(); 
            playerAction.action = readInt(
                String.format("プレイヤー%dの行動を選んでください。\n手札: %s\n所持チップ: %d\n０:コール\n１:レイズ\n２:ドロップ\n＞", player.getId(), player.handToString(), player.getChips())
            );
            
            if (playerAction.action == 1) {
                // レイズ額を入力させる
                while (true) {
                    int raiseAmount = readInt("レイズする額を入力してください。＞");
                    if (raiseAmount <= game.getMinimumRaiseAmount()) {
                        System.out.printf("※ %d よりも大きい額を入力してください。\n", game.getMinimumRaiseAmount());
                        continue;
                    }
                    
                    game.setMinimumRaiseAmount(raiseAmount);  // レイズ額を最小レイズ額として設定
                    playerAction.raiseAmount = raiseAmount;
                    break;
                }
            }
            actions.add(playerAction);
        }

        return actions;
    }
}
