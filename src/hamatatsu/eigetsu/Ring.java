package hamatatsu.eigetsu;

import com.badlogic.gdx.utils.Array;


public class Ring extends Enemy {
	Array<Mover> bullet;
	private float SHOOT_INTERVAL = 3f; //発射間隔 秒
	private float shootTimer; // 前回の発射からの経過時間
	// 弾の大きさ
	private final float BULLET_WIDTH = 30;
	private final float BULLET_HEIGHT = 30;

	Ring(Array<Mover> eBulletArray, float x) {
		super(Assets.ringTexture);
		setSize(50, 50);
		setPosition(x, EiGetsuGame.HEIGHT);
		this.bullet = eBulletArray;
		SHOOT_INTERVAL /= PlayScreen.difficulty;
	}

	@Override
	public void act(float delta) {
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
	    
		bullet.add(new EBullet1(getX() + getWidth() / 2, getY() + getHeight() / 2, (float)Math.PI, BULLET_WIDTH, BULLET_HEIGHT));
	}
}
