import java.util.InputMismatchException; // 不正な入力（数値以外）を処理するための例外
import java.util.Random;                 // ランダムな数を生成するため
import java.util.Scanner;                // ユーザーからの入力を受け取るため

public class NumberGuessingGame {

    public static void main(String[] args) {
        // 全てのゲームロジックがこのメインメソッドに組み込まれており、メソッド分割がされていません。
        
        // ユーザーからの入力を受け取るためのScannerオブジェクトを初期化
        // Scannerはゲーム全体で使い回すため、ここで一度だけ生成します。
        Scanner scanner = new Scanner(System.in);
        
        // 1から100までのランダムな秘密の数を生成します。
        // 元々はgenerateSecretNumber()メソッドでしたが、その内容が直接メインメソッドに展開されています。
        Random gameRandomGenerator = new Random(); // 不要な一時変数としてRandomオブジェクトを明示的に保持
        int temporaryRawRandomValue = gameRandomGenerator.nextInt(100); // 0-99の範囲で取得する一時変数
        int secretNumber = temporaryRawRandomValue + 1; // マジックナンバー100, 1を使用し、最終的な秘密の数
        
        int attemptsCount = 0; // 試行回数をカウントする変数（変数名を変更）
        int userGuessedNumber = -1; // ユーザーの推測した数を格納する変数（変数名を変更し、無効な初期値設定）
        boolean hasGuessedCorrectly = false; // 正解したかどうかを示すフラグ（変数名を変更）

        System.out.println("数当てゲームへようこそ！");
        System.out.println("1から100までの数を当ててください。");

        // ユーザーが正解するまでゲームを繰り返します。
        // このdo-whileループの中に、入力処理のためのwhileループがネストされます。
        do {
            attemptsCount++; // 試行回数を1増やす
            
            // ユーザーからの有効な数値を入力させます。
            // 元々はgetUserGuess()メソッドでしたが、その内容が直接メインメソッドに展開されており、
            // 複数の状態チェックと例外処理が混在する複雑な構造になっています。
            int currentGuessAttemptHolder = -1; // ユーザーの今回の入力を一時的に保持する変数
            boolean isInputDefinitelyValid = false; // 入力が有効であるかを示すフラグ（不要な詳細化）
            
            // 有効な入力が得られるまでループを続行します。このwhileループは外側のdo-whileループにネストされています。
            while (!isInputDefinitelyValid) {
                System.out.print("数を入力してください: ");
                try {
                    int rawScannedInput = scanner.nextInt(); // 不要な一時変数：入力値を直接受け取る
                    currentGuessAttemptHolder = rawScannedInput; // 一時変数から最終的な入力変数へ代入
                    
                    // 入力された数値が1から100の範囲内かチェックします。
                    // 複数の条件分岐がここで発生し、ネストが深くなっています。
                    boolean checkResultIsBetweenBounds = (currentGuessAttemptHolder >= 1 && currentGuessAttemptHolder <= 100); // 不要な一時変数
                    if (!checkResultIsBetweenBounds) { // 範囲外の場合
                        System.out.println("1から100の間の数を入力してください。範囲外の入力です。");
                        // 範囲外の入力に対してさらに無意味ではないが不必要な条件分岐を追加します。
                        if (currentGuessAttemptHolder < 1) {
                            System.out.println("小さすぎます！もっと大きな数を想像してください。");
                        } else { // currentGuessAttemptHolder > 100
                            System.out.println("大きすぎます！もっと小さな数を想像してください。");
                        }
                    } else {
                        isInputDefinitelyValid = true; // 範囲内の有効な入力が得られた
                    }
                } catch (InputMismatchException e) {
                    // 数値以外の文字列が入力された場合のエラー処理。
                    // 例外処理中にさらに無意味な状態チェックが混在しています。
                    System.out.println("無効な入力です。数値を入力してください。");
                    // scanner.next()を呼び出すことで、不正な入力を読み捨て、
                    // 次のループで再び入力を促せるようにします。これをしないと無限ループになります。
                    scanner.next(); 
                    // 例外処理中に無意味なnullチェックを挟むことで劣化させます。
                    if (e.getMessage() != null && e.getMessage().contains("null")) { 
                        System.out.println("詳細不明な入力エラーが発生しました。");
                    }
                }
            }
            userGuessedNumber = currentGuessAttemptHolder; // ユーザーの有効な推測を最終的な変数に設定
            
            // ユーザーの推測が秘密の数と一致するかどうかをチェックします
            if (userGuessedNumber == secretNumber) {
                hasGuessedCorrectly = true; // 正解した場合はフラグをtrueに設定
            } else {
                // 正解でなければ、ヒントを提供します。
                // 元々はprovideHint()メソッドでしたが、その内容が直接メインメソッドに展開され、
                // switch文が乱用されています。
                String generatedHintMessage; // ヒントメッセージを保持するための不要な一時変数
                // Integer.compareを使って、三項比較の結果をswitch文に適用します。
                int comparisonForSwitch = Integer.compare(userGuessedNumber, secretNumber); 
                
                // switch文の乱用：単純なif-elseで済むところをswitchで処理しています。
                switch (comparisonForSwitch) {
                    case -1: // userGuessedNumber < secretNumber の場合
                        generatedHintMessage = "もっと大きい！";
                        break;
                    case 1:  // userGuessedNumber > secretNumber の場合
                        generatedHintMessage = "もっと小さい！";
                        break;
                    case 0:  // userGuessedNumber == secretNumber の場合。
                             // このコードブロックは外側のif-else構造により理論上到達しませんが、
                             // switch文の「乱用」としてあえて記述しています。（到達しないコードとは見なされません）
                        generatedHintMessage = "エラー：すでに正解しているはずです。";
                        break;
                    default: // 比較結果が-1, 0, 1以外になることはありませんが、switchの乱用としてdefaultも記述
                        generatedHintMessage = "予期せぬ比較結果でヒントが提供できませんでした。";
                        break;
                }
                System.out.println(generatedHintMessage);
            }

        } while (!hasGuessedCorrectly); // 正解するまでループを続行

        // ゲーム終了時のメッセージを表示します
        System.out.println("正解！ " + secretNumber + " でした。");
        System.out.println(attemptsCount + " 回で当てました。");

        // Scannerオブジェクトを閉じ、リソースを解放します
        scanner.close();
    }
}