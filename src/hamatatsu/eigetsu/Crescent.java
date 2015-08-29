package hamatatsu.eigetsu;

import com.badlogic.gdx.utils.Array;


public class Crescent extends Enemy {
	Array<Mover> bullet;
	private float SHOOT_INTERVAL = 0.5f; //発射間隔 秒
	private float shootTimer; // 前回の発射からの経過時間
	// 弾の大きさ
	public static final float BULLET_WIDTH = 30;
	public static final float BULLET_HEIGHT = 30;
	// 体力
	private int MAX_HP = 1000;

	Crescent(Array<Mover> eBulletArray) {
		super(Assets.crescentTexture);
		setSize(200, 2000);
		setPosition(EiGetsuGame.WIDTH / 2 - getWidth() / 2, EiGetsuGame.HEIGHT);
		this.bullet = eBulletArray;
		SHOOT_INTERVAL /= PlayScreen.difficulty;
		MAX_HP *= PlayScreen.difficulty;
		super.hp = MAX_HP;
	}

	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
		if (super.hp > MAX_HP * 3 / 4) {
			
		}
		shootTimer += delta;
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
