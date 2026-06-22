import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TodoListApp {
    // TODO項目を格納するリスト
    private ArrayList<String> tasks;
    // ユーザー入力を受け取るためのScanner
    private Scanner scanner;

    /**
     * TodoListAppのコンストラクタ。
     * リストとScannerのインスタンスを初期化します。
     */
    public TodoListApp() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * アプリケーションのエントリーポイント。
     * TodoListAppのインスタンスを作成し、実行を開始します。
     */
    public static void main(String[] args) {
        TodoListApp app = new TodoListApp();
        app.run();
    }

    /**
     * アプリケーションのメインループを実行します。
     * メニューを表示し、ユーザーの選択に応じて各機能（追加、表示、削除、終了）を呼び出します。
     */
    public void run() {
        int choice;
        do {
            displayMenu(); // メニューを表示
            choice = getUserMenuChoice(); // ユーザーの選択を取得（エラー処理を含む）

            switch (choice) {
                case 1:
                    addTask(); // TODO項目を追加
                    break;
                case 2:
                    displayTasks(); // TODO項目を表示
                    break;
                case 3:
                    deleteTask(); // TODO項目を削除
                    break;
                case 4:
                    System.out.println("TODOリストアプリを終了します。"); // アプリケーションを終了
                    break;
                // getUserMenuChoice()で不正な入力は処理されるため、defaultには到達しない想定
            }
            System.out.println(); // 各処理の後に空行を挿入して見やすくする
        } while (choice != 4); // ユーザーが「4: 終了」を選択するまでループを続ける

        scanner.close(); // アプリケーション終了時にScannerをクローズ
    }

    /**
     * TODOリストのメニューを表示します。
     */
    private void displayMenu() {
        System.out.println("--- TODOリストメニュー ---");
        System.out.println("1: 追加");
        System.out.println("2: 表示");
        System.out.println("3: 削除");
        System.out.println("4: 終了");
        System.out.print("選択してください: ");
    }

    /**
     * ユーザーからのメニュー選択を受け取り、入力の検証を行います。
     * 不正な入力（数値以外、範囲外の数値）があった場合はエラーメッセージを表示し、再入力を促します。
     *
     * @return ユーザーが選択した有効なメニュー番号
     */
    private int getUserMenuChoice() {
        int choice = -1;
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                choice = scanner.nextInt(); // 数値として入力を読み込む
                if (choice >= 1 && choice <= 4) { // 選択肢が1〜4の範囲内かチェック
                    isValidInput = true; // 有効な入力であればループを終了
                } else {
                    System.out.println("不正な入力です。1〜4の数値を入力してください。");
                    System.out.print("選択してください: ");
                }
            } catch (InputMismatchException e) {
                // 数値以外の入力があった場合
                System.out.println("不正な入力です。1〜4の数値を入力してください。");
                scanner.next(); // 不正な入力を消費して、Scannerのバッファをクリア
                System.out.print("選択してください: ");
            }
        }
        scanner.nextLine(); // nextInt()の後に残る改行文字を消費
        return choice;
    }

    /**
     * 新しいTODO項目をリストに追加します。
     */
    private void addTask() {
        System.out.print("追加するTODO項目を入力してください: ");
        String task = scanner.nextLine(); // ユーザーからのTODO項目内容を読み込む
        tasks.add(task); // リストに追加
        System.out.println("「" + task + "」を追加しました。");
    }

    /**
     * 現在のTODO項目を番号付きで全て表示します。
     * リストが空の場合はその旨を表示します。
     */
    private void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("TODOリストは空です。");
        } else {
            System.out.println("--- TODOリスト ---");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ": " + tasks.get(i)); // 1から始まる番号で表示
            }
        }
    }

    /**
     * 指定された番号のTODO項目をリストから削除します。
     * リストが空の場合、不正な番号が指定された場合、数値以外の文字が入力された場合は
     * エラーメッセージを表示し、再入力を促します。
     */
    private void deleteTask() {
        if (tasks.isEmpty()) {
            // リストが空の場合の削除試行
            System.out.println("TODOリストは空です。削除できる項目はありません。");
            return;
        }

        displayTasks(); // 削除対象をユーザーに示すために現在のリストを表示
        System.out.print("削除するTODO項目の番号を入力してください: ");

        int taskNumber = -1;
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                taskNumber = scanner.nextInt(); // 数値として入力を読み込む
                // 入力された番号が有効な範囲内（1からリストのサイズまで）かチェック
                if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                    isValidInput = true; // 有効な入力であればループを終了
                } else {
                    // 範囲外の番号の場合
                    System.out.println("不正な番号です。1〜" + tasks.size() + "の数値を入力してください。");
                    System.out.print("削除するTODO項目の番号を入力してください: ");
                }
            } catch (InputMismatchException e) {
                // 数値以外の入力があった場合
                System.out.println("不正な番号です。1〜" + tasks.size() + "の数値を入力してください。");
                scanner.next(); // 不正な入力を消費して、Scannerのバッファをクリア
                System.out.print("削除するTODO項目の番号を入力してください: ");
            }
        }
        scanner.nextLine(); // nextInt()の後に残る改行文字を消費

        // リストは0から始まるインデックスなので、ユーザー入力の番号から1を引く
        String removedTask = tasks.remove(taskNumber - 1);
        System.out.println("「" + removedTask + "」を削除しました。");
    }
}