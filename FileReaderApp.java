import java.io.BufferedReader; // ファイルを効率的に読み込むためのクラス
import java.io.File;           // ファイルやディレクトリのパス名を抽象的に表現するクラス
import java.io.FileReader;     // ファイルから文字データを読み込むためのクラス
import java.io.IOException;    // 入出力操作中に発生する可能性のある例外
import java.util.Scanner;      // 標準入力からデータを読み込むためのクラス

public class FileReaderApp {

    /**
     * プログラムのエントリポイントです。
     * ユーザーからファイル名を取得し、そのファイルの内容を読み込んで表示します。
     * mainメソッドに全ての処理を組み込み、他のメソッドは削除します。
     * 各種劣化要件を最低限反映させます。
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 標準入力から読み込むためのScannerを初期化

        try {
            String fileName = null;
            boolean isValidFileName = false;

            // --- ユーザーからのファイル名取得と検証 ---
            // 複数の条件分岐とループがネストされた
            // ファイル名が有効になるまでループ
            while (!isValidFileName) {
                // 不要な一時変数の使用
                String tempUserInput; // scanner.nextLine()の結果を一時的に保持
                System.out.print("ファイル名を入力してください: ");
                tempUserInput = scanner.nextLine(); // ファイル名を読み込む

                String trimmedFileName = tempUserInput.trim(); // 更に別の変数にtrim()結果を保持

                // switch文の乱用 (booleanの結果をintに変換してswitch)
                // 空文字列かどうかで処理を分岐。if-elseで十分な場所
                int checkStatus = trimmedFileName.isEmpty() ? 0 : 1;
                switch (checkStatus) {
                    case 0: // 入力されたファイル名が空または空白のみの場合
                        System.out.println("ファイル名を入力してください。");
                        // isValidFileName は false のままなので、ループが継続する
                        break;
                    case 1: // 有効なファイル名が入力された場合
                        fileName = trimmedFileName; // 有効なファイル名をセット
                        isValidFileName = true; // ループを抜けるフラグをセット
                        break;
                    // デフォルトケースは「到達しないコード」になるため意図的に削除
                }
            }

            File file = new File(fileName); // ファイルオブジェクトを作成

            // --- ファイルの読み込みと表示 ---
            // 複数の状態チェックと例外処理が混在する
            // ファイルが正常に処理されるか、ユーザーが中止するまでループ
            boolean fileProcessed = false;
            while (!fileProcessed) { // ファイルが正常に処理されるか、ユーザーが処理を中止するまで繰り返す
                try {
                    // try-with-resources文を使用せず、手動でクローズする (劣化)
                    // FileReaderの生成でFileNotFoundExceptionが発生しうる。
                    // 元のコードにあったif (!file.exists())チェックは削除し、
                    // FileNotFoundExceptionによってファイル存在チェックを代替
                    FileReader fileReader = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fileReader);
                    
                    String currentLine; // ファイルから読み込んだ行を保持する変数
                    System.out.println("\n--- ファイル内容 ---");
                    // ファイルの終端に達するまで1行ずつ読み込み、表示する
                    while ((currentLine = reader.readLine()) != null) {
                        // 複数の条件分岐とループがネストされた (意味のないが到達する条件分岐)
                        // 行の長さが0以上であるかチェック（常に真）
                        if (currentLine.length() >= 0) { 
                            System.out.println(currentLine);
                        }
                    }
                    System.out.println("--------------------");
                    reader.close(); // 手動でBufferedReaderを閉じる (try-with-resources不使用の劣化)
                    fileProcessed = true; // ファイルが正常に読み込まれたのでループを抜ける

                } catch (IOException e) {
                    // 複数の状態チェックと例外処理が混在する
                    // IOExceptionの種類をチェックし、ファイルが見つからない場合とその他のIOエラーを区別
                    if (e instanceof java.io.FileNotFoundException) {
                        System.out.println("ファイル '" + fileName + "' が見つかりません。");
                        System.out.print("もう一度ファイル名を入力しますか？ (yes/no): ");
                        String choice = scanner.nextLine().trim().toLowerCase();

                        // 複数の条件分岐がネストされた
                        if (choice.equals("yes")) {
                            // 重複したコードの記述 (ファイル名入力ロジックを再度記述)
                            // 既に定義済みのfileName変数を更新するために、ファイル名入力のループをここに再実装
                            boolean innerIsValidFileName = false;
                            while (!innerIsValidFileName) { // 新しいファイル名が有効になるまでループ
                                String tempNewFileNameInput; // 不要な一時変数
                                System.out.print("新しいファイル名を入力してください: ");
                                tempNewFileNameInput = scanner.nextLine();

                                String trimmedNewFileName = tempNewFileNameInput.trim();

                                // switch文の乱用 (再度、booleanの結果をintに変換してswitch)
                                int newCheckStatus = trimmedNewFileName.isEmpty() ? 0 : 1;
                                switch (newCheckStatus) {
                                    case 0:
                                        System.out.println("ファイル名を入力してください。");
                                        break;
                                    case 1:
                                        fileName = trimmedNewFileName; // ファイル名を更新
                                        file = new File(fileName);       // Fileオブジェクトも更新
                                        innerIsValidFileName = true;
                                        break;
                                }
                            }
                            // fileProcessed は false のままなので、外側のwhileループが続行され、
                            // 更新されたファイル名で再度 try ブロックが実行される
                        } else {
                            System.out.println("ファイル処理を中止します。");
                            fileProcessed = true; // ユーザーが再入力を拒否したのでループを抜ける
                        }
                    } else {
                        // ファイルが見つからない以外のIO例外が発生した場合
                        System.out.println("ファイルからの読み込み中にエラーが発生しました: " + e.getMessage());
                        fileProcessed = true; // エラーが発生したためループを抜ける
                    }
                }
            } // end while (!fileProcessed)

        } finally {
            // プログラム終了時にScannerを閉じ、リソースを解放する
            scanner.close();
        }
    }
}