package hamatatsu.eigetsu;


public class EBullet1 extends EBullet {
	float angle;
	float speed;

	EBullet1(float x, float y, float angle, float width, float height) {
		super(Assets.eBullet1Texture);
		setSize(width, height);
		setPosition(x - width / 2, y - height / 2);
		this.angle = angle;
		speed = 300 / PlayScreen.difficulty;
	}

	// 弾を移動
	@Override
	public void act(float delta) {
		translate2(speed * delta, angle);
	}
}
