import java.util.Scanner;

public class SimpleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;

        System.out.println("簡単な電卓プログラムを開始します。");

        while (continueCalculation) {
            try {
                // 1つ目の数値の入力
                double num1 = getInputNumber(scanner, "1つ目の数値を入力してください: ");

                // 演算子の入力
                String operator = getInputOperator(scanner, "演算子(+, -, *, /)を入力してください: ");

                // 2つ目の数値の入力
                double num2 = getInputNumber(scanner, "2つ目の数値を入力してください: ");

                // 計算の実行
                double result = calculateResult(num1, num2, operator);

                // 結果の表示
                System.out.println("結果: " + num1 + " " + operator + " " + num2 + " = " + result);

            } catch (IllegalArgumentException e) {
                // 0除算エラーが発生した場合、メッセージを表示し、計算を最初からやり直す
                System.out.println(e.getMessage());
                System.out.println("計算を最初からやり直します。");
                continue; // ループの次のイテレーション（つまり、最初の数値入力）へ進む
            }

            // ユーザーに計算の継続を尋ねる
            System.out.print("続けて計算しますか？ (はい/いいえ): ");
            String continueInput = scanner.nextLine();
            if (!continueInput.equalsIgnoreCase("はい")) {
                continueCalculation = false;
            }
            System.out.println(); // 見やすいように改行
        }

        scanner.close(); // Scannerを閉じる
        System.out.println("電卓を終了します。");
    }

    /**
     * ユーザーから有効なdouble型数値の入力を受け取るメソッド。
     * 無効な数値が入力された場合はエラーメッセージを表示し、再入力を促します。
     *
     * @param scanner Scannerオブジェクト
     * @param prompt ユーザーに表示するプロンプトメッセージ
     * @return 入力されたdouble型の数値
     */
    private static double getInputNumber(Scanner scanner, String prompt) {
        while (true) { // 有効な数値が入力されるまでループ
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input); // 文字列をdoubleに変換
            } catch (NumberFormatException e) {
                // 数値として無効な文字列が入力された場合
                System.out.println("無効な数値が入力されました。");
            }
        }
    }

    /**
     * ユーザーから有効な演算子（+、-、*、/）の入力を受け取るメソッド。
     * 無効な演算子が入力された場合はエラーメッセージを表示し、再入力を促します。
     *
     * @param scanner Scannerオブジェクト
     * @param prompt ユーザーに表示するプロンプトメッセージ
     * @return 入力されたString型の演算子
     */
    private static String getInputOperator(Scanner scanner, String prompt) {
        while (true) { // 有効な演算子が入力されるまでループ
            System.out.print(prompt);
            String operator = scanner.nextLine();
            // 有効な演算子かどうかをチェック
            if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/")) {
                return operator;
            } else {
                // 有効な演算子以外の文字列が入力された場合
                System.out.println("無効な演算子が入力されました。");
            }
        }
    }

    /**
     * 2つの数値と1つの演算子に基づいて計算を実行するメソッド。
     * 除算で0を割る数として入力された場合、IllegalArgumentExceptionをスローします。
     *
     * @param num1 1つ目の数値
     * @param num2 2つ目の数値
     * @param operator 実行する演算子
     * @return 計算結果
     * @throws IllegalArgumentException 0で割る数として0が入力された場合
     */
    private static double calculateResult(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                // 0で割る数として0が入力された場合のエラー処理
                if (num2 == 0) {
                    throw new IllegalArgumentException("0で割ることはできません。");
                }
                return num1 / num2;
            default:
                // getInputOperatorで有効な演算子のみが渡されるはずなので、
                // ここには通常到達しないが、念のためIllegalArgumentExceptionをスロー
                throw new IllegalArgumentException("不明な演算子です。");
        }
    }
}