package uet.oop.bomberman.util.gameUtil;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.Main;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.entities.breakable.Bomb;
import uet.oop.bomberman.entities.breakable.Brick;
import uet.oop.bomberman.entities.breakable.Flame;
import uet.oop.bomberman.entities.breakable.item.BombItem;
import uet.oop.bomberman.entities.breakable.item.FlameItem;
import uet.oop.bomberman.entities.breakable.Portal;
import uet.oop.bomberman.entities.breakable.item.SpeedItem;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.entities.character.enemy.Ballom;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.unbreakable.Grass;
import uet.oop.bomberman.entities.unbreakable.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.util.entityUtil.EnemyAI;
import uet.oop.bomberman.util.entityUtil.Layer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Board implements Serializable {
    public double boardOffsetX = 0;
    public double boardOffsetY = 0;

    public int nRow;
    public int nCol;

    private List<Layer> stillObjects = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public List<Bomb> bombs = new ArrayList<>();
    public List<Flame> flames = new ArrayList<>();
    public Bomber bomber;
    public boolean endGame = false;
    public boolean nextLevel = false;
    private int exitsCount = 2;
    private char itemType;

    public Board() {

    }

    public Board(int level) {
        // create map
        try {
            loadLevel(level);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!!!");
        }
        endGame = false;
        nextLevel = false;
        bombs.clear();
        flames.clear();
    }

    /**
     * Tải tải màn chơi từ tập cấu hình.
     *
     * @param level tên level cần tải
     * @throws FileNotFoundException khi không tìm thấy tệp cấu hình cần tải
     */
    public void loadLevel(int level) throws FileNotFoundException {
        String path = "/levels/Level" + level + ".txt";
        try {
            if (level >= 1) {
                new CreateLevel(path);
            }
            InputStream fstream = this.getClass().getResourceAsStream(path);
            Scanner scanner = new Scanner(fstream);
            scanner.nextInt();
            nRow = scanner.nextInt();
            nCol = scanner.nextInt();
            itemType = scanner.next().charAt(0);
            scanner.nextLine();
            scanner.nextLine();
            enemies.clear();
            bombs.forEach(bomb -> bomb.isBroken = true);
            bombs.clear();
            flames.clear();
            for (int i = 0; i < nRow; ++i) {
                String data = scanner.nextLine();
                for (int j = 0; j < nCol; ++j) {
                    Layer layer = new Layer(j, i);
                    if (data.charAt(j) != '#') {
                        layer.add(new Grass(j, i));
                    } else {
                        layer.add(new Wall(j, i));
                    }

                    switch (data.charAt(j)) {
                        case '*':
                            layer.add(new Brick(j, i));
                            break;
                        case 'x':
                            layer.add(new Portal(j, i));
                            break;
                        case 's':
                            layer.add(new SpeedItem(j, i));
                            break;
                        case 'b':
                            layer.add(new BombItem(j, i));
                            break;
                        case 'f':
                            layer.add(new FlameItem(j, i));
                            break;
                        case 'p':
                            bomber = new Bomber(j, i);
                            break;
                        case '1':
                            enemies.add(new Ballom(j, i));
                            break;
                        case '2':
                            enemies.add(new Oneal(j, i));
                            break;
                        case '3':
                            enemies.add(new Doll(j, i));
                            break;
                        case '4':
                            enemies.add(new Minvo(j, i));
                            break;
                        case '5':
                            enemies.add(new Kondoria(j, i));
                            break;
                        default:
                            break;
                    }
                    if (layer.stack.peek().isItem() || layer.stack.peek().isPortal()) {
                        layer.add(new Brick(j, i));
                    }
                    stillObjects.add(layer);
                }
            }
        } catch (Exception e) {
            new CreateLevel(path);
            loadLevel(level);
        }
    }

    public void render(GraphicsContext gc) {
        stillObjects.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        flames.forEach(f -> f.render(gc));

        Collections.sort(enemies, new Comparator<MovingEntity>() {
            @Override
            public int compare(MovingEntity o1, MovingEntity o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });
        boolean bomberRender = false;
        for (MovingEntity g : enemies) {
            g.render(gc);
            if (bomber != null && g.getY() >= bomber.getY()) {
                bomberRender = true;
                bomber.render(gc);
            }
        }
        if (bomber != null && !bomberRender) {
            bomber.render(gc);
        }
    }




}


