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
            createMap();

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

    private void createMap() {
        map = new char[row][col];
        // border
        for (int j = 0; j < col; ++j) {
            map[0][j] = '#';
            map[row - 1][j] = '#';
        }
        // border
        for (int i = 0; i < row; ++i) {
            map[i][0] = '#';
            map[i][col - 1] = '#';
        }
        // wall
        for (int i = 2; i < row; i += 2) {
            for (int j = 2; j < col; j += 2) {
                map[i][j] = '#';
            }
        }
        // brick, grass
        int brickCount = 0;
        for (int i = 1; i < row - 1; ++i) {
            for (int j = 1; j < col - 1; ++j) {
                if (map[i][j] == '#') continue;
                if (StdRandom.uniformInt(4) == 0) {
                    map[i][j] = '*';
                    ++brickCount;
                } else {
                    map[i][j] = ' ';
                }
            }
        }
        // portal, power up item
        int p = 2;
        int item = 1;
        for (int i = 1; i < row - 1; ++i) {
            for (int j = 1; j < col - 1; ++j) {
                if (map[i][j] == '*') {
                    int num = StdRandom.uniformInt(brickCount);
                    if (num == p && item > 0) {
                        map[i][j] = itemType;
                        --item;
                    }
                    if (num < p) {
                        map[i][j] = 'x';
                        --p;
                    }
                    --brickCount;
                }
            }
        }
        // enemy
        int grassCount = 0;
        for (int i = 3; i >= 0; --i) enemyCount[i] += enemyCount[i + 1];
        for (int i = 5; i < row - 1; ++i)
            for (int j = 5; j < col - 1; ++j)
                if (map[i][j] == ' ')
                    ++grassCount;
        for (int i = 5; i < row - 1; ++i) {
            for (int j = 5; j < col - 1; ++j) {
                if (map[i][j] != ' ') continue;
                map[i][j] = chooseEnemy(grassCount);
                --grassCount;
            }
        }
        // bomber
        map[1][1] = 'p';
        map[1][2] = ' ';
        map[2][1] = ' ';
    }

    private char chooseEnemy(int grassCount) {
        int r = StdRandom.uniformInt(grassCount);
        for (int i = 4; i >= 0; --i) {
            if (r < enemyCount[i]) {
                for (int j = i; j >= 0; --j) --enemyCount[j];
                return (char) (i + '1');
            }
        }
        return ' ';
    }
}
