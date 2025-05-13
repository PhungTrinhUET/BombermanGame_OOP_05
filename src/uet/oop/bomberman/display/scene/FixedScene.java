package uet.oop.bomberman.display.scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.Main;
import uet.oop.bomberman.display.DisplayScene;

/**
 * Class FixedScene: tạo scene cố định, đơn giản.
 */
public class FixedScene extends DisplayScene {
    public FixedScene(String mes) { //Nhận một tham số String mes là nội dung cần hiển thị
        Text text = new Text(mes); // đối tượng text có nội dung truyền vào
        text.setFont(Main.FONT); //Thiết lập Font và màu
        text.setFill(Color.WHITE);

        VBox root = new VBox(text); //Create Vbox là container chính ->
        root.setAlignment(Pos.CENTER); //Thêm text vào Vbox và căn giữa, nền đen
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        scene = new Scene(root, Main.initialSceneWidth, Main.initialSceneHeight); //Tạo Scene với kích thước cố định từ main
    }
}
