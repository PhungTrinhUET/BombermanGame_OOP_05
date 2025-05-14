package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.gameUtil.StdRandom;

public class Doll extends Enemy {
    private final double initSpeed;
    private double accel;
    /**
     * Constructor enemy.
     *
     * @param x la toa do.
     * @param y la toa do.
     * Đặt point = 400 khi ăn Dool
     * Khởi tạo sprite cho các trạng thái như Ballom
     * super speed: Base cho Dool là 1.6 + random ừ (0-0.9)
     */
    public Doll(int x, int y) {
        super(x, y);
        point = 400;
        super.spriteList = new Sprite[]{
                Sprite.doll_left1,
                Sprite.doll_left2,
                Sprite.doll_left3,
                Sprite.doll_right1,
                Sprite.doll_right2,
                Sprite.doll_right3,
                Sprite.doll_dead,
                Sprite.doll_score
        };
        super.speed = 1.6 + (double) ((int) (StdRandom.uniformDouble() * 10)) / 10;
        super.img = spriteList[0].getFxImage();
        this._direction = 0;
        initSpeed = super.speed;
        accel = initSpeed;
    }

    /**
     * Phương thức đảo ngược hướng di chuyển.
     * @param dir hướng di chuyển
     * @return hướng ngược lại
     */
    private int reverseDirection(int dir) {
        switch (dir) {
            case directionUp:
                return directionDown;
            case directionDown:
                return directionUp;
            case directionLeft:
                return directionRight;
            case directionRight:
                return directionLeft;
            default:
                return 0;
        }
    }

    /**
     * Override calculateMove từ Enemy.
     * Logic di chuyển:
     * - Nếu chưa có hướng -> tìm hướng mới.
     * - Nếu không thể di chuyển theo hướng hiện tại -> Reset gia tốc và Đảo Ngược hướng
     * Tính tốc độ = tốc độ cơ bản + gia tốc
     * Giảm dần gia tốc -> di chuyển theo hướng đã chọn
     */
    @Override
    protected void calculateMove() {
        if (this._direction == 0) {
            calMoveForNone();
        } else {
            if (!canMove(this._direction)) {
                accel = initSpeed;
                _direction = reverseDirection(_direction);
            }
            this.speed = initSpeed + accel;
            if (accel > 0) accel -= 0.1;
            else accel = 0;
            super.move();
        }
    }

    //Override method canPass từ Enemy giống Ballom
    @Override
    protected boolean canPass(Entity entity) {
        return entity.canBePassed();
    }
}
