package uet.oop.bomberman.util.entityUtil;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Stack;

public class Layer {
    private int x;
    private int y;
    public Stack<Entity> stack = new Stack<>();

    public Layer(int xUnit, int yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
