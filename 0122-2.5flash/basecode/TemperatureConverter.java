import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemperatureConverter {

    /**
     * 温度の数値とその単位('C'または'F')を保持する内部クラス。
     * 変換結果の表示にも使用します。
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
         * 温度と単位を「XX.XC」または「XX.XF」の形式で返します。
         * 例: 25.0C, 77.0F
         */
        @Override
        public String toString() {
            return String.format("%.1f%s", value, unit);
        }
    }

    /**
     * ユーザーからの入力文字列をパースし、Temperatureオブジェクトを生成します。
     * 入力形式の検証、数値パース、単位検証を行い、エラーが発生した場合は
     * 適切なエラーメッセージを表示し、nullを返します。
     *
     * @param input ユーザーからの入力文字列 (例: "25C", "68F", "-10.5c")
     * @return 成功した場合はTemperatureオブジェクト、失敗した場合はnull
     */
    private static Temperature parseTemperatureInput(String input) {
        // 正規表現パターン:
        // ^                 -> 文字列の始まり
        // (-?\d+(\.\d+)?)   -> 数値部分 (キャプチャグループ1)
        //   -?              -> オプションのマイナス記号
        //   \d+             -> 1桁以上の数字 (整数部)
        //   (\.\d+)?        -> オプションの小数部分 (例: .5)。この全体がキャプチャグループ2
        // ([CFcf])          -> 単位 (CまたはF、大文字小文字区別なし) (キャプチャグループ3)
        // $                 -> 文字列の終わり
        Pattern pattern = Pattern.compile("^(-?\\d+(\\.\\d+)?)([CFcf])$");
        Matcher matcher = pattern.matcher(input);

        // 1. 入力された文字列が温度と単位の形式に合致しない場合
        if (!matcher.matches()) {
            System.out.println("無効な形式です。例: 25C または 77F");
            return null;
        }

        // 正規表現でマッチした場合、数値部分と単位部分を抽出
        String valueStr = matcher.group(1); // 数値全体 (例: "25", "-10.5")
        String unitStr = matcher.group(3);  // 単位 (例: "C", "f")

        double value;
        try {
            // 2. 温度の数値部分が有効な数値としてパースできない場合
            // 正規表現が数値形式をある程度保証しているため、このエラーは稀ですが、念のため。
            value = Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            System.out.println("無効な温度値です。");
            return null;
        }

        // 単位を大文字に変換して標準化
        char unit = Character.toUpperCase(unitStr.charAt(0));

        // 3. 単位が「C」または「F」以外の場合
        // 正規表現がC/Fのみにマッチするようになっているため、この条件には通常入らないが、仕様に明記されているため残します。
        if (unit != 'C' && unit != 'F') {
            System.out.println("無効な単位です。CまたはFを入力してください。");
            return null;
        }

        return new Temperature(value, unit);
    }

    /**
     * 摂氏温度を華氏温度に変換します。
     * 変換式: F = C * 9/5 + 32
     *
     * @param celsius 摂氏温度
     * @return 華氏温度
     */
    private static double celsiusToFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }

    /**
     * 華氏温度を摂氏温度に変換します。
     * 変換式: C = (F - 32) * 5/9
     *
     * @param fahrenheit 華氏温度
     * @return 摂氏温度
     */
    private static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("温度を入力してください (例: 25C または 77F): ");
        String input = scanner.nextLine();

        // 入力文字列をパースし、Temperatureオブジェクトを取得
        Temperature inputTemp = parseTemperatureInput(input);

        // パースが成功した場合のみ変換と表示を行う
        if (inputTemp != null) {
            double convertedValue;
            char targetUnit;

            // 入力された単位に基づいて変換処理を分岐
            if (inputTemp.getUnit() == 'C') {
                // 摂氏から華氏へ
                convertedValue = celsiusToFahrenheit(inputTemp.getValue());
                targetUnit = 'F';
            } else { // inputTemp.getUnit() == 'F'
                // 華氏から摂氏へ
                convertedValue = fahrenheitToCelsius(inputTemp.getValue());
                targetUnit = 'C';
            }
            
            // 変換結果をTemperatureオブジェクトとして保持し、表示
            Temperature outputTemp = new Temperature(convertedValue, targetUnit);
            System.out.println(inputTemp.toString() + " -> " + outputTemp.toString());
        }

        scanner.close();
    }
}