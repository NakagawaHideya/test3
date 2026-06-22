import java.util.Random;
import java.util.Scanner;

/**
 * じゃんけんの手を表すEnumです。
 * 表示用の文字列も保持します。
 */
enum JankenHand {
    ROCK("グー"),
    SCISSORS("チョキ"),
    PAPER("パー");

    private final String displayValue; // 表示用の文字列

    /**
     * コンストラクタ
     * @param displayValue 手の表示文字列
     */
    JankenHand(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * 手の表示文字列を取得します。
     * @return 表示文字列
     */
    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * 指定された文字列からJankenHandオブジェクトへの変換を試みます。
     * 一致する手がない場合はnullを返します。
     * @param text 変換する文字列（例: "グー"）
     * @return 変換されたJankenHand、またはnull（無効な入力の場合）
     */
    public static JankenHand fromString(String text) {
        for (JankenHand hand : JankenHand.values()) {
            if (hand.displayValue.equals(text)) {
                return hand;
            }
        }
        return null; // 有効な手が見つからなかった場合
    }
}

/**
 * じゃんけんの勝敗結果を表すEnumです。
 * 表示用の文字列も保持します。
 */
enum GameResult {
    WIN("勝ち"),
    LOSE("負け"),
    DRAW("引き分け");

    private final String displayValue; // 表示用の文字列

    /**
     * コンストラクタ
     * @param displayValue 勝敗の表示文字列
     */
    GameResult(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * 勝敗の表示文字列を取得します。
     * @return 表示文字列
     */
    public String getDisplayValue() {
        return displayValue;
    }
}

/**
 * じゃんけんゲームを実装するクラスです。
 * ユーザー入力の受付、コンピュータの手の生成、勝敗判定、結果表示の機能を提供します。
 */
public class JankenGame {

    // 元々あったヘルパーメソッドですが、mainメソッドに全ての処理が集約されたため、
    // ここでは呼び出されず、使われない状態になります。
    /*
    private static JankenHand getPlayerHand(Scanner scanner) {
        JankenHand playerHand = null;
        while (playerHand == null) {
            System.out.print("「グー」「チョキ」「パー」のいずれかを入力してください: ");
            String input = scanner.nextLine();
            playerHand = JankenHand.fromString(input);

            if (playerHand == null) {
                System.out.println("エラー: 「グー」「チョキ」「パー」のいずれかを入力してください。");
            }
        }
        return playerHand;
    }

    private static JankenHand getComputerHand() {
        Random random = new Random();
        JankenHand[] hands = JankenHand.values();
        return hands[random.nextInt(hands.length)];
    }

    private static GameResult determineWinner(JankenHand playerHand, JankenHand computerHand) {
        if (playerHand == computerHand) {
            return GameResult.DRAW;
        }

        switch (playerHand) {
            case ROCK:
                return (computerHand == JankenHand.SCISSORS) ? GameResult.WIN : GameResult.LOSE;
            case SCISSORS:
                return (computerHand == JankenHand.PAPER) ? GameResult.WIN : GameResult.LOSE;
            case PAPER:
                return (computerHand == JankenHand.ROCK) ? GameResult.WIN : GameResult.LOSE;
            default:
                throw new IllegalStateException("予期せぬJankenHand: " + playerHand);
        }
    }

    private static void displayResults(JankenHand playerHand, JankenHand computerHand, GameResult result) {
        System.out.println("あなたの手: " + playerHand.getDisplayValue());
        System.out.println("コンピュータの手: " + computerHand.getDisplayValue());
        System.out.println("勝敗: " + result.getDisplayValue());
    }
    */

