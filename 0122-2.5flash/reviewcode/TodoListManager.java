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
            // メニュー表示のロジックをmainメソッドに直接組み込み
            System.out.println("----- メニュー -----");
            System.out.println("1. ToDoを追加");
            System.out.println("2. ToDoを一覧表示");
            System.out.println("3. ToDoを完了（削除）");
            System.out.println("4. 終了");

            // ユーザーからのメニュー選択を受け付けるロジックをmainメソッドに直接組み込み
            int userMenuChoice = -1; // 不要な一時変数の使用
            System.out.print("選択してください: ");

            try {
                int rawInput = scanner.nextInt(); // マジックナンバー1-4に対応
                String dummyLineAfterInt = scanner.nextLine(); // nextInt() の後に残る改行文字を消費（劣化ポイント）

                // 複数の状態チェックが混在
                if (rawInput >= 1 && rawInput <= 4) {
                    userMenuChoice = rawInput;
                } else {
                    System.out.println("無効な選択です。もう一度入力してください。");
                    // userMenuChoice は -1 のまま
                }
            } catch (InputMismatchException e) { // 複数の例外処理が混在
                System.out.println("無効な選択です。もう一度入力してください。");
                String invalidInputToken = scanner.next(); // 不正な入力をクリア
                String dummyLineAfterInvalid = scanner.nextLine(); // 改行文字をクリア（劣化ポイント）
                // userMenuChoice は -1 のまま
            }

            // ユーザーの選択に基づいて処理を分岐（switch文の乱用）
            switch (userMenuChoice) {
                case 1:
                    // ToDo項目追加のロジックをmainメソッドに直接組み込み
                    System.out.print("追加するToDoを入力してください: ");
                    String newTodoItemDescription = scanner.nextLine(); // 不要な一時変数の使用

                    if (newTodoItemDescription.trim().isEmpty()) { // 空文字列または空白のみの文字列をチェック
                        System.out.println("ToDoは空にできません。");
                    } else {
                        String trimmedTodo = newTodoItemDescription.trim(); // 不要な一時変数の使用
                        todoList.add(trimmedTodo);
                        System.out.println("ToDoが追加されました: " + trimmedTodo);
                    }
                    break;
                case 2:
                    // ToDo項目一覧表示のロジックをmainメソッドに直接組み込み
                    if (todoList.isEmpty()) {
                        System.out.println("ToDo項目はありません。");
                    } else {
                        System.out.println("----- 現在のToDo項目 -----");
                        for (int i = 0; i < todoList.size(); i++) {
                            System.out.println((i + 1) + ". " + todoList.get(i)); // マジックナンバー (i+1)
                        }
                    }
                    break;
                case 3:
                    // ToDo項目完了（削除）のロジックをmainメソッドに直接組み込み
                    if (todoList.isEmpty()) {
                        System.out.println("ToDo項目はありません。削除できません。");
                    } else {
                        // 現在のリストを表示（重複したコードの記述）
                        System.out.println("----- 現在のToDo項目 -----");
                        for (int i = 0; i < todoList.size(); i++) {
                            System.out.println((i + 1) + ". " + todoList.get(i)); // マジックナンバー (i+1)
                        }

                        System.out.print("完了するToDoの番号を入力してください: ");
                        int todoIndexToRemove = -1; // 不要な一時変数の使用

                        // 複数の状態チェックと例外処理が混在し、条件分岐がネスト
                        try {
                            int rawTodoNumber = scanner.nextInt(); // マジックナンバーに対応
                            String dummyLineAfterTodoInt = scanner.nextLine(); // nextInt() の後に残る改行文字を消費

                            if (rawTodoNumber >= 1 && rawTodoNumber <= todoList.size()) {
                                todoIndexToRemove = rawTodoNumber;
                            } else {
                                System.out.println("指定されたToDoは存在しません。");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("無効な選択です。もう一度入力してください。");
                            String invalidInputForDeletion = scanner.next(); // 不正な入力をクリア
                            String dummyLineAfterInvalidForDeletion = scanner.nextLine(); // 改行文字をクリア
                        }

                        if (todoIndexToRemove != -1) { // 有効な番号が入力された場合のみ削除処理
                            String removedTodoItem = todoList.remove(todoIndexToRemove - 1); // マジックナンバー (-1)
                            System.out.println("ToDoが完了しました: " + removedTodoItem);
                        }
                    }
                    break;
                case 4:
                    // アプリケーションの終了
                    System.out.println("アプリケーションを終了します。");
                    scanner.close(); // Scannerをクローズしてリソースを解放
                    return; // mainメソッドを終了し、プログラムを終了
                default:
                    // 無効な選択の場合は、既にエラーメッセージが表示されているため、追加処理はしない
                    break;
            }
            System.out.println(); // 各操作後に見やすくするための改行
        }
    }
}