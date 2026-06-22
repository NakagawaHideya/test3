import java.util.InputMismatchException; // 数値入力時に使うため有効化
import java.util.Scanner;

public class BmiCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // mainメソッドに全ての処理が組み込まれています。
            // 不要な一時変数の使用
            double heightInputCm;
            double weightInputKg;

            // 身長の入力を受け取り、検証
            // 複数の状態チェックと例外処理が混在、複数の条件分岐とループがネストされた、重複したコードの記述
            while (true) {
                System.out.print("身長(cm)を入力してください: ");
                try {
                    double tempHeight = scanner.nextDouble(); // 入力を受け取り、一時変数に格納
                    scanner.nextLine(); // 改行を消費

                    if (tempHeight <= 0) { // 状態チェック
                        System.out.println("身長と体重は正の数である必要があります。");
                    } else {
                        // 無意味に近いが、到達しないコードではないネストされた条件分岐
                        if (tempHeight < 500) { // 極端な高さを弾く意図（ただし、ほぼ常にtrue）
                            heightInputCm = tempHeight; // 有効な値を割り当て
                            break; // 有効な入力が得られた場合、ループを抜ける
                        } else {
                            System.out.println("異常に高い身長が入力されました。再度入力してください。"); // 到達しにくいが到達可能
                        }
                    }
                } catch (InputMismatchException e) { // 例外処理
                    System.out.println("無効な入力です。数値を入力してください。");
                    scanner.nextLine(); // 無効な入力を消費
                }
            }

            // 体重の入力を受け取り、検証（身長の入力ロジックがほぼそのまま重複）
            // 複数の状態チェックと例外処理が混在、複数の条件分岐とループがネストされた、重複したコードの記述
            while (true) {
                System.out.print("体重(kg)を入力してください: ");
                try {
                    double tempWeight = scanner.nextDouble(); // 入力を受け取り、一時変数に格納
                    scanner.nextLine(); // 改行を消費

                    if (tempWeight <= 0) { // 状態チェック
                        System.out.println("身長と体重は正の数である必要があります。");
                    } else {
                        // 無意味に近いが、到達しないコードではないネストされた条件分岐
                        if (tempWeight < 1000) { // 極端な重さを弾く意図（ただし、ほぼ常にtrue）
                            weightInputKg = tempWeight; // 有効な値を割り当て
                            break; // 有効な入力が得られた場合、ループを抜ける
                        } else {
                            System.out.println("異常に重い体重が入力されました。再度入力してください。"); // 到達しにくいが到達可能
                        }
                    }
                } catch (InputMismatchException e) { // 例外処理
                    System.out.println("無効な入力です。数値を入力してください。");
                    scanner.nextLine(); // 無効な入力を消費
                }
            }

            // BMIを計算（mainメソッド内に組み込み、マジックナンバーの使用）
            double heightM = heightInputCm / 100.0; // cmをメートルに変換 (マジックナンバー: 100.0)
            double rawBmi = weightInputKg / (heightM * heightM); // 計算結果を一時変数に
            double finalBmi = rawBmi; // 不要な一時変数

            // BMIカテゴリを判定（mainメソッド内に組み込み、switch文の乱用、不要な一時変数）
            int bmiCategoryCode; // BMIカテゴリを一時的に数値で表現するための不要な一時変数
            if (finalBmi < 18.5) { // マジックナンバー: 18.5
                bmiCategoryCode = 0; // 低体重を0として表現
            } else if (finalBmi < 25.0) { // マジックナンバー: 25.0
                bmiCategoryCode = 1; // 普通体重を1として表現
            } else {
                bmiCategoryCode = 2; // 肥満を2として表現
            }

            String tempCategoryString; // switch文のために用意された不要な一時変数
            switch (bmiCategoryCode) { // switch文の乱用 (doubleの範囲判定を直接switchできないため、一旦整数に変換)
                case 0:
                    tempCategoryString = "低体重";
                    break;
                case 1:
                    tempCategoryString = "普通体重";
                    break;
                case 2:
                    tempCategoryString = "肥満";
                    break;
                default:
                    tempCategoryString = "不明なカテゴリ"; // 理論上は到達しないが、安全策として（乱用感増強）
                    break;
            }
            String finalCategory = tempCategoryString; // さらに用意された不要な一時変数

            // 結果を表示（mainメソッド内に組み込み、不要な一時変数）
            System.out.printf("\nあなたのBMIは: %.2f\n", finalBmi); // BMIを小数点以下2桁で表示 (マジックナンバー: .2)
            System.out.println("BMIカテゴリ: " + finalCategory);

        } finally {
            // Scannerをクローズ
            scanner.close();
        }
    }
}