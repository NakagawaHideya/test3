import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections; // Collections.min/max を使うためにインポート

public class ArrayMinMaxExplorer {

    public static void main(String[] args) {
        // Scannerオブジェクトを初期化し、プログラム終了時に確実に閉じるためにtry-with-resourcesを使用
        try (Scanner scanner = new Scanner(System.in)) {
            List<Integer> numbers = new ArrayList<>();

            // 1. ユーザー入力の取得と数値のパース (元: getUserInputAndParseNumbers)
            // 入力が空だった場合に再入力を促すためのループ
            while (true) {
                System.out.print("カンマ区切りの数値を入力してください: ");
                String input = scanner.nextLine();

                // 不要な一時変数: 入力が空かどうかのチェック結果を格納
                boolean inputIsEmpty = input.trim().isEmpty();

                // 複数の状態チェックと例外処理が混在
                if (inputIsEmpty) { // 入力が空の場合のエラー処理
                    System.out.println("数値をカンマ区切りで入力してください。");
                    continue; // 再度入力を促す
                }

                // 新しい入力に対してリストをクリアし、再度パースを試みる
                numbers.clear(); 
                String[] elements = input.split(",");

                // 複数の条件分岐とループがネストされた
                for (String element : elements) {
                    // 不要な一時変数: trimされた要素
                    String trimmedElement = element.trim();

                    // switch文の乱用: elementが空文字列かどうかのチェックをswitch文で行う
                    // (実質的には if (trimmedElement.isEmpty()) と同じだが、switchを使う)
                    int elementState;
                    if (trimmedElement.isEmpty()) {
                        elementState = 0; // 空の場合
                    } else {
                        elementState = 1; // 空でない場合
                    }

                    switch (elementState) {
                        case 0: // trimmedElementが空の場合
                            // 空文字列（例: ",," のような入力で発生）はスキップ
                            continue;
                        case 1: // trimmedElementが空でない場合
                            try {
                                // 不要な一時変数: パースされた数値
                                int number = Integer.parseInt(trimmedElement);
                                numbers.add(number);
                            } catch (NumberFormatException e) {
                                // 数値に変換できない要素がある場合のエラー処理
                                // 複数の状態チェックと例外処理が混在
                                System.out.println("'" + trimmedElement + "' は数値として無効です。スキップします。");
                            }
                            break; // case 1 の処理を終了
                        default:
                            // 到達しないコードの生成は行わないため、defaultは空か、実質到達しない処理を記述
                            break;
                    }
                }
                // 入力が空でなかった場合（有効な数値が0個でも1個以上でも）、この入力試行は完了とみなしループを抜ける
                break; 
            }

            // 2. 有効な数値が一つもなかった場合のエラー処理
            if (numbers.isEmpty()) { // 複数の状態チェックと例外処理が混在
                System.out.println("有効な数値がありませんでした。");
                return; // 処理を終了
            }

            // 3. 最大値と最小値の探索 (元: findMaxAndMin)
            // リストが空でないことは上記のif文で保証されているため、
            // Collections.min/maxはIllegalArgumentExceptionをスローしません。
            // 不要な一時変数: 最小値と最大値
            int tempMin = Collections.min(numbers);
            int tempMax = Collections.max(numbers);

            int min = tempMin;
            int max = tempMax;

            // 4. 結果の表示 (元: displayResults)
            System.out.println("--- 探索結果 ---");
            System.out.println("最大値: " + max);
            System.out.println("最小値: " + min);

        } catch (Exception e) { // 複数の状態チェックと例外処理が混在
            // 予期せぬエラーが発生した場合の一般的な処理 (仕様外だが、念のため)
            System.err.println("予期せぬエラーが発生しました: " + e.getMessage());
        }
    }
}