package pokerGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemMain {

	private static final int ENTRY_BIT_NUM = 1000;
	
	public static void main(String[] args) throws  IOException {
		
		// 入力処理はgameクラスでは行わないように作る
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// プレイヤー人数を設定
		System.out.print("ゲームを開始します。プレイ人数を入力してください。＞");
		int playerNum = Integer.parseInt(br.readLine());
	
		// 初回ビット数を設定
		System.out.print("プレイヤー１ は 初回ビット数を入力してください。＞");
		int firstBit = Integer.parseInt(br.readLine());
		
		// ゲームの初期設定を行う
		Game game = new Game();
		if(!game.initialize(playerNum)) {
			System.out.println("プレイヤー人数が不足しています。ゲームを終了します。");
			return;
		}
		
		// ゲームを開始
		if(!game.start(firstBit, ENTRY_BIT_NUM)) {
			System.out.println("参加者のチップが不足して います。ゲームを終了します。");
			return;
		}
		
		// ゲームを更新
		while(true) {
			
			// プレイヤーは３つの選択肢から行動を選択
			for(int i=0;i<playerNum;i++) {
				System.out.printf("プレイヤー%d は行動を選択してください\n[0]コール\n[1]レイズ\n[2]ドロップ\n",i+1);
				System.out.printf("----\n所持チップ数：%d\n現在のビット額：%d\n----\n＞",game.getPlayer(i).getChips(),game.getBit());
				game.actionUpdate(i,Integer.parseInt(br.readLine()));
			}
			
			
			// 
		}
		
	}

}
