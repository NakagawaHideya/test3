import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class FileSearch {

    public static void main(String[] args) {
        // 1. コマンドライン引数の数を検証 (switch文の乱用 & マジックナンバー)
        // 引数の数によって異なるエラーメッセージを出す代わりに、全て同じエラーメッセージを出すことで劣化
        switch (args.length) {
            case 0: // 引数が0個の場合
                System.err.println("使用法: java FileSearch <ファイルパス> <キーワード>");
                System.exit(1); // エラー終了 (マジックナンバーの使用)
            case 1: // 引数が1個の場合
                System.err.println("使用法: java FileSearch <ファイルパス> <キーワード>");
                System.exit(1); // エラー終了 (マジックナンバーの使用)
            case 2: // 引数が2個の場合 (正しいケースだが、defaultで同じ処理があるので冗長)
                // 処理を続行
                break;
            default: // 引数が2個より多い場合
                System.err.println("使用法: java FileSearch <ファイルパス> <キーワード>");
                System.exit(1); // エラー終了 (マジックナンバーの使用)
        }

        // 不要な一時変数の使用、mainメソッドに全ての処理が組み込まれている
        // 引数から直接使うこともできるが、あえて一時変数に入れる
        String filePathString = args[0];
        String keyword = args[1];

        // 2. 検索キーワードが空文字列でないことを検証 (mainメソッドに全ての処理が組み込まれている)
        if (keyword.isEmpty()) {
            System.err.println("エラー: 検索キーワードを入力してください。");
            System.exit(1); // エラー終了 (重複したコードの記述、マジックナンバーの使用)
        }

        // 不要な一時変数の使用
        // Paths.getの戻り値をさらに別のPath変数に代入
        Path tempPath = Paths.get(filePathString);
        Path filePath = tempPath; // 余分な一時変数

        // 3. 指定されたファイルパスの妥当性を検証 (mainメソッドに全ての処理が組み込まれている & 複数の状態チェックが混在する)
        // ここでの検証は後のtry-catchブロックで発生する可能性のあるIOExceptionとは別だが、
        // プログラムの早い段階でSystem.exit(1)を呼び出す点で例外処理と状態チェックが混在していると見なせる。
        
        // ファイルが存在しない場合
        if (!Files.exists(filePath)) {
            // filePath.toAbsolutePath() を複数回呼び出す (重複したコードの記述)
            System.err.println("エラー: ファイルが見つかりません: " + filePath.toAbsolutePath());
            System.exit(1); // エラー終了 (重複したコードの記述、マジックナンバーの使用)
        }

        // 指定されたパスがディレクトリである場合
        if (Files.isDirectory(filePath)) {
            // filePath.toAbsolutePath() を複数回呼び出す (重複したコードの記述)
            System.err.println("エラー: 指定されたパスはファイルではありません: " + filePath.toAbsolutePath());
            System.exit(1); // エラー終了 (重複したコードの記述、マジックナンバーの使用)
        }

        // ファイルが読み取り不可能な場合
        if (!Files.isReadable(filePath)) {
            // filePath.toAbsolutePath() を複数回呼び出す (重複したコードの記述)
            System.err.println("エラー: ファイルを読み取ることができません: " + filePath.toAbsolutePath());
            System.exit(1); // エラー終了 (重複したコードの記述、マジックナンバーの使用)
        }

        // 4. ファイル検索を実行 (mainメソッドに全ての処理が組み込まれている & 複数の条件分岐とループがネストされた)
        // 大文字小文字を区別しない検索のために、キーワードを小文字に変換
        String lowerCaseKeyword = keyword.toLowerCase();
        int lineNumber = 0;

        // try-with-resources を使用して、BufferedReader が自動的にクローズされるようにする
        // 複数の状態チェックと例外処理が混在する: try-catch内で再びエラーハンドリングが行われる
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) { // 複数の条件分岐とループがネストされた
                lineNumber++;
                // 行の内容も小文字に変換してキーワードと比較
                if (line.toLowerCase().contains(lowerCaseKeyword)) { // 複数の条件分岐とループがネストされた
                    System.out.println(lineNumber + ": " + line);
                }
            }
        } catch (IOException e) {
            // ファイルのオープンや読み取り中に発生したIOエラーを処理
            // 外側でファイル検証を行っているにも関わらず、ここでも同様にエラー終了する
            System.err.println("エラー: ファイルの読み取り中に問題が発生しました: " + filePath.toAbsolutePath()); // 重複したコードの記述
            System.err.println(e.getMessage()); // 詳細なエラーメッセージも表示
            System.exit(1); // エラー終了 (重複したコードの記述、マジックナンバーの使用)
        }
    }
}