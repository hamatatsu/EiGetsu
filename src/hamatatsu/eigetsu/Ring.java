package hamatatsu.eigetsu;

import com.badlogic.gdx.utils.Array;

public class Ring extends Enemy {
	Array<Mover> bullet;
	private float SHOOT_INTERVAL = 3f; // 発射間隔 秒
	private float shootTimer; // 前回の発射からの経過時間
	// 大きさ
	public static final float WIDTH = 30;
	public static final float HEIGHT = 30;
	// 弾の大きさ
	public static final float BULLET_WIDTH = 30;
	public static final float BULLET_HEIGHT = 30;
	// 体力
	private int MAX_HP = 1;

	Ring(Array<Mover> eBulletArray, float x) {
		super(Assets.ringTexture);
		setSize(WIDTH, HEIGHT);
		setPosition(x, EiGetsuGame.HEIGHT);
		this.bullet = eBulletArray;
		SHOOT_INTERVAL /= PlayScreen.difficulty;
		MAX_HP *= Math.sqrt(PlayScreen.difficulty);
		super.hp = MAX_HP;
	}

	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
		shootTimer += delta;
		translate(0, -50 * delta);
		shoot();
	}

	@Override
	public void shoot() {
		if (shootTimer < SHOOT_INTERVAL) {
			return;
		}

		// 射撃タイマーをリセット
		shootTimer = 0;

		bullet.add(new EBullet1(getX() + getWidth() / 2, getY() + getHeight()
				/ 2, (float) Math.PI, BULLET_WIDTH, BULLET_HEIGHT));
	}
}
