package PokerGame02;

enum Suit{
	SPADE("♠"), HEART("♥"), DIAMOND("♦"), CLUB("♣");
	
	private final String symbol;
	private Suit(String symbol) {this.symbol = symbol;}
	public String getSymbol() { return symbol; }
}

public class Card {
	private Suit suit;
	private int number;
	public static final int MAX_CARD_NUM = 13;
	
	public Card(Suit suit,int number) {
		this.setSuit(suit);
		this.setNumber(number);
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
