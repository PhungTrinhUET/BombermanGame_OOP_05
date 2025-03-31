/******************************************************************************
 *
 *  Dependency: Entity.java
 *
 *  The abstract data type for the Breakable Entity in general.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BreakableEntity extends Entity {
    // Biến boolean kiểm tra trạng thái của đối tượng
    public boolean isBroken; // true = đã bị phá hủy
    protected boolean isExploding; // true = đang nổ
    protected int animate = 0;
    protected final int animate_MAX_VALUE = 7500;

    /**
     * Khởi tạo đối tượng sử dụng phương thức khởi tạo của lớp cha Entity.
     * Gán trạng thái ban đầu chưa bị phá hủy, không đang nổ cho đối tượng.
     * Khởi tạo đối tượng nền Grass.
     */
    public BreakableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        isBroken = false;
        isExploding = false;
    }

    /**
     * Phương thức kiểm tra trạng thái của đối tượng.
     * @return trạng thái hiện tại của đối tượng, true = đã bị phá hủy, false = chưa bị phá hủy
     */
    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public boolean isExploding() {
        return isExploding;
    }


    public void breakEntity() {
        isExploding = true;
        animate = 0;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isExploding = false;
                isBroken = true;
            }
        }, 500L);
    }


    @Override
    public void render(GraphicsContext gc) {
        if (!isBroken) {
            super.render(gc);
        }
    }

    protected void animate() {
        if (animate < animate_MAX_VALUE) {
            animate++;
        } else animate = 0;
    }

    @Override
    public void update() {

    }
}

