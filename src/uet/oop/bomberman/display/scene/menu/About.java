package uet.oop.bomberman.display.scene.menu;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.oop.bomberman.Main;
import uet.oop.bomberman.display.scene.MenuScene;

/**
 * Class About extends MenuScene: Hiển thị thông tin về game
 */
public class About extends MenuScene {
    public About() {
        text.setText("ABOUT"); //Hiển thị about

        VBox description = new VBox(); //container chứa thông tin về game
        description.setSpacing(5); //khoảng cách giữa các phần tử là 5
        description.setAlignment(Pos.CENTER); //căn giữa

        //Các description chứa thông tin nhóm
        description.getChildren().add(createText("a game project made under"));
        description.getChildren().add(createText("object-oriented programming course"));
        description.getChildren().add(createText("K66GAT-UET-VNU"));
        description.getChildren().add(createText(""));
        description.getChildren().add(createText("AUTHOR:"));
        description.getChildren().add(createText("PHUNG TRUONG TRINH"));
        description.getChildren().add(createText("DAO NGOC BICH"));
        description.getChildren().add(createText(""));
        description.getChildren().add(createText("this is free software: you can redistribute it "));
        description.getChildren().add(createText("and/or modify it under the terms of the gnu"));
        description.getChildren().add(createText("general public license as published by"));
        description.getChildren().add(createText("the free software foundation,"));
        description.getChildren().add(createText("either version 3 of the license,"));
        description.getChildren().add(createText("or (at your option) any later version"));

        menuItems = new Option[1]; //Chỉ có một option "BACK" -> quay về menu chính
        menuItems[0] = new Option("BACK");
        menuItems[0].setOnActivate(() -> Main.setPlayingStatus(0, "back"));
        menuItems[0].setActive(true); //set active mặc định

        //Layout:
        root.setSpacing(20); //Khoảng cách giữa các phần
        root.getChildren().add(text); //add tiêu đề
        root.getChildren().add(description); //add mô tả
        root.getChildren().addAll(menuItems); //thêm menu items
    }

    /**
     * createText method:
     * @param mes tạo text với nội dung cho trước
     * @return ...
     */
    private Text createText(String mes) {
        Text text = new Text(mes);
        text.setFont(Main.FONT); //Thiết lập font chữ từ Main.FONT
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-size: 20"); //Kích thước font: 20-WHITE
        text.setTextAlignment(TextAlignment.CENTER); //căn giữa
        return text;
    }
}