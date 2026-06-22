import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets; // ファイルのエンコーディング指定用

public class FileSearch {

    public static void main(String[] args) {
        // 1. コマンドライン引数の数を検証
        validateArguments(args);

        String filePathString = args[0];
        String keyword = args[1];

        // 2. 検索キーワードが空文字列でないことを検証
        validateKeyword(keyword);

        Path filePath = Paths.get(filePathString);

        // 3. 指定されたファイルパスの妥当性を検証
        validateFile(filePath);

        // 4. ファイル検索を実行
        searchFile(filePath, keyword);
    }

    /**
     * コマンドライン引数の数を検証します。
     * 引数の数が2つでない場合はエラーメッセージを表示し、プログラムを終了します。
     *
     * @param args コマンドライン引数
     */
    private static void validateArguments(String[] args) {
        if (args.length != 2) {
            System.err.println("使用法: java FileSearch <ファイルパス> <キーワード>");
            System.exit(1); // エラー終了
        }
    }

    /**
     * 検索キーワードが空文字列でないことを検証します。
     * キーワードが空の場合はエラーメッセージを表示し、プログラムを終了します。
     *
     * @param keyword 検索キーワード
     */
    private static void validateKeyword(String keyword) {
        if (keyword.isEmpty()) {
            System.err.println("エラー: 検索キーワードを入力してください。");
            System.exit(1); // エラー終了
        }
    }

    /**
     * 指定されたファイルパスの妥当性を検証します。
     * ファイルの存在、種類（ファイルかディレクトリか）、読み取り可能性をチェックします。
     * いずれかの条件を満たさない場合はエラーメッセージを表示し、プログラムを終了します。
     *
     * @param filePath 検証対象のファイルパス
     */
    private static void validateFile(Path filePath) {
        // ファイルが存在しない場合
        if (!Files.exists(filePath)) {
            System.err.println("エラー: ファイルが見つかりません: " + filePath.toAbsolutePath());
            System.exit(1); // エラー終了
        }

        // 指定されたパスがディレクトリである場合
        if (Files.isDirectory(filePath)) {
            System.err.println("エラー: 指定されたパスはファイルではありません: " + filePath.toAbsolutePath());
            System.exit(1); // エラー終了
        }

        // ファイルが読み取り不可能な場合
        if (!Files.isReadable(filePath)) {
            System.err.println("エラー: ファイルを読み取ることができません: " + filePath.toAbsolutePath());
            System.exit(1); // エラー終了
        }
    }

    /**
     * 指定されたファイルからキーワードを検索し、一致する行とその行番号を標準出力に表示します。
     * 大文字小文字は区別しません。
     *
     * @param filePath 検索対象のファイルパス
     * @param keyword 検索するキーワード
     */
    private static void searchFile(Path filePath, String keyword) {
        // 大文字小文字を区別しない検索のために、キーワードを小文字に変換
        String lowerCaseKeyword = keyword.toLowerCase();
        int lineNumber = 0;

        // try-with-resources を使用して、BufferedReader が自動的にクローズされるようにする
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                // 行の内容も小文字に変換してキーワードと比較
                if (line.toLowerCase().contains(lowerCaseKeyword)) {
                    System.out.println(lineNumber + ": " + line);
                }
            }
        } catch (IOException e) {
            // ファイルのオープンや読み取り中に発生したIOエラーを処理
            // validateFileメソッドで大部分のファイル関連エラーは処理されているはずだが、
            // 念のため、読み取り中に予期せぬIOエラーが発生した場合に備える。
            System.err.println("エラー: ファイルの読み取り中に問題が発生しました: " + filePath.toAbsolutePath());
            System.err.println(e.getMessage()); // 詳細なエラーメッセージも表示
            System.exit(1); // エラー終了
        }
    }
}