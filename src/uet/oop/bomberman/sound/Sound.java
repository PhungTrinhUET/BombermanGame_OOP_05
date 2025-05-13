package uet.oop.bomberman.sound;

import com.sun.media.jfxmedia.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Abstract class Sound: dùng JavaFX Media API để phát âm thanh - Hoox trợ định dạng MP3 và WAV
 * Các âm thanh nền (BGM - Background Music):
 * - titleBGM: Nhạc nền màn hình chính
 * - stageStartBGM: Nhạc khi bắt đầu level mới
 * - gameOverBGM: Nhạc khi thua game
 * - stageClearBgm: Nhạc khi hoàn thành level
 * - MainBGM: Nhạc nền chính khi chơi game
 *
 * Các hiệu ứng âm thanh (SE - Sound Effects):
 * - explosionSound: Âm thanh nổ bom
 * - LayBombSound: Âm thanh đặt bom
 * - powerUpSound: Âm thanh nhận power-up
 * - bomberisKilledSound: Âm thanh khi Bomberman chết
 * - allEnemiesIsKilledSound: Âm thanh khi tiêu diệt hết kẻ thù
 * - endingSE: Âm thanh kết thúc game
 */
public abstract class Sound {
    //BGM - Background Music
    public static final MediaPlayer TitleBGM = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/TitleScreen.mp3").toString())
    );
    public static final MediaPlayer stageStartBGM = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/StageStart.mp3").toString())
    );
    public static final MediaPlayer gameOverBGM = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/GameOver.mp3").toString())
    );

    public static final MediaPlayer stageClearBgm = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/StageClear.mp3").toString())
    );

    public static final MediaPlayer MainBGM = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/MainBGM.mp3").toString())
    );


    //SE - Sound Effects
    public static final MediaPlayer explosionSound = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/ExplosionSE.mp3").toString())
    );

    public static final MediaPlayer LayBombSound = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/LayBombSE.wav").toString())
    );

    public static final MediaPlayer powerUpSound = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/PowerUpSE.wav").toString())
    );

    public static final MediaPlayer bomberisKilledSound = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/BomberKilledSE.wav").toString())
    );

    public static final MediaPlayer allEnemiesIsKilledSound = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/KillAllEnemiesSE.wav").toString())
    );

    public static final MediaPlayer endingSE = new MediaPlayer(
            new Media(Sound.class.getResource("/audio/Ending.mp3").toString())
    );

    /**
     * Phương thức cloneOf:
     * - Tạo một bản sao của MediaPlayer
     * - Dùng để phát nhiều âm thanh cùng lúc
     * - Tránh xung đột khi phát âm thanh
     */
    public static MediaPlayer cloneOf(MediaPlayer that) {
        return new MediaPlayer(that.getMedia());
    }
}

/* Cấu trúc thư mục âm thanh
/audio/
    TitleScreen.mp3
    StageStart.mp3
    GameOver.mp3
    StageClear.mp3
    MainBGM.mp3
    ExplosionSE.mp3
    LayBombSE.wav
    PowerUpSE.wav
    BomberKilledSE.wav
    KillAllEnemiesSE.wav
    Ending.mp3
*/
