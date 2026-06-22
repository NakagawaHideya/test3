import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TodoListManager {

    // ToDo項目を保持するためのリスト
    private static List<String> todoList = new ArrayList<>();
    // ユーザーからの入力を受け付けるためのScanner
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("ToDoリスト管理アプリケーションへようこそ！");

        // アプリケーションのメインループ
        while (true) {
            displayMenu(); // メニューを表示
            int choice = getUserMenuChoice(); // ユーザーの選択を取得

            // ユーザーの選択に基づいて処理を分岐
            switch (choice) {
                case 1:
                    addTodoItem(); // ToDo項目を追加
                    break;
                case 2:
                    listTodoItems(); // ToDo項目を一覧表示
                    break;
                case 3:
                    completeTodoItem(); // ToDo項目を完了（削除）
                    break;
                case 4:
                    // アプリケーションの終了
                    System.out.println("アプリケーションを終了します。");
                    scanner.close(); // Scannerをクローズしてリソースを解放
                    return; // mainメソッドを終了し、プログラムを終了
                default:
                    // getUserMenuChoice() メソッド内で既にエラーメッセージが表示されるため、
                    // ここでは追加のメッセージは不要ですが、無効な選択であったことを示すために
                    // 何も表示しないか、または再度のプロンプトで対応します。
                    // 今回はgetUserMenuChoice()でメッセージ出力済のため、特に追加処理はしません。
                    break;
            }
            System.out.println(); // 各操作後に見やすくするための改行
        }
    }

    /**
     * メニューオプションをコンソールに表示します。
     */
    private static void displayMenu() {
        System.out.println("----- メニュー -----");
        System.out.println("1. ToDoを追加");
        System.out.println("2. ToDoを一覧表示");
        System.out.println("3. ToDoを完了（削除）");
        System.out.println("4. 終了");
    }

    /**
     * ユーザーからのメニュー選択を受け付け、その値を返します。
     * 無効な入力（数値以外、範囲外の数値）の場合、エラーメッセージを表示し、-1を返します。
     *
     * @return ユーザーが選択したメニュー番号（1-4）、または無効な場合は-1
     */
    private static int getUserMenuChoice() {
        int choice = -1; // 無効な初期値
        System.out.print("選択してください: "); // 選択プロンプト
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            // 数値以外の入力があった場合のエラー処理
            System.out.println("無効な選択です。もう一度入力してください。");
            scanner.next(); // 不正な入力をクリア
            return -1; // 不正な選択として-1を返す
        } finally {
            // nextInt() の後に残る改行文字を消費
            scanner.nextLine();
        }

        // 範囲外の数値チェック
        if (choice < 1 || choice > 4) {
            System.out.println("無効な選択です。もう一度入力してください。");
            return -1; // 不正な選択として-1を返す
        }
        return choice;
    }

    /**
     * 新しいToDo項目をリストに追加します。
     * 空の文字列が入力された場合はエラーメッセージを表示します。
     */
    private static void addTodoItem() {
        System.out.print("追加するToDoを入力してください: ");
        String todo = scanner.nextLine();

        // ToDo項目追加時に空の文字列を入力した場合のチェック
        if (todo.trim().isEmpty()) { // 空文字列または空白のみの文字列をチェック
            System.out.println("ToDoは空にできません。");
        } else {
            todoList.add(todo.trim()); // ToDoリストに追加（前後の空白を削除）
            System.out.println("ToDoが追加されました: " + todo.trim());
        }
    }

    /**
     * 現在の全てのToDo項目を番号付きで一覧表示します。
     * リストが空の場合はその旨を表示します。
     */
    private static void listTodoItems() {
        if (todoList.isEmpty()) {
            System.out.println("ToDo項目はありません。");
            return;
        }

        System.out.println("----- 現在のToDo項目 -----");
        for (int i = 0; i < todoList.size(); i++) {
            System.out.println((i + 1) + ". " + todoList.get(i)); // 1から始まる番号で表示
        }
    }

    /**
     * 指定した番号のToDo項目をリストから完了（削除）します。
     * 存在しない番号が入力された場合、または無効な入力があった場合はエラーメッセージを表示します。
     */
    private static void completeTodoItem() {
        if (todoList.isEmpty()) {
            System.out.println("ToDo項目はありません。削除できません。");
            return;
        }

        listTodoItems(); // 現在のリストを表示して、ユーザーが選択しやすいようにする
        System.out.print("完了するToDoの番号を入力してください: ");

        int todoNumber = -1;
        try {
            todoNumber = scanner.nextInt();
        } catch (InputMismatchException e) {
            // 数値以外の入力があった場合のエラー処理
            System.out.println("無効な選択です。もう一度入力してください。");
            scanner.next(); // 不正な入力をクリア
            scanner.nextLine(); // 改行文字をクリア
            return; // 処理を中断
        } finally {
            // nextInt() の後に残る改行文字を消費
            scanner.nextLine();
        }

        // ToDo項目削除時に、存在しない番号を入力した場合のチェック
        // リストのインデックスは0から始まるため、todoNumber - 1 を使用
        if (todoNumber < 1 || todoNumber > todoList.size()) {
            System.out.println("指定されたToDoは存在しません。");
        } else {
            String removedTodo = todoList.remove(todoNumber - 1); // リストから項目を削除
            System.out.println("ToDoが完了しました: " + removedTodo);
        }
    }
}