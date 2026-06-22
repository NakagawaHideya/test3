import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 標準入力のためのScannerオブジェクトを作成
        
        // ゲーム開始メッセージを表示
        displayStartMessage();

        // 1から100までのランダムな数を生成
        int targetNumber = generateRandomNumber();
        int guessCount = 0; // 試行回数をカウントする変数
        int userGuess;      // ユーザーの入力値を保持する変数
        boolean correctGuess = false; // 正解したかどうかを示すフラグ

        // ユーザーが正解するまでゲームを繰り返すループ
        while (!correctGuess) {
            guessCount++; // 試行回数を1増やす

            // ユーザーからの有効な入力を取得 (数値でない場合や範囲外の場合はエラー処理を行う)
            userGuess = getUserGuess(scanner);

            // ユーザーの入力値と正解値を比較し、ヒントを表示
            if (userGuess < targetNumber) {
                System.out.println("小さいです！");
            } else if (userGuess > targetNumber) {
                System.out.println("大きいです！");
            } else {
                System.out.println("正解です！");
                correctGuess = true; // 正解したのでフラグをtrueにする
            }
        }

        // ゲーム終了メッセージと試行回数を表示
        displayEndMessage(guessCount);

        scanner.close(); // Scannerオブジェクトをクローズし、リソースを解放
    }

    /**
     * ゲーム開始時のメッセージを表示します。
     */
    private static void displayStartMessage() {
        System.out.println("------------------------------------");
        System.out.println("  数値当てゲームへようこそ！ (1-100)");
        System.out.println("------------------------------------");
        System.out.println("私が1から100までの数を考えました。当ててみてください！");
    }

    /**
     * 1から100までのランダムな整数を生成します。
     *
     * @return 生成されたランダムな整数
     */
    private static int generateRandomNumber() {
        Random random = new Random();
        // random.nextInt(100) は0から99までの整数を生成するため、+1して1から100の範囲にする
        return random.nextInt(100) + 1; 
    }

    /**
     * ユーザーからの入力を受け取り、以下のバリデーションを行います。
     * 1. 入力が数値であること
     * 2. 入力が1から100の範囲内であること
     * いずれかの条件を満たさない場合はエラーメッセージを表示し、有効な入力が得られるまで再入力を促します。
     *
     * @param scanner 標準入力を読み込むためのScannerオブジェクト
     * @return 1から100の範囲内の有効なユーザー入力
     */
    private static int getUserGuess(Scanner scanner) {
        while (true) { // 有効な入力が得られるまでループ
            System.out.print("あなたの予想を入力してください: ");
            if (scanner.hasNextInt()) { // 入力が整数であるかをチェック
                int guess = scanner.nextInt();
                if (guess >= 1 && guess <= 100) { // 入力が1から100の範囲内であるかをチェック
                    return guess; // 有効な入力なので値を返す
                } else {
                    // 範囲外のエラーメッセージ
                    System.out.println("エラー: 1から100までの数値を入力してください。");
                }
            } else {
                // 数値でない場合のエラーメッセージ
                System.out.println("エラー: 有効な数値を入力してください。");
                scanner.next(); // 無効な入力を消費し、次の入力に進めるようにする
            }
        }
    }

    /**
     * ゲーム終了時のメッセージと、ユーザーが正解するまでにかかった試行回数を表示します。
     *
     * @param guessCount ユーザーが正解するまでにかかった試行回数
     */
    private static void displayEndMessage(int guessCount) {
        System.out.println("おめでとうございます！" + guessCount + "回で正解しました！");
        System.out.println("ゲームを終了します。");
        System.out.println("------------------------------------");
    }
}