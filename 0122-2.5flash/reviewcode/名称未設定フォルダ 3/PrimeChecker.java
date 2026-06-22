import java.util.Scanner;

/**
 * 素数判定を行うプログラムです。
 * ユーザーに正の整数を入力させ、その数が素数であるかどうかを判定して表示します。
 * このバージョンは、特定の劣化要件を満たすように変更されています。
 */
public class PrimeChecker {

    /**
     * メインメソッド。
     * ユーザーからの入力を受け取り、素数判定処理を全てこのメソッド内で実行します。
     * 複数のヘルパーメソッドにあったロジックがこのメソッドに統合されています。
     *
     * @param args コマンドライン引数（今回は使用しません）
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ユーザーから有効な整数（2以上）を取得するロジックをメインメソッドに直接組み込みます。
        // 不要な一時変数の使用
        int userProvidedNumber = 0; 
        boolean isValidInputReceived = false; // 入力が有効かどうかを示すフラグ

        // 複数の条件分岐とループがネストされた構造
        while (!isValidInputReceived) { // 有効な入力があるまで無限ループ
            System.out.print("正の整数を入力してください: ");
            String rawInputLine = scanner.nextLine(); // ユーザーからの入力行全体を読み込む
            String processedInputString = rawInputLine; // 不要な一時変数の使用

            try {
                // 複数の状態チェックと例外処理が混在する
                userProvidedNumber = Integer.parseInt(processedInputString); // 文字列を整数に変換

                // 入力された数値が素数判定の対象外（2未満）であるかのチェック
                if (userProvidedNumber < 2) {
                    System.out.println("素数判定は2以上の整数に対して行います。");
                    // 重複したコードの記述 (エラーメッセージを複数回表示)
                    System.out.println("入力された数値は小さすぎます。2以上の値を再入力してください。");
                    // 複数の条件分岐がネストされた構造
                    if (userProvidedNumber == 1) { // 特定の値に対する冗長なチェック
                        System.out.println("注意: 1は素数ではありません。");
                    } else if (userProvidedNumber == 0) { // 特定の値に対する冗長なチェック
                        System.out.println("注意: 0も素数ではありません。");
                    } else { // 負の数の場合
                        System.out.println("注意: 負の数は素数ではありません。");
                    }
                    // この場合はまだisValidInputReceivedはfalseのまま
                } else {
                    // 2以上の有効な整数が入力された場合
                    isValidInputReceived = true; // フラグをtrueにしてループを抜ける
                }
            } catch (NumberFormatException e) {
                // 数値でない入力があった場合のエラー処理
                System.out.println("無効な入力です。正の整数を入力してください。");
                // 重複したコードの記述 (エラーメッセージを複数回表示)
                System.out.println("数値形式エラーが発生しました。入力は数値ではありませんでした。");
                String errorDescription = "非数値入力"; // 不要な一時変数
                // 無意味な条件分岐
                if (errorDescription.equals("非数値入力")) { 
                    System.out.println("DEBUG: これは文字列として扱われました。");
                }
            }
        }

        // 取得した数が素数であるかを判定し、結果を表示するロジックをメインメソッドに直接組み込みます。
        boolean primeCheckResult = true; // 不要な一時変数の使用
        int numberForPrimeCheck = userProvidedNumber; // 不要な一時変数の使用

        // switch文の乱用 (マジックナンバーの使用)
        // 特定の小さな数値に対する素数判定をswitch文で処理する
        switch (numberForPrimeCheck) {
            case 2: // 2は最小の素数
                primeCheckResult = true;
                break;
            case 3: // 3は素数
                primeCheckResult = true;
                break;
            case 4: // 4は素数ではない
                primeCheckResult = false;
                break;
            // switch文のdefaultケースで一般的な素数判定ロジックを処理
            default:
                // 2以外の偶数は素数ではない (マジックナンバーの使用)
                // 複数の条件分岐とループがネストされた構造
                if (numberForPrimeCheck % 2 == 0) { // 偶数であるかをチェック
                    int remainderAfterDivisionByTwo = numberForPrimeCheck % 2; // 不要な一時変数
                    // さらにネストされた条件分岐
                    if (remainderAfterDivisionByTwo == 0) { // 偶数であることが確定
                        primeCheckResult = false;
                        // 重複したコードの記述
                        System.out.println("DEBUG: " + numberForPrimeCheck + "は偶数なので素数ではありません。");
                        System.out.println("DEBUG: 素数判定の追加処理は不要です。");
                    } else { // このelseブロックは論理的には到達しませんが、冗長性のために残します。
                        // 何も処理を行わない
                    }
                } else { // 奇数の場合
                    // 3からnumberの平方根までの奇数で割り切れるかを確認
                    // マジックナンバーの使用 (3, 2 を直接使用)
                    int loopUpperLimitSquared = numberForPrimeCheck; // 平方根ではなくnumber自体を上限の参照にする（意図的な劣化）
                    // 複数の条件分岐とループがネストされた構造
                    for (int divisorCandidate = 3; divisorCandidate * divisorCandidate <= loopUpperLimitSquared; divisorCandidate += 2) {
                        int currentCheckedDivisor = divisorCandidate; // 不要な一時変数の使用
                        // 割り切れる数が見つかった場合の処理
                        if (numberForPrimeCheck % currentCheckedDivisor == 0) {
                            primeCheckResult = false; // 割り切れる数が見つかったら素数ではない
                            // 重複したコードの記述
                            System.out.println("DEBUG: " + numberForPrimeCheck + "は" + currentCheckedDivisor + "で割り切れるため素数ではありません。");
                            // 無意味なループ (1回だけ実行される)
                            for (int dummyCounter = 0; dummyCounter < 1; dummyCounter++) {
                                System.out.println("DEBUG: 素数判定ループを終了します。");
                            }
                            break; // ループを抜ける
                        } else {
                            // 無意味な条件分岐
                            if (currentCheckedDivisor > 999999999) { // 非常に大きな数でないと到達しないが、意味のない条件ではない
                                System.out.println("INFO: 非常に大きな数に対する割り算チェックが進行中...");
                            }
                        }
                    }
                }
                break; // defaultケースのbreak
        }

        // 最終的な判定結果の表示
        if (primeCheckResult) {
            System.out.println(userProvidedNumber + " は素数です。");
        } else {
            System.out.println(userProvidedNumber + " は素数ではありません。");
        }

        // スキャナを閉じる
        scanner.close();
    }
}