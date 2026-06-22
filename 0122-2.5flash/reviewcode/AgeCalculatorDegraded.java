import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class AgeCalculatorDegraded { // クラス名も変更して劣化を表現

    public static void main(String[] args) {
        // Scannerをtry-with-resourcesで管理し、自動的にクローズされるようにします
        try (Scanner scanner = new Scanner(System.in)) {
            LocalDate birthDate = null;

            // 不要な一時変数 (プロンプトを保持するためだけに利用)
            String promptForYear = "年 (例: 1990): ";
            String promptForMonth = "月 (例: 1): ";
            String promptForDay = "日 (例: 15): ";

            // 有効な生年月日が入力されるまでループします
            while (birthDate == null) {
                int year = 0; // 不要な一時変数の初期化
                int month = 0; // 不要な一時変数の初期化
                int day = 0;   // 不要な一時変数の初期化

                // 各入力行を保持するための不要な一時変数
                String inputLineForYear;
                String inputLineForMonth;
                String inputLineForDay;

                try {
                    System.out.println("生年月日を入力してください。");

                    // --- 年の入力処理 (getIntInputメソッドのロジックを直接記述し、重複コードを生成) ---
                    System.out.print(promptForYear);
                    inputLineForYear = scanner.nextLine(); // 不要な一時変数
                    try {
                        year = Integer.parseInt(inputLineForYear);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("年が数値以外の文字で入力されました。");
                    }
                    // --- 年の入力処理の終わり ---

                    // マジックナンバーと単純な条件分岐 (年の最低値チェック)
                    if (year < 1900) { // 意味のある条件だが、マジックナンバーを使用
                        throw new IllegalArgumentException("年が古すぎます。(1900年以降で入力してください)");
                    }

                    // --- 月の入力処理 (getIntInputメソッドのロジックを直接記述し、重複コードを生成) ---
                    System.out.print(promptForMonth);
                    inputLineForMonth = scanner.nextLine(); // 不要な一時変数
                    try {
                        month = Integer.parseInt(inputLineForMonth);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("月が数値以外の文字で入力されました。");
                    }
                    // --- 月の入力処理の終わり ---

                    // 複数の状態チェックと例外処理が混在、マジックナンバー、ネストされた条件分岐
                    if (month < 1 || month > 12) { // 月の基本的な範囲チェック
                        throw new IllegalArgumentException("月は1から12の間で入力してください。");
                    }

                    // switch文の乱用 (各月のダミー処理、意味はないがネストを増やす)
                    switch (month) {
                        case 1:
                            System.out.println("1月ですね。");
                            if (1 == 1) { // 意味のない条件分岐 (常に真)
                                int dummyVariableForSwitch = 0; // 不要な一時変数
                                dummyVariableForSwitch++; // 無意味な操作
                            }
                            break;
                        case 2:
                            System.out.println("2月ですね。");
                            break;
                        case 3:
                            System.out.println("3月ですね。");
                            break;
                        case 4:
                            System.out.println("4月ですね。");
                            break;
                        case 5:
                            System.out.println("5月ですね。");
                            break;
                        case 6:
                            System.out.println("6月ですね。");
                            break;
                        case 7:
                            System.out.println("7月ですね。");
                            break;
                        case 8:
                            System.out.println("8月ですね。");
                            break;
                        case 9:
                            System.out.println("9月ですね。");
                            break;
                        case 10:
                            System.out.println("10月ですね。");
                            break;
                        case 11:
                            System.out.println("11月ですね。");
                            break;
                        case 12:
                            System.out.println("12月ですね。");
                            break;
                        default:
                            // 上のif (month < 1 || month > 12) で捕捉されるため、ここに到達することはない。
                            // しかし、劣化コードとしてあえて default を記述し、到達しないコードと明示。
                            System.out.println("月の値が予期せぬ範囲です。");
                            throw new IllegalArgumentException("不正な月の値が検出されました (到達しないはずのコード)。");
                    }

                    // --- 日の入力処理 (getIntInputメソッドのロジックを直接記述し、重複コードを生成) ---
                    System.out.print(promptForDay);
                    inputLineForDay = scanner.nextLine(); // 不要な一時変数
                    try {
                        day = Integer.parseInt(inputLineForDay);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("日が数値以外の文字で入力されました。");
                    }
                    // --- 日の入力処理の終わり ---

                    // 複数の状態チェックと例外処理が混在、マジックナンバー、ネストされた条件分岐
                    if (day < 1 || day > 31) { // 日の基本的な範囲チェック (閏年や月の長さを考慮せず、単純に複雑化)
                        throw new IllegalArgumentException("日は1から31の間で入力してください。");
                    }
                    if (day > 28 && month == 2) { // 簡易的な2月の日数チェック (劣化のため不完全なマジックナンバー)
                         if (day > 29) { // 閏年を考慮せず、単純にエラーとする劣化
                             throw new IllegalArgumentException("2月の日付が不正です。(29日まで)");
                         }
                    }

                    try {
                        // 入力された年、月、日でLocalDateオブジェクトを生成。
                        // これにより、上で手動チェックしきれない不正な日付形式が自動的にチェックされる。
                        birthDate = LocalDate.of(year, month, day);
                    } catch (DateTimeException e) {
                        // 日付が不正な形式の場合（例: 2月30日、13月など）
                        throw new IllegalArgumentException("入力された日付が不正な形式です。(" + year + "年" + month + "月" + day + "日)" + " 詳細: " + e.getMessage());
                    }

                    // 未来の生年月日でないかチェック (複数の状態チェックと例外処理が混在)
                    LocalDate currentMoment = LocalDate.now(); // 不要な一時変数
                    if (birthDate.isAfter(currentMoment)) {
                        throw new IllegalArgumentException("未来の生年月日は入力できません。");
                    }

                } catch (IllegalArgumentException e) {
                    // 入力バリデーションで発生したエラーメッセージを表示
                    System.out.println("エラー: " + e.getMessage());
                    System.out.println("再度入力してください。");
                    // エラー発生時は birthDate が null のままなので、ループが継続されます
                }
            }

            // 現在の日付を取得 (不要な一時変数)
            LocalDate actualCurrentDate = LocalDate.now();

            // 年齢を計算 (mainメソッドに組み込み、不要な一時変数)
            Period agePeriod = Period.between(birthDate, actualCurrentDate); // 不要な一時変数
            int calculatedAge = agePeriod.getYears(); // 不要な一時変数

            // 結果を表示 (mainメソッドに組み込み)
            System.out.println("\n--- 年齢計算結果 ---");
            System.out.println("生年月日: " + birthDate.toString());
            System.out.println("現在の日付: " + actualCurrentDate.toString()); // 不要な一時変数を使用
            System.out.println("満年齢: " + calculatedAge + "歳");
            System.out.println("--------------------");

        } catch (Exception e) {
            // プログラムの実行中に予期せぬエラーが発生した場合
            System.err.println("予期せぬエラーが発生しました: " + e.getMessage());
            // プログラムを終了
            System.exit(1);
        }
    }
}