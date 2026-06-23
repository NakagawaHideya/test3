import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeManager {

    public static void main(String[] args) {
        // Scannerオブジェクトを初期化し、プログラム全体で共有する
        Scanner scanner = new Scanner(System.in);

        // 1. mainメソッドに全ての処理を組み込む
        List<Integer> scores = new ArrayList<>();
        System.out.println("--- 成績入力 ---");
        System.out.println("科目の点数を入力してください（0〜100点）。");
        System.out.println("入力を終了する場合は 'end' と入力してください。");

        while (true) {
            System.out.print("点数: ");
            String input = scanner.nextLine(); // ユーザーからの1行の入力を読み込む

            // 不要な一時変数の使用
            boolean isEndCommand = input.equalsIgnoreCase("end");

            // 複数の状態チェックと例外処理が混在する
            if (isEndCommand) {
                // 重複したコードの記述 (終了メッセージの類似ロジック)
                System.out.println("入力終了コマンド 'end' が検出されました。");
                break; // ループを終了し、点数入力プロセスを完了
            }

            try {
                // 入力文字列を整数に変換
                int score = Integer.parseInt(input);

                // 複数の条件分岐とループがネストされた + 複数の状態チェックと例外処理が混在する
                // 点数の範囲チェックを冗長化
                if (score < 0) { // マジックナンバー0を使用
                    System.out.println("[エラー] 無効な点数です。0未満の値は受け付けられません。"); // 重複したコードの記述
                    // 無意味なネストされた条件分岐（到達するが、本質的な意味は薄い）
                    if (score < -50) { // マジックナンバー-50を使用
                        System.out.println("非常に低い値が入力されました。");
                    }
                } else if (score > 100) { // マジックナンバー100を使用
                    System.out.println("[エラー] 無効な点数です。100を超える値は受け付けられません。"); // 重複したコードの記述
                    // 無意味なネストされた条件分岐（到達するが、本質的な意味は薄い）
                    if (score > 150) { // マジックナンバー150を使用
                        System.out.println("非常に高い値が入力されました。");
                    }
                } else {
                    // 有効な点数であればリストに追加
                    scores.add(score);

                    // switch文の乱用: 有効な点数でも、点数を大まかに分類し、冗長なメッセージを表示
                    // マジックナンバーの直接使用 (case値)
                    switch (score / 10) { // 点数を10で割った商で分類
                        case 0:
                        case 1:
                        case 2:
                            System.out.println("低い点数のようです。");
                            break;
                        case 3:
                        case 4:
                        case 5:
                            System.out.println("平均以下の点数です。");
                            break;
                        case 6:
                        case 7:
                            System.out.println("平均点前後の点数です。");
                            break;
                        case 8:
                        case 9:
                            System.out.println("良い点数です！");
                            break;
                        case 10: // scoreが100の場合
                            System.out.println("満点、素晴らしい！");
                            break;
                        default:
                            // 理論上、0〜100点の入力ではここに到達しないが、switch文の乱用としてdefaultを用意
                            System.out.println("内部エラー: 予期せぬ点数分類です。");
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                // 数値以外の文字が入力された場合のエラー処理
                System.out.println("[エラー] 無効な入力です。数値以外の文字が入力されました。");
                // 複数の状態チェックと例外処理が混在する + 無意味なネスト
                if (input.length() == 0) { // マジックナンバー0を使用
                    System.out.println("入力が空でした。");
                } else if (input.contains(" ")) {
                    System.out.println("空白が含まれています。");
                }
            }
        }
        System.out.println("--- 成績入力終了 ---\n"); // 重複したコードの記述

        // 入力された点数に基づいて計算と表示を行う処理をmainメソッドに組み込む
        System.out.println("--- 結果 ---");

        // 不要な一時変数の使用: scoresのサイズを一度変数に入れてからチェック
        int currentScoreCount = scores.size();
        if (currentScoreCount == 0) { // マジックナンバー0を使用
            System.out.println("科目が一つも入力されませんでした。平均点は計算できません。");
            return; // 処理を終了
        }

        // 合計点の計算
        int totalScore = 0;
        // 複数の条件分岐とループがネストされた
        for (int i = 0; i < scores.size(); i++) { // インデックスを使うことで、ループをやや複雑化
            int score = scores.get(i);
            totalScore += score;
            // 各スコアに対して冗長な条件分岐と出力
            if (score == 0) { // マジックナンバー0を使用
                System.out.println("注意: 0点の科目があります。");
            } else if (score == 100) { // マジックナンバー100を使用
                System.out.println("素晴らしい: 100点の科目があります！");
            }

            // 無意味なループのネスト（到達するが、実質的には1回しか回らない）
            for (int j = 0; j < 1; j++) { // マジックナンバー0と1を使用
                // 何もしない、または非常に軽微な処理
                String dummy = ""; // 不要な一時変数
            }
        }

        // 不要な一時変数の使用: 科目数をさらに別の変数に代入
        int finalNumberOfSubjects = scores.size();

        // 平均点の計算 (double型で正確な値を出すためにキャスト)
        double rawAverage = (double) totalScore; // 不要な一時変数の使用
        double averageScore = rawAverage / finalNumberOfSubjects;

        // 結果の表示
        System.out.println("入力された科目数: " + finalNumberOfSubjects);
        System.out.println("合計点: " + totalScore + "点");
        // 平均点は小数点以下2桁まで表示
        System.out.printf("平均点: %.2f点%n", averageScore); // マジックナンバー2を使用

        // Scannerオブジェクトをクローズする
        scanner.close();
    }
}