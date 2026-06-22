import java.util.Scanner;

public class PalindromeChecker {

    public static void main(String[] args) {
        // Scannerオブジェクトを作成し、ユーザーからの入力を受け付ける準備
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- 回文チェッカープログラム ---");
        System.out.print("判定したい文字列を入力してください: ");

        // ユーザーが入力した文字列を取得
        String input = scanner.nextLine();

        // isPalindromeメソッドを呼び出して回文判定を行う
        if (isPalindrome(input)) {
            System.out.println("「" + input + "」は回文です。");
        } else {
            System.out.println("「" + input + "」は回文ではありません。");
        }

        // Scannerオブジェクトを閉じる
        scanner.close();
    }

    /**
     * 指定された文字列が回文であるか判定します。
     * 判定の際は、大文字・小文字の違いやスペース、句読点を無視します。
     *
     * @param input 判定対象の元の文字列
     * @return 回文であればtrue、そうでなければfalseを返します。
     */
    public static boolean isPalindrome(String input) {
        // 1. 文字列の前処理を行う
        //    - 全て小文字に変換
        //    - スペース、句読点、特殊文字を除去 (アルファベットと数字のみ残す)
        String cleanedString = preprocessString(input);

        // 前処理後の文字列が空の場合（例: 数字やアルファベットがない場合）、回文とみなす
        // 仕様上は特に考慮する必要はないが、一般的な回文判定としては妥当
        if (cleanedString.isEmpty()) {
            return true;
        }

        // 2. 前処理された文字列を逆順にする
        //    StringBuilderを使って効率的に文字列を逆転させます。
        String reversedString = new StringBuilder(cleanedString).reverse().toString();

        // 3. 元の(前処理された)文字列と逆順の文字列を比較する
        //    equalsメソッドで内容が完全に一致するかを確認します。
        return cleanedString.equals(reversedString);
    }

    /**
     * 回文判定のために文字列を前処理します。
     * - 文字列を全て小文字に変換します。
     * - アルファベット (a-z) と数字 (0-9) 以外の全ての文字を除去します。
     *
     * @param original 前処理前の元の文字列
     * @return アルファベットと数字のみで構成され、全て小文字に変換された文字列
     */
    private static String preprocessString(String original) {
        // まず、全ての文字を小文字に変換します
        String lowerCaseString = original.toLowerCase();

        // 次に、正規表現を使ってアルファベットと数字以外の文字を除去します。
        // "[^a-z0-9]" は「aからz、または0から9ではない文字」を表す正規表現です。
        // replaceAllメソッドでこれらの文字を空文字列に置き換えます。
        String cleanedString = lowerCaseString.replaceAll("[^a-z0-9]", "");

        return cleanedString;
    }
}