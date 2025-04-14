/******************************************************************************
 *
 *  Dependency: Item.java
 *
 *  The data type for the Speed item.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable.item;

import uet.oop.bomberman.entities.breakable.Item;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {
    /**
     * Khởi tạo đối tượng sử dụng phương thức khởi tạo của lớp cha Item.
     */
    public SpeedItem(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public void powerUp(Bomber bomberman) {
        if (isBroken()) return;
        super.powerUp(bomberman);
        bomberman.setSpeed(bomberman.getSpeed() + 1);
        breakEntity();
    }

    @Override
    public void update() {

    }
}

