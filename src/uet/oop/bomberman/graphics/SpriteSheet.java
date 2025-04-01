package uet.oop.bomberman.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private String _path;
    public final int SIZE;
    public int[] _pixels;
    public BufferedImage image;

    public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);

    public SpriteSheet(String path, int size) {
        _path = path;
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        load();
    }

    public void load() {
        image = SpriteLoader.loadImage(_path);
        int w = image.getWidth();
        int h = image.getHeight();
        _pixels = new int[w * h];
        image.getRGB(0, 0, w, h, _pixels, 0, w);
    }

}
