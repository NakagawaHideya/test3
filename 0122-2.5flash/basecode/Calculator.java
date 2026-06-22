public class Calculator {

    public static void main(String[] args) {
        // 1. 引数の数チェック
        // コマンドライン引数の数が3つ（数値1、演算子、数値2）でない場合、エラーメッセージを表示して終了
        if (args.length != 3) {
            System.err.println("使用法: java Calculator <数値1> <演算子> <数値2>");
            System.exit(1);
            return; // プログラムを確実に終了させる
        }

        String num1Str = args[0];
        String operator = args[1];
        String num2Str = args[2];

        double num1;
        double num2;

        // 2. 数値1のパースとエラー処理
        // 数値1が有効なdouble型に変換できない場合、エラーメッセージを表示して終了
        try {
            num1 = parseNumber(num1Str);
        } catch (NumberFormatException e) {
            System.err.println("エラー: 無効な数値が入力されました。");
            System.exit(1);
            return; // プログラムを確実に終了させる
        }

        // 3. 数値2のパースとエラー処理
        // 数値2が有効なdouble型に変換できない場合、エラーメッセージを表示して終了
        try {
            num2 = parseNumber(num2Str);
        } catch (NumberFormatException e) {
            System.err.println("エラー: 無効な数値が入力されました。");
            System.exit(1);
            return; // プログラムを確実に終了させる
        }

        // 4. 演算子の有効性チェック
        // 演算子が+,-,*,/のいずれでもない場合、エラーメッセージを表示して終了
        if (!isValidOperator(operator)) {
            System.err.println("エラー: 無効な演算子です。+,-,*,/ のいずれかを使用してください。");
            System.exit(1);
            return; // プログラムを確実に終了させる
        }

        // 5. 計算の実行とゼロ除算チェック
        double result;
        try {
            result = calculate(num1, operator, num2);
        } catch (IllegalArgumentException e) {
            // calculateメソッド内でスローされたゼロ除算エラーをキャッチし、メッセージを表示して終了
            System.err.println(e.getMessage());
            System.exit(1);
            return; // プログラムを確実に終了させる
        }

        // 6. 結果の出力
        // 計算結果を標準出力に表示
        System.out.println(result);
    }

    /**
     * 文字列をdouble型数値に変換します。
     * 変換に失敗した場合、{@link NumberFormatException}をスローします。
     *
     * @param numberString 変換する文字列
     * @return 変換されたdouble型数値
     * @throws NumberFormatException 文字列が有効な数値形式でない場合
     */
    private static double parseNumber(String numberString) throws NumberFormatException {
        return Double.parseDouble(numberString);
    }

    /**
     * 指定された演算子が有効な演算子（+,-,*,/）であるかを確認します。
     *
     * @param operator 確認する演算子文字列
     * @return 有効な演算子であればtrue、そうでなければfalse
     */
    private static boolean isValidOperator(String operator) {
        // String.equals() を使用して演算子を比較
        return "+".equals(operator) ||
               "-".equals(operator) ||
               "*".equals(operator) ||
               "/".equals(operator);
    }

    /**
     * 2つの数値と演算子に基づいて計算を実行します。
     * ゼロ除算が発生した場合、{@link IllegalArgumentException}をスローします。
     * このメソッドに渡される演算子は、{@link #isValidOperator(String)} によって
     * 事前に有効性が確認されていることを前提とします。
     *
     * @param num1 1つ目の数値
     * @param operator 演算子（+,-,*,/）
     * @param num2 2つ目の数値
     * @return 計算結果
     * @throws IllegalArgumentException 演算子が'/'で数値2が0の場合
     */
    private static double calculate(double num1, String operator, double num2) throws IllegalArgumentException {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                // ゼロ除算のチェック
                if (num2 == 0) {
                    throw new IllegalArgumentException("エラー: ゼロで除算することはできません。");
                }
                return num1 / num2;
            // isValidOperatorで事前にチェックされているため、
            // defaultケースに到達することはありません。
            // よって、defaultブロックは記述しません。
        }
        // ここに到達することは論理的にないが、コンパイラが到達しないコードとして認識しない場合、
        // 戻り値を要求することがある。その場合は IllegalStateException などをスローするが、
        // Javaのswitch with Stringでは、全てのケースを網羅していればdefaultは不要であり、
        // かつコンパイルエラーにはなりません。
        // したがって、この位置で追加の return や throw は不要です。
    }
}