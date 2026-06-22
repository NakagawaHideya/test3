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

    /**
     * ユーザーからじゃんけんの手の入力を受け取ります。
     * 「グー」「チョキ」「パー」のいずれかを入力するまで再入力を促します。
     * @param scanner 標準入力のスキャナ
     * @return ユーザーが選択したJankenHand
     */
    private static JankenHand getPlayerHand(Scanner scanner) {
        JankenHand playerHand = null;
        while (playerHand == null) { // 有効な入力が得られるまでループ
            System.out.print("「グー」「チョキ」「パー」のいずれかを入力してください: ");
            String input = scanner.nextLine(); // ユーザーの入力行を読み込む
            playerHand = JankenHand.fromString(input); // 入力をJankenHandに変換を試みる

            if (playerHand == null) {
                // 無効な入力の場合、エラーメッセージを表示し、再入力を促す
                System.out.println("エラー: 「グー」「チョキ」「パー」のいずれかを入力してください。");
            }
        }
        return playerHand;
    }

    /**
     * コンピュータのじゃんけんの手をランダムに生成します。
     * @return コンピュータが選択したJankenHand
     */
    private static JankenHand getComputerHand() {
        Random random = new Random();
        JankenHand[] hands = JankenHand.values(); // 全てのじゃんけんの手を取得
        // ランダムなインデックスを使って手を選択
        return hands[random.nextInt(hands.length)];
    }

    /**
     * ユーザーとコンピュータの手を比較し、勝敗を判定します。
     * @param playerHand ユーザーの手
     * @param computerHand コンピュータの手
     * @return 勝敗の結果 (GameResult.WIN, GameResult.LOSE, GameResult.DRAW)
     */
    private static GameResult determineWinner(JankenHand playerHand, JankenHand computerHand) {
        // 同じ手の場合は引き分け
        if (playerHand == computerHand) {
            return GameResult.DRAW;
        }

        // 異なる手の場合の勝敗判定
        switch (playerHand) {
            case ROCK: // ユーザーがグーの場合
                // コンピュータがチョキなら勝ち、パーなら負け
                return (computerHand == JankenHand.SCISSORS) ? GameResult.WIN : GameResult.LOSE;
            case SCISSORS: // ユーザーがチョキの場合
                // コンピュータがパーなら勝ち、グーなら負け
                return (computerHand == JankenHand.PAPER) ? GameResult.WIN : GameResult.LOSE;
            case PAPER: // ユーザーがパーの場合
                // コンピュータがグーなら勝ち、チョキなら負け
                return (computerHand == JankenHand.ROCK) ? GameResult.WIN : GameResult.LOSE;
            default:
                // 通常、JankenHandの全てのケースを網羅していればここに到達することはない
                // 不測の事態（Enumの定義変更など）に備えたエラーハンドリング
                throw new IllegalStateException("予期せぬJankenHand: " + playerHand);
        }
    }

    /**
     * ゲームの結果を標準出力に表示します。
     * @param playerHand ユーザーの手
     * @param computerHand コンピュータの手
     * @param result 勝敗の結果
     */
    private static void displayResults(JankenHand playerHand, JankenHand computerHand, GameResult result) {
        System.out.println("あなたの手: " + playerHand.getDisplayValue());
        System.out.println("コンピュータの手: " + computerHand.getDisplayValue());
        System.out.println("勝敗: " + result.getDisplayValue());
    }

    /**
     * じゃんけんゲームのメインメソッドです。
     * ゲームの全体的な流れを制御します。
     * @param args コマンドライン引数（使用しません）
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 標準入力用のScannerを初期化

        try {
            // 1. ユーザーからじゃんけんの手を受け取る
            JankenHand playerHand = getPlayerHand(scanner);

            // 2. コンピュータのじゃんけんの手をランダムに生成する
            JankenHand computerHand = getComputerHand();

            // 3. ユーザーの手とコンピュータの手を比較し、勝敗を判定する
            GameResult result = determineWinner(playerHand, computerHand);

            // 4. ゲームの結果を標準出力に表示する
            displayResults(playerHand, computerHand, result);

        } finally {
            // リソースリークを防ぐため、Scannerは必ずクローズする
            scanner.close();
        }
    }
}