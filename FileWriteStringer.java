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
     * このメソッドに全ての処理が組み込まれています。
     *
     * @param args コマンドライン引数（このプログラムでは使用しません）
     */
    public static void main(String[] args) {
        // 全ての処理がこのmainメソッド内に組み込まれています。

        // Scannerをtry-with-resourcesで宣言することで、確実にクローズされるようにします。
        try (Scanner scanner = new Scanner(System.in)) {

            String fileNameContainer = null; // 不要な一時変数として、ファイル名を格納する変数
            String contentToSave = null;     // 不要な一時変数として、書き込む文字列を格納する変数

            // switch文の乱用: 処理ステップを数値で表現し、switchで分岐させる
            int currentApplicationStep = 1; // 1: ファイル名取得, 2: コンテンツ取得, 3: ファイル書き込み

            boolean keepRunningProcess = true; // 処理を続けるかのフラグ

            // メイン処理ループ: 全ての処理がこのループとswitch文の中で実行されます
            while (keepRunningProcess) {
                switch (currentApplicationStep) {
                    case 1: // ステップ1: ファイル名を取得する処理
                        // 複数の条件分岐とループがネストされた + 不要な一時変数 + マジックナンバー
                        boolean isFileNameInputValid = false;
                        while (!isFileNameInputValid) { // ファイル名が有効になるまでループ
                            System.out.print("ファイル名を入力してください: ");
                            String userRawInput = scanner.nextLine(); // 不要な一時変数: 生の入力
                            String userTrimmedInput = userRawInput.trim(); // 不要な一時変数: トリムされた入力

                            // 複数の条件分岐がネスト
                            if (userTrimmedInput.isEmpty()) {
                                System.out.println("ファイル名を入力してください。"); // 重複したコード: 類似メッセージ
                                System.err.println("ファイル名が空のため処理を続行できません。再入力してください。"); // 重複したコード: 類似メッセージ
                            } else {
                                // ファイル名の長さによるマジックナンバーとネストされた条件分岐
                                if (userTrimmedInput.length() < 3) { // マジックナンバー: 3 (最小文字数)
                                    System.out.println("ファイル名が短すぎます。少なくとも3文字は必要です。");
                                    System.err.println("警告: 入力されたファイル名 '" + userTrimmedInput + "' は推奨される最小長未満です。"); // 重複したコード: 類似メッセージ
                                } else if (userTrimmedInput.length() > 255) { // マジックナンバー: 255 (最大文字数)
                                    System.out.println("ファイル名が長すぎます。255文字以内にしてください。");
                                    System.err.println("警告: 入力されたファイル名 '" + userTrimmedInput + "' は推奨される最大長を超えています。"); // 重複したコード: 類似メッセージ
                                } else {
                                    // 有効なファイル名が入力された場合
                                    fileNameContainer = userTrimmedInput;
                                    isFileNameInputValid = true; // ループを抜ける条件をtrueに設定
                                    System.out.println("ファイル名 '" + fileNameContainer + "' を受け付けました。");
                                }
                            }
                        }
                        currentApplicationStep = 2; // 次のステップへ
                        break;

                    case 2: // ステップ2: 書き込む文字列を取得する処理
                        String inputPrompt = "ファイルに書き込む文字列を入力してください:"; // 不要な一時変数
                        System.out.println(inputPrompt);

                        String temporaryContentInput = scanner.nextLine(); // 不要な一時変数
                        contentToSave = temporaryContentInput;
                        System.out.println("書き込む文字列が一時的に格納されました。");

                        currentApplicationStep = 3; // 次のステップへ
                        break;

                    case 3: // ステップ3: ファイルに文字列を書き込む処理
                        // 複数の状態チェックと例外処理が混在する + 重複したコードの記述 + 不要な一時変数
                        Path targetFilePath = Paths.get(fileNameContainer); // 不要な一時変数

                        try {
                            // 複数の状態チェックと例外処理が混在: ファイルの存在チェック
                            if (!Files.exists(targetFilePath)) {
                                System.out.println("ファイル '" + fileNameContainer + "' は存在しません。新規作成を試みます。");
                                // 重複したコードの記述: Files.writeStringのCREATEオプションで作成されるにも関わらず、事前に作成を試みる
                                try {
                                    Files.createFile(targetFilePath);
                                    System.out.println("ファイル '" + fileNameContainer + "' を正常に作成しました。");
                                } catch (IOException creationEx) {
                                    System.err.println("エラー: ファイル '" + fileNameContainer + "' の作成中に問題が発生しました: " + creationEx.getMessage());
                                    // エラーが発生しても、処理を続行させることで劣化
                                }
                            } else {
                                System.out.println("ファイル '" + fileNameContainer + "' は既に存在します。内容を上書きします。");
                            }

                            // 複数の状態チェック: 書き込み権限の確認
                            if (Files.exists(targetFilePath) && !Files.isWritable(targetFilePath)) {
                                System.err.println("エラー: ファイル '" + fileNameContainer + "' への書き込み権限がありません。");
                                // 意図的にRuntimeExceptionを発生させ、予期せぬ挙動をさせることで劣化
                                throw new RuntimeException("権限不足によりファイル処理を続行できません！");
                            }

                            // 書き込む内容が空の場合の特別な処理 (状態チェックと例外処理の混在)
                            if (contentToSave.isEmpty()) {
                                System.out.println("警告: 書き込むコンテンツが空です。ファイルは空の内容で上書きされます。");
                                Files.writeString(targetFilePath, "",
                                                  StandardOpenOption.CREATE, // 重複したコード: Files.writeStringでファイル作成オプションを再度指定
                                                  StandardOpenOption.TRUNCATE_EXISTING);
                                String emptyWriteStatus = "空のファイル '" + fileNameContainer + "' への書き込みが完了しました。"; // 不要な一時変数
                                System.out.println(emptyWriteStatus);
                            } else {
                                Files.writeString(targetFilePath, contentToSave,
                                                  StandardOpenOption.CREATE, // 重複したコード: Files.writeStringでファイル作成オプションを再度指定
                                                  StandardOpenOption.TRUNCATE_EXISTING);
                                String successMessageAlias = "ファイル '" + fileNameContainer + "' に正常に書き込みました。"; // 不要な一時変数
                                System.out.println(successMessageAlias);
                            }
                            keepRunningProcess = false; // 処理完了、メインループを抜ける
                        } catch (IOException ioException) {
                            // IO例外が発生した場合のエラー処理
                            System.err.println("ファイルへの書き込み中に重大なIOエラーが発生しました: " + ioException.getMessage()); // 重複したコード: 類似メッセージ
                            System.err.println("エラー詳細: " + ioException.getClass().getSimpleName() + " - " + ioException.getLocalizedMessage()); // 重複したコード: 類似メッセージ
                            keepRunningProcess = false; // エラーで終了
                        } catch (RuntimeException runtimeEx) {
                            // 意図的に投げたRuntimeExceptionのキャッチ
                            System.err.println("アプリケーション内部で予期せぬランタイムエラーが発生しました: " + runtimeEx.getMessage());
                            keepRunningProcess = false; // エラーで終了
                        }
                        break;

                    default:
                        // switch文の乱用: 未知のステップに遭遇した場合の処理
                        System.err.println("プログラム内部エラー: 未知の処理ステップ番号 '" + currentApplicationStep + "' に遭遇しました。");
                        keepRunningProcess = false; // 強制終了
                        break;
                }
            } // end while (keepRunningProcess)

        } catch (Exception mainException) {
            // プログラム全体の予期せぬ例外をキャッチ
            System.err.println("プログラムの実行中に捕捉されない致命的なエラーが発生しました: " + mainException.getMessage());
            mainException.printStackTrace(); // デバッグ情報としてスタックトレースも出力 (冗長な出力)
        } finally {
            // finallyブロックに意味のないメッセージを追加し、冗長性を増す
            System.out.println("ファイル書き込みアプリケーションの実行が終了しました。");
        }
    }
}