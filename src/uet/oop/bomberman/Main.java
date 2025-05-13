package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import uet.oop.bomberman.display.DisplayScene;
import uet.oop.bomberman.display.scene.BombermanGame;
import uet.oop.bomberman.display.scene.FixedScene;
import uet.oop.bomberman.display.scene.menu.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Class Main extends Application, class Main được thiết kế sd JavaFX xây dựng game:
 * - Quản lý trạng thái game (10 trạng thái)
 * - Xử lý scene cho từng trạng thái - kèm âm thanh và hiệu ứng cho: GameOver, Win, Pause...
 * - Xử lý close game
 */
public class Main extends Application {

    // Kích thước 20 x 14
    public static final int WIDTH = 20;
    public static final int HEIGHT = 14;

    //Kích thước thực tế của scene game (tính dựa trên kích thước sprite)
    public static final double initialSceneWidth = WIDTH * Sprite.SCALED_SIZE;
    public static final double initialSceneHeight = HEIGHT * Sprite.SCALED_SIZE;

    public static int status;
    /**
     * Thuộc tính lưu trạng thái hiện tại của game.
     * status = 0: Home scene
     * status = 1: Playing
     * status = 2: Pause scene
     * status = 3: Level name
     * status = 4: Game over
     * status = 5: Leaderboard
     * status = 6: New high score
     * status = 7: Instruction
     * status = 8: About
     * status = 9: Winning
     */

    public static DisplayScene[] scenes = new DisplayScene[10]; //Mảng lưu trữ các scene của game - mỗi scene là 1 trạng thái.

    //Font chữ được add từ file /font/font.ttf và size chữ 30
    public static final Font FONT = Font.loadFont(Main.class.getResource("/font/font.ttf").toString(), 30);

    // Khởi chạy ứng dụng -> gọi phương thức launch của JavaFX
    public static void main(String[] args) {
        launch(Main.class);
    }

    /**
     * Phương thức start:
     * @param stage the primary stage for this application
     * Khởi tạo cửa sổ game (cố định kích thước)
     * Khởi tạo các scene cho từng trạng thái game
     * Set trạng thái ban đầu auto là màn hình chính [0]
     * Không cho phép continue game mới
     */
    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        scenes[0] = new HomeScene();
        scenes[1] = new BombermanGame();
        scenes[2] = new PauseScene();
        scenes[3] = new FixedScene("Stage");
        scenes[4] = new FixedScene("GAME OVER");
        scenes[5] = new LeaderBoard();
        scenes[6] = new NewHighScore();
        scenes[7] = new Instruction();
        scenes[8] = new About();
        scenes[9] = new FixedScene("CONGRATULATIONS\n\n" +
                "YOU HAVE SUCCEEDED IN\nHELPING BOMBERMAN TO BECOME\nA HUMAN BEING\n\n" +
                "GOOD BYE");
        status = 0;
        ((HomeScene) scenes[0]).setCanContinue(false);

        // Animation timer: Tạo vòng lặp game chính và với mỗi frame thì:
        // Cập nhật trạng thái hiện tại -> Update trạng thái game -> Render lại màn hình
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                stage.setScene(chooseScene());
                update();
                render();
            }
        };
        timer.start();

        stage.show();

        //Xử lý khi đóng cửa sổ: -> thoát JavaFX Platform -> thoát chương trình
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Các phương thức quản lý scene: chooseScene, update, render
     * @return 3 thành phần
     * chooseScene: lấy scene tương ứng trạng thái game now
     * update: cập nhật trạng thái scene hiện tại
     * render: vẽ lại scene hiện tại
     */
    public Scene chooseScene() {
        return scenes[status].getScene();
    }

    public void update() {
        scenes[status].update();
    }

    public void render() {
        scenes[status].render();
    }

    /**
     * Phương thức setPlayingStatus:
     * @param newStatus trạng thái game
     * @param mes xử lý các sự kiện đặc biệt
     * Phương thức này -> chuyển đổi giữa các trạng thái game
     * Xử lý các sự kiện: Game over, chiến thắng, tạm dừng, chuyển level
     * -> Từ đó phát âm thanh tương ứng và Reset scene mới
     */
    public static void setPlayingStatus(int newStatus, String mes) {
        scenes[status].close();
        switch (newStatus) {
            case 0:
                if (mes.equals("return")) {
                    ((HomeScene) scenes[0]).setCanContinue(true);
                } else if (mes.equals("game over")) {
                    ((HomeScene) scenes[0]).setCanContinue(false);
                }
                break;
            case 1:
                if (mes.equals("continue")) {
                    status = newStatus;
                    return;
                }
                break;
            case 3: // mes == "STAGE " + level
                scenes[3] = new FixedScene(mes);
                Sound.stageStartBGM.play();
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        status = 1;
                        Sound.stageStartBGM.stop();
                    }
                }, 2600L);
                break;
            case 4: // mes = "game over"
                Sound.gameOverBGM.play();
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setPlayingStatus(0, "game over");
                        Sound.gameOverBGM.stop();
                    }
                }, 6000L);
                break;
            case 9: // wining
                Sound.endingSE.play();
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setPlayingStatus(0, "game over");
                        Sound.endingSE.stop();
                    }
                }, 12000L);
                break;
        }
        status = newStatus;
        scenes[status].reset();
    }
}