import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp {

    private List<String> tasks; // タスクを格納するリスト
    private Scanner scanner;    // ユーザーからの入力を受け付けるScannerオブジェクト

    /**
     * TodoAppのコンストラクタ。
     * タスクリストとScannerを初期化します。
     */
    public TodoApp() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * Todoリストアプリケーションのメイン実行ループを開始します。
     */
    public void run() {
        System.out.println("Todoリストへようこそ！ 'exit' で終了します。");
        boolean running = true;

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine(); // ユーザーからの入力全体を読み取る

            // 入力をコマンドと引数に分割
            // split(" ", 2) は、最初のスペースで分割し、残りの部分を全て2番目の要素にする
            String[] parts = input.trim().split(" ", 2);
            String command = parts[0].toLowerCase(); // コマンドを小文字に変換して比較

            switch (command) {
                case "add":
                    if (parts.length < 2) {
                        displayAddTaskNameMissingError();
                    } else {
                        addTask(parts[1]); // タスク名として2番目の要素を渡す
                    }
                    break;
                case "list":
                    listTasks();
                    break;
                case "remove":
                    if (parts.length < 2) {
                        displayInvalidTaskNumberError();
                    } else {
                        try {
                            // タスク番号を整数に変換
                            int taskNumber = Integer.parseInt(parts[1]);
                            removeTask(taskNumber);
                        } catch (NumberFormatException e) {
                            // 数値に変換できない場合はエラー
                            displayInvalidTaskNumberError();
                        }
                    }
                    break;
                case "exit":
                    running = false;
                    System.out.println("Todoリストを終了します。");
                    break;
                default:
                    displayUnknownCommandError(); // 不明なコマンドの場合のエラー
                    break;
            }
        }
        scanner.close(); // アプリケーション終了時にScannerを閉じる
    }

    /**
     * 新しいタスクをリストに追加します。
     *
     * @param taskName 追加するタスクの名前
     */
    private void addTask(String taskName) {
        tasks.add(taskName);
        System.out.println("タスクを追加しました: " + taskName);
    }

    /**
     * 現在登録されている全てのタスクを番号付きで表示します。
     */
    private void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("タスクはまだありません。");
        } else {
            System.out.println("--- タスク一覧 ---");
            for (int i = 0; i < tasks.size(); i++) {
                // ユーザーには1始まりの番号で表示
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
            System.out.println("-----------------");
        }
    }

    /**
     * 指定された番号のタスクをリストから削除します。
     *
     * @param taskNumber 削除するタスクの番号（1始まり）
     */
    private void removeTask(int taskNumber) {
        // ユーザー入力は1始まりなので、リストのインデックスに合わせるために-1する
        int index = taskNumber - 1;
        if (index >= 0 && index < tasks.size()) {
            String removedTask = tasks.remove(index);
            System.out.println("タスクを削除しました: " + removedTask);
        } else {
            // 範囲外の番号が指定された場合はエラー
            displayTaskNotFoundError();
        }
    }

    /**
     * 不明なコマンドが入力された場合のエラーメッセージを表示します。
     */
    private void displayUnknownCommandError() {
        System.out.println("不明なコマンドです。'add [タスク名]', 'list', 'remove [タスク番号]', 'exit' のいずれかを入力してください。");
    }

    /**
     * `add` コマンドでタスク名が指定されていない場合のエラーメッセージを表示します。
     */
    private void displayAddTaskNameMissingError() {
        System.out.println("タスク名が指定されていません。『add [タスク名]』の形式で入力してください。");
    }

    /**
     * `remove` コマンドでタスク番号が指定されていない、または数値でない場合のエラーメッセージを表示します。
     */
    private void displayInvalidTaskNumberError() {
        System.out.println("無効なタスク番号です。『remove [タスク番号]』の形式で有効な番号を入力してください。");
    }

    /**
     * `remove` コマンドで存在しないタスク番号が指定された場合のエラーメッセージを表示します。
     */
    private void displayTaskNotFoundError() {
        System.out.println("指定されたタスク番号は存在しません。");
    }

    /**
     * アプリケーションのエントリーポイントです。
     *
     * @param args コマンドライン引数 (今回は使用しません)
     */
    public static void main(String[] args) {
        TodoApp app = new TodoApp();
        app.run();
    }
}