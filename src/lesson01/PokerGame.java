package lesson01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PokerGame {

// field ... fix:ちょっとクラス化するのめんどくさい
	static int pot;
	static int prevBit;
	static boolean isRaise;
	static List<Card> stockCards;
	static List<Player> players;
	
// method
	public static void main(String[] args) throws IOException {
		
		// 入力：初期化
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// プレイヤー人数の設定
		System.err.println("プレイヤー人数を設定＞");
		int playerNum = Integer.parseInt(br.readLine());

		// ゲームの初期化
		init(playerNum);
		
		while(true) {
			// 初回ビット処理
			System.out.printf("プレイヤー1の方は、ビット額を入力してください＞");
			bit(players.get(0),Integer.parseInt(br.readLine()));
			
			// 各プレイヤーの選択
			while(true) {
				isRaise = false;
				for(Player player : players) {
					// ドロップしている場合、
					if(player.isDrop())continue;
					
					// コール or レイズ or ドロップを選択
					System.out.printf("プレイヤー%dの番です。行動を選んでください。\n[0]コール\n[1]レイズ\n[2]ドロップ\n＞",player.getId());
					int inputAction = Integer.parseInt(br.readLine());
					switch (inputAction) {
					case 0: call(player);break;
					case 1: 
						while(true) {
							System.out.printf("レイズ額を入力してください＞");
							if(!raise(player, Integer.parseInt(br.readLine()))) {
								System.out.printf("%dより大きい額を入力してください。",prevBit);
								continue;
							}
							break;
						}
						break;
					case 2: drop(player); break;
					}
				}
				
				// 一人でもレイズを行っていた場合、dropしていない人たちでもう一巡
				if(isRaise)continue;
				
				// dropしていない人が2人以下の場合は判定処理に移行
				int notDropPlayerNum = 0;
				for(Player player : players)if(!player.isDrop())notDropPlayerNum++;
				
				if(notDropPlayerNum <=2)break;
				
			}
		}
	}
	
	public static void init(int playerNum) {
		
		// fieldの初期化
		pot = 0;
		stockCards = new ArrayList<Card>();
		players = new ArrayList<Player>();
		
		// 山札の初期化
		for (Suit suit : Suit.values())
			for (int j = 1; j <= 13; j++) stockCards.add(new Card(suit, j));
		
		// 山札をシャッフル
		Collections.shuffle(stockCards);
		
		// プレイヤーの初期化 & 参加費の支払い
		for(int i=1;i <= playerNum;i++) {
			
			Player player = new Player(i);
			bit(player,1000);
			players.add(player);
		}
		
		// プレイヤー1から番号順にカードを5枚、手札に加える
		for(Player player : players) {
			
			// 山札の一番上から取り出す
			for(int i=0;i<5;i++) player.addHandCards(stockCards.remove(0));

		}
	}
	
	public static void bit(Player p,int amount) {
		pot += amount;
		p.setChips(p.getChips() - amount);
		prevBit = amount;
	}
	
	public static void call(Player p) {
		bit(p, prevBit);
	}
	
	public static boolean raise(Player p,int amount) {
		if(amount <= prevBit) return false;
		bit(p, amount); isRaise = true;return true;
	}
	
	public static void drop(Player p) {
		p.setDrop(true);
	}
}

enum Suit {
	SPADE("♠"), HEART("♥"), DIAMOND("♦"), CLUB("♣");

	private final String symbol;

	private Suit(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}

class Card {
	private Suit suit;
	private int number;

	public Card(Suit suit, int number) {
		this.suit = suit;
		this.number = number;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getNumber() {
		return number;
	}
}

class Player {
	private int id;
	private int chips;
	private boolean isDrop;
	private List<Card> handCards;
	
	Player(int id) {
		this.setId(id);
		this.setChips(10000);
		this.handCards = new ArrayList<Card>();
	}

	public List<Card> getHandCards() {
		return handCards;
	}

	public void addHandCards(Card card) {
		handCards.add(card);
	}

	public void addHandCards(List<Card> cards) {
		for (Card card : cards) addHandCards(card);
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

	public boolean isDrop() {
		return isDrop;
	}

	public void setDrop(boolean isDrop) {
		this.isDrop = isDrop;
	}
}