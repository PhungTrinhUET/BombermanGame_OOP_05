/******************************************************************************
 *
 *  Dependency: BreakableEntity.java
 *
 *  The data type for the Flame.
 *
 ******************************************************************************/

package uet.oop.bomberman.entities.breakable;


import uet.oop.bomberman.entities.BreakableEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends BreakableEntity {
    public static final int horizontalLeft = 0;
    public static final int horizontalRight = 1;
    public static final int verticalTop = 2;
    public static final int verticalDown = 3;

    private final int direction;
    private final boolean isLast;
    public Flame(int xUnit, int yUnit, int direction, boolean isLast) {
        super(xUnit, yUnit, Sprite.bomb.getFxImage());
        this.direction = direction;
        this.isLast = isLast;
        breakEntity();
    }

    @Override
    public void update() {
        if (isBroken) {
            board.flames.remove(this);
            return;
        }
        Sprite sprite = Sprite.bomb;
        switch (direction) {
            case horizontalLeft:
                if (isLast) {
                    sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                            Sprite.explosion_horizontal_left_last1,
                            Sprite.explosion_horizontal_left_last2,
                            animate, 35);
                } else {
                    sprite = Sprite.movingSprite(Sprite.explosion_horizontal,
                            Sprite.explosion_horizontal1,
                            Sprite.explosion_horizontal2,
                            animate, 35);
                }
                break;
            case horizontalRight:
                if (isLast) {
                    sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                            Sprite.explosion_horizontal_right_last1,
                            Sprite.explosion_horizontal_right_last2,
                            animate, 35);
                } else {
                    sprite = Sprite.movingSprite(Sprite.explosion_horizontal,
                            Sprite.explosion_horizontal1,
                            Sprite.explosion_horizontal2,
                            animate, 35);
                }
                break;
            case verticalTop:
                if (isLast) {
                    sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                            Sprite.explosion_vertical_top_last1,
                            Sprite.explosion_vertical_top_last2,
                            animate, 35);
                } else {
                    sprite = Sprite.movingSprite(Sprite.explosion_vertical,
                            Sprite.explosion_vertical1,
                            Sprite.explosion_vertical2,
                            animate, 35);
                }
                break;
            case verticalDown:
                if (isLast) {
                    sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                            Sprite.explosion_vertical_down_last1,
                            Sprite.explosion_vertical_down_last2,
                            animate, 35);
                } else {
                    sprite = Sprite.movingSprite(Sprite.explosion_vertical,
                            Sprite.explosion_vertical1,
                            Sprite.explosion_vertical2,
                            animate, 35);
                }
                break;
        }
        this.img = sprite.getFxImage();
        animate();
    }
}

