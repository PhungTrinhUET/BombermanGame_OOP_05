package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.display.scene.BombermanGame;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.gameUtil.StdRandom;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Enemy Class By PhungTrinh.
 * Enemy ke thua MovingEntity (toc do, huong, animate...), enemy co them:
 * Hanh vi di chuyen (AI hoac random).
 * Co che bi giet (kill) dac thu (hien thi diem, hieu ung chet).
 * He so diem (point) khi nguoi choi tieu diet enemy.
 * ...
 * Enemy la abstract, cac lop con cu the nhu (Ballon, Oneal...)
 * se ke thua Enemy va override mot so phuong thuc.
 */

public abstract class Enemy extends MovingEntity {
    private Sprite _sprite = Sprite.player_right; //Sprite - hinh enemy dung de ve hien tai.
    protected Sprite[] spriteList = new Sprite[8];
    protected final int leftSprite = 0; //sprite chay trai.
    protected final int rightSprite = 3; //sprite chay phai.
    protected final int deadSprite = 6; //sprite khi enemy chet.
    protected final int scoreSprite = 7; //sprite hien thi diem.
    protected int _direction = directionNone; //huong di chuyen

    protected boolean isBlocked; //cờ (khong the di chuyen)
    protected double _x;
    protected double _y;
    protected int point; // so diem sau khi giet enemy (gia tri cu the o phuong thuc enemy)
    protected boolean isShowingPoint = false; //hien thi diem (sau khi enemy chet)
    protected boolean isVanishing = false; //kiem soat trang thai chet dan (khong cho bien mat ngay lap tuc)

    /**
     * Constructor enemy.
     *
     * @param x la toa do.
     * @param y la toa do.
     *          _x/_y = _x/_y * Sprite.SCALED_SIZE: bien doi toa do x,y thanh pixel -> muot hon.
     */
    public Enemy(int x, int y) {
        super(x, y);
        _x = x * Sprite.SCALED_SIZE;
        _y = y * Sprite.SCALED_SIZE;
        this.isDead = false; //ban dau enemy chua chet.
        this.isBlocked = true; // Auto bi chan.
    }

    @Override
    public void update() {
        animate(); //moi frame -> tang bien animate de dieu khien animation "chay/di/chet".
        chooseSprite();
        this.img = _sprite.getFxImage();
        if (!isKilled && !isDead) { //kiem tra neu enemy bi kill va chua chet han thi goi calculateMove()
            calculateMove();
            this.x = _x; //sau khi di chuyen thi thay doi _x, _y - gan ve this.x/y
            this.y = _y;
        }
    }

    private void chooseSprite() {
    }

    private void calculateMove() {
    }

    @Override
    public void kill() {
        if (isKilled || isDead) return;
        isKilled = true;
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                isVanishing = true; // sau 600ms chuyen sprite chet
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isShowingPoint = true; // sau 600ms nua thi hien sprite "diem + xxx"
                        (new Timer()).schedule(new TimerTask() {
                            @Override
                            public void run() {
                                isDead = true;
                            }
                        }, 500); // sau 500ms nua, enemy chet hoan toan -> xoa khoi game.
                    }
                }, 600L);
            }
        }, 600L);
        BombermanGame.addScore(point);
        System.out.println("You killed an enemy! Score +" + point + ". Current score: " + BombermanGame.score);
    }

    public void set_direction(int _direction) {
        this._direction = _direction;
    }

    public int get_direction() {
        return this._direction;
    }

    public boolean isKilled() {
        return isKilled;
    }

    /**
     * -> randomMovement se giup Enemy di chuyen doi huong ngau nhien moi khi bi chan.
     * Neu dang khong di chuyen hoac khong the di chuyen theo huong _direction, dat isBlocked = true.
     */
    public void randomMovement() {
        if (this._direction == 0 || !canMove(this._direction)) isBlocked = true;
        int temp = 0;
        if (isBlocked || StdRandom.uniformInt(100) % 2 == 0) {
            int cnt = 0;
            temp = StdRandom.uniformInt(4) + 1; // Random mot huong (tu 1..4) neu khong di chuyen duoc -> tang temp
            while (!canMove(temp)) {
                temp++; //Tang temp va thu lai neu khong di chuyen duoc
                if (temp == 5) {
                    temp = 1;
                }
                cnt++;
                if (cnt == 5) return;
            }
            _direction = temp; //Gan _direction = temp neu tim duoc huong hop le.
        }
    }

    /**
     * Cai tien hon so voi randomMovement -> them board.EnemyAIDirection(this).
     */
    protected void aiMovement() {
        if (this._direction == 0 || !canMove(this._direction)) isBlocked = true;
        if ((x + speed) % Sprite.SCALED_SIZE <= speed * 2 && (y + speed) % Sprite.SCALED_SIZE <= speed * 2) {
            int temp = board.EnemyAIDirection(this);
            if (temp != 0) _direction = temp;
            else if (isBlocked) randomMovement();
        }
    }

    private boolean canMove(int direction) {
        return false;
    }
}
