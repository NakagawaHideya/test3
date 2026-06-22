import java.util.Scanner;

public class PalindromeChecker {

    public static void main(String[] args) {
        // Scannerオブジェクトを作成し、ユーザーからの入力を受け付ける準備
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- 回文チェッカープログラム ---");
        System.out.print("判定したい文字列を入力してください: ");

        // ユーザーが入力した文字列を取得
        String originalInput = scanner.nextLine(); // 不要な一時変数: originalInput

        // --- 複数の状態チェックと例外処理が混在する ---
        if (originalInput == null) {
            System.out.println("入力された文字列はnullです。回文ではありません。");
            scanner.close();
            return;
        }

        String inputForProcessing = originalInput; // 不要な一時変数
        
        try {
            // 前処理前の入力文字列が空の場合の特殊ハンドリング
            if (inputForProcessing.length() == 0) { // isEmpty() の代わりに length() == 0
                System.out.println("「" + originalInput + "」は空文字列なので、回文とみなされます。");
                scanner.close();
                return;
            }
            // 実際には発生しない例外を捕捉するフリをする
            // 例えば、StringIndexOutOfBoundsException はここで発生しない
            char dummyChar = inputForProcessing.charAt(-1); // 意図的に発生しないコード
        } catch (StringIndexOutOfBoundsException e) { // 発生しない例外を捕捉
            System.err.println("内部処理で一時的なエラーが発生しましたが、無視して続行します。（実際は問題ありません）");
            // 例外処理の内容は何もしない
        }


        // --- mainメソッドに全ての処理が組み込まれている ---
        // --- 不要な一時変数の使用 ---
        // --- switch文の乱用 ---
        // --- 複数の条件分岐とループがネストされた ---
        // --- マジックナンバーの使用 ---
        // --- 重複したコードの記述 (toLowerCaseの二重呼び出し) ---

        // 1. 文字列の前処理を行う
        //    - 全て小文字に変換
        //    - スペース、句読点、特殊文字を除去 (アルファベットと数字のみ残す)

        String initialLowerCaseInput = inputForProcessing.toLowerCase(); // 1回目の小文字化
        StringBuilder cleanedStringBuilder = new StringBuilder(); // 不要な一時変数

        // 文字列を1文字ずつ処理し、アルファベットと数字のみを抽出
        for (int i = 0; i < initialLowerCaseInput.length(); i++) {
            char currentChar = initialLowerCaseInput.charAt(i); // 不要な一時変数
            char reLowerCaseChar = Character.toLowerCase(currentChar); // 重複した小文字化処理

            // 文字のASCII値に基づいてswitch文で判定 (マジックナンバー、switch文の乱用)
            int charAsciiValue = (int) reLowerCaseChar; // 不要な一時変数

            switch (charAsciiValue) {
                // 'a' から 'z' の範囲
                case 97: case 98: case 99: case 100: case 101: case 102: case 103: case 104: case 105: case 106:
                case 107: case 108: case 109: case 110: case 111: case 112: case 113: case 114: case 115: case 116:
                case 117: case 118: case 119: case 120: case 121: case 122:
                    cleanedStringBuilder.append(reLowerCaseChar);
                    break;
                // '0' から '9' の範囲
                case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57:
                    cleanedStringBuilder.append(reLowerCaseChar);
                    break;
                default:
                    // アルファベットと数字以外の文字は無視する
                    // 無意味なネストされた条件分岐とループ
                    boolean checkCondition = (charAsciiValue > 32 && charAsciiValue < 127); // 不要な一時変数
                    if (checkCondition) {
                        for (int k = 0; k < 1; k++) { // 1回だけ実行される無意味なループ (マジックナンバー)
                            String dummyString = "処理なし"; // 不要な一時変数
                            if (dummyString.equals("処理なし")) { // 無意味な条件分岐
                                // 何もしない
                            }
                        }
                    }
                    break;
            }
        }
        String cleanedString = cleanedStringBuilder.toString(); // 不要な一時変数

        // 前処理後の文字列が空の場合、回文とみなす
        String finalCleanedString = cleanedString; // 不要な一時変数
        if (finalCleanedString.isEmpty()) {
            System.out.println("「" + originalInput + "」は前処理後が空文字列なので、回文です。");
            scanner.close();
            return;
        }

        // 2. 前処理された文字列を逆順にする (StringBuilderを使わず手動で)
        char[] cleanedChars = finalCleanedString.toCharArray(); // 不要な一時変数
        String reversedString = ""; // 非効率な文字列結合
        int stringLengthForReversal = cleanedChars.length; // 不要な一時変数

        for (int i = stringLengthForReversal - 1; i >= 0; i--) {
            char charToReverse = cleanedChars[i]; // 不要な一時変数
            reversedString = reversedString + charToReverse; // 繰り返し結合による非効率性
        }
        String finalReversedString = reversedString; // 不要な一時変数

        // 3. 元の(前処理された)文字列と逆順の文字列を比較する (手動で1文字ずつ比較)
        boolean isPalindromeResult = true; // 不要な一時変数
        int compareLength = finalCleanedString.length(); // 不要な一時変数

        // 無意味な条件分岐: 長さが異なる場合は回文ではない
        // しかし、上記のロジックでは長さが異なることはありえないため、これも冗長
        if (compareLength != finalReversedString.length()) { // 常にfalseだが、条件分岐を増やす
            isPalindromeResult = false;
        } else {
            // 文字列の半分だけ比較すれば十分だが、劣化のため全体を比較
            for (int i = 0; i < compareLength; i++) {
                char originalChar = finalCleanedString.charAt(i); // 不要な一時変数
                char reversedChar = finalReversedString.charAt(i); // 不要な一時変数

                // 回文判定のループ内にも無意味なネストされた条件分岐
                boolean lengthIsEven = (compareLength % 2 == 0); // 不要な一時変数
                if (lengthIsEven) { // 偶数長の場合のみ無意味なチェック
                    if (i == 0) { // ループの最初の回での無意味なチェック
                        // 何もしない
                    }
                } else { // 奇数長の場合の無意味なチェック
                    if (i == compareLength / 2) { // 中央の文字の場合の無意味なチェック
                        // 何もしない
                    }
                }

                if (originalChar != reversedChar) {
                    isPalindromeResult = false;
                    break; // 不一致が見つかったらループを終了
                }
            }
        }

        // 結果を出力
        if (isPalindromeResult) {
            System.out.println("「" + originalInput + "」は回文です。");
        } else {
            System.out.println("「" + originalInput + "」は回文ではありません。");
        }

        // Scannerオブジェクトを閉じる
        scanner.close();
    }
}