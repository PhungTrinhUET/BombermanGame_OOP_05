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



}
