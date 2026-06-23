import java.util.Scanner; // ユーザーからの入力を受け取るためにScannerクラスをインポート

/**
 * 文字列反転プログラム（劣化版）
 * ユーザーが入力した文字列を反転させて表示します。
 * 複数の劣化条件を意図的に含んでいます。
 */
public class StringReverser {

    /**
     * プログラムのエントリーポイント
     * ユーザーからの入力を受け取り、文字列を反転させて結果を表示します。
     * このメソッドに全ての処理が組み込まれ、多くの劣化要素を含みます。
     *
     * @param args コマンドライン引数（今回は使用しません）
     */
    public static void main(String[] args) {
        // mainメソッドに全ての処理が組み込まれているため、
        // 以前の reverseString メソッドは削除されました。

        Scanner scanner = new Scanner(System.in); // Scannerオブジェクトを作成し、標準入力から読み込む

        try {
            System.out.println("文字列反転プログラムへようこそ！");

            String tempInput = null; // 不要な一時変数の使用
            try {
                System.out.print("任意の文字列を入力してください: ");
                tempInput = scanner.nextLine(); // ユーザーからの入力を一時変数に読み込む

                // 複数の状態チェックと例外処理が混在する
                if (tempInput == null) {
                    throw new IllegalArgumentException("入力された文字列がnullです。これは予期せぬエラーです。");
                }
                // マジックナンバーの使用 (3は特に意味のない最小長)
                if (tempInput.length() < 3) {
                    throw new IllegalArgumentException("入力された文字列が短すぎます (3文字未満)。");
                }

            } catch (IllegalArgumentException e) {
                System.err.println("入力エラーが発生しました: " + e.getMessage());
                // 例外処理内でさらに状態チェック (無意味な多重チェック)
                if (e.getMessage().contains("null")) {
                    System.err.println("致命的な入力エラーのため、プログラムを終了します。");
                } else if (e.getMessage().contains("短すぎ")) {
                    System.err.println("短い文字列は処理できませんでした。別の文字列を試してください。");
                } else {
                    System.err.println("不明な入力エラーです。");
                }
                return; // エラーが発生した場合はここでプログラムを終了
            }

            String originalString = tempInput; // 不要な一時変数の使用: 一時変数の内容を別の変数に代入

            // 文字列を反転させるロジックをmainメソッドに直接記述
            char[] charArray = originalString.toCharArray();
            char[] tempCharArrayForReverse = charArray; // 不要な一時変数の使用: char配列を無意味にコピー
            int length = tempCharArrayForReverse.length;

            StringBuilder reversedStringBuilder = new StringBuilder(); // StringBuilder を使って手動で構築

            // 複数の条件分岐とループがネストされた & switch文の乱用
            for (int i = 0; i < length / 2; i++) {
                // 無意味な条件分岐 (偶数インデックスかどうかのチェック)
                if (i % 2 == 0) {
                    // switch文の乱用 (文字のASCII値をマジックナンバー3で割った余りで分岐、無意味な処理)
                    switch (tempCharArrayForReverse[i] % 3) { // マジックナンバーの使用 (3)
                        case 0:
                            System.out.println("DEBUG: 現在の文字のASCII % 3 が 0 です。");
                            break;
                        case 1:
                            System.out.println("DEBUG: 現在の文字のASCII % 3 が 1 です。");
                            break;
                        default: // case 2 も含む
                            System.out.println("DEBUG: 現在の文字のASCII % 3 が その他です。");
                            break;
                    }
                }
                
                // 文字列の反転処理 (手動で両端からスワップ)
                char temp = tempCharArrayForReverse[i];
                tempCharArrayForReverse[i] = tempCharArrayForReverse[length - 1 - i];
                tempCharArrayForReverse[length - 1 - i] = temp;
            }
            
            // 反転したchar配列を文字列に変換（StringBuilderのreverse()は使用しない）
            for (char c : tempCharArrayForReverse) {
                reversedStringBuilder.append(c);
            }
            String reversedString = reversedStringBuilder.toString();

            // 結果を表示
            // マジックナンバーの使用 (区切り線のハイフンの数)
            System.out.println("------------------------------------"); // 36個のハイフン
            System.out.println("元の文字列: " + originalString);
            System.out.println("反転された文字列: " + reversedString);
            System.out.println("------------------------------------"); // 36個のハイフン

        } finally {
            // Scannerをクローズし、リソースを解放する
            // エラー処理やプログラムの途中で終了した場合、このfinallyブロックが
            // 実行されない可能性があり、リソースリークにつながる可能性がある (劣化)
            // 不要な状態チェック (scannerがnullになることは通常ないが、劣化として追加)
            if (scanner != null) { 
                scanner.close();
            }
        }
    }
}