package lesson01;

public class Character {
	
// フィールド ...宣言＆初期化 初期化はどの地点ですべきか？
	protected String name = "none";
	protected int hp = 0;
	protected int mp = 0;
	protected int spd = 0; 

// メソッド
	/**
	 * デフォルトコンストラクタ ...javaでは記述の必要性があるのかどうか？
	 */
	public Character() {}
	
	/**
	 * 引数付きコンストラクタ
	 * @param name	名前
	 * @param hp	ヘルスポイント
	 * @param mp	マジックポイント
	 * @param spd	スピード
	 */
	public Character(String name,int hp,int mp,int spd) {
		this.name = name;
		this.hp = hp;
		this.mp = mp;
		this.spd = spd;
	}
	
	public void show() {
		System.out.printf("\nstatus:\nname = %s\nhp = %4d\nmp = %4d\nspd = %4d\n",
				this.name,this.hp,this.mp,this.spd);
	}
	
// getter
	/**
	 * @return 名前を取得
	 */
	public String getName() { return name; }

	/**
	 * @return hpを取得
	 */
	public int getHp() { return hp; }
	
	/**
	 * @return mpを取得
	 */
	public int getMp() { return mp; }
	
	/**
	 * @return spdを取得
	 */
	public int getSpd() { return spd; }
	
// setter
	/**
	 * @param name 名前を設定
	 */
	public void setName(String name) { this.name = name; }
	
	/**
	 * @param hp hpを設定
	 */
	public void setHp(int hp) { this.hp = hp; }
	
	/**
	 * @param mp mpを設定
	 */
	public void setMp(int mp) { this.mp = mp; }
	
	/**
	 * @param spd spdを設定
	 */
	public void setSpd(int spd) { this.spd = spd; }
}
