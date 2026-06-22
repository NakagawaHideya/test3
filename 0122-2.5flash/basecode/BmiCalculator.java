import java.util.InputMismatchException; // 今回はNumberFormatExceptionを使うため不要だが、数値入力時に使うことがある
import java.util.Scanner;

public class BmiCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // 身長の入力を受け取り、検証
            double heightCm = getValidatedPositiveDoubleInput(scanner, "身長(cm)を入力してください: ");
            // 体重の入力を受け取り、検証
            double weightKg = getValidatedPositiveDoubleInput(scanner, "体重(kg)を入力してください: ");

            // BMIを計算
            double bmi = calculateBmi(heightCm, weightKg);

            // BMIカテゴリを判定
            String category = getBmiCategory(bmi);

            // 結果を表示
            displayBmiResult(bmi, category);

        } finally {
            // Scannerをクローズ
            scanner.close();
        }
    }

    /**
     * ユーザーから正の浮動小数点数入力を受け取り、検証するメソッド。
     * 無効な入力（非数値、0以下）の場合はエラーメッセージを表示し、再入力を促します。
     *
     * @param scanner Scannerオブジェクト
     * @param prompt  ユーザーに表示するプロンプトメッセージ
     * @return 検証済みの正の浮動小数点数
     */
    private static double getValidatedPositiveDoubleInput(Scanner scanner, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine(); // 入力を文字列として取得

            try {
                value = Double.parseDouble(input); // 文字列をdouble型に変換

                // 0以下の数値チェック
                if (value <= 0) {
                    System.out.println("身長と体重は正の数である必要があります。");
                } else {
                    // 有効な入力が得られた場合、ループを抜ける
                    break;
                }
            } catch (NumberFormatException e) {
                // 非数値入力の場合のエラー処理
                System.out.println("無効な入力です。数値を入力してください。");
            }
        }
        return value;
    }

    /**
     * 身長(cm)と体重(kg)からBMIを計算するメソッド。
     *
     * @param heightCm 身長 (cm)
     * @param weightKg 体重 (kg)
     * @return 計算されたBMI値
     */
    private static double calculateBmi(double heightCm, double weightKg) {
        double heightM = heightCm / 100.0; // cmをメートルに変換
        return weightKg / (heightM * heightM);
    }

    /**
     * BMI値に基づいてカテゴリを判定するメソッド。
     *
     * @param bmi 計算されたBMI値
     * @return BMIカテゴリを表す文字列（例: "低体重", "普通体重", "肥満"）
     */
    private static String getBmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "低体重";
        } else if (bmi < 25.0) { // 18.5以上25未満
            return "普通体重";
        } else { // 25以上
            return "肥満";
        }
    }

    /**
     * 計算されたBMI値とカテゴリを表示するメソッド。
     *
     * @param bmi      計算されたBMI値
     * @param category BMIカテゴリの文字列
     */
    private static void displayBmiResult(double bmi, String category) {
        System.out.printf("\nあなたのBMIは: %.2f\n", bmi); // BMIを小数点以下2桁で表示
        System.out.println("BMIカテゴリ: " + category);
    }
}