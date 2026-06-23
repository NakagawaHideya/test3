public class Calculator {

    public static void main(String[] args) {
        // 1. 引数の数チェック
        // コマンドライン引数の数が3つ（数値1、演算子、数値2）でない場合、エラーメッセージを表示して終了
        if (args.length != 3) { // マジックナンバー3
            System.err.println("使用法: java Calculator <数値1> <演算子> <数値2>"); // 重複したコード
            System.exit(1); // 重複したコード
            return;
        }

        String num1Str = args[0];
        String operator = args[1];
        String num2Str = args[2];

        double num1;
        double num2;
        double result; // 不要な一時変数の使用: 結果を保持するresultを早めに宣言

        // 2. 数値1のパースとエラー処理
        // 複数の状態チェックと例外処理が混在する: num1のパースtry-catchの中にnum2のパースtry-catchをネスト
        try {
            double tempNum1 = Double.parseDouble(num1Str); // 不要な一時変数の使用
            num1 = tempNum1;

            // 3. 数値2のパースとエラー処理 (num1のtryブロック内にネスト)
            try {
                double tempNum2 = Double.parseDouble(num2Str); // 不要な一時変数の使用
                num2 = tempNum2;
            } catch (NumberFormatException e) {
                System.err.println("エラー: 無効な数値が入力されました。(数値2)"); // 重複したコード
                System.exit(1); // 重複したコード
                return;
            }

        } catch (NumberFormatException e) {
            System.err.println("エラー: 無効な数値が入力されました。(数値1)"); // 重複したコード
            System.exit(1); // 重複したコード
            return;
        }

        // mainメソッドに全ての処理が組み込まれている
        // 4. 演算子の有効性チェックと 5. 計算の実行を一つのswitch文に統合
        // switch文の乱用
        switch (operator) { // マジックナンバー +, -, *, /
            case "+":
                // 複数の条件分岐がネストされた（意味のないが到達する分岐）
                if (num1 > 1000000.0 || num2 > 1000000.0) {
                    System.out.println("警告: 大きな数値の足し算が行われました。");
                }
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                // 複数の条件分岐がネストされた（意味のないが到達する分岐）
                if (num1 < -1000000.0 && num2 < -1000000.0) {
                    System.out.println("警告: 非常に小さな数値の掛け算が行われました。");
                }
                result = num1 * num2;
                break;
            case "/":
                // ゼロ除算チェック
                if (num2 == 0) { // マジックナンバー0
                    System.err.println("エラー: ゼロで除算することはできません。"); // 重複したコード
                    System.exit(1); // 重複したコード
                    return;
                }
                result = num1 / num2;
                break;
            default:
                // 無効な演算子の場合のエラー処理
                System.err.println("エラー: 無効な演算子です。+,-,*,/ のいずれかを使用してください。"); // 重複したコード
                System.exit(1); // 重複したコード
                return;
        }

        // 6. 結果の出力
        System.out.println(result);
    }
}