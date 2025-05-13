package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.gameUtil.StdRandom;

/**
 * Class Oneal enxtends Enemy
 * Point 200
 * Tốc độ cơ bản 1.0 + random (0-0.3)
 * Override canPass và calculateMove
 * Chỉ đi qua entity cho phép
 * Oneal sử dụng AI để di chuyển thông minh
 */
public class Oneal extends Enemy {
    public Oneal(int x, int y) {
        super(x, y);
        point = 200;
        super.spriteList = new Sprite[]{
                Sprite.oneal_left1,
                Sprite.oneal_left2,
                Sprite.oneal_left3,
                Sprite.oneal_right1,
                Sprite.oneal_right2,
                Sprite.oneal_right3,
                Sprite.oneal_dead,
                Sprite.oneal_score
        };
        super.speed = 1 + (double) ((int) ((StdRandom.uniformDouble()/3) * 10)) / 10;
        super.img = spriteList[0].getFxImage();
    }

    @Override
    protected boolean canPass(Entity entity) {
        return entity.canBePassed();
    }

    @Override
    protected void calculateMove() {
        aiMovement();
    }
}