package uet.oop.bomberman.display.scene.menu;

import uet.oop.bomberman.Main;
import uet.oop.bomberman.display.scene.MenuScene;
import uet.oop.bomberman.sound.Sound;

/**
 * Class HomeScene extends MenuScene: (kế thừa thuộc tính: text, menuItems, currentItem, root - container chính)
 * Kế thừa các phương thức (update(), render(), reset(), close().
 * Tương tác với Main:
 * - Sử dụng Main.setPlayingStatus():
 * + tiếp tục game (status = 1)
 * + Bắt đầu game mới (status = 1)
 * + Hiển thị hướng dẫn (status = 7)
 * + Hiển thị bảng xếp hạng (status = 5)
 * + Hiển thị thonong tin (status = 8)
 */
public class HomeScene extends MenuScene {
    public HomeScene() {
        text.setText("BOMBERMAN"); // tiêu đề
        text.setStyle("-fx-font-size: 80");

        menuItems = new Option[6]; //6 Optione lựa chọn: CONTINUE, START, INSTRUCTION, LEADERBOARD....QUIT.

        menuItems[0] = new Option("CONTINUE");
        menuItems[0].setOnActivate(() -> Main.setPlayingStatus(1, "continue")); //các hành động khi click

        menuItems[1] = new Option("START");
        menuItems[1].setOnActivate(() -> Main.setPlayingStatus(1, "start"));

        menuItems[2] = new Option("INSTRUCTION");
        menuItems[2].setOnActivate(() -> Main.setPlayingStatus(7, "instruction"));

        menuItems[3] = new Option("LEADERBOARD");
        menuItems[3].setOnActivate(() -> Main.setPlayingStatus(5, "leaderboard"));

        menuItems[4] = new Option("ABOUT");
        menuItems[4].setOnActivate(() -> Main.setPlayingStatus(8, "about"));

        menuItems[5] = new Option("QUIT");
        menuItems[5].setOnActivate(() -> System.exit(0));

        currentItem = 0; //Chọn item đầu tiên (CONTINUE hoặc START)
        menuItems[currentItem].setActive(true); // đánh dấu active

        root.getChildren().add(text); //Thêm tiêu đề vào scene
        root.getChildren().addAll(menuItems); //Thêm tất cả menu items vào scene
    }

    /**
     * Phương thức setCanContinue:
     * @param canContinue : có lưu game hay khoong.
     * Kiểm soát việc hiển thị option CONTINUE:
     * - Nếu CÓ game đã lưu (canContinue = true);
     *   + Cho phép chọn option CONTINUE
     *   + Hiển thị option CONTINUE
     * - Nếu KHÔNG có game ã lưu (canContinue = false)
     *   + Ẩn option CONTINUE
     *   + Bắt đầu lại option START
     */
    public void setCanContinue(boolean canContinue) {
        minPos = (canContinue ? 0 : 1);
        reset();
        menuItems[0].setVisible(canContinue);
    }
}
