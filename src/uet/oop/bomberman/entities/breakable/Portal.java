/******************************************************************************
 *
 *  Dependency: BreakableEntity.java
 *
 *  The data type for the Portal.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable;


import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.BreakableEntity;
import uet.oop.bomberman.entities.unbreakable.Grass;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends BreakableEntity {
    private Grass base;
    public Portal(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.portal.getFxImage());
        base = new Grass(xUnit, yUnit);
    }

    /**
     * Ghi đè phương thức render() của lớp cha Entity.
     * @param gc GraphicsContext
     */
    @Override
    public void render(GraphicsContext gc) {
        base.render(gc);
        super.render(gc);
    }

    @Override
    public void update() {

    }
}

