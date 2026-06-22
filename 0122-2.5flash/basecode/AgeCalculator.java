import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class AgeCalculator {

    public static void main(String[] args) {
        // Scannerをtry-with-resourcesで管理し、自動的にクローズされるようにします
        try (Scanner scanner = new Scanner(System.in)) {
            LocalDate birthDate = null;

            // 有効な生年月日が入力されるまでループします
            while (birthDate == null) {
                try {
                    // ユーザーから生年月日を取得し、バリデーションを行います
                    birthDate = getBirthDateFromUser(scanner);
                } catch (IllegalArgumentException e) {
                    // getBirthDateFromUser内で発生したエラーメッセージを表示
                    System.out.println("エラー: " + e.getMessage());
                    System.out.println("再度入力してください。");
                    // エラー発生時は birthDate が null のままなので、ループが継続されます
                }
            }

            // 現在の日付を取得
            LocalDate currentDate = LocalDate.now();

            // 年齢を計算
            int age = calculateAge(birthDate, currentDate);

            // 結果を表示
            displayResult(birthDate, currentDate, age);

        } catch (Exception e) {
            // プログラムの実行中に予期せぬエラーが発生した場合
            System.err.println("予期せぬエラーが発生しました: " + e.getMessage());
            // プログラムを終了
            System.exit(1);
        }
    }

    /**
     * ユーザーから年、月、日を取得し、LocalDateオブジェクトを生成する。
     * 入力検証を行い、不正な場合はIllegalArgumentExceptionをスローする。
     *
     * @param scanner 標準入力からの入力を読み取るためのScannerオブジェクト
     * @return ユーザーが入力した生年月日を表すLocalDateオブジェクト
     * @throws IllegalArgumentException 入力が数値でない、日付が不正、または未来の日付である場合
     */
    private static LocalDate getBirthDateFromUser(Scanner scanner) throws IllegalArgumentException {
        System.out.println("生年月日を入力してください。");

        int year;
        int month;
        int day;

        // 年の入力と数値形式の検証
        year = getIntInput(scanner, "年 (例: 1990): ");
        // 月の入力と数値形式の検証
        month = getIntInput(scanner, "月 (例: 1): ");
        // 日の入力と数値形式の検証
        day = getIntInput(scanner, "日 (例: 15): ");

        LocalDate birthDate;
        try {
            // 入力された年、月、日でLocalDateオブジェクトを生成。
            // これにより、2月30日、13月などの不正な日付形式が自動的にチェックされる。
            birthDate = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            // 日付が不正な形式の場合（例: 2月30日、13月など）
            throw new IllegalArgumentException("入力された日付が不正な形式です。(" + year + "年" + month + "月" + day + "日)");
        }

        // 未来の生年月日でないかチェック
        if (isFutureDate(birthDate)) {
            throw new IllegalArgumentException("未来の生年月日は入力できません。");
        }

        return birthDate;
    }

    /**
     * ユーザーからの数値入力を受け取り、整数に変換して返す。
     * 数値以外の文字が入力された場合は、IllegalArgumentExceptionをスローする。
     *
     * @param scanner 標準入力からの入力を読み取るためのScannerオブジェクト
     * @param prompt ユーザーに表示するプロンプトメッセージ
     * @return ユーザーが入力した整数値
     * @throws IllegalArgumentException 数値以外の文字が入力された場合
     */
    private static int getIntInput(Scanner scanner, String prompt) throws IllegalArgumentException {
        System.out.print(prompt);
        String inputLine = scanner.nextLine(); // 1行すべて読み込む
        try {
            return Integer.parseInt(inputLine);
        } catch (NumberFormatException e) {
            // 数値以外の文字が入力された場合
            throw new IllegalArgumentException("数値以外の文字が入力されました。");
        }
    }

    /**
     * 入力された生年月日が未来の日付であるかどうかをチェックする。
     *
     * @param birthDate チェック対象の生年月日
     * @return birthDateが現在のLocalDateより未来であればtrue、そうでなければfalse
     */
    private static boolean isFutureDate(LocalDate birthDate) {
        return birthDate.isAfter(LocalDate.now());
    }

    /**
     * 生年月日と現在の日付を基に満年齢を計算する。
     *
     * @param birthDate 生年月日
     * @param currentDate 現在の日付
     * @return 満年齢
     */
    private static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }

    /**
     * 計算された年齢と関連情報を表示する。
     *
     * @param birthDate 生年月日
     * @param currentDate 現在の日付
     * @param age 満年齢
     */
    private static void displayResult(LocalDate birthDate, LocalDate currentDate, int age) {
        System.out.println("\n--- 年齢計算結果 ---");
        System.out.println("生年月日: " + birthDate.toString());
        System.out.println("現在の日付: " + currentDate.toString());
        System.out.println("満年齢: " + age + "歳");
        System.out.println("--------------------");
    }
}