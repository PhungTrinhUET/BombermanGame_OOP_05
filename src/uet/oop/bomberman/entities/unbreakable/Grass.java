/******************************************************************************
 *
 *  Dependency: UnbreakableEntity.java
 *
 *  The data type for the Grass entity.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.unbreakable;


import uet.oop.bomberman.entities.UnbreakableEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends UnbreakableEntity {

    private double topX;
    private double topY;
    private double botX;
    private double botY;

    public Grass(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.grass.getFxImage());
    }


    public void setTopX(double x) {
        this.topX = x;
    }

    @Override
    public double getTopX() {
        return this.topX;
    }

    public void setTopY(double y) {
        this.topY = y;
    }

    @Override
    public double getTopY() {
        return this.topY;
    }

    public void setBotX(double botX) {
        this.botX = botX;
    }

    @Override
    public double getBotX() {
        return this.botX;
    }

    public void setBotY(double botY) {
        this.botY = botY;
    }

    @Override
    public double getBotY() {
        return this.botY;
    }

    @Override
    public void update() {

    }
}
