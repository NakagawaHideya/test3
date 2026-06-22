import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections; // Collections.min/max を使うためにインポート

public class ArrayMinMaxExplorer {

    public static void main(String[] args) {
        // Scannerオブジェクトを初期化し、プログラム終了時に確実に閉じるためにtry-with-resourcesを使用
        try (Scanner scanner = new Scanner(System.in)) {
            // 1. ユーザー入力の取得と数値のパース
            List<Integer> numbers = getUserInputAndParseNumbers(scanner);

            // 2. 有効な数値が一つもなかった場合のエラー処理
            // getUserInputAndParseNumbersメソッドは、入力が空でない限り一度の入力試行で終了します。
            // その結果、有効な数値が一つもなければ空のリストを返します。
            if (numbers.isEmpty()) {
                System.out.println("有効な数値がありませんでした。");
                return; // 処理を終了
            }

            // 3. 最大値と最小値の探索
            // リストが空でないことは上記のif文で保証されているため、
            // findMaxAndMinメソッドはIllegalArgumentExceptionをスローしません。
            int[] minMax = findMaxAndMin(numbers);
            int min = minMax[0];
            int max = minMax[1];

            // 4. 結果の表示
            displayResults(min, max);

        } catch (Exception e) {
            // 予期せぬエラーが発生した場合の一般的な処理 (仕様外だが、念のため)
            System.err.println("予期せぬエラーが発生しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーからカンマ区切りの数値を入力させ、パースして数値リストを返します。
     * 入力が空の場合や無効な数値がある場合のエラー処理を含みます。
     *
     * @param scanner ユーザー入力を読み取るためのScannerオブジェクト
     * @return パースされた数値のリスト。有効な数値が一つもなかった場合は空のリストを返します。
     */
    private static List<Integer> getUserInputAndParseNumbers(Scanner scanner) {
        List<Integer> parsedNumbers = new ArrayList<>();
        // 入力が空だった場合に再入力を促すためのループ
        while (true) {
            System.out.print("カンマ区切りの数値を入力してください: ");
            String input = scanner.nextLine();

            // 入力が空の場合のエラー処理
            if (input.trim().isEmpty()) {
                System.out.println("数値をカンマ区切りで入力してください。");
                continue; // 再度入力を促す
            }

            // 新しい入力に対してリストをクリアし、再度パースを試みる
            parsedNumbers.clear(); 
            String[] elements = input.split(",");

            for (String element : elements) {
                String trimmedElement = element.trim();
                // 空文字列（例: ",," のような入力で発生）はスキップ
                if (trimmedElement.isEmpty()) { 
                    continue;
                }
                try {
                    int number = Integer.parseInt(trimmedElement);
                    parsedNumbers.add(number);
                } catch (NumberFormatException e) {
                    // 数値に変換できない要素がある場合のエラー処理
                    System.out.println("'" + trimmedElement + "' は数値として無効です。スキップします。");
                }
            }
            // 入力が空でなかった場合（有効な数値が0個でも1個以上でも）、この入力試行は完了とみなしループを抜ける
            break; 
        }
        return parsedNumbers;
    }

    /**
     * 指定された数値リストの中から最大値と最小値を見つけます。
     *
     * @param numbers 探索対象の数値リスト（空でないことが前提）
     * @return [最小値, 最大値] の順で格納されたint配列
     */
    private static int[] findMaxAndMin(List<Integer> numbers) {
        // Collectionsクラスのmin()とmax()メソッドを使用して、リストから最小値と最大値を効率的に取得
        // mainメソッドでリストが空でないことが保証されているため、ここではNullPointerExceptionや
        // NoSuchElementExceptionは発生しません。
        int min = Collections.min(numbers);
        int max = Collections.max(numbers);

        return new int[]{min, max};
    }

    /**
     * 最大値と最小値をコンソールに表示します。
     *
     * @param min 最小値
     * @param max 最大値
     */
    private static void displayResults(int min, int max) {
        System.out.println("--- 探索結果 ---");
        System.out.println("最大値: " + max);
        System.out.println("最小値: " + min);
    }
}