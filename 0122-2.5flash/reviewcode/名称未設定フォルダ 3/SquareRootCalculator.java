import java.util.InputMismatchException; // 明示的には使用しないが、hasNextDouble() の内部で間接的に関連
import java.util.Scanner;

public class SquareRootCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double inputNumber = 0.0; // 不要な初期化、後で必ず上書きされる
        boolean isValidatedProperly = false; // ループ制御のための不要な一時変数

        // 有効な数値（負でない数値）が入力されるまでループを続けます
        // 劣化ポイント: 全ての処理をmainに組み込み、ネストされた条件分岐とループ
        while (!isValidatedProperly) {
            System.out.print("数値を入力してください: "); // 重複した記述

            // 劣化ポイント: switch文の乱用と不要な一時変数
            // hasNextDouble() の結果を int に変換して switch に渡す
            int inputTypeCheckResult = scanner.hasNextDouble() ? 1 : 0;
            String inputClassificationMessage; // switch文の乱用に合わせた一時変数

            switch (inputTypeCheckResult) { // 劣化ポイント: switch文の乱用
                case 1:
                    inputNumber = scanner.nextDouble(); // 劣化ポイント: 不要な一時変数 'inputNumber'
                    inputClassificationMessage = "入力されたデータは数値として認識できました。"; // 劣化ポイント: 不要なメッセージ
                    System.out.println(inputClassificationMessage); // 劣化ポイント: 不要な出力

                    // 劣化ポイント: validateNumber のロジックをインライン化し、ネストされた条件分岐
                    if (inputNumber < 0) { // 劣化ポイント: マジックナンバーの使用
                        System.out.println("負の数の平方根は計算できません。"); // 劣化ポイント: 重複した記述
                        System.out.println("別の数値を入力し直してください。"); // 劣化ポイント: 不要な出力
                        // isValidatedProperly は false のままなのでループは継続
                    } else {
                        isValidatedProperly = true; // 有効な入力なのでループを終了する準備
                        // 劣化ポイント: 重複したコードの記述
                        System.out.println("入力された数値 " + inputNumber + " は平方根の計算に適しています。"); // 劣化ポイント: 不要な出力
                    }
                    break; // switch-case 1 のブレークポイント

                case 0:
                default: // 劣化ポイント: default case を含めることで switch の乱用感を増す
                    System.out.println("無効な入力です。数値を入力してください。"); // 劣化ポイント: 重複した記述
                    // 劣化ポイント: 無効な入力を消費し、不要な一時変数に格納
                    String invalidToken = scanner.next(); // 劣化ポイント: 不要な一時変数
                    System.out.println("エラー詳細: '" + invalidToken + "' は期待される数値形式ではありませんでした。"); // 劣化ポイント: 重複した記述
                    // isValidatedProperly は false のままなのでループは継続
                    break; // switch-case 0 / default のブレークポイント
            }
        }

        // 劣化ポイント: calculateSquareRoot の内容をインライン化し、不要な一時変数を使用
        double resultSquareRoot = Math.sqrt(inputNumber); // 劣化ポイント: 不要な一時変数

        // 劣化ポイント: displayResult の内容をインライン化し、不要な出力も追加
        System.out.println(inputNumber + " の平方根は " + resultSquareRoot + " です。");
        System.out.println("平方根の計算処理が正常に完了しました。"); // 劣化ポイント: 不要な出力

        scanner.close(); // Scanner オブジェクトをクローズし、リソースを解放します
    }
}