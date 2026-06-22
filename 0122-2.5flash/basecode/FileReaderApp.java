import java.io.BufferedReader; // ファイルを効率的に読み込むためのクラス
import java.io.File;           // ファイルやディレクトリのパス名を抽象的に表現するクラス
import java.io.FileReader;     // ファイルから文字データを読み込むためのクラス
import java.io.IOException;    // 入出力操作中に発生する可能性のある例外
import java.util.Scanner;      // 標準入力からデータを読み込むためのクラス

public class FileReaderApp {

    /**
     * ユーザーにファイル名を入力させ、有効なファイル名が入力されるまで再入力を促します。
     * ファイル名が空の場合、エラーメッセージを表示し再入力を求めます。
     *
     * @param scanner 標準入力からの読み込みに使用するScannerオブジェクト
     * @return ユーザーが入力した、空でないファイル名
     */
    public static String getFileNameFromUser(Scanner scanner) {
        String fileName;
        while (true) { // 有効なファイル名が入力されるまでループ
            System.out.print("ファイル名を入力してください: ");
            fileName = scanner.nextLine(); // ファイル名を読み込む

            if (fileName.trim().isEmpty()) { // 入力されたファイル名が空または空白のみの場合
                System.out.println("ファイル名を入力してください。");
            } else {
                return fileName; // 有効なファイル名が入力されたらそれを返す
            }
        }
    }

    /**
     * 指定されたファイルの内容を読み込み、標準出力に表示します。
     * 以下のエラー処理を行います：
     * - ファイルが存在しない場合
     * - ファイルからの読み込み中にIO例外が発生した場合
     *
     * @param fileName 読み込むファイルのパス名
     */
    public static void readFileAndDisplay(String fileName) {
        File file = new File(fileName); // ファイルオブジェクトを作成

        // 1. ファイルが存在しない場合のエラー処理
        if (!file.exists()) {
            System.out.println("ファイル '" + fileName + "' が見つかりません。");
            return; // 処理を終了
        }

        // 2. ファイルからの読み込み中にIO例外が発生した場合のエラー処理
        // try-with-resources文を使用し、BufferedReaderが自動的に閉じられるようにする
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n--- ファイル内容 ---");
            // ファイルの終端に達するまで1行ずつ読み込み、表示する
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("--------------------");
        } catch (IOException e) {
            // 読み込み中にIO例外が発生した場合
            System.out.println("ファイルからの読み込み中にエラーが発生しました: " + e.getMessage());
        }
    }

    /**
     * プログラムのエントリポイントです。
     * ユーザーからファイル名を取得し、そのファイルの内容を読み込んで表示します。
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 標準入力から読み込むためのScannerを初期化
        try {
            // ユーザーからファイル名を取得
            String fileName = getFileNameFromUser(scanner);
            // 取得したファイルの内容を読み込み、表示
            readFileAndDisplay(fileName);
        } finally {
            // プログラム終了時にScannerを閉じ、リソースを解放する
            scanner.close();
        }
    }
}