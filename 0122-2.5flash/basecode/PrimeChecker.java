import java.util.Scanner;

/**
 * 素数判定を行うプログラムです。
 * ユーザーに正の整数を入力させ、その数が素数であるかどうかを判定して表示します。
 */
public class PrimeChecker {

    /**
     * メインメソッド。
     * ユーザーからの入力を受け取り、素数判定処理を実行します。
     *
     * @param args コマンドライン引数（今回は使用しません）
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ユーザーから有効な整数（2以上）を取得する
        int number = getValidPositiveIntegerInput(scanner);

        // 取得した数が素数であるかを判定し、結果を表示
        if (isPrime(number)) {
            System.out.println(number + " は素数です。");
        } else {
            System.out.println(number + " は素数ではありません。");
        }

        // スキャナを閉じる
        scanner.close();
    }

    /**
     * ユーザーから有効な正の整数（2以上）を繰り返し取得します。
     * 入力が数値でない場合や1以下の場合は、エラーメッセージを表示し再入力を促します。
     *
     * @param scanner Scannerオブジェクト
     * @return ユーザーが入力した2以上の整数
     */
    private static int getValidPositiveIntegerInput(Scanner scanner) {
        int number;
        while (true) { // 有効な入力があるまでループを続ける
            System.out.print("正の整数を入力してください: ");
            String input = scanner.nextLine(); // ユーザーからの入力行全体を読み込む

            try {
                number = Integer.parseInt(input); // 文字列を整数に変換

                // 1以下の数が入力された場合のエラー処理
                if (number < 2) {
                    System.out.println("素数判定は2以上の整数に対して行います。");
                } else {
                    return number; // 2以上の有効な整数が入力されたので、その値を返してループを抜ける
                }
            } catch (NumberFormatException e) {
                // 数値でない入力があった場合のエラー処理
                System.out.println("無効な入力です。正の整数を入力してください。");
            }
        }
    }

    /**
     * 指定された数が素数であるかどうかを判定します。
     * このメソッドは2以上の整数に対して呼び出されることを想定しています。
     *
     * @param number 判定する整数 (2以上の値であることが前提)
     * @return 素数であればtrue、そうでなければfalse
     */
    private static boolean isPrime(int number) {
        // 2は最小の素数
        if (number == 2) {
            return true;
        }
        // 2以外の偶数は素数ではない
        if (number % 2 == 0) {
            return false;
        }
        // 3からnumberの平方根までの奇数で割り切れるかを確認
        // i * i <= number は i <= Math.sqrt(number) と同じ意味で、
        // 浮動小数点演算を避けるために乗算を使っています。
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false; // 割り切れる数が見つかったら素数ではない
            }
        }
        // どの数でも割り切れなかった場合は素数
        return true;
    }
}