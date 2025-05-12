package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.gameUtil.StdRandom;

public class Ballom extends Enemy {

    /**
     * Constructor enemy.
     *
     * @param x la toa do.
     * @param y la toa do.
     * Point = 100 khi tiêu diet được Ballom
     * Khởi tạo mảng SpriteList với 8 sprite cụ thể
     * 3 sprite cho di chuyển sang trái (chỉ số 0-2)
     * 3 sprite cho di chuyển sang phải (chỉ số 3-5)
     * 1 sprite cho trạng thái chết (chỉ số 6)
     * 1 sprite cho hiển thị điểm (chỉ s 7)
     * super lại speed và thiết lập ngẫu nhiên tốc độ (base là 0.5, random thêm 0 - 1, làm tròn)
     * -> tạo ra cho ballom có tốc độ ngẫu nhieen và khác nhau cho mỗi lâần chơi.
     */
    public Ballom(int x, int y) {
        super(x, y);
        point = 100;
        super.spriteList = new Sprite[] {
                  Sprite.ballom_left1,
                  Sprite.ballom_left2,
                  Sprite.ballom_left3,
                  Sprite.ballom_right1,
                  Sprite.ballom_right2,
                  Sprite.ballom_right3,
                  Sprite.ballom_dead,
                  Sprite.ballom_score
          };
        super.speed = 0.5 + (double) ((int) (StdRandom.uniformDouble() * 10)) / 10;
        super.img = spriteList[0].getFxImage(); //sprite ban đầu là di chuyển sang trái
        super.spriteOffsetTop = 0; //Đặt offset va chạm là 0 (sử dụng toàn bộ kích thước sprite)
        super.spriteOffsetLeft = 0;
    }

    /**
     * Override phương thức calculateMove từ class Enemy
     * Điểm đặc biệt: ->>> Khiến cho Ballom có kiểu di chuyển không thể dự đoán được vì random hướng ngẫu nhiên
     * Nếu không có hướng di chuyển (_directon == 0) thì gọi calMoveForNone(); để tìm hướng di chuyển
     * Nếu đã có hướng di chuyển:
     * - Kiểm tra vị trí: if... -> Xác định xem ballom có ở gần trung tâm của trong ô không:
     * + Nếu đúng -> gọi super.randomMovement()  random di chuyển
     * -> xong sẽ gọi super.move để thực hiện di chuyển theo huonwgs đã xác định
     */
    @Override
    protected void calculateMove() {
        if (this._direction == 0){
            calMoveForNone();
        } else {
            if ((x + speed) % Sprite.SCALED_SIZE <= speed * 2 && (y + speed) % Sprite.SCALED_SIZE >= speed * 2) {
                super.randomMovement();
            }
            super.move();
        }
    }

    /**
     * Override phương thức canPass của Enemy để xác định Ballom có thể đi qua entity nào:
     * @param entity
     * @return entity.canBePassed(): Ballom chỉ có thể đi qua các entity được phép đi qua (không phải Brick, Wall, Bomb)
     * Ballom không có khả năng đi qua tường hoặc vật cản đặc biệt nào.
     */
    @Override
    protected boolean canPass(Entity entity) {
        return entity.canBePassed();
    }
}
