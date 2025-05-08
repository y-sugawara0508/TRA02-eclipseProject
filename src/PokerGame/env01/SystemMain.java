package PokerGame.env01;

import java.io.IOException;

public class SystemMain {

    public static void main(String[] args) throws IOException {
        // ゲーム開始時にプレイヤー人数を入力させる
        GameIO gameIO = new GameIO();
        int playerNum = gameIO.readInt("ゲームを開始します。\nプレイヤーの人数を入力してください。＞");

        // ゲームの初期化
        Game game = new Game();
        if (!game.initialize(playerNum, 5000)) {
            System.out.println("プレイヤーの人数が不足しています。ゲームを終了します。");
            return;  // ゲーム終了
        }

        // ゲームが終了するまで繰り返す
        while (!game.isEnd()) {
            // 各プレイヤーは参加費500チップをビットする
            if (!game.start(500)) {
                System.out.println("参加費が不足しているプレイヤーがいます。ゲームを終了します。");
                game.setEnd(true);
            }

            // 1人目のプレイヤーが1000チップをビットする
            if (game.firstBit(1000)) {
                System.out.println("選出プレイヤーは初回チップを払えませんでした。ゲームを終了します");
                game.setEnd(true);
            }

            // プレイヤーが行動を選択（コール・レイズ・ドロップ）
            while (true) if (!game.playerAction(gameIO.readActions(game))) break;

            // プレイヤーは手札から最大2枚を捨てることができる
            game.playerDiscard(gameIO.readActions(game));

            // 勝者判定を行い、一番強い手札を持つプレイヤーがチップを獲得する
            if (game.hasPlayerWithNoChips()) {
                System.out.println("チップ数が0以下のプレイヤーがいます。ゲームを終了します。");
                game.setEnd(true);
            }
        }
        
        // ゲーム終了後、結果を表示する
        gameIO.showResult(game);
    }
}
