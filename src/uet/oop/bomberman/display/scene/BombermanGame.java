package uet.oop.bomberman.display.scene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.oop.bomberman.Main;
import uet.oop.bomberman.display.DisplayScene;
import uet.oop.bomberman.display.scene.menu.LeaderBoard;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.util.gameUtil.Board;
import uet.oop.bomberman.util.gameUtil.Controller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class BombermanGame: chạy scene game Bomberman, BombermanGame:
 * - Quản lý toàn bộ gameplay
 * - Xử lý input từ người chơi
 * - Cập nhaajt render game
 * - Quản lý âm thanh
 * - Xử lý các sự kiện game
 * Class BombermanGame tích hợp với các thành phần class khác như:
 * - Board: Quản lý map và entities
 * - Controller: Xử lý input
 * - Sound: Quản lý âm thanh
 * - LeaderBoard: QUản lý điểm cao
 * - Entity: Quản lý các đối tượng game
 *
 * (A) Luồng xử lý chính: (1) Khởi tọa game -> (2) Load level -> (3) Cập nhaajt trạng thái game
 * -> (4) Render game -> (5) X lý input -> (6) Kiểm tra điều kiện thắng thua -> (7) Chuyển level.
 * (B) Xử lý trường hợp đặc biet: Thua game - giảm mạng và load lại level, Qua level - tăng mạng và load level mới
 * Thắng game - kiểm tra high score, Gameover - kết thúc màn hình
 */
public class BombermanGame extends DisplayScene {
    private GraphicsContext gc; //Context để vẽ lên canvas
    private Canvas canvas; //bề mặt của game
    private int level = 0; //level hiện tại
    public static int score = 0; // điểm số của người chơi (static: cho phép truy cập ở nhiều nơi)
    public static int lives = 0; //Số mạng còn lại

    //Load font từ /font/RetroGaming.ttf
    public static final Font INFOFONT = Font.loadFont(BombermanGame.class.getResource("/font/RetroGaming.ttf").toString(), 30);

    //Controller xử lý input từ người chơi
    private Controller controller = new Controller();

    /**
     * Contructor BombermanGame:
     * Khởi tạo canvas với kích thước từ Main -> thiết lập font chữ ->...
     */
    public BombermanGame() {
        // Tao Canvas
        canvas = new Canvas(Main.initialSceneWidth, Main.initialSceneHeight);
        gc = canvas.getGraphicsContext2D();
        gc.setFont(INFOFONT);

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root, Main.initialSceneWidth, Main.initialSceneHeight);

        //tao input event handler cho bàn phím.
        scene.setOnKeyPressed(controller::listen);
        scene.setOnKeyReleased(controller::release);

        this.BGM = Sound.MainBGM; //gán nhạc nền
    }

    @Override
    public void reset() { //Reset game về trạng thái ban đầu
        level = -1; //load level đầu tiên
        score = 0; //khởi tạo lại cho 0 điểm và 2 mạng
        lives = 2;
        Bomber.reset();
        loadNextLevel();
    }

    /**
     * loadNextLevel: Tăng level và load level mới:
     */
    private void loadNextLevel() {
        controller = new Controller(); //Tạo controller mới cho level
        ++level;
        if (level <= 20) { //Kiểm tra điều kiện thắng game -> tạo board mới cho level
            Main.setPlayingStatus(3, "STAGE " + level);
            Entity.board = new Board(level);
        } else {
            Main.setPlayingStatus(9, "winning");
        }
    }


    @Override
    public void update() { //cập nhật các trạng thái của game
        if (Sound.stageClearBgm.getStatus().equals(MediaPlayer.Status.PLAYING)) { //kiểm tra âm thanh stage clear
            //StopBGM();
            return;
        }
        super.reset();
        Entity.board.update();
        if (Entity.board.endGame) { //xử lý trạng thái end game
            stopBGM();
            --lives;
            if (lives > 0) {
                --level;
                loadNextLevel();
                return;
            }
            if (LeaderBoard.checkHighScore(score)) {
                // System.out.println("New high score!");
                Main.setPlayingStatus(6, "new high score");
            } else Main.setPlayingStatus(4, "game over");
        }
        if (Entity.board.nextLevel) { //xử lý trạng thái next level
            ++lives;
            stopBGM();
            Sound.stageClearBgm.play();
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    Sound.stageClearBgm.stop();
                    loadNextLevel();
                }
            }, 2500);
        }
    }

    @Override
    public void render() { // Vẽ game lên canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //Xóa canvas cũ

        gc.setFill(Color.GRAY); //Vẽ nền
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Entity.board.render(gc); //Render board game

        gc.setFill(Color.WHITE); //vẽ lại thông tin cho level, lives, score
        gc.fillText("STAGE " + level, 50, 35);
        gc.fillText("LEFT: " + lives, 750, 35);
        gc.fillText(String.valueOf(score), 450, 35);
    }

    // Các phương thức quản lý điểm: thêm điểm và lấy điểm hiện tại
    public static void addScore(int value) {
        score += value;
    }

    public static int getScore() {
        return score;
    }
}

