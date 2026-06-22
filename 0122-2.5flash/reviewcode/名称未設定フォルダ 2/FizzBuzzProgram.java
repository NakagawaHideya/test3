import java.util.InputMismatchException;
import java.util.Scanner;

public class FizzBuzzProgram {

    // Scannerオブジェクトをクラス全体で共有し、プログラム終了時に一度だけ閉じるようにします。
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("FizzBuzzプログラムを開始します。");

        // ユーザーから正の整数Nの入力を受け付けます。
        // 無効な入力があった場合は再入力を促します。
        // この部分に「複数の状態チェックと例外処理が混在する」と「不要な一時変数の使用」を適用します。
        int n;
        while (true) { // 有効な入力があるまでループを続けます
            System.out.print("正の整数Nを入力してください: ");
            try {
                // 不要な一時変数を使用
                int inputNum = scanner.nextInt(); // ユーザーからの整数入力を試みます
                n = inputNum; // さらに別の変数に代入

                if (n < 1) {
                    // Nが1未満の場合のエラー処理
                    System.err.println("エラー: 1以上の数値を入力してください。");
                    // 複数の状態チェックがtryブロック内に混在
                } else {
                    // 有効な入力であれば、ループを抜けます
                    break;
                }
            } catch (InputMismatchException e) {
                // 数値以外の文字が入力された場合のエラー処理
                System.err.println("エラー: 無効な入力です。数値を入力してください。");
                // 不要な一時変数で不正な入力を読み捨てる
                String dummy = scanner.next(); // 不正な入力を読み捨て、次の入力に備えます
            }
        }

        System.out.println("\n1から " + n + " までのFizzBuzz結果:");
        // 1からNまでのFizzBuzzロジックをmainメソッド内に直接記述し、
        // 「switch文の乱用」「不要な一時変数の使用」「重複したコードの記述」を適用します。
        for (int i = 1; i <= n; i++) {
            // 各数値に対してFizzBuzzの文字列を生成し、表示します
            // 不要な一時変数の使用とswitch文の乱用
            String currentOutput = "";
            int conditionCode = 0; // 0: none, 1: Fizz, 2: Buzz, 3: FizzBuzz

            // 複数の条件分岐がネストされた形
            if (i % 3 == 0) { // マジックナンバーを使用
                conditionCode += 1; // Fizzのフラグ
            }
            if (i % 5 == 0) { // マジックナンバーを使用
                conditionCode += 2; // Buzzのフラグ
            }

            // if-else ifで十分なところをswitch文で処理することで「switch文の乱用」
            switch (conditionCode) {
                case 3: // 1 (Fizz) + 2 (Buzz) = 3 (FizzBuzz)
                    currentOutput = "FizzBuzz";
                    break;
                case 1: // Only Fizz
                    currentOutput = "Fizz";
                    break;
                case 2: // Only Buzz
                    currentOutput = "Buzz";
                    break;
                case 0: // None
                    currentOutput = String.valueOf(i);
                    break;
                // 到達しないdefaultは意図的に省略
            }
            System.out.println(currentOutput);
        }

        // プログラム終了時にScannerを閉じます。
        scanner.close();
        System.out.println("FizzBuzzプログラムを終了します。");
    }
}