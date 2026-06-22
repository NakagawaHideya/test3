import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 距離単位変換ツール
 * ユーザーから距離の数値と単位（「m」または「ft」）を受け取り、
 * もう一方の単位に変換して表示する。
 */
public class DistanceConverter {

    // 変換レートを定数として定義
    private static final double METER_TO_FEET = 3.28084;
    private static final double FEET_TO_METER = 0.3048;

    // 入力文字列を解析するための正規表現パターン
    // グループ1: 数値部分 (例: "10", "5.5", "-1.2")
    // グループ2: 単位部分 (例: "m", "ft", "x", "meter"など、アルファベットなら何でもマッチ)
    // この正規表現により、入力が「数値+単位」の形式であるかを最初にチェックします。
    // 単位が「m」または「ft」であるかの厳密なチェックは後で行います。
    private static final Pattern INPUT_PATTERN = Pattern.compile("^(-?\\d+(\\.\\d+)?)([a-zA-Z]+)$");

    /**
     * プログラムのエントリポイント。
     * ユーザーからの入力を受け取り、変換処理を実行し、結果またはエラーメッセージを表示します。
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("距離単位変換ツールへようこそ！");
        System.out.println("例: 10m または 5ft (終了する場合は 'exit' と入力)");

        // ユーザーが 'exit' と入力するまでループ
        while (true) {
            System.out.print("変換したい距離を入力してください: ");
            String input = scanner.nextLine();

            // 終了コマンドのチェック
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("ツールを終了します。");
                break; // ループを終了し、プログラムを終了
            }

            // 距離変換処理を実行
            try {
                String result = convert(input);
                System.out.println("結果: " + result);
            } catch (IllegalArgumentException e) {
                // 仕様に記述されているエラーメッセージを表示
                System.out.println(e.getMessage());
            }
        }

        scanner.close(); // Scannerを閉じる
    }

    /**
     * ユーザーからの入力文字列を解析し、距離を変換する主要なメソッド。
     * 入力形式、数値の妥当性、単位、および負の値でないことを検証します。
     *
     * @param input ユーザー入力文字列 (例: "10m", "5ft")
     * @return 変換結果文字列 (例: "32.81ft", "1.52m")
     * @throws IllegalArgumentException 入力形式、距離値、単位、または負の距離に関するエラーが発生した場合
     */
    public static String convert(String input) throws IllegalArgumentException {
        // 1. 入力形式のチェックと数値・単位の抽出
        Matcher matcher = INPUT_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("無効な形式です。例: 10m または 5ft");
        }

        String valueString = matcher.group(1); // 数値部分の文字列
        // 単位は大文字・小文字を区別しないため、小文字に変換して処理
        String unit = matcher.group(3).toLowerCase(); // 単位部分の文字列

        // 2. 距離値のパース（文字列からdouble型への変換）
        double distanceValue;
        try {
            distanceValue = Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            // 例外が発生した場合、数値としてパースできなかったと判断
            throw new IllegalArgumentException("無効な距離値です。");
        }

        // 3. 負の距離値のチェック
        if (distanceValue < 0) {
            throw new IllegalArgumentException("距離は負の値にはできません。");
        }

        // 4. 単位の検証
        // 正規表現でアルファベットであることを確認済みだが、具体的な単位が「m」または「ft」であるかを確認
        if (!unit.equals("m") && !unit.equals("ft")) {
            throw new IllegalArgumentException("無効な単位です。mまたはftを入力してください。");
        }

        // 5. すべての検証が成功したら、実際の変換ロジックを実行
        return performConversion(distanceValue, unit);
    }

    /**
     * 実際の距離変換を行い、結果をフォーマットするプライベートメソッド。
     * このメソッドは、入力値と単位がすでにバリデーションされていることを前提とします。
     *
     * @param value 変換する距離の数値 (負ではない、有効なdouble値)
     * @param unit  変換元の単位 ("m" または "ft" の小文字)
     * @return 変換された距離と新しい単位を含む文字列 (小数点以下2桁にフォーマット)
     */
    private static String performConversion(double value, String unit) {
        double convertedValue;
        String targetUnit;

        if (unit.equals("m")) {
            // メートルからフィートへ変換
            convertedValue = value * METER_TO_FEET;
            targetUnit = "ft";
        } else { // unit.equals("ft")
            // フィートからメートルへ変換
            convertedValue = value * FEET_TO_METER;
            targetUnit = "m";
        }

        // 結果を小数点以下2桁まででフォーマットして返す
        return String.format("%.2f%s", convertedValue, targetUnit);
    }
}