/******************************************************************************
 *
 *  Dependency: Entity.java
 *
 *  The abstract data type for the Unbreakable Entity in general.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class UnbreakableEntity extends Entity {
    public UnbreakableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
}
