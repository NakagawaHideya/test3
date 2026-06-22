import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("数字当てゲームへようこそ！");
        System.out.println("1から100の間の数字を当ててください。");

        // 1. ランダムな数字を生成
        int targetNumber = generateRandomNumber();
        int attempts = 0;
        boolean guessedCorrectly = false;

        // 2. ユーザーが正解するまでループ
        while (!guessedCorrectly) {
            attempts++;
            // 3. ユーザーからの有効な入力を取得 (エラー処理を含む)
            int userGuess = getUserGuess(scanner);

            // 4. ヒントを表示し、正解かどうかを判定
            guessedCorrectly = checkGuessAndGiveHint(userGuess, targetNumber);
        }

        // 5. 正解時のメッセージと試行回数を表示
        System.out.println("おめでとうございます！" + targetNumber + "を当てました！");
        System.out.println(attempts + "回で正解しました。");
        
        // Scannerをクローズ
        scanner.close();
    }

    /**
     * 1から100の範囲でランダムな整数を生成します。
     * @return 生成されたランダムな数字
     */
    private static int generateRandomNumber() {
        Random random = new Random();
        // nextInt(100) は 0から99までの乱数を生成するため、+1して1から100にする
        return random.nextInt(100) + 1;
    }

    /**
     * ユーザーから数字の入力を受け取り、有効性をチェックします。
     * 数値以外の入力や1から100の範囲外の入力があった場合は、エラーメッセージを表示し再入力を促します。
     * @param scanner 標準入力からの入力を読み取るためのScannerオブジェクト
     * @return ユーザーが入力した有効な数字
     */
    private static int getUserGuess(Scanner scanner) {
        int guess = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("数字を入力してください (1-100): ");
            String input = scanner.nextLine(); // 行全体を読み込む

            try {
                guess = Integer.parseInt(input); // 文字列を整数に変換
                
                // 1から100の範囲内かチェック
                if (guess >= 1 && guess <= 100) {
                    validInput = true; // 有効な入力であればループを抜ける
                } else {
                    // 範囲外の場合のエラーメッセージ
                    System.out.println("エラー: 1から100の範囲で数字を入力してください。");
                }
            } catch (NumberFormatException e) {
                // 数値以外の文字が入力された場合のエラーメッセージ
                System.out.println("エラー: 無効な入力です。数字を入力してください。");
            }
        }
        return guess;
    }

    /**
     * ユーザーの推測と正解の数字を比較し、ヒントを表示します。
     * 正解であればtrueを、そうでなければfalseを返します。
     * @param guess ユーザーが推測した数字
     * @param targetNumber プログラムが生成した正解の数字
     * @return 推測が正解であった場合はtrue、それ以外はfalse
     */
    private static boolean checkGuessAndGiveHint(int guess, int targetNumber) {
        if (guess < targetNumber) {
            System.out.println("もっと大きい！");
            return false;
        } else if (guess > targetNumber) {
            System.out.println("もっと小さい！");
            return false;
        } else {
            // 正解の場合
            return true;
        }
    }
}