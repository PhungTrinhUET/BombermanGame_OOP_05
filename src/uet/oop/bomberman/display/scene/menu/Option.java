package uet.oop.bomberman.display.scene.menu;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.Main;

/**
 * Tham khảo youtuber Almas Baimagambetov.
 */
public class Option extends HBox {
    private ImageView leftArrow;
    private ImageView rightArrow;
    private Text text;
    private Runnable script;

    public Option(String name) {
        super(15);
        setAlignment(Pos.CENTER);

        leftArrow = new ImageView(getClass().getResource("/textures/leftarrow.png").toString());
        rightArrow = new ImageView(getClass().getResource("/textures/rightarrow.png").toString());;

        text = new Text(name);
        text.setFont(Main.FONT);

        getChildren().addAll(leftArrow, text, rightArrow);
        setActive(false);
    }

    public void setActive(boolean active) {
        leftArrow.setVisible(active);
        rightArrow.setVisible(active);
        text.setFill(active ? Color.WHITE : Color.GRAY);
    }

    public void setOnActivate(Runnable r) {
        script = r;
    }

    public void activate() {
        if (script != null) {
            script.run();
        }
    }
}
