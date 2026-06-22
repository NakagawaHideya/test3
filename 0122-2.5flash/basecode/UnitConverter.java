import java.util.InputMismatchException; // 今回は使用しないが、通常数値入力で使う可能性

public class UnitConverter {

    // 変換係数を定数として定義
    private static final double METERS_TO_FEET_FACTOR = 3.28084;
    private static final double FEET_TO_METERS_FACTOR = 0.3048;

    /**
     * メートルをフィートに変換します。
     * 1メートル = 3.28084フィート
     *
     * @param meters 変換するメートル値
     * @return 変換されたフィート値
     */
    private static double convertToFeet(double meters) {
        return meters * METERS_TO_FEET_FACTOR;
    }

    /**
     * フィートをメートルに変換します。
     * 1フィート = 0.3048メートル
     *
     * @param feet 変換するフィート値
     * @return 変換されたメートル値
     */
    private static double convertToMeters(double feet) {
        return feet * FEET_TO_METERS_FACTOR;
    }

    public static void main(String[] args) {
        // --- エラー処理: 引数の数チェック ---
        if (args.length != 2) {
            System.out.println("使用法: java UnitConverter <m2ft|ft2m> <数値>");
            System.exit(1);
        }

        String command = args[0];
        String valueStr = args[1];
        double value;
        double convertedValue;

        // --- エラー処理: 変換方向のバリデーション ---
        boolean isMetersToFeet = command.equals("m2ft");
        boolean isFeetToMeters = command.equals("ft2m");

        if (!isMetersToFeet && !isFeetToMeters) {
            System.out.println("エラー: 無効な変換方向です。m2ftまたはft2mを指定してください。");
            System.exit(1);
        }

        // --- エラー処理: 数値のバリデーションとパース ---
        try {
            value = Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            System.out.println("エラー: 無効な数値が入力されました。");
            System.exit(1);
            return; // System.exitが呼ばれるため到達しないが、コンパイラの警告回避のため
        }

        // --- エラー処理: 数値が0未満であるかのチェック ---
        if (value < 0) {
            System.out.println("エラー: 変換する数値は0以上である必要があります。");
            System.exit(1);
        }

        // --- 変換ロジックの実行と結果表示 ---
        if (isMetersToFeet) {
            convertedValue = convertToFeet(value);
            // 例: 10.0メートルは32.8084フィートです。
            // 変換係数 (3.28084) に合わせて、フィートは小数点以下5桁まで表示。
            System.out.printf("%.1fメートルは%.5fフィートです。\n", value, convertedValue);
        } else { // isFeetToMeters
            convertedValue = convertToMeters(value);
            // 変換係数 (0.3048) に合わせて、メートルは小数点以下4桁まで表示。
            System.out.printf("%.1fフィートは%.4fメートルです。\n", value, convertedValue);
        }
    }
}