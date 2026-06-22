import java.util.InputMismatchException; // 明示的には使用しないが、hasNextDouble() の内部で間接的に関連
import java.util.Scanner;

public class SquareRootCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double inputNumber;
        boolean isValidInput = false;

        // 有効な数値（負でない数値）が入力されるまでループを続けます
        while (!isValidInput) {
            // ユーザーから数値を安全に（数値形式で）入力させます
            inputNumber = getUserNumber(scanner);

            // 入力された数値が負でないかを検証します
            if (validateNumber(inputNumber)) {
                isValidInput = true; // 有効な入力なのでループを終了します
            }
            // validateNumber が false を返した場合、エラーメッセージは validateNumber メソッド内で
            // 表示されているため、ここでは特別な処理は不要で、ループが継続されます。
        }

        // 平方根を計算します
        double squareRoot = calculateSquareRoot(inputNumber);

        // 結果を表示します
        displayResult(inputNumber, squareRoot);

        scanner.close(); // Scanner オブジェクトをクローズし、リソースを解放します
    }

    /**
     * ユーザーから数値を入力させ、有効な double 型が入力されるまで再入力を促します。
     * 数値以外の入力があった場合はエラーメッセージを表示します。
     *
     * @param scanner Scanner オブジェクト
     * @return 入力された double 型の数値
     */
    public static double getUserNumber(Scanner scanner) {
        while (true) {
            System.out.print("数値を入力してください: ");
            if (scanner.hasNextDouble()) {
                double number = scanner.nextDouble();
                return number;
            } else {
                // 数値以外の入力があった場合
                System.out.println("無効な入力です。数値を入力してください。");
                scanner.next(); // 無効な入力を消費して、次の入力を受け付ける準備をします
            }
        }
    }

    /**
     * 入力された数値が負の数でないかを検証します。
     * 負の数の場合はエラーメッセージを表示します。
     *
     * @param number 検証する数値
     * @return 負の数でなければ true、負の数であれば false
     */
    public static boolean validateNumber(double number) {
        if (number < 0) {
            // 負の数が入力された場合
            System.out.println("負の数の平方根は計算できません。");
            return false;
        }
        return true;
    }

    /**
     * 指定された数値の平方根を計算します。
     *
     * @param number 平方根を計算する数値
     * @return 計算された平方根
     */
    public static double calculateSquareRoot(double number) {
        return Math.sqrt(number);
    }

    /**
     * 元の数値とその平方根の結果をコンソールに表示します。
     *
     * @param originalNumber 元の数値
     * @param squareRoot 計算された平方根
     */
    public static void displayResult(double originalNumber, double squareRoot) {
        System.out.println(originalNumber + " の平方根は " + squareRoot + " です。");
    }
}