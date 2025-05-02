package pokerGame;

public class Player {
	private int id;
	private int chips;
	
	private static final int FIRST_CHIPS_NUM = 10000;
	
	public Player(int id) {
		setId(id);
		setChips(FIRST_CHIPS_NUM);
	}
	
	public void bit(int bitChips,Game game) {
		game.addPot(bitChips);
		game.setBit(bitChips);
		this.chips -= bitChips;
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
}
