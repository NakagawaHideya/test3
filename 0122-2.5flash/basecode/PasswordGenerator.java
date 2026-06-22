import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    // パスワードに使用する文字セットを定義
    // 英小文字、英大文字、数字、記号を含めます
    private static final String CHARACTERS = 
        "abcdefghijklmnopqrstuvwxyz" +
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "0123456789" +
        "!@#$%^&*()-_+=[]{}|;:,.<>/?";

    // パスワードの最大の長さ
    private static final int MAX_LENGTH = 20;

    public static void main(String[] args) {
        // Scannerをtry-with-resourcesで宣言し、確実にクローズされるようにする
        try (Scanner scanner = new Scanner(System.in)) {
            // ユーザーからパスワードの長さを取得する
            int passwordLength = getPasswordLength(scanner);

            // 取得した長さに基づいてパスワードを生成する
            String generatedPassword = generatePassword(passwordLength);

            // 生成されたパスワードを表示する
            System.out.println("生成されたパスワード: " + generatedPassword);

        } catch (Exception e) {
            // 想定外のエラーが発生した場合の一般的な処理
            System.err.println("プログラム実行中に予期せぬエラーが発生しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーからパスワードの長さを入力させ、有効な長さが入力されるまで再入力を促します。
     *
     * @param scanner ユーザー入力を受け取るためのScannerオブジェクト
     * @return 有効なパスワードの長さ（1以上、MAX_LENGTH以下）
     */
    private static int getPasswordLength(Scanner scanner) {
        int length = 0;
        while (true) {
            System.out.print("生成するパスワードの長さを入力してください (1〜" + MAX_LENGTH + "文字): ");

            if (scanner.hasNextInt()) {
                length = scanner.nextInt();
                scanner.nextLine(); // 数値の後の改行文字を消費

                if (length <= 0) {
                    System.out.println("パスワードの長さは1以上の数値を入力してください。");
                } else if (length > MAX_LENGTH) {
                    System.out.println("パスワードの長さは" + MAX_LENGTH + "文字以下にしてください。");
                } else {
                    // 有効な長さが入力された場合
                    break;
                }
            } else {
                // 数値以外の入力があった場合
                System.out.println("パスワードの長さは数値を入力してください。");
                scanner.nextLine(); // 無効な入力を消費
            }
        }
        return length;
    }

    /**
     * 指定された長さのランダムなパスワードを生成します。
     *
     * @param length 生成するパスワードの長さ
     * @return 生成されたパスワードの文字列
     */
    private static String generatePassword(int length) {
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // CHARACTERSからランダムなインデックスを選び、その位置の文字をパスワードに追加
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }

        return password.toString();
    }
}