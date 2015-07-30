package hamatatsu.eigetsu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EiGetsuGame extends Game {
	
	public static final float WIDTH = 720;
	public static final float HEIGHT = 1280;
	
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
