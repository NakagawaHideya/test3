import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class FileWriteStringer {

    /**
     * プログラムのエントリポイントです。
     * ユーザーからの入力を受け付け、ファイルへの書き込み処理を調整します。
     *
     * @param args コマンドライン引数（このプログラムでは使用しません）
     */
    public static void main(String[] args) {
        // Scannerをtry-with-resourcesで宣言することで、確実にクローズされるようにします。
        try (Scanner scanner = new Scanner(System.in)) {
            FileWriteStringer app = new FileWriteStringer();

            // 1. ファイル名を取得する
            String fileName = app.getFileNameFromUser(scanner);
            if (fileName == null) { // ファイル名取得中にエラーが発生した場合（ここでは起こり得ないが、 defensive programming）
                return;
            }

            // 2. 書き込む文字列を取得する
            String content = app.getStringContentFromUser(scanner);

            // 3. ファイルに文字列を書き込む
            app.writeToFile(fileName, content);

        } catch (Exception e) {
            // 想定外の例外が発生した場合のハンドリング
            System.err.println("予期せぬエラーが発生しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーからファイル名を入力させ、空でないことを確認します。
     * 空の場合、エラーメッセージを表示し、再入力を促します。
     *
     * @param scanner ユーザー入力を読み取るためのScannerオブジェクト
     * @return ユーザーが入力した有効なファイル名
     */
    private String getFileNameFromUser(Scanner scanner) {
        String fileName;
        while (true) {
            System.out.print("ファイル名を入力してください: ");
            fileName = scanner.nextLine();
            if (fileName.trim().isEmpty()) { // trim()で空白のみの入力も空とみなす
                System.out.println("ファイル名を入力してください。");
            } else {
                break; // 有効なファイル名が入力されたらループを抜ける
            }
        }
        return fileName;
    }

    /**
     * ユーザーからファイルに書き込む文字列を入力させます。
     *
     * @param scanner ユーザー入力を読み取るためのScannerオブジェクト
     * @return ユーザーが入力した書き込み文字列
     */
    private String getStringContentFromUser(Scanner scanner) {
        System.out.println("ファイルに書き込む文字列を入力してください:");
        return scanner.nextLine();
    }

    /**
     * 指定されたファイルに文字列を書き込みます。
     * ファイルが存在しない場合は新規作成し、存在する場合は内容を上書きします。
     * IO例外が発生した場合は、エラーメッセージを表示します。
     *
     * @param fileName 書き込むファイルの名前
     * @param content ファイルに書き込む文字列
     */
    private void writeToFile(String fileName, String content) {
        Path filePath = Paths.get(fileName);
        try {
            // Files.writeString を使用してファイルに文字列を書き込みます。
            // StandardOpenOption.CREATE: ファイルが存在しない場合は新規作成します。
            // StandardOpenOption.TRUNCATE_EXISTING: ファイルが存在する場合は既存の内容を上書き（切り詰め）ます。
            Files.writeString(filePath, content,
                              StandardOpenOption.CREATE,
                              StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("ファイルに正常に書き込みました: " + fileName);
        } catch (IOException e) {
            // IO例外が発生した場合のエラー処理
            System.err.println("ファイルへの書き込み中にエラーが発生しました: " + e.getMessage());
        }
    }
}