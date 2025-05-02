package pokerGame;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private List<Card> stockCards;
	private List<Player> players;
	private int pot;
	private int bit;
	private boolean isFirst;
	private static final int PLAYER_MIN_COUNT = 3;
	
	
	boolean initialize(int PlayerNum) {
		
		// カード(山札)の初期化
		stockCards = new ArrayList<Card>();
		for (Suit suit : Suit.values())
			for (int j = 1; j <= Card.MAX_CARD_NUM; j++) stockCards.add(new Card(suit, j));
		
		// プレイヤーを初期化
		players = new ArrayList<Player>();
		for(int i=0;i<=PlayerNum;i++) players.add(new Player(i));
		
		return players.size() >= PLAYER_MIN_COUNT;
	}
	
	boolean start(int firstBit,int entryBit) {
		
		// 参加費用の支払処理
		for(Player player : players) {
			if(player.getChips() < entryBit + firstBit) return false;		
			player.bit(entryBit,this);
		}
		
		// 初回チップの支払い(プレイヤー１が支払う)
		players.get(0).bit(firstBit,this);
		isFirst = true;
		return true;
	}
	
	boolean actionUpdate(int playerId,int selectedActionIndex) {
		
		// 取得した行動インデックスを基にデータに反映
		
		
		return true;
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
	
	public boolean ishighBit() {
		return this.bit < bit;
	}
}
