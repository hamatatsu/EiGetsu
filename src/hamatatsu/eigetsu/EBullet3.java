package hamatatsu.eigetsu;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class EBullet3 extends EBullet {
	float angle;
	float speed;
	float exTimer;
	Array<Mover> bullet;
	// 弾の大きさ
	public static final float BULLET_WIDTH = 15;
	public static final float BULLET_HEIGHT = 15;
	// 発射基点
	private float preX, preY;
	boolean isShoot = false;

	EBullet3(Array<Mover> eBulletArray, float x, float y, float angle,
			float width, float height) {
		super(Assets.eBullet3Texture);
		setSize(width, height);
		setPosition(x - width / 2, y - height / 2);
		this.angle = angle;
		this.bullet = eBulletArray;
		speed = 150;
	}

	// 弾を移動
	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
		translate2(speed * delta, angle);
		int branch = 4;
		branch *= PlayScreen.difficulty;
		if (exTimer > 2 && exTimer < 3) {
			if (!isShoot) {
				isShoot = true;
				preX = getX();
				preY = getY();
			}
			for (int i = -branch / 2; i <= branch / 2; i++) {
				bullet.add(new EBullet1(preX + getWidth() / 2, preY
						+ getHeight() / 2, ((float) i / (branch / 2))
						* MathUtils.PI, BULLET_WIDTH, BULLET_HEIGHT));
			}
		}
		exTimer += delta;
	}
}
