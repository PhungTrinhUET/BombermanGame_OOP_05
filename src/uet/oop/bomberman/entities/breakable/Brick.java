/******************************************************************************
 *
 *  Dependency: BreakableEntity.java
 *
 *  The data type for the Brick.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable;

import uet.oop.bomberman.entities.BreakableEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends BreakableEntity {
    /**
     * Khởi tạo đối tượng sử dụng phương thức khởi tạo của lớp cha BreakableEntity.
     */
    public Brick(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.brick.getFxImage());
    }

    @Override
    public void update() {
        if (!isExploding) {
            return;
        }
        animate();
        Sprite sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 35);
        this.img = sprite.getFxImage();
    }
}

