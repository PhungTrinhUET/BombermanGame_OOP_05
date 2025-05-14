package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.display.scene.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.gameUtil.Board;
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
    protected int _direction = directionNone; //huong di chuyen hien tai cua ke thu

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
        super(x, y); //kế thừa từ entity
        _x = x * Sprite.SCALED_SIZE;
        _y = y * Sprite.SCALED_SIZE;
        this.isDead = false; //ban dau enemy chua chet.
        this.isBlocked = true; // Auto bi chan.
    }

    @Override
    public void update() {
        animate(); //gọi từ lớp cha -> cập nhật biến đếm animation.
        chooseSprite(); //Chọn sprite phù hợp với trạng thái và hướng di chuyển hiện tại
        this.img = _sprite.getFxImage(); //cập nhật img với sprite đã chọn, chuyển đổi thành FX Image.
        if (!isKilled && !isDead) { //kiem tra neu enemy bi kill va chua chet han thi goi calculateMove()
            calculateMove(); //chưa bị tiêu diệt thì sẽ gọi calculateMove ể tính toán di chuyển
            this.x = _x; //cập nhật tọa độ hiển thị
            this.y = _y;
        }
    }

    @Override
    public void kill() { //Override từ MovingEntity xử lý khi kẻ thù bị tiêu diệt
        if (isKilled || isDead) return;
        isKilled = true; //Nếu đã bị kill hoặc dead thì không làm gì -> đánh dấu true
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() { //Tạo chuỗi animation chết qua 3 timer lồng nhau
                isVanishing = true; // sau 600ms chuyển sang đang biến mất
                (new Timer()).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isShowingPoint = true; // sau 600ms nua thi hien sprite "diem + xxx"
                        (new Timer()).schedule(new TimerTask() {
                            @Override
                            public void run() {
                                isDead = true;
                            }
                        }, 500); // sau 500ms nua, xác nhận đã chết.
                    }
                }, 600L);
            }
        }, 600L);
        BombermanGame.addScore(point);
        System.out.println("You killed an enemy! Score +" + point + ". Current score: " + BombermanGame.score);
    }

    //Phương thức get/set_direction cho hướng di chuyển
    public void set_direction(int _direction) {
        this._direction = _direction;
    } //set hướng di chuyển mới

    public int get_direction() {
        return this._direction;
    } // lấy hướng di chuyển hiện tại

    public boolean isKilled() {
        return isKilled;
    } //Kiểm tra xem đã bị tiêu diệt chưa

    /**
     * Xử lý hành vi di chuyển ngẫu nhiên của kẻ thù.
     * <p>Phương thức này được thiết kế để mô phỏng hành vi di chuyển không đoán trước của kẻ thù trong trò chơi.
     * Nếu kẻ thù không có hướng di chuyển hiện tại hoặc không thể di chuyển theo hướng đó thì được đánh dấu là bị chặn.
     * Trong trường hợp bị chặn hoặc với xác suất 50%, phương thức sẽ thử tạo một hướng di chuyển mới ngẫu nhiên.</p>
     * <p>Các bước thực hiện chính:</p>
     *   <li>Kiểm tra xem hướng hiện tại có tồn tại và có thể di chuyển không.</li>
     *   <li>Nếu bị chặn hoặc ngẫu nhiên chọn thay đổi hướng (50% xác suất), thì:
     *       <li>Chọn một hướng mới ngẫu nhiên (giá trị từ 1 đến 4).</li>
     *       <li>Nếu hướng mới không thể di chuyển, thử lần lượt các hướng còn lại theo thứ tự 1→2→3→4→1... cho đến khi tìm được hướng hợp lệ hoặc vượt quá 5 lần thử.</li>
     *   <li>Nếu tìm được hướng hợp lệ, cập nhật hướng di chuyển mới cho kẻ thù.</li>
     * <p>Phương thức này giúp kẻ thù có hành vi di chuyển linh hoạt, tránh mắc kẹt và tăng tính thử thách cho người chơi.</p>
     *
     * @see #canMove(int) phương thức kiểm tra tính khả thi của việc di chuyển theo một hướng cụ thể.
     */
    protected void randomMovement() {
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
     * Xử lý hành vi di chuyển thông minh (AI) của kẻ thù.
     *
     * <p>Phương thức này kết hợp giữa trí tuệ nhân tạo và di chuyển ngẫu nhiên để xác định hướng di chuyển phù hợp cho kẻ thù.
     * Mục tiêu là tạo nên hành vi tự nhiên, linh hoạt và thách thức hơn cho người chơi.</p>
     *
     * <p>Quy trình xử lý bao gồm các bước chính sau:</p>
     * <ul>
     *   <li>Kiểm tra nếu kẻ thù không có hướng di chuyển hoặc không thể di chuyển theo hướng hiện tại, đánh dấu là bị chặn.</li>
     *   <li>Chỉ tiến hành tính toán hướng di chuyển mới khi kẻ thù đang ở gần trung tâm của một ô trên bản đồ.
     *     <ul>
     *       <li>Điều kiện kiểm tra: <code>(x + speed) % Sprite.SCALED_SIZE &lt;= speed * 2</code> và <code>(y + speed) % Sprite.SCALED_SIZE &lt;= speed * 2</code></li>
     *       <li>Giúp giảm số lần gọi AI không cần thiết và đảm bảo kẻ thù chỉ đổi hướng tại các giao điểm.</li>
     *     </ul>
     *   </li>
     *   <li>Lấy hướng di chuyển từ AI thông qua phương thức <code>board.EnemyAIDirection(this)</code>.</li>
     *   <li>Nếu hướng hợp lệ (khác 0), cập nhật hướng di chuyển.</li>
     *   <li>Nếu không có hướng từ AI và kẻ thù đang bị chặn, sử dụng chiến lược di chuyển ngẫu nhiên.</li>
     *   <li>Cuối cùng, gọi phương thức <code>move()</code> để thực hiện bước di chuyển tương ứng với hướng hiện tại.</li>
     * </ul>
     *
     * <p>Phương thức này giúp tối ưu hóa hành vi của kẻ thù, kết hợp giữa chiến lược thông minh và phản ứng linh hoạt khi gặp chướng ngại vật.</p>
     *
     * @see #randomMovement() để xử lý khi không có hướng AI phù hợp
     * @see #move() thực hiện di chuyển theo hướng đã chọn
     * @see Board#EnemyAIDirection(Enemy) trả về hướng di chuyển đề xuất từ AI
     */
    protected void aiMovement() {
        if (this._direction == 0 || !canMove(this._direction)) isBlocked = true;
        if ((x + speed) % Sprite.SCALED_SIZE <= speed * 2 && (y + speed) % Sprite.SCALED_SIZE <= speed * 2) {
            int temp = board.EnemyAIDirection(this);
            if (temp != 0) _direction = temp;
            else if (isBlocked) randomMovement();
        }
        move();
    }

    //Tìm hướng di chuyển khi không có hướng nào
    protected void calMoveForNone(){
        for (int i = 1; i <=4; ++i){ //duyệt các hướng từ 1 đến 4 (UP, RIGHT, DOWN, LEFT)
            if (canMove(i)) { //Kiểm tra xem có di chuyển theo hướng đó không, nếu có thì update hướng di chuyển
                _direction = i;
                break;
            }
        }
    }

    //Phương thức trừu tượng, cập nhật vị trí dựa trên AI cụ thể
    abstract protected void calculateMove();

    //Phương thức trừu tượng, kiểm tra xem có thể đi qua đối tượng không
    abstract protected boolean canPass(Entity entity);

    //Kiểm tra xem có di chuyển theo hướng không dựa vào addX, addY
    public boolean canMove(int direction) {
        int addX = 0;
        int addY = 0;
        if (direction == directionUp) addY--;
        if (direction == directionDown) addY++;
        if (direction == directionLeft) addX--;
        if (direction == directionRight) addX++;
        double tempSpeed = Math.ceil(speed);
        Entity temp = board.getEntityCollideWith(this, addX * tempSpeed, addY * tempSpeed);
        if (temp == null) {
            return true;
        } else return canPass(temp);
    }

    public void move() { //xác định vector di chuyển addX,Y dựa trên hướng hiện tại
        int addX = 0;
        int addY = 0;
        if (_direction == directionUp) addY--;
        if (_direction == directionDown) addY++;
        if (_direction == directionLeft) addX--;
        if (_direction == directionRight) addX++;
        if (addX != 0 || addY != 0) { //nếu di chuyển thì cập nhật tọa độ thực tế
            _x += addX * speed; //di chuyển khoảng cách bằng tốc độ theo hướng chọn.
            _y += addY * speed;
        }
    }

    /**
     * Phương thức chọn sprite phù hợp dựa trên trạng thái và hướng di chuyển của kẻ thù
     * Nếu đã chết hoàn toàn - isDead thì không cần cập nhaajt sprite
     * Nếu đang hiển thị điểm - isShowingPoint, thì sd sprite hiển thị điểm
     * Nếu bị tiu diệt - isKilled thì có 2 trường hợp: giai đoạn biến mất -isVanishing và nếu không thì sử dụng sprite chết đặc trưng của loại kẻ thù khác nhau.
     * TH1 - isVanishing: Sử dụng animation chết chung (mob_dead1, mob_dead2, mob_dead3).
     * TH2 - Nếu không, sử dụng sprite đặc trưng của từng loại quái vật - kẻ thù.
     * Nếu đang sống và di chuyển - 2 trường hợp:
     * Sử dụng animation di chuyển sang trái (khi lên hoặc sang trái)
     * Sử dụng animation di chuyển sang phải (khi xuống hoặc sang phải)
     */
    private void chooseSprite(){
        if (isDead) return;
        if (isShowingPoint) {
            _sprite = spriteList[scoreSprite];
        } else if (isKilled) {
            _sprite = spriteList[deadSprite];
            if (isVanishing) {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 100);
            }
        } else {
            switch (_direction) {
                case MovingEntity.directionUp:
                case MovingEntity.directionLeft:
                    _sprite = Sprite.movingSprite(spriteList[leftSprite], spriteList[leftSprite + 1],
                            spriteList[leftSprite + 2], animate, 20);
                    break;
                default:
                    _sprite = Sprite.movingSprite(spriteList[rightSprite], spriteList[rightSprite + 1],
                            spriteList[rightSprite + 2], animate, 20);
                    break;
            }
        }
    }
}
