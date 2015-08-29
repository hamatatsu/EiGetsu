package hamatatsu.eigetsu;

import com.badlogic.gdx.utils.Array;


public class Player extends Mover {
	public static final float MAX_SPEED = 500;
	// 初期位置
	private final float START_X = 360;
	private final float START_Y = 300;
	// 大きさ
	static final float WIDTH = 70;
	static final float HEIGHT = 70;
	// 弾の大きさ
	private final float BULLET_WIDTH = 12;
	private final float BULLET_HEIGHT = 22;
	// 発射間隔
	private final float SHOOT_INTERVAL = 0.06f; //発射間隔 秒
	private float shootTimer; // 前回の発射からの経過時間
		
	// 弾
	private Array<Mover> bulletArray;
	
	public Player(Array<Mover> pBulletArray) {
		super(Assets.playerTexture);
		setSize(WIDTH, HEIGHT);
		setPosition(START_X - WIDTH / 2, START_Y - HEIGHT / 2);
		this.bulletArray = pBulletArray;
	}
	
	public void move(float dx, float dy) {
	    translate(dx * MAX_SPEED, dy * MAX_SPEED);
	     
	    // 画面外に出ないようにする
	    if (getX() < 0) {
	        setX(0);
	    } else if (getX() > EiGetsuGame.WIDTH - WIDTH) {
	        setX(EiGetsuGame.WIDTH - WIDTH);
	    }
	    if (getY() < 0) {
	        setY(0);
	    } else if (getY() > EiGetsuGame.HEIGHT - HEIGHT) {
	        setY(EiGetsuGame.HEIGHT - HEIGHT);
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
	    PBullet bullet = new PBullet(getX() + WIDTH / 2, getY() + HEIGHT / 2,
	    		BULLET_WIDTH, BULLET_HEIGHT);
	 
	    // グループに追加
	    bulletArray.add(bullet);
	}
	
	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
		shootTimer += delta;
	}

}
