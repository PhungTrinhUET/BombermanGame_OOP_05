/******************************************************************************
 *
 *  Immutable data type that calculates
 *  the best way possible for the enemies
 *  to get to the bomber position.
 *
 ******************************************************************************/

package uet.oop.bomberman.util.entityUtil;

import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.PriorityQueue;


public final class EnemyAI {

    private static int bomberPosX;
    private static int bomberPosY;

    private static class Node implements Comparable<Node> {
        private final int x;
        private final int y;
        private final int direction;
        private final Node prev;
        private final int euclidVal; //Euclid value of this position.
        private final int step;

        /**
         * Node class constructor.
         *
         * @param x         the x Position of the enemy after the direction turn.
         * @param y         the y Position of the enemy after the direction turn.
         * @param direction the turn to get to this position.
         * @param prev      previous node.
         */
        Node(int x, int y, int direction, Node prev) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.prev = prev;
            if (prev != null) {
                this.step = prev.step + 1;
            } else {
                this.step = 1;
            }
            this.euclidVal = (int) Math.sqrt(Math.pow((bomberPosX - x), 2) + Math.pow((bomberPosY - y), 2));
        }

        /**
         * Method override in comparable.
         *
         * @param that the object to be compared.
         * @return the compare result, using manhattan value
         * and the step it took from the first posiotion.
         */
        @Override
        public int compareTo(Node that) {
            int comp = (this.euclidVal + this.step) - (that.euclidVal + that.step);
            if (comp != 0) return comp;
            else return this.euclidVal - that.euclidVal;
        }
    }

    /**
     * check if one direction is moving backward from the other.
     *
     * @param direction1 input direction1.
     * @param direction2 input direction2.
     * @return result.
     */
    private static boolean movingBackward(int direction1, int direction2) {
        if (direction1 == MovingEntity.directionUp && direction2 == MovingEntity.directionDown) return true;
        if (direction1 == MovingEntity.directionLeft && direction2 == MovingEntity.directionRight) return true;
        if (direction1 == MovingEntity.directionDown && direction2 == MovingEntity.directionUp) return true;
        if (direction1 == MovingEntity.directionRight && direction2 == MovingEntity.directionLeft) return true;
        return false;
    }

    /**
     * find the next step from the enemy's start position in the shortest path to
     * bomber's position, only one step because bomber is always moving.
     *
     * @param enemy  the enemy to consider.
     * @param bomber the bomber to get posiotion.
     * @param preDir the direction enemy took before this.
     * @return the next step.
     */
    public static int find(Enemy enemy, Bomber bomber, int preDir) {
        bomberPosX = (int) (bomber.getTopX() / Sprite.SCALED_SIZE);
        bomberPosY = (int) (bomber.getTopY() / Sprite.SCALED_SIZE);

        Node initNode = new Node((int) (enemy.getTopX() + enemy.getSpeed() * 2) / Sprite.SCALED_SIZE
                , (int) (enemy.getTopY() + enemy.getSpeed() * 2) / Sprite.SCALED_SIZE, preDir, null);
        if (Math.abs(initNode.x - bomberPosX) >= 15) return 0;
        if (Math.abs(initNode.y - bomberPosY) >= 15) return 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(initNode);
        int loop_count = 0;
        while (!pq.isEmpty()) {
            loop_count++;
            Node top = pq.peek();
            if (top.x == bomberPosX && top.y == bomberPosY) {
                break;
            }
            if (top.step >= 15 || pq.size() >= 15 || loop_count == 15)  // return 0 if it takes too long for the enemy to get to bomber's position
                return 0;                                               // Average max step value is 18 in Level 1.
            top = pq.poll();
            for (int direction = 1; direction <= 4; ++direction) {
                if (movingBackward(direction, top.direction)) continue;
                int addX = 0, addY = 0;
                if (direction == MovingEntity.directionUp) addY--;
                if (direction == MovingEntity.directionDown) addY++;
                if (direction == MovingEntity.directionLeft) addX--;
                if (direction == MovingEntity.directionRight) addX++;
                try {
                    Enemy enemy1 = enemy.getClass().getConstructor(int.class, int.class).newInstance(top.x, top.y);
                    if (enemy1.canMove(direction)) {
                        pq.add(new Node(top.x + addX, top.y + addY, direction, top));
                    }
                } catch (Exception e) {
                    System.out.println("Enemy does not have this constructor");
                }

            }
        }

        return top.direction;
    }
}
