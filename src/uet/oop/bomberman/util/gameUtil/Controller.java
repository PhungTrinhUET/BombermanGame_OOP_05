package uet.oop.bomberman.util.gameUtil;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.Main;
import uet.oop.bomberman.entities.character.Bomber;

public class Controller {
    public static boolean[] direction = new boolean[5];
    public static boolean layBomb;

    public Controller() {
        direction[Bomber.directionNone] = true;
        for (int i = 1; i <= 4; ++i) {
            direction[i] = false;
        }
        layBomb = false;
    }

    public void listen(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE:
                Main.setPlayingStatus(2, "pause");
                break;
            case SPACE:
                layBomb = true;
                break;
            case UP:
                direction[Bomber.directionUp] = true;
                break;
            case DOWN:
                direction[Bomber.directionDown] = true;
                break;
            case LEFT:
                direction[Bomber.directionLeft] = true;
                break;
            case RIGHT:
                direction[Bomber.directionRight] = true;
                break;
            default:
                direction[Bomber.directionNone] = false;
                break;
        }
    }

    public void release(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                direction[Bomber.directionUp] = false;
                break;
            case DOWN:
                direction[Bomber.directionDown] = false;
                break;
            case LEFT:
                direction[Bomber.directionLeft] = false;
                break;
            case RIGHT:
                direction[Bomber.directionRight] = false;
                break;
            default:
                direction[Bomber.directionNone] = true;
                break;
        }
    }
}
