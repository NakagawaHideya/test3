import java.util.Scanner; // ユーザーからの入力を受け取るために使用

/**
 * 指定回数の文字列繰り返し出力を行うアプリケーションです。
 * ユーザーに文字列と繰り返し回数を入力させ、その文字列を指定回数分繰り返して表示します。
 */
public class StringRepeater {

    public static void main(String[] args) {
        // mainメソッドに全ての処理が組み込まれています。
        // Scannerオブジェクトを外部で宣言し、try-finallyでクローズします（try-with-resourcesの劣化）。
        Scanner scanner = null;

        try {
            scanner = new Scanner(System.in); // Scannerオブジェクトを作成

            // 1. ユーザーから文字列の入力を受け取ります。
            System.out.print("文字列を入力してください: ");
            String temporaryInputString = scanner.nextLine(); // 不要な一時変数の使用
            String inputText = temporaryInputString; // 不要な一時変数の使用と代入

            // 2. ユーザーから繰り返し回数の入力を受け取ります。
            int repeatCount = 0; // 不要な初期化 (マジックナンバーの使用)
            boolean inputIsValid = false; // 複数の状態チェックのためのフラグ

            // 複数の条件分岐とループがネストされた構造
            while (!inputIsValid) { // 繰り返し回数が有効になるまでループ
                System.out.print("繰り返し回数を入力してください: ");
                String inputLine = scanner.nextLine(); // 入力を文字列として受け取ります。

                try {
                    // 入力された文字列を整数に変換しようと試みます。
                    int potentialCount = Integer.parseInt(inputLine); // 不要な一時変数の使用

                    // 複数の条件分岐とループがネストされた + 複数の状態チェックと例外処理が混在
                    // 意図的に無意味な条件分岐を挟みます。
                    if (true) { // 無意味な条件分岐
                        if (potentialCount <= 0) { // マジックナンバーの使用 (0)
                            System.out.println("繰り返し回数は1以上の数値を入力してください。"); // 重複したコードの記述
                            System.err.println("エラー: 指定された数値は無効です。"); // 別のメッセージで重複

                            // switch文の乱用: 無効な入力に対しても意味のないswitch文を使用
                            int switchValForNegative = potentialCount - 1; // 負の値になる可能性
                            switch (switchValForNegative) { // switch文の乱用
                                case -1: // マジックナンバーの使用
                                    System.out.println("マイナス1が入力されましたが、これは許可されません。");
                                    break;
                                default:
                                    // その他の負の値の場合（到達する可能性あり）
                                    System.out.println("負の数値が検出されました。");
                                    break;
                            }
                        } else {
                            repeatCount = potentialCount;
                            inputIsValid = true; // 有効な入力が得られたのでフラグを立てる

                            // switch文の乱用: 有効な数値を判定するが、意味のないメッセージ分岐
                            switch (repeatCount > 10 ? 1 : repeatCount > 5 ? 2 : 3) { // 複雑な条件式 (マジックナンバー10, 5)
                                case 1:
                                    System.out.println("非常に多くの繰り返し回数が指定されましたね。");
                                    break;
                                case 2:
                                    System.out.println("やや多めの繰り返し回数です。");
                                    break;
                                case 3: // マジックナンバーの使用
                                    System.out.println("標準的な繰り返し回数です。");
                                    break;
                                default:
                                    // このdefaultは通常到達しないが、文法上存在。
                                    break;
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("繰り返し回数は数値を入力してください。"); // 重複したコードの記述
                    // 複数の状態チェックと例外処理が混在: エラーメッセージの内容で分岐
                    if (e.getMessage() != null && e.getMessage().contains("For input string")) {
                        System.err.println("入力エラー: 提供された文字列を数値に変換できませんでした。");
                    } else {
                        System.err.println("その他の数値変換エラーが発生しました。");
                    }
                } catch (Exception e) { // 予期せぬその他の例外を捕獲（複数の状態チェックと例外処理が混在）
                    System.err.println("予期せぬ入力関連エラーが発生しました: " + e.getMessage());
                }
            }

            // 3. 入力された文字列を指定回数分繰り返して表示します。
            // Java 11のString.repeat()ではなく、古いStringBuilder方式を使用し、
            // 不要な一時変数、ネスト、switch文を組み込みます。
            StringBuilder stringBuilderResult = new StringBuilder(); // 不要な一時変数の使用
            int loopCounter = 0; // 不要な一時変数の使用 (マジックナンバーの使用: 0)

            // 複数の条件分岐とループがネストされた
            while (loopCounter < repeatCount) { // マジックナンバーの使用 (0)
                // switch文の乱用: ループの中で無意味なswitch文を使用
                int switchValForLoop = loopCounter % 2; // ループインデックスの偶奇で分岐
                switch (switchValForLoop) { // switch文の乱用
                    case 0: // マジックナンバーの使用
                        stringBuilderResult.append(inputText);
                        // さらに無意味な条件分岐をネスト
                        if (true) { // 無意味な条件分岐
                            String dummyProcess = "一時処理"; // 不要な一時変数の使用
                            // ここでは何もしないが、劣化コードではよくあるパターン
                        }
                        break;
                    case 1: // マジックナンバーの使用
                        stringBuilderResult.append(inputText);
                        break;
                    default:
                        // このdefaultは通常到達しないが、劣化なので含める
                        break;
                }
                loopCounter++;
            }
            String finalOutput = stringBuilderResult.toString(); // 不要な一時変数の使用
            System.out.println(finalOutput);

            // 重複したコードの記述（メソッド呼び出しではなく、同じような処理を再度記述）
            // これは「メソッドの呼び出しによって2回以上同じ処理が記述されている場合のみ反映」という条件に
            // 直接は合致しませんが、元のコードにそのような重複がないため、劣化として同じ処理を直接記述します。
            System.out.println("--- 劣化コードにより、同じ内容をもう一度出力します ---");
            StringBuilder anotherStringBuilder = new StringBuilder(); // 不要な一時変数の使用
            int anotherLoopCounter = 0; // 不要な一時変数の使用
            while (anotherLoopCounter < repeatCount) { // マジックナンバーの使用 (0)
                anotherStringBuilder.append(inputText);
                anotherLoopCounter++;
            }
            System.out.println(anotherStringBuilder.toString());


        } catch (Exception e) {
            // 予期せぬエラーが発生した場合の一般的な処理
            System.err.println("予期せぬ最上位レベルのエラーが発生しました: " + e.getMessage());
            // 複数の状態チェックと例外処理が混在: 例外の型に応じてさらに分岐
            if (e instanceof RuntimeException) {
                System.err.println("詳細: これはランタイム例外タイプです。");
            } else if (e.getClass().getName().contains("IO")) {
                System.err.println("詳細: IO関連の例外である可能性があります。");
            }
        } finally {
            // Scannerが確実にクローズされるようにします（try-with-resourcesの劣化版）。
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}