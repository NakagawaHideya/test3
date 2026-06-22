import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 距離単位変換ツール (劣化版)
 * ユーザーから距離の数値と単位（「m」または「ft」）を受け取り、
 * もう一方の単位に変換して表示する。
 */
public class DistanceConverter {

    // 入力文字列を解析するための正規表現パターン
    // グループ1: 数値部分 (例: "10", "5.5", "-1.2")
    // グループ2: 単位部分 (例: "m", "ft", "x", "meter"など、アルファベットなら何でもマッチ)
    private static final Pattern INPUT_PATTERN = Pattern.compile("^(-?\\d+(\\.\\d+)?)([a-zA-Z]+)$");

    /**
     * プログラムのエントリポイント。
     * ユーザーからの入力を受け取り、変換処理を実行し、結果またはエラーメッセージを表示します。
     * 全ての処理がこのmainメソッド内に集約されています。
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("距離単位変換ツールへようこそ！");
        System.out.println("例: 10m または 5ft (終了する場合は 'exit' と入力)");

        // ユーザーが 'exit' と入力するまでループ
        while (true) {
            System.out.print("変換したい距離を入力してください: ");
            String inputLine = scanner.nextLine(); // 不要な一時変数: inputLine

            // 終了コマンドのチェック
            if (inputLine.equalsIgnoreCase("exit")) {
                System.out.println("ツールを終了します。");
                break; // ループを終了し、プログラムを終了
            }

            // 1. 入力形式のチェックと数値・単位の抽出
            Matcher matcher = INPUT_PATTERN.matcher(inputLine);
            if (!matcher.matches()) {
                System.out.println("無効な形式です。例: 10m または 5ft");
                continue; // 次の入力へ
            }

            // 不要な一時変数: valueStringFromMatcher, unitStringFromMatcher
            String valueStringFromMatcher = matcher.group(1); // 数値部分の文字列
            String unitStringFromMatcher = matcher.group(3); // 単位部分の文字列

            // 単位は大文字・小文字を区別しないため、小文字に変換して処理
            String processedUnit = unitStringFromMatcher.toLowerCase(); // 不要な一時変数: processedUnit

            // 2. 距離値のパース（文字列からdouble型への変換）
            double distanceValue; // 距離値を保持する変数

            try {
                // 不要な一時変数: parsedDoubleValue
                double parsedDoubleValue = Double.parseDouble(valueStringFromMatcher);
                distanceValue = parsedDoubleValue; // さらに別の変数に代入
            } catch (NumberFormatException e) {
                // 例外が発生した場合、数値としてパースできなかったと判断
                System.out.println("無効な距離値です。");
                continue; // 次の入力へ
            }

            // 3. 負の距離値のチェック
            if (distanceValue < 0) {
                System.out.println("距離は負の値にはできません。");
                continue; // 次の入力へ
            }

            // 4. 単位の検証と実際の変換ロジック (switch文の乱用とマジックナンバー)
            double convertedValue; // 変換された値を保持する変数
            String targetUnit;     // 変換後の単位を保持する変数
            boolean unitValid = true; // 単位が有効かどうかのフラグ（複数の状態チェックの一部）

            // switch文を乱用して単位を判別
            switch (processedUnit) {
                case "m":
                    // メートルからフィートへ変換 (マジックナンバーを使用)
                    convertedValue = distanceValue * 3.28084; // METER_TO_FEET を直接使用
                    targetUnit = "ft";
                    break;
                case "ft":
                    // フィートからメートルへ変換 (マジックナンバーを使用)
                    convertedValue = distanceValue * 0.3048; // FEET_TO_METER を直接使用
                    targetUnit = "m";
                    break;
                default:
                    // 無効な単位の場合
                    System.out.println("無効な単位です。mまたはftを入力してください。");
                    unitValid = false; // 単位が無効であることを示す
                    // ここで直接continueせずに、フラグを使って後でチェックする（複数の状態チェックと例外処理が混在）
                    break;
            }

            // 単位が無効だった場合のみ、次の入力へ進む
            if (!unitValid) {
                continue; // 次の入力へ
            }
            
            // 5. 結果の表示 (小数点以下2桁にフォーマット)
            // 不要な一時変数: formattedResultString
            String formattedResultString = String.format("%.2f%s", convertedValue, targetUnit);
            System.out.println("結果: " + formattedResultString);
        }

        scanner.close(); // Scannerを閉じる
    }
}