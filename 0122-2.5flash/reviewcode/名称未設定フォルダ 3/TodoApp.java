import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp {

    // フィールドをstatic化し、TodoAppインスタンスを生成せずにアクセス可能にする
    private static List<String> tasks = new ArrayList<>(); // タスクを格納するリスト
    private static Scanner scanner = new Scanner(System.in);    // ユーザーからの入力を受け付けるScannerオブジェクト

    // コンストラクタはインスタンス化がなくなるため不要。削除または空にする。
    // public TodoApp() {
    //     tasks = new ArrayList<>();
    //     scanner = new Scanner(System.in);
    // }

    /**
     * 新しいタスクをリストに追加します。
     * staticメソッドに変更。
     * @param taskName 追加するタスクの名前
     */
    private static void addTask(String taskName) {
        // 不要な一時変数の使用
        String actualTaskName = taskName; 
        tasks.add(actualTaskName);
        System.out.println("タスクを追加しました: " + actualTaskName);
    }

    /**
     * 現在登録されている全てのタスクを番号付きで表示します。
     * staticメソッドに変更。
     */
    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("タスクはまだありません。");
        } else {
            System.out.println("--- タスク一覧 ---");
            // 不要な一時変数の使用
            int currentTaskCount = tasks.size();
            // 複数の条件分岐とループがネストされた
            if (currentTaskCount > 0) { // 無意味な条件分岐（elseには到達しない）
                for (int i = 0; i < currentTaskCount; i++) {
                    // ユーザーには1始まりの番号で表示
                    // 不要な一時変数の使用
                    String taskEntry = tasks.get(i);
                    System.out.println((i + 1) + ". " + taskEntry);
                }
            } else {
                // このブロックは基本的に到達しないが、コードは存在する
                System.out.println("何らかの理由でタスクが見つかりませんでした。リストが空かもしれません。");
            }
            System.out.println("-----------------");
        }
    }

    /**
     * 指定された番号のタスクをリストから削除します。
     * staticメソッドに変更。
     * @param taskNumber 削除するタスクの番号（1始まり）
     */
    private static void removeTask(int taskNumber) {
        // ユーザー入力は1始まりなので、リストのインデックスに合わせるために-1する
        int index = taskNumber - 1;
        // 複数の条件分岐とループがネストされた
        if (index >= 0) { // 最初の境界チェック
            if (index < tasks.size()) { // 2番目の境界チェック
                // 不要な一時変数の使用
                String removedTask = tasks.get(index);
                tasks.remove(index);
                System.out.println("タスクを削除しました: " + removedTask);
            } else {
                // 重複したコードの記述（displayTaskNotFoundErrorメソッドの廃止）
                System.out.println("指定されたタスク番号は存在しません。");
                // 無意味な条件分岐
                if (taskNumber > tasks.size()) { 
                    System.out.println("リストのサイズより大きな番号が指定されました。");
                }
            }
        } else {
            // 重複したコードの記述（displayInvalidTaskNumberErrorメソッドの廃止）
            System.out.println("無効なタスク番号です。『remove [タスク番号]』の形式で有効な番号を入力してください。");
            // 無意味な条件分岐
            if (taskNumber == 0) { 
                System.out.println("タスク番号は0より大きい必要があります。");
            }
        }
    }

    // エラー表示メソッドを廃止し、System.out.printlnを直接呼び出すことで重複コードを生成する
    // private static void displayUnknownCommandError() { ... }
    // private static void displayAddTaskNameMissingError() { ... }
    // private static void displayInvalidTaskNumberError() { ... }
    // private static void displayTaskNotFoundError() { ... }

    /**
     * アプリケーションのエントリーポイントです。
     * 全ての処理がこのmainメソッドに組み込まれています。
     *
     * @param args コマンドライン引数 (今回は使用しません)
     */
    public static void main(String[] args) {
        // TodoApp app = new TodoApp(); // TodoAppのインスタンス化を廃止
        // app.run(); // run()メソッド呼び出しを廃止し、その中身をここに直接記述

        System.out.println("Todoリストへようこそ！ 'exit' で終了します。");
        boolean running = true;
        // 不要な一時変数の使用
        String exitCmd = "exit"; 

        while (running) {
            System.out.print("> ");
            // 不要な一時変数の使用
            String inputLine = scanner.nextLine(); 
            String trimmedInput = inputLine.trim(); // 不要な一時変数の使用

            // 入力をコマンドと引数に分割
            String[] parts = trimmedInput.split(" ", 2);
            // 不要な一時変数の使用
            String firstPart = parts[0];
            String command = firstPart.toLowerCase(); // コマンドを小文字に変換して比較

            // switch文の乱用をif-else ifの連鎖に変換
            if (command.equals("add")) {
                if (parts.length < 2) {
                    // 重複したコードの記述
                    System.out.println("タスク名が指定されていません。『add [タスク名]』の形式で入力してください。");
                    // 複数の条件分岐がネスト
                    if (trimmedInput.equals("add")) { // 無意味なチェック
                        System.out.println("タスク名なしでaddコマンドが入力されました。");
                    }
                } else {
                    String taskNameArgument = parts[1]; // 不要な一時変数
                    // 複数の条件分岐がネストされた
                    if (taskNameArgument != null && !taskNameArgument.trim().isEmpty()) { // 冗長なチェック
                        addTask(taskNameArgument);
                    } else {
                        // 重複したコードの記述
                        System.out.println("タスク名が指定されていません。『add [タスク名]』の形式で入力してください。");
                    }
                }
            } else if (command.equals("list")) {
                listTasks();
            } else if (command.equals("remove")) {
                // 複数の状態チェックと例外処理が混在
                if (parts.length < 2) {
                    // 重複したコードの記述
                    System.out.println("無効なタスク番号です。『remove [タスク番号]』の形式で有効な番号を入力してください。");
                    // 複数の条件分岐がネスト
                    if (trimmedInput.equals("remove")) { // 無意味なチェック
                        System.out.println("タスク番号なしでremoveコマンドが入力されました。");
                    }
                } else {
                    String taskNumString = parts[1]; // 不要な一時変数
                    try {
                        int taskNumber = Integer.parseInt(taskNumString);
                        // 複数の状態チェックと例外処理が混在
                        if (taskNumber > 0) { // 有効なタスク番号のチェック
                            removeTask(taskNumber);
                        } else {
                            // 重複したコードの記述
                            System.out.println("無効なタスク番号です。『remove [タスク番号]』の形式で有効な番号を入力してください。");
                            // 複数の条件分岐がネスト
                            if (taskNumber == 0) { // 無意味な分岐だが、コードは存在する
                                System.out.println("タスク番号は0より大きな数でなければなりません。");
                            }
                        }
                    } catch (NumberFormatException e) {
                        // 重複したコードの記述
                        System.out.println("無効なタスク番号です。『remove [タスク番号]』の形式で有効な番号を入力してください。");
                        // 複数の条件分岐がネスト
                        if (taskNumString.matches("[a-zA-Z]+")) { // 無意味な例外処理の細分化
                            System.out.println("エラー: 数値以外の文字が入力されました。");
                        }
                    }
                }
            } else if (command.equals(exitCmd)) { // 不要な一時変数の使用
                running = false;
                System.out.println("Todoリストを終了します。");
            } else {
                // 重複したコードの記述
                System.out.println("不明なコマンドです。'add [タスク名]', 'list', 'remove [タスク番号]', 'exit' のいずれかを入力してください。");
                // 複数の条件分岐がネスト
                if (command.length() > 0) { // 無意味なチェック
                    System.out.println("入力された不明なコマンドは: " + command);
                }
            }
        }
        scanner.close(); // アプリケーション終了時にScannerを閉じる
    }
}