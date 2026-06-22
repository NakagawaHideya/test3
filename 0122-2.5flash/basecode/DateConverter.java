import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 日付のフォーマット変換を行うプログラムです。
 * ユーザーに「YYYY/MM/DD」形式の日付を入力させ、
 * それを「DD-MM-YYYY」形式に変換して表示します。
 * 入力形式のチェックと日付の存在チェックを行い、エラーの場合は再入力を促します。
 */
public class DateConverter {

    /**
     * プログラムのエントリポイントです。
     * ユーザーからの入力を受け付け、日付の変換処理を実行し、結果を表示します。
     */
    public static void main(String[] args) {
        // try-with-resources を使用してScannerを自動的に閉じます
        try (Scanner scanner = new Scanner(System.in)) {
            // 有効な日付文字列（YYYY/MM/DD形式）の入力を受け取る
            String inputDateString = getInputDate(scanner);

            // 日付をDD-MM-YYYY形式に変換する
            String convertedDate = convertDateFormat(inputDateString);

            // 変換結果を表示
            System.out.println("変換後の日付: " + convertedDate);

        } catch (Exception e) {
            // 予期せぬエラーが発生した場合の一般的な処理
            // 仕様で要求されているエラー処理とは異なる
            System.err.println("予期せぬエラーが発生しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーから日付文字列を繰り返し入力させ、
     * 有効な「YYYY/MM/DD」形式かつ存在する日付を受け取るまでループします。
     *
     * @param scanner 標準入力を読み取るためのScannerオブジェクト
     * @return 有効な「YYYY/MM/DD」形式の日付文字列
     */
    private static String getInputDate(Scanner scanner) {
        while (true) {
            System.out.print("日付を『YYYY/MM/DD』形式で入力してください: ");
            String dateString = scanner.nextLine();

            // 1. 入力形式のチェック
            if (!isValidDateFormat(dateString)) {
                System.out.println("無効な日付形式です。『YYYY/MM/DD』形式で入力してください。");
                continue; // 無効な形式なので再入力を促す
            }

            // 2. 日付の存在チェック
            if (!isExistingDate(dateString)) {
                System.out.println("存在しない日付です。");
                continue; // 存在しない日付なので再入力を促す
            }

            // 両方のチェックをパスした場合、有効な日付として返す
            return dateString;
        }
    }

    /**
     * 入力文字列が「YYYY/MM/DD」形式に合致するかを正規表現でチェックします。
     * 例: "2023/01/23" は true、"2023-01-23" や "2023/1/23" は false。
     *
     * @param dateString チェックする日付文字列
     * @return 形式が正しい場合は true、そうでない場合は false
     */
    private static boolean isValidDateFormat(String dateString) {
        // YYYY/MM/DD 形式の正規表現
        // ^\\d{4}/\\d{2}/\\d{2}$ は、文字列全体が "4桁の数字 / 2桁の数字 / 2桁の数字" に一致することを示します。
        String regex = "^\\d{4}/\\d{2}/\\d{2}$";
        return Pattern.matches(regex, dateString);
    }

    /**
     * 「YYYY/MM/DD」形式の文字列が実際に存在する日付を表しているかをチェックします。
     * 例: "2023/02/30" や "2023/13/01" は存在しないため false を返します。
     * このメソッドは、isValidDateFormat が true を返した後に呼び出されることを想定しています。
     *
     * @param dateString チェックする日付文字列 (YYYY/MM/DD形式であることを前提)
     * @return 日付が存在する場合は true、そうでない場合は false
     */
    private static boolean isExistingDate(String dateString) {
        try {
            // "yyyy/MM/dd" パターンでLocalDateをパースします。
            // 無効な日付（例: 2023/02/30, 2023/13/01）の場合、
            // java.time.format.DateTimeParseException が発生します。
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate.parse(dateString, formatter);
            return true; // パースに成功すれば存在する日付です
        } catch (DateTimeParseException e) {
            return false; // パースに失敗すれば存在しない日付です
        }
    }

    /**
     * 「YYYY/MM/DD」形式の日付文字列を「DD-MM-YYYY」形式に変換します。
     * このメソッドは、入力文字列が有効かつ存在する日付であることが確認された後に呼び出されます。
     *
     * @param dateString 変換する「YYYY/MM/DD」形式の日付文字列
     * @return 変換された「DD-MM-YYYY」形式の日付文字列
     */
    private static String convertDateFormat(String dateString) {
        // 入力形式のフォーマッターを定義
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // 出力形式のフォーマッターを定義
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // 入力文字列をLocalDateオブジェクトにパース
        LocalDate date = LocalDate.parse(dateString, inputFormatter);

        // LocalDateオブジェクトを出力形式でフォーマットして文字列として返す
        return date.format(outputFormatter);
    }
}