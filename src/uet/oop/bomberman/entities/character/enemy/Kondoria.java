package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.gameUtil.StdRandom;

/**
 * Kondoria là Enemy đặc biệt trong đống Enemy, có khả năng đi xuyên tường (xuyên Brick)
 * Kondoria được ứng dụng thêm AI thông minh, có thể đuổi theo Bomberman, tránh bom.
 * Tốc độ di chuyển từ 0.3 tới 1.2. Nếu tốc độ là 0.5 thì sẽ được tăng lên 0.6
 */
public class Kondoria extends Enemy {
    public Kondoria(int x, int y) {
        super(x, y);
        point = 1000;
        super.spriteList = new Sprite[] {
                Sprite.kondoria_left1,
                Sprite.kondoria_left2,
                Sprite.kondoria_left3,
                Sprite.kondoria_right1,
                Sprite.kondoria_right2,
                Sprite.kondoria_right3,
                Sprite.kondoria_dead,
                Sprite.kondoria_score
        };
        super.speed = 0.3 + (double) ((int) (StdRandom.uniformDouble() * 10)) / 10;
        if (speed == 0.5) speed = 0.6;
        super.img = spriteList[0].getFxImage();
    }

    /**
     * Sử dụng method aiMovement() để tính toán hướng di chuyển
     */
    @Override
    protected void calculateMove() {
        aiMovement();
    }

    /**
     * Override method canPass() từ Enemy.
     * @param entity là Kondoria
     * @return entity.isBrick
     * xác định xem Kondoria có đi qua entity không. Có theer đi qua nếu entity là gạch (isBrick)
     */
    @Override
    protected boolean canPass(Entity entity) {
        if (!entity.canBePassed()) {
            return entity.isBrick();
        }
        return false;
    }
}
