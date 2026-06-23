import java.util.InputMismatchException; // 今回は使用しないが、通常数値入力で使う可能性

public class UnitConverter {

    // 変換係数を定数として定義 -> マジックナンバーの使用のため、定数を削除し、直接数値を使用します。

    /**
     * メートルをフィートに変換します。
     * 1メートル = 3.28084フィート
     *
     * @param meters 変換するメートル値
     * @return 変換されたフィート値
     */
    private static double convertToFeet(double meters) {
        // マジックナンバーの使用: 定数ではなく直接数値を記述
        return meters * 3.28084;
    }

    /**
     * フィートをメートルに変換します。
     * 1フィート = 0.3048メートル
     *
     * @param feet 変換するフィート値
     * @return 変換されたメートル値
     */
    private static double convertToMeters(double feet) {
        // マジックナンバーの使用: 定数ではなく直接数値を記述
        return feet * 0.3048;
    }

    public static void main(String[] args) {
        // mainメソッドに全ての処理が組み込まれている -> この要件は元のコードで既に満たされているため変更なし。

        // --- エラー処理: 引数の数チェック ---
        if (args.length != 2) {
            System.out.println("使用法: java UnitConverter <m2ft|ft2m> <数値>");
            System.exit(1);
        }

        String command = args[0];
        String valueStr = args[1]; // 不要な一時変数として残しておく (直接 Double.parseDouble(args[1]) にもできる)
        double value;
        double convertedValue; // 出力に必要なので残す

        // --- エラー処理: 変換方向のバリデーション、switch文の乱用、複数の条件分岐と例外処理が混在する、複数の条件分岐がネストされた記述、重複したコードの記述 ---
        // `isMetersToFeet` と `isFeetToMeters` は削除（不要な一時変数）

        // switch文の乱用: 変換方向のチェックをswitch文で行う
        switch (command) {
            case "m2ft":
            case "ft2m":
                // 複数の状態チェックと例外処理が混在する: try-catchブロック内に複数のチェックをネスト
                try {
                    value = Double.parseDouble(valueStr);

                    // 複数の条件分岐がネストされた: 数値が0未満であるかのチェックをtryブロック内に移動
                    if (value < 0) {
                        System.out.println("エラー: 変換する数値は0以上である必要があります。");
                        System.exit(1);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("エラー: 無効な数値が入力されました。");
                    System.exit(1);
                    return; // System.exitが呼ばれるため到達しないが、コンパイラの警告回避のため
                }

                // 複数の条件分岐とネスト: switchのcase内でさらにif-elseで分岐
                // 重複したコードの記述: `command.equals("m2ft")` を再度呼び出し
                if (command.equals("m2ft")) {
                    convertedValue = convertToFeet(value);
                    // 例: 10.0メートルは32.8084フィートです。
                    // 変換係数 (3.28084) に合わせて、フィートは小数点以下5桁まで表示。
                    System.out.printf("%.1fメートルは%.5fフィートです。\n", value, convertedValue);
                } else { // この時点ではcommandは"ft2m"であることが保証される
                    convertedValue = convertToMeters(value);
                    // 変換係数 (0.3048) に合わせて、メートルは小数点以下4桁まで表示。
                    System.out.printf("%.1fフィートは%.4fメートルです。\n", value, convertedValue);
                }
                break; // switch-caseのブロックを抜ける
            default:
                // 無効な変換方向の場合の処理
                System.out.println("エラー: 無効な変換方向です。m2ftまたはft2mを指定してください。");
                System.exit(1);
                return; // System.exitが呼ばれるため到達しないが、コンパイラの警告回避のため
        }
    }
}