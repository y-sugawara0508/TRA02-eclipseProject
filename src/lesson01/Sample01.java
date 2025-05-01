package lesson01;

import java.util.ArrayList;
import java.util.List;

public class Sample01 {
	public static void main(String[] args) {
		
		// キャラクターを生成
		List<Character> characters = new ArrayList<Character>();
		characters.add(new Character("勇者",100,50,255));
		characters.add(new Character("戦士",255,50,10));
		characters.add(new Character("魔法使い",50,255,100));
		
		// キャラクター情報を表示
		for(Character character : characters) character.show();
		
	}
}
