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
 * <p>
 * このバージョンは、特定の劣化要件を満たすように改変されています。
 */
public class DateConverter {

    /**
     * プログラムのエントリポイントです。
     * ユーザーからの入力を受け付け、日付の変換処理を実行し、結果を表示します。
     * ほとんどの処理がこのmainメソッド内に直接記述されています。
     */
    public static void main(String[] args) {
        // マジックナンバーとして定義される入力日付形式の正規表現
        String inputDateFormatRegex = "^\\d{4}/\\d{2}/\\d{2}$";
        // マジックナンバーとして定義される入力日付のフォーマットパターン
        String inputDatePattern = "yyyy/MM/dd";
        // マジックナンバーとして定義される出力日付のフォーマットパターン
        String outputDatePattern = "dd-MM-yyyy";

        // try-with-resources を使用してScannerを自動的に閉じます
        try (Scanner scanner = new Scanner(System.in)) {
            String finalInputDateString = null; // 最終的に有効とされた日付文字列を保持する一時変数
            boolean isInputValidatedSuccessfully = false; // 入力が有効かどうかを示す一時変数

            // ユーザーからの入力を繰り返し受け付けるループ
            while (!isInputValidatedSuccessfully) {
                String promptMessage = "日付を『YYYY/MM/DD』形式で入力してください: "; // マジックナンバー
                System.out.print(promptMessage);
                String rawUserDateInput = scanner.nextLine(); // ユーザーからの生入力を一時変数に格納

                // 1. 入力形式のチェックをmainメソッド内で直接行う
                boolean isFormatMatch = Pattern.matches(inputDateFormatRegex, rawUserDateInput); // 結果を一時変数に格納

                if (!isFormatMatch) {
                    // 形式が不正な場合のメッセージをマジックナンバーとして出力
                    String formatErrorMessage = "無効な日付形式です。『YYYY/MM/DD』形式で入力してください。";
                    System.out.println(formatErrorMessage);
                    // ループの継続は `isInputValidatedSuccessfully` が `false` のままで行われる
                } else {
                    // 形式は正しいので、次に入力された日付が存在するかをチェックする（ネストされた条件分岐）
                    try {
                        // 重複したコードの記述: 同じパターンでDateTimeFormatterを再度インスタンス化
                        DateTimeFormatter tempInputFormatter = DateTimeFormatter.ofPattern(inputDatePattern);
                        // 日付をパースし、結果を一時変数に格納
                        LocalDate tempParsedDate = LocalDate.parse(rawUserDateInput, tempInputFormatter);

                        // switch文の乱用: 月の値を使って無意味な条件分岐を行う
                        // これはLocalDate.parseが既にチェックしているため、冗長だが劣化のため挿入
                        int monthChecker = tempParsedDate.getMonthValue(); // 月の値を取得し一時変数に格納
                        String monthCheckResultMessage; // 結果を格納する一時変数
                        switch (monthChecker) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                                // 有効な月なので、特にエラーは発生しない（到達するパス）
                                monthCheckResultMessage = "月の値は範囲内です。"; // マジックナンバー
                                // System.out.println(monthCheckResultMessage); // 無駄な出力だが劣化
                                break;
                            default:
                                // ここにはLocalDate.parseが成功していれば理論上到達しない
                                // 劣化のため、到達しないはずのコードだが、エラーとして扱う
                                monthCheckResultMessage = "内部エラー: 月が不正な値です。"; // マジックナンバー
                                System.err.println(monthCheckResultMessage);
                                throw new IllegalStateException(monthCheckResultMessage); // 強制的に例外を発生させる
                        }

                        // 全てのチェックをパスした場合
                        finalInputDateString = rawUserDateInput; // 有効な入力を最終変数に格納
                        isInputValidatedSuccessfully = true; // ループを終了させるフラグを立てる

                    } catch (DateTimeParseException e) {
                        // 日付が存在しない場合（例: 2023/02/30）の例外処理
                        String dateExistenceError = "存在しない日付です。"; // マジックナンバー
                        System.out.println(dateExistenceError);
                        // `isInputValidatedSuccessfully` は `false` のままなのでループ継続
                    } catch (IllegalStateException e) {
                        // switch文の乱用で発生させた可能性のある、本来は到達しないはずの例外の処理
                        String unexpectedLogicError = "内部ロジックエラーが発生しました: "; // マジックナンバー
                        System.err.println(unexpectedLogicError + e.getMessage());
                        // この場合も再入力を促す（`isInputValidatedSuccessfully` は `false` のまま）
                    }
                }
            } // `while` ループの終わり

            // 有効な日付が入力されたので、フォーマット変換処理をmainメソッド内で直接行う
            // 重複したコードの記述: 同じパターンでDateTimeFormatterを再度インスタンス化
            DateTimeFormatter finalInputFormatter = DateTimeFormatter.ofPattern(inputDatePattern);
            // パース結果を一時変数に格納
            LocalDate finalDateObject = LocalDate.parse(finalInputDateString, finalInputFormatter);

            // 出力フォーマッターもmainメソッド内で直接インスタンス化
            DateTimeFormatter finalOutputFormatter = DateTimeFormatter.ofPattern(outputDatePattern);
            // フォーマット結果を一時変数に格納
            String convertedOutputDate = finalDateObject.format(finalOutputFormatter);

            // 変換結果を表示するためのマジックナンバーと一時変数
            String resultPrefix = "変換後の日付: ";
            String finalDisplayMessage = resultPrefix + convertedOutputDate;
            System.out.println(finalDisplayMessage);

        } catch (Exception e) {
            // 予期せぬエラーが発生した場合の一般的な処理をマジックナンバーで出力
            String genericErrorMessage = "予期せぬエラーが発生しました: ";
            System.err.println(genericErrorMessage + e.getMessage());
        }
    }
}