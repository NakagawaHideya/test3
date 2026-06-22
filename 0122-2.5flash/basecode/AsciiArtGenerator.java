/**
 * アスキーアートジェネレーター (文字列反転)
 * コマンドライン引数として文字列を受け取り、その文字列を反転させて標準出力に表示します。
 */
public class AsciiArtGenerator {

    /**
     * 指定された文字列を反転させます。
     *
     * @param input 反転させる元の文字列
     * @return 反転された文字列
     */
    public static String reverseString(String input) {
        // StringBuilderクラスのreverse()メソッドを利用すると効率的に文字列を反転できます。
        return new StringBuilder(input).reverse().toString();
    }

    /**
     * プログラムのエントリポイントです。
     * コマンドライン引数を処理し、文字列の反転とエラーチェックを行います。
     *
     * @param args コマンドライン引数。反転する文字列が格納されます。
     */
    public static void main(String[] args) {
        // 1. 引数が指定されていない場合のエラー処理
        if (args.length == 0) {
            System.err.println("使用法: java AsciiArtGenerator <文字列>");
            System.exit(1); // エラー終了コード
        }

        // コマンドライン引数から文字列を取得
        String inputString = args[0];

        // 2. 指定された文字列が空文字列である場合のエラー処理
        if (inputString.isEmpty()) {
            System.err.println("エラー: 空の文字列は処理できません。何らかの文字を入力してください。");
            System.exit(1); // エラー終了コード
        }

        // 文字列を反転させるメソッドを呼び出し
        String reversedString = reverseString(inputString);

        // 反転された文字列を標準出力に表示
        System.out.println(reversedString);
    }
}