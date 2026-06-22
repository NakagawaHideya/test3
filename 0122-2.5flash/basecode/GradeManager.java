import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeManager {

    public static void main(String[] args) {
        // Scannerオブジェクトを初期化し、プログラム全体で共有する
        Scanner scanner = new Scanner(System.in);

        // 1. ユーザーからの点数入力を受け付け、エラー処理を行うメソッド
        List<Integer> scores = getScores(scanner);

        // 2. 入力された点数に基づいて計算と表示を行うメソッド
        calculateAndDisplayResults(scores);

        // Scannerオブジェクトをクローズする
        scanner.close();
    }

    /**
     * ユーザーから科目の点数を入力してもらい、有効な点数のリストを返します。
     * 入力エラー（非数値、範囲外）を処理します。
     * 入力終了は「end」と入力することで行います。
     *
     * @param scanner 標準入力からの入力を受け取るためのScannerオブジェクト
     * @return 入力された有効な点数のリスト
     */
    private static List<Integer> getScores(Scanner scanner) {
        List<Integer> scores = new ArrayList<>();
        System.out.println("--- 成績入力 ---");
        System.out.println("科目の点数を入力してください（0〜100点）。");
        System.out.println("入力を終了する場合は 'end' と入力してください。");

        while (true) {
            System.out.print("点数: ");
            String input = scanner.nextLine(); // ユーザーからの1行の入力を読み込む

            // 入力終了のチェック（大文字・小文字を区別しない）
            if (input.equalsIgnoreCase("end")) {
                break; // ループを終了し、点数入力プロセスを完了
            }

            try {
                // 入力文字列を整数に変換
                int score = Integer.parseInt(input);

                // 点数の範囲チェック (0未満または100を超える値)
                if (score < 0 || score > 100) {
                    // エラーメッセージを表示し、その入力をスキップ（リストに追加しない）
                    System.out.println("[エラー] 無効な点数です。0から100の範囲で入力してください。");
                } else {
                    // 有効な点数であればリストに追加
                    scores.add(score);
                }
            } catch (NumberFormatException e) {
                // 数値以外の文字が入力された場合のエラー処理
                System.out.println("[エラー] 無効な入力です。数値以外の文字が入力されました。");
                // この入力をスキップ（リストに追加しない）
            }
        }
        System.out.println("--- 成績入力終了 ---\n");
        return scores;
    }

    /**
     * 入力された点数リストに基づいて合計点、平均点を計算し、結果を表示します。
     * 科目が一つも入力されなかった場合の処理も行います。
     *
     * @param scores 入力された点数のリスト
     */
    private static void calculateAndDisplayResults(List<Integer> scores) {
        System.out.println("--- 結果 ---");

        // 科目が一つも入力されなかった場合のエラー処理
        if (scores.isEmpty()) {
            System.out.println("科目が一つも入力されませんでした。平均点は計算できません。");
            return; // 処理を終了
        }

        // 合計点の計算
        int totalScore = 0;
        for (int score : scores) {
            totalScore += score;
        }

        // 科目数の取得
        int numberOfSubjects = scores.size();

        // 平均点の計算 (double型で正確な値を出すためにキャスト)
        double averageScore = (double) totalScore / numberOfSubjects;

        // 結果の表示
        System.out.println("入力された科目数: " + numberOfSubjects);
        System.out.println("合計点: " + totalScore + "点");
        // 平均点は小数点以下2桁まで表示
        System.out.printf("平均点: %.2f点%n", averageScore);
    }
}