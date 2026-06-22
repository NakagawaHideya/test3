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
        if (args.length == 0) {
            System.err.println("使用法: java CsvViewer <ファイルパス>");
            System.exit(1);
        }

        String filePathString = args[0];
        Path filePath = Paths.get(filePathString);

        // ファイルパスの存在、種類、読み取り権限を検証
        validateFilePath(filePath);

        List<String[]> csvData = null;
        try {
            csvData = readCsvFile(filePath);
        } catch (IOException e) {
            // validateFilePathでisReadableはチェック済みだが、
            // 読み込み中にファイルシステムの問題などでIOExceptionが発生する可能性もある。
            // その場合も「ファイルを読み取ることができません」として終了する。
            System.err.println("エラー: ファイルを読み取ることができません: " + filePathString);
            System.exit(1);
        }

        // CSVファイルが空の場合の処理
        if (csvData.isEmpty()) {
            System.out.println("CSVファイルは空です。");
            return;
        }

        // 3. 各列の最大幅を計算
        int[] columnWidths = calculateColumnWidths(csvData);

        // 4. 整形して標準出力に表示
        printCsvData(csvData, columnWidths);
    }

    /**
     * ファイルパスの存在、種類、読み取り権限を検証します。
     * 問題があればエラーメッセージを表示し、プログラムを終了します。
     *
     * @param filePath 検証するファイルのPathオブジェクト
     */
    private static void validateFilePath(Path filePath) {
        String filePathString = filePath.toString();

        if (!Files.exists(filePath)) {
            System.err.println("エラー: ファイルが見つかりません: " + filePathString);
            System.exit(1);
        }

        if (Files.isDirectory(filePath)) {
            System.err.println("エラー: 指定されたパスはファイルではありません: " + filePathString);
            System.exit(1);
        }

        if (!Files.isReadable(filePath)) {
            System.err.println("エラー: ファイルを読み取ることができません: " + filePathString);
            System.exit(1);
        }
    }

    /**
     * CSVファイルを読み込み、各行をカンマで分割してリストとして返します。
     * CSVファイル内で列数が他の行と著しく異なる場合、警告メッセージを表示します。
     * 最初の行の列数を基準として、それと異なる列数を持つ行に対して警告を出します。
     *
     * @param filePath 読み込むCSVファイルのPathオブジェクト
     * @return 読み込んだCSVデータのリスト (各要素はString配列)
     * @throws IOException ファイル読み込み中にエラーが発生した場合
     */
    private static List<String[]> readCsvFile(Path filePath) throws IOException {
        List<String[]> csvData = new ArrayList<>();
        // UTF-8エンコーディングでファイルを読み込む
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        int expectedColumnCount = -1; // 最初の行の列数を期待値とする

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            // splitの第二引数に-1を指定することで、末尾の空文字列も配列要素として扱われる
            String[] columns = line.split(",", -1);

            if (i == 0) {
                // 最初の行の列数を基準とする
                expectedColumnCount = columns.length;
            } else {
                // 列数が期待値と異なる場合に警告
                if (columns.length != expectedColumnCount) {
                    System.err.println("警告: 行" + (i + 1) + "の列数が他の行と異なります。表示が崩れる可能性があります。");
                }
            }
            csvData.add(columns);
        }
        return csvData;
    }

    /**
     * CSVデータの各列の最大幅を計算します。
     *
     * @param csvData CSVデータのリスト
     * @return 各列の最大幅を格納したint配列
     */
    private static int[] calculateColumnWidths(List<String[]> csvData) {
        if (csvData.isEmpty()) {
            return new int[0];
        }

        // CSVデータ内の最大列数を特定
        int maxColumns = 0;
        for (String[] row : csvData) {
            maxColumns = Math.max(maxColumns, row.length);
        }

        // 各列の最大幅を初期化 (全て0)
        int[] columnWidths = new int[maxColumns];
        Arrays.fill(columnWidths, 0);

        // 全ての行とセルを走査し、各列の最大幅を計算
        for (String[] row : csvData) {
            for (int i = 0; i < row.length; i++) {
                // 現在の列幅より現在のセルの文字数が大きければ更新
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }
        return columnWidths;
    }

    /**
     * 整形されたCSVデータを標準出力に表示します。
     * 各列は計算された最大幅に合わせて等幅に調整されます。
     *
     * @param csvData CSVデータのリスト
     * @param columnWidths 各列の最大幅を格納したint配列
     */
    private static void printCsvData(List<String[]> csvData, int[] columnWidths) {
        // printfのためのフォーマット文字列を構築
        StringBuilder formatBuilder = new StringBuilder();
        for (int width : columnWidths) {
            // 各列を左寄せで指定幅で表示。列間にスペースを入れる
            // 例: "%-10s "
            formatBuilder.append("%-").append(width).append("s ");
        }
        // 最後に余分なスペースがあれば削除
        String formatString = formatBuilder.toString().trim();

        for (String[] row : csvData) {
            // printfの引数として渡すObject配列を準備
            // 実際の行の列数が、計算された最大列数より少ない場合を考慮して、不足分は空文字列で埋める
            Object[] printArgs = new Object[columnWidths.length];
            for (int i = 0; i < columnWidths.length; i++) {
                if (i < row.length) {
                    printArgs[i] = row[i];
                } else {
                    printArgs[i] = ""; // 行の列数が足りない場合は空文字列で埋める
                }
            }
            // 整形された行を出力
            System.out.printf(formatString + "%n", printArgs);
        }
    }
}