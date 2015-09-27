package hamatatsu.eigetsu;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Collision {
	private static Circle aCircle = new Circle();
	private static Circle bCircle = new Circle();

	// 衝突判定
	public static boolean isCollided(Mover a, Mover b) {
		Vector2 ao = new Vector2(a.getX() + a.getWidth() / 2, a.getY()
				+ a.getHeight() / 2);
		float ar = a.getWidth() / 2;

		Vector2 bo = new Vector2(b.getX() + b.getWidth() / 2, b.getY()
				+ b.getHeight() / 2);
		float br = 5;

		aCircle.set(ao, ar);
		bCircle.set(bo, br);

		return aCircle.overlaps(bCircle);
	}
}