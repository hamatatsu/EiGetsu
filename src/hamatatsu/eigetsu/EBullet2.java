package hamatatsu.eigetsu;

import com.badlogic.gdx.math.MathUtils;

public class EBullet2 extends EBullet {
	float angle;
	final float maxAngle = (float) (Math.PI / 8);
	float speed;
	float width;
	float height;

	EBullet2(float x, float y, float width, float height) {
		super(Assets.eBullet2Texture);
		setSize(width, height);
		setPosition(x - width / 2, y - height / 2);
		speed = 300;
		this.width = width;
		this.height = height;
	}

	// 弾を移動
	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
		angle = MathUtils.atan2(
				PlayScreen.player.getX() + PlayScreen.player.getWidth() / 2
						- (getX() + width / 2), PlayScreen.player.getY()
						+ PlayScreen.player.getHeight() / 2
						- (getY() + height / 2));
		if (0 <= angle && angle < Math.PI - maxAngle) {
			angle = (float) (Math.PI - maxAngle);
		} else if (0 > angle && angle > -(Math.PI - maxAngle)) {
			angle = (float) -(Math.PI - maxAngle);
		}
		translate2(speed * delta, angle);
	}
}
