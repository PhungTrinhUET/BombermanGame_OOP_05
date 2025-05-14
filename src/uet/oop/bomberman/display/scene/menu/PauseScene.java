package uet.oop.bomberman.display.scene.menu;

import uet.oop.bomberman.Main;
import uet.oop.bomberman.display.scene.MenuScene;

/**
 * PauseScene: tạo màn hình tạm dừng game. Kế thừa các thuộc tính (text, menuItems, currentItem, root)
 * Kế thừa các phương thức (update(), render(), reset(), close()
 * Tương tác với Main: (A) Tiếp tục game (status = 1), (B) Quay lại game (status = 0)
 */
public class PauseScene extends MenuScene {
    public PauseScene() {
        text.setText("GAME PAUSED"); //text hiển thị

        menuItems = new Option[2]; // Mảng chứa 2 lựa chọn: (1) CONTINUE, (2) RETURN - quay lại menu chính

        menuItems[0] = new Option("CONTINUE"); //Khi được chọn, set trạng thái về 1 - tiếp tục
        menuItems[0].setOnActivate(() -> Main.setPlayingStatus(1, "continue"));

        menuItems[1] = new Option("RETURN"); //Khi được chọn, set trạng thái game về 0 (quay lại game)
        menuItems[1].setOnActivate(() -> {
            Main.setPlayingStatus(0, "return");
        });

        currentItem = 0; //chọn item đầu tiên (CONTINUE) -> đánh dấu item đó là active
        menuItems[currentItem].setActive(true);

        root.getChildren().add(text); //Thêm vào scene -> thêm tất cả menu items vào scene
        root.getChildren().addAll(menuItems);
    }
}
