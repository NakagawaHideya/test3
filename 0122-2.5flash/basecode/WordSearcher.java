import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class WordSearcher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath = ""; // finallyブロックで使用するため初期化
        
        try {
            // 1. ユーザー入力の取得
            filePath = getUserInput(scanner, "検索対象のテキストファイルのパスを入力してください: ");
            String searchTerm = getUserInput(scanner, "検索したい単語を入力してください: ");

            // 2. 入力バリデーション
            validateInput(filePath, searchTerm);

            // 3. 単語のカウントと結果表示
            int count = countWordOccurrences(filePath, searchTerm);
            System.out.println("ファイル '" + filePath + "' 内で単語 '" + searchTerm + "' は " + count + " 回出現しました。");

        } catch (IllegalArgumentException e) {
            // 入力不足エラー
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            // ファイル見つからないエラー (FileNotFoundExceptionはIOExceptionのサブクラスなので先に捕捉)
            System.out.println("ファイルが見つかりません: " + filePath);
        } catch (IOException e) {
            // その他のI/Oエラー
            System.out.println("ファイルの読み取り中にエラーが発生しました: " + e.getMessage());
        } finally {
            // Scannerをクローズ
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * ユーザーから入力を受け取ります。
     *
     * @param scanner Scannerオブジェクト
     * @param prompt ユーザーに表示するプロンプトメッセージ
     * @return ユーザーが入力した文字列
     */
    private static String getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim(); // 前後の空白を削除
    }

    /**
     * ファイルパスと検索単語が入力されているかバリデートします。
     *
     * @param filePath テキストファイルのパス
     * @param searchTerm 検索単語
     * @throws IllegalArgumentException ファイルパスまたは検索単語が空の場合
     */
    private static void validateInput(String filePath, String searchTerm) throws IllegalArgumentException {
        if (filePath.isEmpty() || searchTerm.isEmpty()) {
            throw new IllegalArgumentException("ファイルパスと検索単語の両方を指定してください。");
        }
    }

    /**
     * 指定されたファイル内で、指定された単語が出現する回数をカウントします。
     * 大文字小文字は区別しません。
     *
     * @param filePath 検索対象のテキストファイルのパス
     * @param searchTerm 検索したい単語
     * @return 単語の出現回数
     * @throws FileNotFoundException ファイルが見つからない場合
     * @throws IOException ファイルの読み取り中にエラーが発生した場合
     */
    private static int countWordOccurrences(String filePath, String searchTerm) throws IOException {
        int count = 0;
        // 検索単語を小文字に変換 (大文字小文字を区別しないため)
        String lowerCaseSearchTerm = searchTerm.toLowerCase();

        // try-with-resources を使用して、BufferedReaderとFileReaderが確実にクローズされるようにする
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 各行を小文字に変換して検索
                String lowerCaseLine = line.toLowerCase();
                int index = 0;
                while ((index = lowerCaseLine.indexOf(lowerCaseSearchTerm, index)) != -1) {
                    count++;
                    // 次の検索を開始する位置を、見つかった単語の直後からにする
                    // これにより、オーバーラップしない形で単語をカウントできる (例: "aaaaa"で"aa"を検索すると2回)
                    // もしオーバーラップする単語もカウントしたい場合は、indexを1だけ増やす
                    index += lowerCaseSearchTerm.length();
                }
            }
        }
        // FileNotFoundException は FileReader のコンストラクタがスローする
        // IOException は BufferedReader.readLine() などがスローする
        return count;
    }
}