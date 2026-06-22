/**
 * コマンドライン引数として渡された複数の数値を合計して表示するプログラムです。
 * 無効な引数や引数がない場合のエラー処理も行います。
 */
public class CommandLineSumCalculator {

    /**
     * 計算結果と有効な数値があったかどうかを示すための内部クラス。
     * エラー処理の要件とメソッド分割の指示を満たすために使用します。
     */
    private static class SumResult {
        private final int sum;
        private final boolean hasValidNumber; // 有効な数値が一つでもあったか

        /**
         * SumResultのコンストラクタ。
         *
         * @param sum 計算された合計値
         * @param hasValidNumber 有効な数値が一つでもあった場合はtrue、そうでなければfalse
         */
        public SumResult(int sum, boolean hasValidNumber) {
            this.sum = sum;
            this.hasValidNumber = hasValidNumber;
        }

        /**
         * 計算された合計値を返します。
         *
         * @return 合計値
         */
        public int getSum() {
            return sum;
        }

        /**
         * 有効な数値が一つでもあったかどうかを返します。
         *
         * @return 有効な数値があった場合はtrue、そうでなければfalse
         */
        public boolean hasValidNumber() {
            return hasValidNumber;
        }
    }

    /**
     * プログラムのエントリーポイント。
     * コマンドライン引数のチェック、合計計算メソッドの呼び出し、結果表示を行います。
     *
     * @param args コマンドライン引数の文字列配列
     */
    public static void main(String[] args) {
        // エラー処理1: 引数が渡されない場合
        if (args.length == 0) {
            System.out.println("引数が指定されていません。数値を一つ以上指定してください。");
            return; // プログラムを終了
        }

        // 数値の合計を計算し、途中のエラーメッセージを処理
        SumResult result = calculateSum(args);

        // 結果の表示
        // 有効な数値が一つでもあった場合にのみ合計を表示します。
        // そうでない場合（result.hasValidNumber() が false の場合）は、
        // calculateSum メソッド内で既に「有効な数値が一つもありませんでした。」と
        // 表示されているため、ここでは何もしません。
        if (result.hasValidNumber()) {
            System.out.println("合計: " + result.getSum());
        }
    }

    /**
     * コマンドライン引数から数値を解析し、合計を計算します。
     * 無効な引数についてはエラーメッセージを表示し、スキップします。
     * 全ての引数が無効な場合は、その旨を伝えるメッセージを表示します。
     *
     * @param args コマンドライン引数の文字列配列
     * @return 計算結果と有効な数値があったかどうかを示すSumResultオブジェクト
     */
    private static SumResult calculateSum(String[] args) {
        int sum = 0;
        boolean hasValidNumber = false; // 有効な数値が一つでもあったかを追跡するフラグ

        for (String arg : args) {
            try {
                int num = Integer.parseInt(arg); // 文字列を整数に変換
                sum += num;
                hasValidNumber = true; // 有効な数値が見つかった
            } catch (NumberFormatException e) {
                // エラー処理2: 数値に変換できない引数がある場合
                System.out.println("'" + arg + "' は数値として無効です。スキップします。");
            }
        }

        // エラー処理2の特殊ケース: 全ての引数が無効な場合
        if (!hasValidNumber) {
            System.out.println("有効な数値が一つもありませんでした。");
        }

        return new SumResult(sum, hasValidNumber);
    }
}