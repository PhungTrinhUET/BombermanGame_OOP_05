package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Enemy;

public class Ballom extends Enemy {

    /**
     * Constructor enemy.
     *
     * @param x la toa do.
     * @param y la toa do.
     *          _x/_y = _x/_y * Sprite.SCALED_SIZE: bien doi toa do x,y thanh pixel -> muot hon.
     */
    public Ballom(int x, int y) {
        super(x, y);
    }

    @Override
    protected void calculateMove() {
    }

    @Override
    protected boolean canPass(Entity entity) {
        return false;
    }
}
