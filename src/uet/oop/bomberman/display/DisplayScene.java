package uet.oop.bomberman.display;

import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * abstract class DisplayScene: Quản lý scene và âm thanh bằng JavaFX
 * Là class cơ sở cho vieecj:
 * - Quản lý scene
 * - Cập nhật trạng thái
 * - Vẽ lại màn hình
 * - Xử lý âm thanh nền
 * Các class con kế thừa DisplayScene: About, HomeScene, BombermanGmae, PauseScene...thuộc menu
 * -> Các class con sẽ implement (thêm scene mới...)
 */
public abstract class DisplayScene {
    protected Scene scene; //scene chứa nội dung hiển thị

    protected MediaPlayer BGM; //BGM: Object MediaPlayer phát nhạc nền

    //Trar về Scene hiện tại
    public Scene getScene() {
        return this.scene;
    }

    public void update() { //update sau đó render (vẽ lại) scene -> để trống cho các class con override
    }
    public void render() {
    }

    public void reset() {
        startBGM(); //Khởi tạo lại scene thì bắt đầu phát nhạc nền
    }

    public void close() {
        stopBGM(); //Dừng phát nhạc nền khi close
    }

    /**
     * Quản lý âm thanh:
     */
    public void startBGM() {
        if (BGM != null) { //Kiểm tra nếu có phát nhạc nền -> chưa thì phát và tạo loop phát lại sau khi hết
            BGM.play();
            BGM.setOnEndOfMedia(() -> {
                BGM.seek(Duration.ZERO);
                BGM.play();
            });
        }
    }

    public void stopBGM() {
        if (BGM != null) { //Stop khi đang phát nhạc
            BGM.stop();
        }
    }
}
