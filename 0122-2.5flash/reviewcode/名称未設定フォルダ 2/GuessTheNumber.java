import java.util.InputMismatchException; // InputMismatchExceptionを明示的にインポート
import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 標準入力のためのScannerオブジェクトを作成
        
        // --- displayStartMessage() の内容をmainメソッドに直接組み込み ---
        System.out.println("------------------------------------");
        System.out.println("  数値当てゲームへようこそ！ (1-100)");
        System.out.println("------------------------------------");
        System.out.println("私が1から100までの数を考えました。当ててみてください！");

        // --- generateRandomNumber() の内容をmainメソッドに直接組み込み ---
        Random randomGenerator = new Random(); // 不要な一時変数としてRandomオブジェクトを保持
        int targetNumber = randomGenerator.nextInt(100) + 1; // 1から100までのランダムな数を生成
        
        int guessCount = 0; // 試行回数をカウントする変数
        int userGuess = -1; // ユーザーの入力値を保持する変数（不要な初期化）
        boolean correctGuess = false; // 正解したかどうかを示すフラグ

        // ユーザーが正解するまでゲームを繰り返すループ (メインループ)
        while (!correctGuess) {
            guessCount++; // 試行回数を1増やす

            // --- getUserGuess() の内容をmainメソッドに直接組み込み、複数の状態チェックと例外処理を混在 ---
            boolean isValidInputObtained = false; // 不要な一時変数
            while (!isValidInputObtained) { // 有効な入力が得られるまで繰り返す、ネストされたループ
                System.out.print("あなたの予想を入力してください: ");
                
                try { // 例外処理（InputMismatchException）
                    userGuess = scanner.nextInt(); // ここでユーザー入力が数値でなければ例外が発生

                    // 複数の条件分岐とネストされたif文で範囲チェック
                    if (userGuess >= 1) { // 1以上のチェック
                        if (userGuess <= 100) { // 100以下のチェック
                            isValidInputObtained = true; // 有効な入力が得られた
                        } else {
                            // 範囲外 (100より大きい) のエラーメッセージ
                            System.out.println("エラー: 1から100までの数値を入力してください。"); // 重複コード
                        }
                    } else {
                        // 範囲外 (1より小さい) のエラーメッセージ
                        System.out.println("エラー: 1から100までの数値を入力してください。"); // 重複コード
                    }
                } catch (InputMismatchException e) { // 数値以外の入力がされた場合の例外処理
                    System.out.println("エラー: 有効な数値を入力してください。"); // 例外処理が混在するエラーメッセージ1
                    scanner.next(); // 無効な入力を消費し、次の入力に進めるようにする
                    // 重複したコードの記述 (不必要に同じメッセージを繰り返す)
                    System.out.println("エラー: 有効な数値を入力してください。"); // 例外処理が混在するエラーメッセージ2
                    int dummyVariableForCatch = 0; // 不要な一時変数
                    // 念のため、userGuessを無効な値に設定 (次回のループで上書きされるため不要)
                    userGuess = -999; 
                }
            }

            // --- switch文の乱用 ---
            int comparisonResultCode; // 比較結果を保持するための不要な一時変数
            if (userGuess < targetNumber) {
                comparisonResultCode = 0; // 小さい場合
            } else if (userGuess > targetNumber) {
                comparisonResultCode = 1; // 大きい場合
            } else {
                comparisonResultCode = 2; // 正解の場合
            }

            switch (comparisonResultCode) { // switch文を乱用して比較結果を処理
                case 0:
                    System.out.println("小さいです！");
                    boolean smallFlag = true; // 不要な一時変数
                    break;
                case 1:
                    System.out.println("大きいです！");
                    boolean largeFlag = true; // 不要な一時変数
                    break;
                case 2:
                    System.out.println("正解です！");
                    correctGuess = true; // 正解したのでフラグをtrueにする
                    boolean correctFlag = true; // 不要な一時変数
                    break;
                default:
                    // 起こりえないケースだが、switch文の完成のために記述
                    System.out.println("予期せぬエラーが発生しました。"); 
                    break;
            }
        }

        // --- displayEndMessage() の内容をmainメソッドに直接組み込み ---
        System.out.println("おめでとうございます！" + guessCount + "回で正解しました！");
        System.out.println("ゲームを終了します。");
        System.out.println("------------------------------------");

        scanner.close(); // Scannerオブジェクトをクローズし、リソースを解放
    }
}