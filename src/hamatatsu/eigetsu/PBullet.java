package hamatatsu.eigetsu;

import com.badlogic.gdx.Gdx;

public class PBullet extends Mover {
	private static final float SPEED = 1000;

	public PBullet(float x, float y) {
		super(Assets.enemy1Texture);
		setSize(Player.BULLET_X, Player.BULLET_Y);
		setPosition(x, y);
	}

	@Override
	public void act(float delta) {
        super.act(delta);
 
        // 弾を移動
        translate(0, SPEED * Gdx.graphics.getDeltaTime());
    }
}
