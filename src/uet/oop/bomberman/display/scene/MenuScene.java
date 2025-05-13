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
import uet.oop.bomberman.display.scene.menu.Option;
import uet.oop.bomberman.sound.Sound;


public abstract class MenuScene extends DisplayScene {
    protected int minPos = 0; //vị trí đầu tiên trong menu
    protected int currentItem = 0; //Vị trí hiện tại
    protected Option[] menuItems; // Mảng chứa các option trong menu
    protected Text text; //text hiển thị tên scene
    protected VBox root; //Container chứa các thành phần của menu

    /**
     * Constructor MenuScene:
     * Khởi tạo nhạc nền -> tạo Vbox làm container chính -> thiết lập khoảng cách auto = 10
     * -> Căn giữa và đặt nền đen -> tạo text tiêu đề với font và màu default -> tạo ra scene với kích thước cố định
     */
    public MenuScene() {
        this.BGM = Sound.cloneOf(Sound.TitleBGM);
        root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        text = new Text("SCENE NAME");
        text.setFont(Main.FONT);
        text.setStyle("-fx-font-size: 40");
        text.setFill(Color.YELLOW);

        scene = new Scene(root, Main.initialSceneWidth, Main.initialSceneHeight);
    }

    /**
     * Override reset() của DisplayScene
     * Tắt trạng thái Active now -> Reset vị trí về đầu menu -> kích hoạt item đầu tiên
     */
    @Override
    public void reset() {
        super.reset();
        menuItems[currentItem].setActive(false);
        currentItem = minPos;
        menuItems[currentItem].setActive(true);
    }

    /**
     * Phương thức update: Xử lý INPUT từ bàn phím (UP-lên, DOWN - xuống, Enter - chọn)
     * if...2 if kiểm tra giới hạn menu khi di chuyển
     */
    @Override
    public void update() {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP:
                    if (currentItem > minPos) {
                        menuItems[currentItem].setActive(false);
                        menuItems[--currentItem].setActive(true);
                    }
                    break;
                case DOWN:
                    if (currentItem < menuItems.length - 1) {
                        menuItems[currentItem].setActive(false);
                        menuItems[++currentItem].setActive(true);
                    }
                    break;
                case ENTER:
                    menuItems[currentItem].activate();
                    break;
            }
        });
    }
}
