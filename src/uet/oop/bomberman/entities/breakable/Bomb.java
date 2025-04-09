/******************************************************************************
 *
 *  Dependency: BreakableEntity.java
 *
 *  The data type for the Bomb.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.entities.BreakableEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.util.entityUtil.Layer;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends BreakableEntity {
    private Sprite sprite;
    private boolean waiting;
    private boolean justLay;

    /**
     * Khởi tạo đối tượng sử dụng phương thức khởi tạo của lớp cha BreakableEntity.
     * Gán trạng thái đang đợi cho đối tượng.
     * Cài đặt Timer, hủy trạng thái đang đợi sau 2s.
     */
    public Bomb(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.bomb.getFxImage());

        sprite = Sprite.bomb;
        waiting = true;
        justLay = true;
        isBroken = false;
        Timer timer = new Timer();
        // Hủy trạng thái đang đợi
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            waiting = false;
            explode();
            }
        }, 2000);
    }

    /**
     * Bomb nổ, phá hủy những đối tượng Brick xung quanh nó.
     */
    private void explode() {
        if (isExploding || isBroken) {
            return;
        }
        breakEntity();
        MediaPlayer explosionSound = Sound.cloneOf(Sound.explosionSound);
        explosionSound.play();
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                explosionSound.stop();
            }
        }, (int) explosionSound.getStopTime().toMillis());

        int xUnit = (int) this.x / Sprite.SCALED_SIZE;
        int yUnit = (int) this.y / Sprite.SCALED_SIZE;

        int[] addX = {-1, 1, 0, 0};
        int[] addY = {0, 0, -1, 1};
        int range = Bomber.getBombRange();
        for (int i = 0; i < addX.length; ++i) {
            for (int k = 1; k <= range; ++k) {
                int curX = xUnit + addX[i] * k;
                int curY = yUnit + addY[i] * k;
                Wall temp = new Wall(curX, curY);
                Layer entity = board.getLayerCollideWith(temp);
                if (entity != null) {
                    if (entity.stack.peek().isWall()) {
                        break;
                    }
                    if (entity.stack.peek().isBreakable()) {
                        if (entity.stack.peek().isItem() || entity.stack.peek().isPortal()){
                            // board.flames.add(new Flame(curX, curY, i, true));
                            board.spawnEnemies(entity.getX() / Sprite.SCALED_SIZE, entity.getY() / Sprite.SCALED_SIZE);
                            if (entity.stack.peek().isPortal()) {
                                board.decreaseExits();
                            }
                        }
                        entity.destroyTop();
                        break;
                    }
                }
                board.flames.add(new Flame(curX, curY, i, k == range));
            }
        }
    }

    public boolean isJustLay() {
        return justLay;
    }

    public void setJustLay(boolean justLay) {
        this.justLay = justLay;
    }

    @Override
    public void update() {
        if (isBroken) {
            board.bombs.remove(this);
            return;
        }

        for (int i = board.flames.size() - 1; i >= 0; --i) {
            if(board.collide(board.flames.get(i), this)) {
                this.explode();
                break;
            }
        }

        animate();
        if (isExploding) {
            sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 35);
        } else if (waiting) {
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 30);
        }
        this.img = sprite.getFxImage();
    }

    /**
     * Ghi đè phương thức render(), render thêm đối tượng Flame khi Bomb đang nổ.
     *
     * @param gc GraphicsContext
     */
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
