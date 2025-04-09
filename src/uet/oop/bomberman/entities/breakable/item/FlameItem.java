/******************************************************************************
 *
 *  Dependency: Item.java
 *
 *  The data type for the Flame item.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable.item;

import uet.oop.bomberman.entities.breakable.Item;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {
    public FlameItem(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_flames.getFxImage());
    }

    public void powerUp(Bomber bomberman) {
        if (isBroken()) return;
        super.powerUp(bomberman);
        bomberman.setBombRange(bomberman.getBombRange()+1);
        breakEntity();
    }
}
