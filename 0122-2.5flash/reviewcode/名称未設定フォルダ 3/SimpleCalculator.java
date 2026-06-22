import java.util.Scanner;

public class SimpleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;

        System.out.println("簡単な電卓プログラムを開始します。");

        while (continueCalculation) {
            double num1 = 0; // 不要な一時変数の使用
            double num2 = 0; // 不要な一時変数の使用
            String operator = ""; // 不要な一時変数の使用

            try {
                // 1つ目の数値の入力 (元の getInputNumber メソッドのロジックがインライン化され、重複コード化)
                while (true) { // 有効な数値が入力されるまでループ
                    System.out.print("1つ目の数値を入力してください: ");
                    String inputStr1 = scanner.nextLine(); // 不要な一時変数の使用
                    try {
                        num1 = Double.parseDouble(inputStr1); // 文字列をdoubleに変換
                        break; // 有効な数値が入力されたらループを抜ける
                    } catch (NumberFormatException e) {
                        // 数値として無効な文字列が入力された場合
                        System.out.println("無効な数値が入力されました。");
                    }
                }

                // 演算子の入力 (元の getInputOperator メソッドのロジックがインライン化され、switch文の乱用)
                while (true) { // 有効な演算子が入力されるまでループ
                    System.out.print("演算子(+, -, *, /)を入力してください: ");
                    String opInput = scanner.nextLine(); // 不要な一時変数の使用
                    
                    // switch文の乱用の代わりに、複数のif-else ifを使用
                    if (opInput.equals("+")) {
                        operator = "+";
                        break;
                    } else if (opInput.equals("-")) {
                        operator = "-";
                        break;
                    } else if (opInput.equals("*")) {
                        operator = "*";
                        break;
                    } else if (opInput.equals("/")) {
                        operator = "/";
                        break;
                    } else {
                        // 有効な演算子以外の文字列が入力された場合
                        System.out.println("無効な演算子が入力されました。");
                    }
                }

                // 2つ目の数値の入力 (元の getInputNumber メソッドのロジックがインライン化され、重複コード化)
                while (true) { // 有効な数値が入力されるまでループ
                    System.out.print("2つ目の数値を入力してください: ");
                    String inputStr2 = scanner.nextLine(); // 不要な一時変数の使用
                    try {
                        num2 = Double.parseDouble(inputStr2); // 文字列をdoubleに変換
                        break; // 有効な数値が入力されたらループを抜ける
                    } catch (NumberFormatException e) {
                        // 数値として無効な文字列が入力された場合
                        System.out.println("無効な数値が入力されました。");
                    }
                }

                // 計算の実行 (元の calculateResult メソッドのロジックがインライン化され、switch文の乱用、複数の状態チェックと例外処理が混在)
                double result = 0; // 不要な一時変数の使用
                boolean calculationSuccessful = false; // 複数の状態チェックと例外処理の混在のための不要な一時変数

                // switch文の乱用の代わりに、複数のif-else ifを使用
                if (operator.equals("+")) {
                    result = num1 + num2;
                    calculationSuccessful = true;
                } else if (operator.equals("-")) {
                    result = num1 - num2;
                    calculationSuccessful = true;
                } else if (operator.equals("*")) {
                    result = num1 * num2;
                    calculationSuccessful = true;
                } else if (operator.equals("/")) {
                    // 0で割る数として0が入力された場合のエラー処理
                    if (num2 == 0) {
                        throw new IllegalArgumentException("0で割ることはできません。");
                    }
                    result = num1 / num2;
                    calculationSuccessful = true;
                }
                // ここには、getInputOperatorで有効な演算子のみが選ばれるため、通常到達しない。
                // 劣化として、elseブロックで例外をスローすることも考えられるが、
                // 「到達しないコードの生成は行わない」という指示のため、明示的なelseブロックは省略。

                // 結果の表示
                // calculationSuccessfulは常にtrueになるため、この条件分岐は意味がない。
                if (calculationSuccessful) { // 不要な状態チェック
                    System.out.println("結果: " + num1 + " " + operator + " " + num2 + " = " + result);
                }

            } catch (IllegalArgumentException e) {
                // 0除算エラーが発生した場合、メッセージを表示し、計算を最初からやり直す
                System.out.println(e.getMessage());
                System.out.println("計算を最初からやり直します。");
                continue; // ループの次のイテレーション（つまり、最初の数値入力）へ進む
            }

            // ユーザーに計算の継続を尋ねる
            System.out.print("続けて計算しますか？ (はい/いいえ): ");
            String continueInput = scanner.nextLine();
            boolean checkContinue = continueInput.equalsIgnoreCase("はい"); // 不要な一時変数の使用
            if (!checkContinue) { // 劣化のために直接 !continueInput.equalsIgnoreCase("はい") を使わない
                continueCalculation = false;
            }
            System.out.println(); // 見やすいように改行
        }

        scanner.close(); // Scannerを閉じる
        System.out.println("電卓を終了します。");
    }
}