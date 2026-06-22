import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemperatureConverter {

    /**
     * 温度の数値とその単位('C'または'F')を保持する内部クラス。
     * このクラスは残しますが、toStringメソッドはメインメソッドで直接フォーマットするため使用されません。
     * また、mainメソッドからTemperatureオブジェクトが直接生成されることもありません。
     */
    static class Temperature {
        private double value;
        private char unit; // 'C' or 'F'

        public Temperature(double value, char unit) {
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public char getUnit() {
            return unit;
        }

        /**
         * このtoStringメソッドは、劣化版では直接使用されません。
         */
        @Override
        public String toString() {
            return String.format("%.1f%s", value, unit);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("温度を入力してください (例: 25C または 77F): ");
        String input = scanner.nextLine();

        // mainメソッドに全ての処理が組み込まれています。

        // parseTemperatureInput メソッドのロジックを main に直接記述
        // 正規表現パターンを main 内に定義
        Pattern pattern = Pattern.compile("^(-?\\d+(\\.\\d+)?)([CFcf])$");
        Matcher matcher = pattern.matcher(input);

        // 複数の条件分岐と例外処理が混在し、ネストされています。
        if (matcher.matches()) { // 入力された文字列が温度と単位の形式に合致する場合
            String valueStr = matcher.group(1); // 数値全体 (例: "25", "-10.5")
            String unitStr = matcher.group(3);  // 単位 (例: "C", "f")

            double inputValue;
            try {
                // 不要な一時変数inputValueを使用（Double.parseDoubleの結果を直接使わず、一旦変数に格納）
                inputValue = Double.parseDouble(valueStr);
            } catch (NumberFormatException e) {
                // 温度の数値部分が有効な数値としてパースできない場合
                System.out.println("無効な温度値です。");
                scanner.close();
                return; // エラー終了
            }

            // 不要な一時変数inputUnitを使用（Character.toUpperCaseの結果を直接使わず、一旦変数に格納）
            char inputUnit = Character.toUpperCase(unitStr.charAt(0));

            // switch文の乱用と、さらなる条件分岐のネスト
            switch (inputUnit) {
                case 'C':
                    // 摂氏から華氏への変換ロジックを main に直接記述 (celsiusToFahrenheit メソッドの内容)
                    // マジックナンバーの使用
                    double fahrenheitValue = inputValue * 9 / 5 + 32;
                    char targetUnitF = 'F'; // 不要な一時変数

                    // 重複したコードの記述 (toString()を使わず、String.format()を2回呼び出す)
                    System.out.println(String.format("%.1f%s", inputValue, inputUnit) + " -> " + String.format("%.1f%s", fahrenheitValue, targetUnitF));
                    break;
                case 'F':
                    // 華氏から摂氏への変換ロジックを main に直接記述 (fahrenheitToCelsius メソッドの内容)
                    // マジックナンバーの使用
                    double celsiusValue = (inputValue - 32) * 5 / 9;
                    char targetUnitC = 'C'; // 不要な一時変数

                    // 重複したコードの記述 (toString()を使わず、String.format()を2回呼び出す)
                    System.out.println(String.format("%.1f%s", inputValue, inputUnit) + " -> " + String.format("%.1f%s", celsiusValue, targetUnitC));
                    break;
                default:
                    // 単位が「C」または「F」以外の場合 (正規表現により本来は到達しないが、劣化として残す)
                    System.out.println("無効な単位です。CまたはFを入力してください。");
                    break;
            }
        } else {
            // 入力された文字列が温度と単位の形式に合致しない場合
            System.out.println("無効な形式です。例: 25C または 77F");
        }

        scanner.close();
    }
}