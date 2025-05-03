package uet.oop.bomberman.util.entityUtil;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.BreakableEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.breakable.Item;
import uet.oop.bomberman.entities.character.Bomber;
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

    /**
     * Thêm một entity vào ô layer
     */

    public void add(Entity entity) {
        stack.add(entity);
    }

    /**
     * Phương thức được gọi nếu ô layer nằm trong phạm vi nổ của bomb
     * Phá hủy đối tuuwognj trên cùng của ô layer nếu có
     *
     * @return true nếu có thể phá hủy, false nếu không
     */

    public boolean destroyTop() {
        if (stack.peek().isBreakable()) {
            BreakableEntity top = (BreakableEntity) stack.peek();
            top.breakEntity();
            return true;
        }
        return false;
    }

    /**
     * Chỉ có thể được sử dụng khi đối tượng trên cùng là một vật phẩm.
     */
    public void powerUp(Bomber bomber) {
        if (!stack.peek().isItem()) {
            return;
        }
        Item top = (Item) stack.peek();
        top.powerUp(bomber);
    }

    /**
     * Trả về phần tử đứng trên cùng của layer tại ô này.
     *
     * @return null nếu không có phần tử nào, nếu có thì trả về phần tử đầu.
     */
    public Entity getTop() {
        if (stack.isEmpty()) return null;
        return stack.peek();
    }

    /**
     * Render đối tượng trên cùng của ô layer.
     *
     * @param gc GraphicsContext
     */
    public void render(GraphicsContext gc) {
        stack.peek().render(gc);
    }

    /**
     * Cập nhật đối tượng trên cùng của ô layer.
     * Nếu đối tượng trên cùng đã bị phá hủy, loại bỏ đối tượng trên cùng.
     */
    public void update() {
        if (stack.peek().isBreakable()) {
            BreakableEntity top = (BreakableEntity) stack.peek();
            if (top.isBroken()) {
                stack.pop();
            }
        }
        stack.peek().update();
    }
}
