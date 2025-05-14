package uet.oop.bomberman.util.gameUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class CreateLevel {
    private int row;
    private int col;
    private char[][] map;
    private int[] enemyCount = new int[5];
    private char itemType;
    public CreateLevel(String path) {
        try {
            Scanner scanner = new Scanner(Files.newInputStream(Paths.get(path)));
            int level = scanner.nextInt();
            row = scanner.nextInt();
            col = scanner.nextInt();
            itemType = scanner.next().charAt(0);
            for (int i = 0; i < 5; ++i)
                enemyCount[i] = scanner.nextInt();

            scanner.close();

            FileWriter file = new FileWriter("res/levels/Level" + level + ".txt");
            file.write(level + " " + row + " " + col + " " + itemType + "\n");
            for (int i = 0; i < 5; ++i) {
                file.write(enemyCount[i] + " ");
            }
            file.write("\n");

            row = 13;


            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    file.write(map[i][j]);
                }
                file.write("\n");
            }
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println("Can't create level!");
        } catch (NullPointerException e) {
            System.out.println("Can't open level file!");
        }
    }
}
