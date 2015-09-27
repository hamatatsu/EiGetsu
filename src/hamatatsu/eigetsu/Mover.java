package hamatatsu.eigetsu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mover extends Sprite {
	public int hp;

	Mover(TextureRegion textureregion) {
		super(textureregion);
	}

	public void move(float dx, float dy) {
	}

	public void shoot() {
	}

	public void act(float delta) {
	}

	public void translate2(float speed, float angle) {
		translate((float) Math.sin(angle) * speed, (float) Math.cos(angle)
				* speed);
	}
}
