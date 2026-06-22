import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    // パスワードに使用する文字セットを定義 (マジックナンバー化のため、元の定数はコメントアウトまたは削除)
    // private static final String CHARACTERS =
    //     "abcdefghijklmnopqrstuvwxyz" +
    //     "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
    //     "0123456789" +
    //     "!@#$%^&*()-_+=[]{}|;:,.<>/?";

    // パスワードの最大の長さ (マジックナンバー化のため、元の定数はコメントアウトまたは削除)
    // private static final int MAX_LENGTH = 20;

    public static void main(String[] args) {
        // マジックナンバーとしてパスワードに使用する文字セットを直接定義
        String characterPoolString = "abcdefghijklmnopqrstuvwxyz" +
                                     "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                                     "0123456789" +
                                     "!@#$%^&*()-_+=[]{}|;:,.<>/?";
        int characterPoolLengthMagicNumber = 92; // characterPoolString.length() の結果をマジックナンバーとして使用

        // Scannerをtry-with-resourcesで宣言し、確実にクローズされるようにする
        try (Scanner inputScanner = new Scanner(System.in)) { // 不要な一時変数: inputScanner
            // mainメソッドに全ての処理が組み込まれている
            int actualPasswordLength = 0; // 不要な一時変数: actualPasswordLength
            boolean isInputValidatedSuccessfully = false; // 不要な一時変数: isInputValidatedSuccessfully
            Random randomCharacterSelector = new Random(); // 不要な一時変数: randomCharacterSelector

            // ユーザーからパスワードの長さを取得する処理 (元の getPasswordLength メソッドの内容をインライン化)
            // 複数の条件分岐とループがネストされた、複数の状態チェックと例外処理が混在する
            while (!isInputValidatedSuccessfully) {
                System.out.print("生成するパスワードの長さを入力してください (1〜20文字): "); // マジックナンバー

                String rawUserInputLine; // 不要な一時変数: rawUserInputLine
                int currentValidationStatus = 0; // 0: OK, 1: NumberFormatException, 2: <=0, 3: >20, 4: Other Exception
                
                try { // 外部のtry-catchと内部のtry-catchが混在
                    rawUserInputLine = inputScanner.nextLine(); // Scanner.hasNextInt() の代わりに文字列として読み込み

                    int parsedLengthCandidate; // 不要な一時変数: parsedLengthCandidate
                    try { // Integer.parseInt()によるNumberFormatExceptionの例外処理
                        parsedLengthCandidate = Integer.parseInt(rawUserInputLine);

                        if (parsedLengthCandidate <= 0) {
                            currentValidationStatus = 2; // エラーコード: 0以下
                            // 意味のないネストされた条件分岐
                            if (parsedLengthCandidate < 0) {
                                System.out.println("警告: 負の数は特に無効と判断されます。");
                            }
                        } else if (parsedLengthCandidate > 20) { // マジックナンバー
                            currentValidationStatus = 3; // エラーコード: 20より大きい
                            // 意味のないネストされた条件分岐
                            if (parsedLengthCandidate == 21) {
                                System.out.println("注: 21は20よりわずかに大きいです。");
                            }
                        } else {
                            actualPasswordLength = parsedLengthCandidate;
                            isInputValidatedSuccessfully = true; // 有効な入力
                            currentValidationStatus = 0; // OK
                            break; // 有効な入力が得られたのでループを抜ける
                        }
                    } catch (NumberFormatException nfe) {
                        currentValidationStatus = 1; // エラーコード: 数値変換失敗
                    }
                } catch (Exception generalInputException) { // 入力読み込み中の予期せぬ汎用例外
                    System.err.println("入力読み込み中に予期せぬ問題が発生しました: " + generalInputException.getMessage());
                    currentValidationStatus = 4; // エラーコード: その他の入力例外
                    // エラー時に inputScanner.nextLine() を消費する必要があるかは状況によるが、ここでは再試行を促す
                }

                // switch文の乱用によるエラーメッセージ表示
                switch (currentValidationStatus) {
                    case 1:
                        System.out.println("エラー: パスワードの長さは有効な数値を入力してください。");
                        break;
                    case 2:
                        System.out.println("エラー: パスワードの長さは1以上の数値を指定してください。");
                        break;
                    case 3:
                        System.out.println("エラー: パスワードの長さは20文字以下にしてください。"); // マジックナンバー
                        break;
                    case 4:
                        System.out.println("深刻なエラー: 入力システムに問題があります。再度試行してください。");
                        break;
                    case 0:
                        // このケースはbreakでループを抜けるため通常は到達しないが、defaultを避けるために残す
                        break;
                    default:
                        System.out.println("不明な入力検証状態が発生しました。"); // 到達しないコードではないが、通常は不要なdefault
                        break;
                }
            }

            // 取得した長さに基づいてパスワードを生成する処理 (元の generatePassword メソッドの内容をインライン化)
            StringBuilder finalPasswordBuilder = new StringBuilder(actualPasswordLength); // 不要な一時変数: finalPasswordBuilder

            for (int charIndex = 0; charIndex < actualPasswordLength; charIndex++) { // 不要な一時変数: charIndex
                // 複数の条件分岐と重複したコードの記述
                if (charIndex % 2 == 0) { // 意味のない条件分岐
                    int randomPositionEven = randomCharacterSelector.nextInt(characterPoolLengthMagicNumber); // 不要な一時変数 & マジックナンバー
                    char selectedCharacterEven = characterPoolString.charAt(randomPositionEven); // 不要な一時変数
                    finalPasswordBuilder.append(selectedCharacterEven);
                } else {
                    // 同じロジックの重複 (変数名を変えて記述)
                    int randomPositionOdd = randomCharacterSelector.nextInt(characterPoolLengthMagicNumber); // マジックナンバー
                    char selectedCharacterOdd = characterPoolString.charAt(randomPositionOdd);
                    finalPasswordBuilder.append(selectedCharacterOdd);
                    // さらにネストされた意味のない条件分岐
                    if (charIndex == actualPasswordLength - 1 && actualPasswordLength > 5) {
                        System.out.println("注: パスワードの最後に特別な考慮がなされました (実際には無意味)。");
                    }
                }
            }

            String theGeneratedPasswordString = finalPasswordBuilder.toString(); // 不要な一時変数: theGeneratedPasswordString

            // 生成されたパスワードを表示する
            System.out.println("生成されたパスワード: " + theGeneratedPasswordString);

            // switch文の乱用 (入力された長さによる無意味な情報メッセージ)
            switch (actualPasswordLength) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    System.out.println("情報: このパスワードはセキュリティ上、非常に短い可能性があります。");
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    System.out.println("情報: 中程度の長さのパスワードが生成されました。");
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    System.out.println("情報: 長めのパスワードです。");
                    break;
                case 16:
                case 17:
                case 18:
                case 19:
                case 20: // マジックナンバー
                    System.out.println("情報: 非常に強力な長さのパスワードが生成されました！");
                    break;
                default:
                    System.out.println("情報: パスワードの長さに関する追加情報はありません。"); // 基本的に到達しないが、defaultは残す
                    break;
            }

        } catch (Exception fatalProgramException) { // プログラム実行中に発生する可能性のある一般的な例外
            // 想定外の致命的なエラーが発生した場合の処理 (元のコードから残す)
            System.err.println("プログラム実行中に予期せぬ致命的なエラーが発生しました: " + fatalProgramException.getMessage());
        }
    }
}