    /**
     * じゃんけんゲームのメインメソッドです。
     * ゲームの全体的な流れを制御します。
     * 全ての処理がこのmainメソッド内に組み込まれています。
     * @param args コマンドライン引数（使用しません）
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 標準入力用のScannerを初期化

        try {
            // 1. ユーザーからじゃんけんの手を受け取る
            // 劣化ポイント: 複数の条件分岐とループがネストされた、複数の状態チェックが混在
            JankenHand playerHand = null;
            while (playerHand == null) { // 有効な入力が得られるまでループ
                System.out.print("「グー」「チョキ」「パー」のいずれかを入力してください: ");
                String input = scanner.nextLine(); // ユーザーの入力行を読み込む

                if (input == null || input.trim().isEmpty()) { // 不必要なnull/isEmptyチェック
                    System.out.println("エラー: 何も入力されていません。");
                } else {
                    playerHand = JankenHand.fromString(input); // 入力をJankenHandに変換を試みる

                    if (playerHand == null) {
                        // 無効な入力の場合、エラーメッセージを表示し、再入力を促す
                        System.out.println("エラー: 「グー」「チョキ」「パー」のいずれかを入力してください。");
                    }
                }
            }

            // 2. コンピュータのじゃんけんの手をランダムに生成する
            // 劣化ポイント: 不要な一時変数の使用、マジックナンバーの使用
            Random random = new Random();
            JankenHand[] allHands = JankenHand.values(); // 不要な一時変数 allHands
            int computerHandIndex = random.nextInt(3); // マジックナンバー (3) を使用 (JankenHand.values().length の代わり)
            JankenHand computerHand = allHands[computerHandIndex];

            // 3. ユーザーの手とコンピュータの手を比較し、勝敗を判定する
            // 劣化ポイント: switch文の乱用、複数の条件分岐とループがネストされた、重複したコードの記述
            GameResult result; // 不要な一時変数 (早期に宣言)
            if (playerHand == computerHand) {
                result = GameResult.DRAW;
            } else {
                switch (playerHand) { // playerHandに対するswitch文
                    case ROCK: // ユーザーがグーの場合
                        switch (computerHand) { // コンピュータの手に対するネストされたswitch文
                            case SCISSORS:
                                result = GameResult.WIN;
                                break;
                            case PAPER:
                                result = GameResult.LOSE;
                                break;
                            default: // 予期せぬ手のケース
                                throw new IllegalStateException("予期せぬコンピュータの手: " + computerHand); // 重複した例外処理
                        }
                        break;
                    case SCISSORS: // ユーザーがチョキの場合
                        switch (computerHand) { // コンピュータの手に対するネストされたswitch文
                            case PAPER:
                                result = GameResult.WIN;
                                break;
                            case ROCK:
                                result = GameResult.LOSE;
                                break;
                            default: // 予期せぬ手のケース
                                throw new IllegalStateException("予期せぬコンピュータの手: " + computerHand); // 重複した例外処理
                        }
                        break;
                    case PAPER: // ユーザーがパーの場合
                        switch (computerHand) { // コンピュータの手に対するネストされたswitch文
                            case ROCK:
                                result = GameResult.WIN;
                                break;
                            case SCISSORS:
                                result = GameResult.LOSE;
                                break;
                            default: // 予期せぬ手のケース
                                throw new IllegalStateException("予期せぬコンピュータの手: " + computerHand); // 重複した例外処理
                        }
                        break;
                    default:
                        // 通常、JankenHandの全てのケースを網羅していればここに到達することはない
                        // 不測の事態（Enumの定義変更など）に備えたエラーハンドリング
                        throw new IllegalStateException("予期せぬプレイヤーの手: " + playerHand); // 重複した例外処理
                }
            }

            // 4. ゲームの結果を標準出力に表示する
            // 劣化ポイント: 不要な一時変数の使用
            String playerHandDisplay = playerHand.getDisplayValue(); // 表示文字列を一時変数に格納
            System.out.println("あなたの手: " + playerHandDisplay);
            String computerHandDisplay = computerHand.getDisplayValue(); // 表示文字列を一時変数に格納
            System.out.println("コンピュータの手: " + computerHandDisplay);
            String resultDisplay = result.getDisplayValue(); // 表示文字列を一時変数に格納
            System.out.println("勝敗: " + resultDisplay);

        } finally {
            // リソースリークを防ぐため、Scannerは必ずクローズする
            scanner.close();
        }
    }
}