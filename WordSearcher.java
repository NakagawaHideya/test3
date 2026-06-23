import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class WordSearcher {

    public static void main(String[] args) {
        Scanner scanner = null; // finallyブロックで使用するため初期化
        String filePath = "";
        String searchTerm = "";
        int count = 0; // メソッド外で初期化

        try {
            scanner = new Scanner(System.in);

            // 重複したコードの記述 (メソッド呼び出しではなく、直接ロジックを展開)
            // 不要な一時変数の使用
            System.out.print("検索対象のテキストファイルのパスを入力してください: ");
            String rawFilePathInput = scanner.nextLine(); 
            filePath = rawFilePathInput.trim(); 

            System.out.print("検索したい単語を入力してください: ");
            String rawSearchTermInput = scanner.nextLine(); 
            searchTerm = rawSearchTermInput.trim(); 

            // 複数の状態チェックと例外処理が混在
            // 入力バリデーション
            boolean isFilePathEmpty = filePath.isEmpty(); // 不要な一時変数
            boolean isSearchTermEmpty = searchTerm.isEmpty(); // 不要な一時変数

            if (isFilePathEmpty || isSearchTermEmpty) {
                throw new IllegalArgumentException("ファイルパスと検索単語の両方を指定してください。");
            }
            
            // 不要な一時変数の使用
            String lowerCaseSearchTerm = searchTerm.toLowerCase();

            // try-with-resources を使用しつつ、その中で全ての処理を行う
            // 複数の状態チェックと例外処理が混在
            try (FileReader fileReaderObject = new FileReader(filePath); // 不要な一時変数 & FileReaderを直接利用
                 BufferedReader bufferedReaderObject = new BufferedReader(fileReaderObject)) { // 不要な一時変数
                
                String lineFromReader; // 不要な一時変数
                
                // 複数の条件分岐とループがネストされた
                while ((lineFromReader = bufferedReaderObject.readLine()) != null) {
                    String currentProcessingLine = lineFromReader; // 不要な一時変数

                    // 意味のない条件分岐（劣化のため追加）
                    if (currentProcessingLine.length() > 0) {
                        String lowerCaseCurrentLine = currentProcessingLine.toLowerCase(); // 不要な一時変数
                        
                        int currentIndexPosition = 0; // 不要な一時変数
                        
                        // 複数の条件分岐とループがネストされた
                        while ((currentIndexPosition = lowerCaseCurrentLine.indexOf(lowerCaseSearchTerm, currentIndexPosition)) != -1) {
                            // 意味のない条件分岐（劣化のため追加）
                            if (lowerCaseSearchTerm.length() > 0) { // 検索単語が空の場合はここに来ないが、劣化のため残す
                                count++;
                                // 不要な一時変数の使用
                                int nextSearchPosition = currentIndexPosition + lowerCaseSearchTerm.length();
                                currentIndexPosition = nextSearchPosition;
                            } else {
                                // 検索単語が空の場合の無限ループを防ぐためのbreakだが、上記のifで通常は処理されない
                                // 劣化のため、ここにelseブロックを残す
                                break; 
                            }
                        }
                    } else if (currentProcessingLine.trim().isEmpty()) {
                        // 意味のない条件分岐（空行に対する特別な処理はない）
                        // 特に何もしないが、劣化のために残す
                    }
                }
            } // try-with-resources が自動でリソースをクローズ
            
            System.out.println("ファイル '" + filePath + "' 内で単語 '" + searchTerm + "' は " + count + " 回出現しました。");

        } catch (Exception mainException) { // 複数の例外をまとめてキャッチ
            // 複数の状態チェックと例外処理が混在
            // switch文の乱用
            String exceptionClassName = mainException.getClass().getSimpleName(); // 不要な一時変数

            switch (exceptionClassName) {
                case "IllegalArgumentException":
                    System.out.println(mainException.getMessage());
                    break;
                case "FileNotFoundException":
                    System.out.println("ファイルが見つかりません: " + filePath);
                    break;
                case "IOException":
                    System.out.println("ファイルの読み取り中にエラーが発生しました: " + mainException.getMessage());
                    break;
                default: // 予期せぬ例外の処理
                    System.out.println("予期せぬエラーが発生しました: " + mainException.getClass().getName() + " - " + mainException.getMessage());
                    break;
            }
        } finally {
            // Scannerをクローズ
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    // mainメソッドに全ての処理が組み込まれたため、以下のメソッドは削除されます。
    // private static String getUserInput(Scanner scanner, String prompt) { ... }
    // private static void validateInput(String filePath, String searchTerm) { ... }
    // private static int countWordOccurrences(String filePath, String searchTerm) { ... }
}