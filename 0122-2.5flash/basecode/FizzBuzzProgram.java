import java.util.InputMismatchException;
import java.util.Scanner;

public class FizzBuzzProgram {

    // Scannerオブジェクトをクラス全体で共有し、プログラム終了時に一度だけ閉じるようにします。
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("FizzBuzzプログラムを開始します。");

        // ユーザーから正の整数Nの入力を受け付けます。
        // 無効な入力があった場合は再入力を促します。
        int n = getPositiveIntegerInput();

        System.out.println("\n1から " + n + " までのFizzBuzz結果:");
        // 1からNまでのFizzBuzzロジックを実行し、結果を表示します。
        runFizzBuzz(n);

        // プログラム終了時にScannerを閉じます。
        scanner.close();
        System.out.println("FizzBuzzプログラムを終了します。");
    }

    /**
     * ユーザーから正の整数Nを入力させるメソッド。
     * <p>
     * 以下のエラー処理を行います：
     * <ul>
     *     <li>数値以外の文字が入力された場合、エラーメッセージを表示し、再入力を促す。</li>
     *     <li>1未満の数値が入力された場合、エラーメッセージを表示し、再入力を促す。</li>
     * </ul>
     *
     * @return ユーザーが入力した正の整数N
     */
    private static int getPositiveIntegerInput() {
        int n;
        while (true) { // 有効な入力があるまでループを続けます
            System.out.print("正の整数Nを入力してください: ");
            try {
                n = scanner.nextInt(); // ユーザーからの整数入力を試みます

                if (n < 1) {
                    // Nが1未満の場合のエラー処理
                    System.err.println("エラー: 1以上の数値を入力してください。");
                } else {
                    // 有効な入力であれば、その値を返してループを抜けます
                    return n;
                }
            } catch (InputMismatchException e) {
                // 数値以外の文字が入力された場合のエラー処理
                System.err.println("エラー: 無効な入力です。数値を入力してください。");
                scanner.next(); // 不正な入力を読み捨て、次の入力に備えます
            }
        }
    }

    /**
     * 1から指定されたNまでのFizzBuzzロジックを実行し、結果を標準出力に表示するメソッド。
     *
     * @param n FizzBuzzプログラムを実行する最大値
     */
    private static void runFizzBuzz(int n) {
        for (int i = 1; i <= n; i++) {
            // 各数値に対してFizzBuzzの文字列を生成し、表示します
            System.out.println(getFizzBuzzOutput(i));
        }
    }

    /**
     * 指定された数値に対してFizzBuzzルールに基づいて文字列を生成するメソッド。
     * <p>
     * ルール:
     * <ul>
     *     <li>3と5の両方の倍数 (15の倍数) の場合は「FizzBuzz」</li>
     *     <li>3の倍数の場合は「Fizz」</li>
     *     <li>5の倍数の場合は「Buzz」</li>
     *     <li>それ以外の場合はその数値を文字列として</li>
     * </ul>
     *
     * @param number 評価する数値
     * @return FizzBuzzルールに沿った文字列（"FizzBuzz", "Fizz", "Buzz", または数値そのもの）
     */
    private static String getFizzBuzzOutput(int number) {
        if (number % 15 == 0) { // 3と5の両方の倍数 (15の倍数) を最初にチェック
            return "FizzBuzz";
        } else if (number % 3 == 0) { // 3の倍数
            return "Fizz";
        } else if (number % 5 == 0) { // 5の倍数
            return "Buzz";
        } else { // 上記のいずれでもない場合
            return String.valueOf(number); // 数値そのものを文字列に変換して返す
        }
    }
}