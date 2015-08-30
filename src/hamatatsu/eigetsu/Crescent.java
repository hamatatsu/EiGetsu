package hamatatsu.eigetsu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;


public class Crescent extends Enemy {
	Array<Mover> bullet;
	private float SHOOT_INTERVAL = 1f; //発射間隔 秒
	private float shootTimer; // 前回の発射からの経過時間
	// 大きさ
	public static final float WIDTH = 200;
	public static final float HEIGHT = 200;
	// 弾の大きさ
	public static final float BULLET1_WIDTH = 50;
	public static final float BULLET1_HEIGHT = 50;
	public static final float BULLET2_WIDTH = 20;
	public static final float BULLET2_HEIGHT = 20;
	public static final float BULLET3_WIDTH = 30;
	public static final float BULLET3_HEIGHT = 30;
	// 体力
	private int MAX_HP = 600;
	// shoot
	private float shootAngle;
	// move
	private int moveMode;
	private float px, py;
	private float movex, movey;
	private float angle;
	private float temp;
	private float wait;
	// move1
	private int move1Mode;
	private float[] locationX = new float[3];
	private float[] locationY = new float[3];

	Crescent(Array<Mover> eBulletArray) {
		super(Assets.crescentTexture);
		setSize(WIDTH, HEIGHT);
		setPosition(EiGetsuGame.WIDTH / 2 - getWidth() / 2, EiGetsuGame.HEIGHT);
		this.bullet = eBulletArray;
		MAX_HP *= Math.sqrt(PlayScreen.difficulty);
		super.hp = MAX_HP;
		
		moveMode = 0;
		move1Mode = 0;
		pUpdate();
		locationX[0] = EiGetsuGame.WIDTH / 4      - WIDTH / 2;
		locationY[0] = EiGetsuGame.HEIGHT / 6 * 5 - HEIGHT / 2;
		locationX[1] = EiGetsuGame.WIDTH / 4 * 3  - WIDTH / 2;
		locationY[1] = EiGetsuGame.HEIGHT / 6 * 5 - HEIGHT / 2;
		locationX[2] = EiGetsuGame.WIDTH / 2      - WIDTH / 2;
		locationY[2] = EiGetsuGame.HEIGHT / 6 * 4 - HEIGHT / 2;
		movex = locationX[2] - getX();
		movey = locationY[0] - getY();
	}

	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
		
		switch(moveMode) {
		case 0:
			move0(delta);
			break;
		case 1:
			move1(delta);
			if (super.hp < MAX_HP * 2 / 3) {
				moveMode = 0;
				Sprite next = new Sprite(Assets.eBullet1Texture);
				next.setPosition(EiGetsuGame.WIDTH / 2 - getWidth() / 2, EiGetsuGame.HEIGHT);
				set(next);
				PlayScreen.bgTexture = Assets.bg1Texture;
				angle = 0;
				movey = locationY[0] - getY();
				movex = locationX[2] - getX();
				pUpdate();
				wait = 0;
			}
			break;
		case 2:
			move2(delta);
			if (super.hp < MAX_HP / 3) {
				moveMode = 0;
				Sprite next = new Sprite(Assets.eBullet1Texture);
				next.setPosition(EiGetsuGame.WIDTH / 2 - getWidth() / 2, EiGetsuGame.HEIGHT);
				set(next);
				PlayScreen.bgTexture = Assets.bg1Texture;
				angle = 0;
				movey = locationY[2] - getY();
				movex = locationX[2] - getX();
				pUpdate();
			}
			break;
		case 3:
			move3(delta);
			break;
		}
		System.out.println(super.hp);
	}
	
	private void move0(float delta) {
		angle += MathUtils.PI / 2 * delta;
		temp = MathUtils.sin(angle);
		setPosition(px + movex * temp, py + movey * temp);

		if (angle >= MathUtils.PI / 2) {
			if (super.hp > MAX_HP * 2 / 3) {
				moveMode = 1;
			}
			else if (super.hp > MAX_HP / 3) {
				moveMode = 2;
			}
			else if (super.hp < MAX_HP / 3) {
				moveMode = 3;
			}
			movex = locationX[0] - getX();
			movey = locationY[0] - getY();
			pUpdate();
			angle = 0;
		}
	}

	private void shoot1() {
		
	    for (int i = -2; i < 3; i++) {
	    	shootAngle = (float) (Math.atan2(PlayScreen.player.getX() + PlayScreen.player.getWidth() / 2 - (getX() + WIDTH / 2), 
					PlayScreen.player.getY() + PlayScreen.player.getHeight() / 2 - (getY() + HEIGHT / 2)) + i * Math.PI / 10);
	    	bullet.add(new EBullet1(getX() + getWidth() / 2, getY() + getHeight() / 2, shootAngle, BULLET1_WIDTH, BULLET1_HEIGHT));
	    	
	    }
	}
	
	private void shoot1Next() {
		for (int i = 1; i < PlayScreen.difficulty; i++) {
    		Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    shoot1();
                }
            }, 0.3f / PlayScreen.difficulty * i);
    	}
	}
	
	private void move1(float delta) {
		if (wait < 0.3f) {
			wait += delta;
			return;
		}
		angle += MathUtils.PI / 2 * delta;
		temp = MathUtils.sin(angle);
		setPosition(px + movex * temp, py + movey * temp);
		
		if (angle >= MathUtils.PI / 2) {
			move1Mode = (move1Mode + 1) % 3;
			movex = locationX[move1Mode] - getX();
			movey = locationY[move1Mode] - getY();
			pUpdate();
			angle = 0;
			shoot1();
			shoot1Next();
			wait = 0;
		}
	}
	
	private void move2(float delta) {
		if (wait < 0.2f) {
			wait += delta;
			return;
		}
		angle += MathUtils.PI / 2 * delta;
		temp = MathUtils.sin(angle);
		setPosition(px + movex * temp, py + movey * temp);
		
		shoot2(delta);
		
		if (angle >= MathUtils.PI / 2) {
			move1Mode = (move1Mode + 1) % 2;
			movex = locationX[move1Mode] - getX();
			movey = locationY[move1Mode] - getY();
			pUpdate();
			angle = 0;
			
			wait = 0;
		}
	}

	private void shoot2(float delta) {

		shootTimer += delta;
		if (shootTimer < SHOOT_INTERVAL / 5) {
	        return;
	    }
	    // 射撃タイマーをリセット
	    shootTimer = 0;
	    
	    for (int i = 0; i < PlayScreen.difficulty; i++) {
	    	bullet.add(new EBullet2(getX() + getWidth() / 2, getY() + getHeight() / 2, BULLET1_WIDTH, BULLET1_HEIGHT));
	    }
	    
	}
	
	private void move3(float delta) {

		shootTimer += delta;
		if (shootTimer < SHOOT_INTERVAL) {
	        return;
	    }
		
		angle += MathUtils.PI * 6 * delta;
		
	    // 射撃タイマーをリセット
	    shootTimer = 0;
	    
	    bullet.add(new EBullet3(bullet, getX() + getWidth() / 2, getY() + getHeight() / 2, angle, BULLET3_WIDTH, BULLET3_HEIGHT));
	}
	
	private void pUpdate() {
		px = getX();
		py = getY();
	}
}
