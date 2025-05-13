package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class Sound {

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
}
