package uet.oop.bomberman.graphics;

import javafx.scene.image.*;
import javafx.scene.image.Image;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */

public class Sprite {
    public static final int DEFAULT_SIZE = 16;
    public static final int SCALED_SIZE = DEFAULT_SIZE * 3;
    private static final int TRANSPARENT_COLOR = 0xffff00ff;
    public final int SIZE;
    private int _x, _y;
    public int[] _pixels;
    protected int _realWidth;
    protected int _realHeight;
    private SpriteSheet _sheet;

    /*
    |--------------------------------------------------------------------------
    | Board sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite grass = new Sprite(DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite brick = new Sprite(DEFAULT_SIZE, 7, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite wall = new Sprite(DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite portal = new Sprite(DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 14, 14);


}
