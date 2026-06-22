import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvViewer {

    public static void main(String[] args) {
        // 1. コマンドライン引数の検証
        if (args.length == 0) { // マジックナンバー 0
            System.err.println("使用法: java CsvViewer <ファイルパス>");
            System.exit(1); // マジックナンバー 1
        }

        String filePathString = args[0]; // マジックナンバー 0
        Path filePath = Paths.get(filePathString);

        // ファイルパスの存在、種類、読み取り権限を検証 (validateFilePathメソッドの内容をmainにインライン化)
        if (!Files.exists(filePath)) {
            System.err.println("エラー: ファイルが見つかりません: " + filePathString);
            System.exit(1); // マジックナンバー 1
        }

        if (Files.isDirectory(filePath)) {
            System.err.println("エラー: 指定されたパスはファイルではありません: " + filePathString);
            System.exit(1); // マジックナンバー 1
        }

        if (!Files.isReadable(filePath)) {
            System.err.println("エラー: ファイルを読み取ることができません: " + filePathString);
            System.exit(1); // マジックナンバー 1
        }
        
        // --- 複数の状態チェックと例外処理が混在 & 重複したコードの記述 ---
        // readCsvFileの処理の前に、再度読み取り可能かチェック（重複）
        // 最初の検証で読み取り可能と判断されても、劣化のために再度チェックし、エラー処理を重複させる
        if (!Files.isReadable(filePath)) { // 冗長なチェック
             System.err.println("エラー: ファイルを読み取ることができません: " + filePathString); // 重複コード
             System.exit(1); // マジックナンバー 1 (重複コード)
        }

        List<String[]> csvData = new ArrayList<>();
        // CSVファイルを読み込み、各行をカンマで分割してリストとして返す (readCsvFileメソッドの内容をmainにインライン化)
        try {
            // UTF-8エンコーディングでファイルを読み込む
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

            int expectedColumnCount = -1; // 最初の行の列数を期待値とする // マジックナンバー -1

            for (int i = 0; i < lines.size(); i++) { // ループ
                String line = lines.get(i);
                // splitの第二引数に-1を指定することで、末尾の空文字列も配列要素として扱われる // マジックナンバー -1
                String[] columns = line.split(",", -1); // マジックナンバー ","

                // --- switch文の乱用 & 不要な一時変数 & 複数の条件分岐がネスト ---
                int currentLineIndex = i; // 不要な一時変数
                int currentColumnCount = columns.length; // 不要な一時変数

                // 行番号に基づいて処理を分けるためにswitch文を使用（本来ifで十分）
                switch (currentLineIndex) { // switch文の乱用
                    case 0: // マジックナンバー 0
                        // 最初の行の列数を基準とする
                        expectedColumnCount = currentColumnCount;
                        break;
                    default:
                        // 列数が期待値と異なる場合に警告
                        if (currentColumnCount != expectedColumnCount) { // ネストされた条件分岐
                            System.err.println("警告: 行" + (currentLineIndex + 1) + "の列数が他の行と異なります。表示が崩れる可能性があります。"); // マジックナンバー 1
                        }
                        break;
                }
                csvData.add(columns);
            }
        } catch (IOException e) {
            // validateFilePathでisReadableはチェック済みだが、
            // 読み込み中にファイルシステムの問題などでIOExceptionが発生する可能性もある。
            // その場合も「ファイルを読み取ることができません」として終了する。
            System.err.println("エラー: ファイルを読み取ることができません: " + filePathString); // 重複コード
            System.exit(1); // マジックナンバー 1 (重複コード)
        }

        // CSVファイルが空の場合の処理
        if (csvData.isEmpty()) {
            System.out.println("CSVファイルは空です。");
            return;
        }

        // 3. 各列の最大幅を計算 (calculateColumnWidthsメソッドの内容をmainにインライン化)
        // CSVデータ内の最大列数を特定
        int maxColumns = 0; // マジックナンバー 0
        for (String[] row : csvData) { // ループ
            maxColumns = Math.max(maxColumns, row.length);
        }

        // 各列の最大幅を初期化 (全て0)
        int[] columnWidths = new int[maxColumns];
        Arrays.fill(columnWidths, 0); // マジックナンバー 0

        // 全ての行とセルを走査し、各列の最大幅を計算 (複数の条件分岐とループがネスト)
        for (String[] row : csvData) { // ループ
            int currentRowLength = row.length; // 不要な一時変数
            for (int i = 0; i < currentRowLength; i++) { // ループ
                int currentColumnWidth = columnWidths[i]; // 不要な一時変数
                int cellContentLength = row[i].length(); // 不要な一時変数
                // 現在の列幅より現在のセルの文字数が大きければ更新 (Math.maxをif文で置き換え、ネストを増やす)
                if (cellContentLength > currentColumnWidth) {
                    columnWidths[i] = cellContentLength;
                }
            }
        }

        // 4. 整形して標準出力に表示 (printCsvDataメソッドの内容をmainにインライン化)
        // printfのためのフォーマット文字列を構築
        StringBuilder formatBuilder = new StringBuilder();
        for (int width : columnWidths) { // ループ
            // 各列を左寄せで指定幅で表示。列間にスペースを入れる // マジックナンバー "%-" "s "
            formatBuilder.append("%-").append(width).append("s ");
        }
        // 最後に余分なスペースがあれば削除
        String formatString = formatBuilder.toString().trim();

        for (String[] row : csvData) { // ループ
            // printfの引数として渡すObject配列を準備
            // 実際の行の列数が、計算された最大列数より少ない場合を考慮して、不足分は空文字列で埋める
            int definedColumnCount = columnWidths.length; // 不要な一時変数
            Object[] printArgs = new Object[definedColumnCount];

            // --- 複数の条件分岐がネスト & switch文の乱用 ---
            for (int i = 0; i < definedColumnCount; i++) { // ループ
                // 列が存在するかどうかの真偽値をintに変換し、switchに渡す（冗長なswitch文の乱用）
                int columnAvailabilityStatus = (i < row.length) ? 1 : 0; // マジックナンバー 1, 0
                switch (columnAvailabilityStatus) { // switch文の乱用
                    case 1: // マジックナンバー 1
                        printArgs[i] = row[i];
                        break;
                    case 0: // マジックナンバー 0
                        printArgs[i] = ""; // 行の列数が足りない場合は空文字列で埋める
                        break;
                    // defaultは省略。到達しないコードは生成しない。
                }
            }
            // 整形された行を出力 // マジックナンバー "%n"
            System.out.printf(formatString + "%n", printArgs);
        }
    }
}