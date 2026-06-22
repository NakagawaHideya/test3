import java.util.InputMismatchException; // 不正な入力（数値以外）を処理するための例外
import java.util.Random;                 // ランダムな数を生成するため
import java.util.Scanner;                // ユーザーからの入力を受け取るため

public class NumberGuessingGame {

    public static void main(String[] args) {
        // ゲームを開始します
        playGame();
    }

    /**
     * 数当てゲームのメインロジックを管理するメソッドです。
     * 秘密の数の生成、ユーザー入力の受付、ヒントの提供、試行回数のカウント、ゲーム終了処理を行います。
     */
    private static void playGame() {
        // ユーザーからの入力を受け取るためのScannerオブジェクトを初期化
        // Scannerはゲーム全体で使い回すため、ここで一度だけ生成します。
        Scanner scanner = new Scanner(System.in);
        
        // 1から100までのランダムな秘密の数を生成します
        int secretNumber = generateSecretNumber();
        
        int attempts = 0; // 試行回数をカウントする変数
        int guess;        // ユーザーの推測した数を格納する変数
        boolean correctGuess = false; // 正解したかどうかを示すフラグ

        System.out.println("数当てゲームへようこそ！");
        System.out.println("1から100までの数を当ててください。");

        // ユーザーが正解するまでゲームを繰り返します
        do {
            attempts++; // 試行回数を1増やす
            
            // ユーザーからの有効な数値を入力させます
            guess = getUserGuess(scanner);

            // ユーザーの推測が秘密の数と一致するかどうかをチェックします
            if (guess == secretNumber) {
                correctGuess = true; // 正解した場合はフラグをtrueに設定
            } else {
                // 正解でなければ、ヒントを提供します
                String hint = provideHint(guess, secretNumber);
                System.out.println(hint);
            }

        } while (!correctGuess); // 正解するまでループを続行

        // ゲーム終了時のメッセージを表示します
        System.out.println("正解！ " + secretNumber + " でした。");
        System.out.println(attempts + " 回で当てました。");

        // Scannerオブジェクトを閉じ、リソースを解放します
        scanner.close();
    }

    /**
     * 1から100までの間でランダムな秘密の数を生成します。
     *
     * @return 生成された秘密の数
     */
    private static int generateSecretNumber() {
        Random random = new Random();
        // nextInt(100)は0から99までの整数を生成するため、+1して1から100の範囲にします。
        return random.nextInt(100) + 1;
    }

    /**
     * ユーザーからの入力を受け付け、それが有効な数値（1から100の範囲内）であるかを検証します。
     * 無効な入力があった場合は、エラーメッセージを表示し、再度入力を促します。
     *
     * @param scanner ユーザーからの入力を読み取るためのScannerオブジェクト
     * @return 検証済みの有効なユーザーの推測値
     */
    private static int getUserGuess(Scanner scanner) {
        int guess = -1; // 初期値として無効な値を設定
        boolean validInput = false; // 有効な入力が得られたかを示すフラグ

        // 有効な入力が得られるまでループを続行
        while (!validInput) {
            System.out.print("数を入力してください: ");
            try {
                // ユーザーが数値として入力したものを読み取る
                guess = scanner.nextInt(); 
                
                // 入力された数値が1から100の範囲内かチェック
                if (guess < 1 || guess > 100) {
                    System.out.println("1から100の間の数を入力してください。");
                } else {
                    validInput = true; // 範囲内の有効な入力が得られた
                }
            } catch (InputMismatchException e) {
                // 数値以外の文字列が入力された場合のエラー処理
                System.out.println("無効な入力です。数値を入力してください。");
                // scanner.next()を呼び出すことで、不正な入力を読み捨て、
                // 次のループで再び入力を促せるようにします。これをしないと無限ループになります。
                scanner.next(); 
            }
        }
        return guess;
    }

    /**
     * ユーザーの推測が秘密の数に対して「もっと大きい！」か「もっと小さい！」かのヒントを提供します。
     * このメソッドは推測が正解ではない場合にのみ呼び出されることを想定しています。
     *
     * @param guess ユーザーが推測した数
     * @param secretNumber ランダムに生成された秘密の数
     * @return ユーザーに提供するヒント文字列
     */
    private static String provideHint(int guess, int secretNumber) {
        if (guess < secretNumber) {
            return "もっと大きい！";
        } else { // guess > secretNumber の場合
            return "もっと小さい！";
        }
    }
}