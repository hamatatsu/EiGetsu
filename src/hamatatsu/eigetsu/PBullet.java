package hamatatsu.eigetsu;


public class PBullet extends Mover {
	private static final float SPEED = 1000;

	public PBullet(float x, float y, float width, float height) {
		super(Assets.pBulletTexture);
		setSize(width, height);
		setPosition(x - width / 2, y - height / 2);
	}

	// 弾を移動
	@Override
	public void act(float delta) {
		if (PlayScreen.gameStatus > 0) {
			return;
		}
        translate(0, SPEED * delta);
    }
}
