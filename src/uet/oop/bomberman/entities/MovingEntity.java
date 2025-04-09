package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.Timer;
import java.util.TimerTask;

/**
 * MovingEntity Class By PhungTrinh.
 * MovingEntity ke thua entity, co them:
 * Toc do di chuyen (speed)
 * Co trang thai: dang di chuyen?, da chet?
 * Phuong thuc ho tro: chet - kill, delay chet - Timer, hieu ung di chuyen - animate.
 */
public abstract class MovingEntity extends Entity {
    protected int animate = 0;
    protected final int animate_MAX_VALUE = 7500;

    protected double speed = 3.0;

    //Dinh nghia cac huong (hang so) dai dien cho huong di chuyen.
    public static final int directionNone = 0;
    public static final int directionUp = 1;
    public static final int directionRight = 2;
    public static final int directionDown = 3;
    public static final int directionLeft = 4;

    //Trang thai di chuyen
    protected boolean isMoving; // dang di chuyen.
    protected boolean isDead; //da chet hay chua?
    protected boolean isKilled; // da bi ket lieu/ dinh don.

    /**
     * Constructor MovingEntity.
     * @param xUnit la toa do theo tile map.
     * @param yUnit la toa do theo tile map.
     */
    public MovingEntity(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    public MovingEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    //Get set cho speed.
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Khi trung bomb/ke thu -> isKilled = true.
     * Sau 750ms -> isDead = true.
     * Dung Timer -> thuc thi isDead = true sau 750ms.
     */
    public void kill() {
        if (isKilled || isDead) return;
        isKilled = true;

        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                isDead = true;
            }
        }, 750L);
    }

    //Kiem tra bi chet/ bi giet.
    public boolean isDead() {
        return isDead; //chet hoan toan.
    }

    public boolean isKilled() {
        return isKilled; //bi ket lieu.
    }

    /**
     * ham animate - tang bien animate moi frame
     * hoac moi lan update().
     * Khi cham animate_MAX_VALUE -> reset ve 0.
     */
    protected void animate() {
        if (animate < animate_MAX_VALUE) {
            animate++;
        } else animate = 0;
    }
}
