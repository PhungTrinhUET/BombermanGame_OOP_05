module Bomberman {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens vn.edu.vnu.uet to javafx.fxml;
    exports vn.edu.vnu.uet;
}