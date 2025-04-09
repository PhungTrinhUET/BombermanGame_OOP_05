/******************************************************************************
 *
 *  Dependency: Item.java
 *
 *  The data type for the Bomb item.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable.item;


import uet.oop.bomberman.entities.breakable.Item;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {
    public BombItem(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_bombs.getFxImage());
    }

    public void powerUp(Bomber bomberman) {
        if (isBroken()) return;
        super.powerUp(bomberman);
        bomberman.setMaxBombCount(bomberman.getMaxBombCount()+1);
        breakEntity();
    }
}

