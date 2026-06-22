import java.util.Scanner; // ユーザーからの入力を受け取るためにScannerクラスをインポート

/**
 * 文字列反転プログラム
 * ユーザーが入力した文字列を反転させて表示します。
 */
public class StringReverser {

    /**
     * プログラムのエントリーポイント
     * ユーザーからの入力を受け取り、文字列を反転させて結果を表示します。
     *
     * @param args コマンドライン引数（今回は使用しません）
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scannerオブジェクトを作成し、標準入力から読み込む

        try {
            System.out.println("文字列反転プログラムへようこそ！");
            System.out.print("任意の文字列を入力してください: ");

            // ユーザーからの入力を読み込む
            String originalString = scanner.nextLine();

            // 文字列を反転させるメソッドを呼び出す
            String reversedString = reverseString(originalString);

            // 結果を表示
            System.out.println("------------------------------------");
            System.out.println("元の文字列: " + originalString);
            System.out.println("反転された文字列: " + reversedString);
            System.out.println("------------------------------------");

        } finally {
            // Scannerをクローズし、リソースを解放する
            // エラー処理は「特になし」のため、try-finallyで確実なクローズを行う
            scanner.close();
        }
    }

    /**
     * 指定された文字列を反転させるメソッド
     *
     * @param originalString 反転する元の文字列
     * @return 反転された文字列
     */
    public static String reverseString(String originalString) {
        // StringBuilderは文字列の変更を効率的に行うためのクラス
        // そのreverse()メソッドを使うと簡単に文字列を反転できる
        StringBuilder sb = new StringBuilder(originalString);
        sb.reverse(); // 文字列を反転させる
        return sb.toString(); // StringBuilderをString型に変換して返す
    }
}