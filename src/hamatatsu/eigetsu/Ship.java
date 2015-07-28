package hamatatsu.eigetsu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Ship extends Sprite {
	public static final float MAX_SPEED = 250;
	
	private final float SHOOT_INTERVAL = 0.06f; //発射間隔　秒
	private float shootTimer; // 前回の発射からの経過時間
	
	private Group pBulletGroup;
	
	public Ship(Group pBulletGroup) {
		super(Assets.playerTexture);
		this.pBulletGroup = pBulletGroup;
	}
	
	public void move(float dx, float dy) {
	    translate(dx, dy);
	     
	    // 画面外に出ないようにする
	}
}
