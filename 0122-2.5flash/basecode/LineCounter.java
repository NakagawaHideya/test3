import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * コマンドライン引数としてファイルパスを受け取り、
 * そのテキストファイルの行数を数えて標準出力に表示するプログラムです。
 */
public class LineCounter {

    public static void main(String[] args) {
        // 1. コマンドライン引数のチェック
        if (args.length == 0) {
            System.out.println("使用法: java LineCounter <ファイルパス>");
            System.exit(1); // エラーコード1で終了
        }

        String filePath = args[0];

        // 2. ファイルの存在、種類（ファイルかディレクトリか）、および読み取り権限の検証
        // エラーが発生した場合は、このメソッド内でエラーメッセージを表示し、プログラムが終了します。
        validateFile(filePath);

        // 3. 検証が成功した場合、ファイルの行数をカウント
        try {
            long lineCount = countLines(filePath);
            System.out.println(lineCount); // 行数を標準出力に表示
        } catch (IOException e) {
            // validateFileで捕捉しきれない、ファイルの読み取り中に発生したIOエラー
            // (例: 読み取り中にファイルが削除された、ディスクI/Oエラーなど)
            // これも「ファイルを読み取ることができません」というエラーとして扱います。
            System.out.println("エラー: ファイルを読み取ることができません: " + filePath);
            System.exit(1); // エラーコード1で終了
        }
    }

    /**
     * 指定されたファイルパスのファイルを検証します。
     * 以下のいずれかの条件に合致する場合、エラーメッセージを表示してプログラムを終了します。
     * - ファイルが存在しない
     * - 指定されたパスがファイルではなくディレクトリである
     * - ファイルが読み取り不可能である (パーミッションなど)
     *
     * @param filePath 検証するファイルのパス
     */
    private static void validateFile(String filePath) {
        File file = new File(filePath);

        // ファイルが存在しない場合のエラー処理
        if (!file.exists()) {
            System.out.println("エラー: ファイルが見つかりません: " + filePath);
            System.exit(1); // エラーコード1で終了
        }

        // 指定されたパスがディレクトリである場合のエラー処理
        if (file.isDirectory()) {
            System.out.println("エラー: 指定されたパスはファイルではありません: " + filePath);
            System.exit(1); // エラーコード1で終了
        }

        // ファイルが読み取り不可能である場合のエラー処理
        if (!file.canRead()) {
            System.out.println("エラー: ファイルを読み取ることができません: " + filePath);
            System.exit(1); // エラーコード1で終了
        }
    }

    /**
     * 指定されたファイルパスのテキストファイルの行数をカウントします。
     * ファイルを1行ずつ読み込み、読み込んだ行の数を合計します。
     *
     * @param filePath 行数をカウントするファイルのパス
     * @return ファイルの行数
     * @throws IOException ファイルの読み取り中にエラーが発生した場合
     */
    private static long countLines(String filePath) throws IOException {
        long lines = 0;
        // try-with-resources を使用して、BufferedReaderが確実に閉じられるようにする
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // ファイルが終端に達するまで1行ずつ読み込み、行数をカウント
            while (reader.readLine() != null) {
                lines++;
            }
        }
        return lines;
    }
}