/******************************************************************************
 *
 *  Dependency: BreakableEntity.java
 *
 *  The abstract data type for all the Item in general.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable;


import javafx.scene.image.Image;
import uet.oop.bomberman.display.scene.BombermanGame;
import uet.oop.bomberman.entities.BreakableEntity;
import uet.oop.bomberman.entities.character.Bomber;

public abstract class Item extends BreakableEntity {
    /**
     * Khởi tạo đối tượng sử dụng phương thức khởi tạo của lớp cha BreakableEntity.
     * Khởi tạo đối tượng Brick đặt trên vật phẩm.
     */
    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.spriteOffsetTop = 3*3;
        this.spriteOffsetBot = 3*3;
        this.spriteOffsetLeft = 3*3;
        this.spriteOffsetRight = 3*3;
    }

    /**
     * Phương thức chuyển trạng thái của đối tượng.
     */
    @Override
    public void breakEntity() {
        this.isBroken = true;
    }

    public void powerUp(Bomber bomber) {
        if (isBroken()) return;
        BombermanGame.addScore(1000);
    }

    @Override
    public void update() {

    }
}
