package hamatatsu.eigetsu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ShootingGame extends Game {
	
	public final static float WIDTH = 720;
	public final static float HEIGHT = 1280;
	
	OrthographicCamera camera;
	Viewport viewport;
	
	@Override
	public void create () {
		Assets.load();
		setScreen(new StartScreen(this));
	}

	@Override
	public void dispose() {
		Assets.dispose();
	}
}
