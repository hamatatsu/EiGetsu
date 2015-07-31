package hamatatsu.eigetsu;

import com.badlogic.gdx.utils.Array;


public class Player extends Mover {
	public static final float MAX_SPEED = 500;
	// 初期位置
	private final float START_X = 360;
	private final float START_Y = 100;
	// 大きさ
	static final float SIZE_X = 100;
	static final float SIZE_Y = 100;
	// 弾の大きさ
	static final float BULLET_X = 12;
	static final float BULLET_Y = 22;
	// 発射間隔
	private final float SHOOT_INTERVAL = 0.06f; //発射間隔　秒
	private float shootTimer; // 前回の発射からの経過時間
		
	// 弾
	private Array<PBullet> bulletArray = new Array<PBullet>();
	
	
	
	public Player(Array<PBullet> bulletArray) {
		super(Assets.playerTexture);
		setSize(SIZE_X, SIZE_Y);
		setPosition(START_X - SIZE_X / 2, START_Y - SIZE_Y / 2);
		this.bulletArray = bulletArray;
	}
	
	public void move(float dx, float dy) {
	    translate(dx * MAX_SPEED, dy * MAX_SPEED);
	     
	    // 画面外に出ないようにする
	    if (getX() < 0) {
	        setX(0);
	    } else if (getX() > EiGetsuGame.WIDTH - SIZE_X) {
	        setX(EiGetsuGame.WIDTH - SIZE_X);
	    }
	    if (getY() < 0) {
	        setY(0);
	    } else if (getY() > EiGetsuGame.HEIGHT - SIZE_Y) {
	        setY(EiGetsuGame.HEIGHT - SIZE_Y);
	    }
	}
	
	public void shoot() {
		if (shootTimer < SHOOT_INTERVAL) {
	        return;
	    }
		
	    // 射撃タイマーをリセット
	    shootTimer = 0;
	    
	    // 発射音を再生
	 
	    // 弾を作成
	    PBullet bullet = new PBullet(getX() + (SIZE_X - BULLET_X) / 2, getY() + SIZE_Y / 2 + BULLET_Y);
	 
	    // グループに追加
	    bulletArray.add(bullet);
	}
	
	@Override
	public void act(float delta) {
		shootTimer += delta;
	}

}
