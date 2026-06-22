import java.util.Scanner; // ユーザーからの入力を受け取るために使用

/**
 * 指定回数の文字列繰り返し出力を行うアプリケーションです。
 * ユーザーに文字列と繰り返し回数を入力させ、その文字列を指定回数分繰り返して表示します。
 */
public class StringRepeater {

    public static void main(String[] args) {
        // Scannerオブジェクトを作成し、標準入力から入力を読み取る準備をします。
        // try-with-resources文を使用することで、Scannerが確実にクローズされるようにします。
        try (Scanner scanner = new Scanner(System.in)) {
            // 1. ユーザーから文字列の入力を受け取ります。
            String inputText = getStringInput(scanner);

            // 2. ユーザーから繰り返し回数の入力を受け取ります。
            //    このメソッド内で数値でない、または0以下の入力に対するエラー処理と再入力を実施します。
            int repeatCount = getRepeatCount(scanner);

            // 3. 入力された文字列を指定回数分繰り返して表示します。
            printRepeatedString(inputText, repeatCount);

        } catch (Exception e) {
            // 予期せぬエラーが発生した場合の一般的な処理（仕様にないため簡易的に）
            System.err.println("予期せぬエラーが発生しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーから文字列の入力を受け取ります。
     *
     * @param scanner 標準入力からの入力を読み取るためのScannerオブジェクト
     * @return ユーザーが入力した文字列
     */
    private static String getStringInput(Scanner scanner) {
        System.out.print("文字列を入力してください: ");
        return scanner.nextLine(); // ユーザーの入力を1行読み込み、そのまま返します。
    }

    /**
     * ユーザーから繰り返し回数の入力を受け取ります。
     * 入力が数値でない場合や、1未満の数値の場合にはエラーメッセージを表示し、
     * 再入力を促します。有効な入力が得られるまでループします。
     *
     * @param scanner 標準入力からの入力を読み取るためのScannerオブジェクト
     * @return 検証済みの繰り返し回数 (1以上の整数)
     */
    private static int getRepeatCount(Scanner scanner) {
        int count = 0; // 繰り返し回数を格納する変数

        // 有効な入力が得られるまでループを続けます。
        while (true) {
            System.out.print("繰り返し回数を入力してください: ");
            String inputLine = scanner.nextLine(); // 入力を文字列として受け取ります。

            try {
                // 入力された文字列を整数に変換しようと試みます。
                count = Integer.parseInt(inputLine);

                // 繰り返し回数が1未満の場合のエラーチェック
                if (count <= 0) {
                    System.out.println("繰り返し回数は1以上の数値を入力してください。");
                } else {
                    // 1以上の有効な数値が入力された場合、ループを抜けます。
                    break;
                }
            } catch (NumberFormatException e) {
                // 文字列が整数に変換できなかった場合（例: "abc"など）
                System.out.println("繰り返し回数は数値を入力してください。");
            }
        }
        return count; // 有効な繰り返し回数を返します。
    }

    /**
     * 指定された文字列を指定された回数分繰り返して表示します。
     * Java 11以降のString.repeat()メソッドを使用しています。
     *
     * @param text  繰り返して表示する文字列
     * @param count 文字列を繰り返す回数 (1以上の整数)
     */
    private static void printRepeatedString(String text, int count) {
        // Java 11以降で利用可能なString.repeat()メソッドを使用すると、
        // 非常に簡潔に文字列を繰り返すことができます。
        System.out.println(text.repeat(count));

        /*
         * もしJava 8など古いバージョンを使用している場合は、以下のようにStringBuilderを使って
         * 文字列を構築し、最後に表示することができます。
         *
         * StringBuilder sb = new StringBuilder();
         * for (int i = 0; i < count; i++) {
         *     sb.append(text);
         * }
         * System.out.println(sb.toString());
         */
    }
}