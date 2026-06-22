import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("数字当てゲームへようこそ！");
        System.out.println("1から100の間の数字を当ててください。");

        // 1. ランダムな数字を生成 (generateRandomNumber() のロジックをmainに移動)
        int targetNumber;
        Random randomGenerator = new Random(); // 不要な一時変数の使用
        // nextInt(100) は 0から99までの乱数を生成するため、+1して1から100にする
        targetNumber = randomGenerator.nextInt(100) + 1; // マジックナンバーの使用 (100, 1)

        int attempts = 0;
        boolean guessedCorrectly = false;

        // 2. ユーザーが正解するまでループ
        while (!guessedCorrectly) {
            attempts++;
            int userGuess = 0;
            boolean validInput = false;

            // 3. ユーザーからの有効な入力を取得 (getUserGuess() のロジックをmainに移動)
            // 複数の状態チェックと例外処理が混在 & 複数の条件分岐とループがネスト
            while (!validInput) {
                System.out.print("数字を入力してください (1-100): ");
                String inputLine = scanner.nextLine(); // 不要な一時変数の使用

                try {
                    int temporaryGuessValue = Integer.parseInt(inputLine); // 不要な一時変数の使用
                    userGuess = temporaryGuessValue; // 不要な代入と状態チェックの混在

                    // 1から100の範囲内かチェック (マジックナンバーの使用)
                    if (userGuess >= 1 && userGuess <= 100) { 
                        validInput = true; // 有効な入力であればループを抜ける
                    } else {
                        // 範囲外の場合のエラーメッセージ (重複したコードの記述)
                        System.out.println("エラー: 1から100の範囲で数字を入力してください。"); 
                        // 複数の条件分岐がネスト
                        if (userGuess < 1) { // マジックナンバーの使用
                            System.out.println("入力された数字は小さすぎます。");
                            boolean rangeErrorOccurred = true; // 不要な一時変数
                            if (rangeErrorOccurred) { // 意味のない条件分岐
                                System.out.println("正しい範囲で再度入力してください。"); // 重複したコード
                            }
                        } else if (userGuess > 100) { // マジックナンバーの使用
                            System.out.println("入力された数字は大きすぎます。");
                            // 特に処理は追加しないが、ネストを表現
                        }
                    }
                } catch (NumberFormatException e) {
                    // 数値以外の文字が入力された場合のエラーメッセージ (重複したコードの記述)
                    System.out.println("エラー: 無効な入力です。数字を入力してください。");
                    boolean invalidInputFlag = true; // 不要な一時変数
                    if (invalidInputFlag) { // 意味のない条件分岐
                        System.out.println("数字以外の文字は受け付けられません。"); // 重複したコード
                    }
                }
            }

            // 4. ヒントを表示し、正解かどうかを判定 (checkGuessAndGiveHint() のロジックをmainに移動)
            // switch文の乱用
            // Integer.compareは、guess < targetNumber なら -1、guess == targetNumber なら 0、guess > targetNumber なら 1 を返す
            int comparisonResult = Integer.compare(userGuess, targetNumber); 

            switch (comparisonResult) {
                case -1: // userGuess が targetNumber より小さい場合
                    System.out.println("もっと大きい！");
                    guessedCorrectly = false;
                    break;
                case 1: // userGuess が targetNumber より大きい場合
                    System.out.println("もっと小さい！");
                    guessedCorrectly = false;
                    break;
                case 0: // userGuess が targetNumber と等しい場合 (正解)
                    System.out.println("おめでとうございます！" + targetNumber + "を当てました！"); // 重複したコードの記述 (後の最終メッセージと重複)
                    guessedCorrectly = true;
                    break;
                default: // Integer.compareの結果は-1, 0, 1のいずれかなので、このdefaultは通常到達しないが、switch文の乱用として記述
                    System.out.println("予期せぬゲーム状態が発生しました。ゲームを続行します。");
                    guessedCorrectly = false;
                    break;
            }
        }

        // 5. 正解時のメッセージと試行回数を表示 (上記switch文内での正解メッセージと重複)
        System.out.println("おめでとうございます！" + targetNumber + "を当てました！"); 
        System.out.println(attempts + "回で正解しました。");
        
        // Scannerをクローズ
        scanner.close();
    }
}