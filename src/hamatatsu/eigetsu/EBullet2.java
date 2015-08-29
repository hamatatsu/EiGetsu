package hamatatsu.eigetsu;

import com.badlogic.gdx.math.MathUtils;


public class EBullet2 extends EBullet {
	float angle;
	float speed;

	EBullet2(float x, float y, float width, float height) {
		super(Assets.eBullet1Texture);
		setSize(width, height);
		setPosition(x - width / 2, y - height / 2);
		angle = MathUtils.atan2(PlayScreen.player.getX() + PlayScreen.player.getWidth() / 2 - x, 
				PlayScreen.player.getY() + PlayScreen.player.getHeight() / 2 - y);
		speed = 300;
	}

	// 弾を移動
	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
		translate2(speed * delta, angle);
	}
}
