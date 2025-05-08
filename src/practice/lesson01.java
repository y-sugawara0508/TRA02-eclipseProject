package practice;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class lesson01 {
	public static void main(String[] args) {

		Random random = new Random();

		// 1. 例外処理
		int a = 0;
		try {			
			// 例）０除算をしてしまう可能性がある処理
			a = 10 / random.nextInt(5); // 0~4までの数値
		
			// 算術エラー発生チェック
		}catch (ArithmeticException e) {
			
			// エラー発生時処理
			a = 10;
			
		}finally {
			
			// エラーの発生に関らず行う処理
			System.out.println(a);
			
		}
		
		// try-catch,throws文などで例外チェックを行わなければならない例外処理
		// - 入出力処理
		// - ファイル操作
		// - クラス定義,メソッド呼出し
		
		// ↓は返された例外をcatchできていないから✖
		// int b = readInt();
		
		try {	
			// これからIOExceptionが発生する可能性があるコード書くつもりだけど...ってとき。
			// 例外をあらかじめ予測してtry-catchを記述しておきたいが、コンパイルエラーに妨げられる場合...
			if(true) throw new IOException(); // クラスインスタンスを直接投げるー
			
		} catch (IOException e) {
			
		}
		
		// 複数の例外をcatchしたい場合
		try {
			if(false) throw new ArrayIndexOutOfBoundsException();
			else throw new NumberFormatException();
		}catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// すべての例外をcatchする場合...
		try {
		// Exceptionクラスはすべての例外の基底クラスである為、catch可能
		} catch (Exception e) {
		}
		
		// Closeableってなに？
		// このインターフェースを付けると、IOExceptionを投げるcloseメソッドが自動で呼び出される
		// finaryを記述しなくとも解放処理が呼び出されるため簡潔な記述が可能
		try(Resource resource = new Resource()){
		}catch(IOException e){
			// 例外発生時の処理
		}
		

		// 独自例外
		try {
			throw new MyException();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	// 例外が発生した場合、例外を返す処理
	public static int readInt() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return Integer.parseInt(br.readLine());
	}
}

// closeableインターフェースを付けると、try-with-resource文の中で自動的にcloseメソッドを呼出してくれる。
class Resource implements Closeable {

	@Override
	public void close() throws IOException {
		System.out.println("よばれました");
	}

}

//独自例外クラス MyException を作成
class MyException extends Exception {
	// コンストラクタ: メッセージを受け取る
	public MyException(String message) {
		super(message); // 親クラスのコンストラクタにメッセージを渡す
	}
	
	public MyException() {
		super("例外はいてるよボケカス。");
	}

	// 追加のコンストラクタを作成することもできます
	public MyException(String message, Throwable cause) {
		super(message, cause); // 親クラスのコンストラクタにメッセージと原因を渡す
	}
}
