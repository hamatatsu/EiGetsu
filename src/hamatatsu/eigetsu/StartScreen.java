package hamatatsu.eigetsu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StartScreen implements Screen {
	private Stage stage;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	int difficulty = 1; // 初期難易度イージー
	boolean immortal = false; // 無敵モード
	TextField level;
	TextButton immortalButton;
	
	public StartScreen(final Game game) {
		System.out.print(true);
		camera = new OrthographicCamera(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT);
		viewport = new FitViewport(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT, camera);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		// 背景を配置
		Image background = new Image(Assets.bg0Texture);
		stage.addActor(background);
		
		// タイトルを配置
		Image title = new Image(Assets.titleTexture);
		title.setSize(500, 250);
		title.setPosition((stage.getWidth()  - title.getWidth()) / 2, stage.getHeight() / 6 * 4);
		stage.addActor(title);
		
		// スタートボタンを作成
		TextButton startButton = new TextButton("START", Assets.skin);
		startButton.setSize(220, 50);
        startButton.setPosition((stage.getWidth()  - startButton.getWidth()) / 2, stage.getHeight() / 6 * 2);
        startButton.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		try {
        			difficulty = Integer.parseInt(level.getText());
        		} catch(NumberFormatException e) {
        			level.setText("1");
        			difficulty = 1;
        		}
        		// プレイ画面に移行
                game.setScreen(new PlayScreen(game, difficulty, immortal));
            }
        });
        
        // 終了ボタンを作成
        TextButton exitButton = new TextButton("EXIT", Assets.skin);
		exitButton.setSize(220, 50);
        exitButton.setPosition((stage.getWidth()  - exitButton.getWidth()) / 2, stage.getHeight() / 6 * 1);
        exitButton.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
                // アプリ終了
                Gdx.app.exit();
            }
        });
        
        // レベル変更
        level = new TextField("1", Assets.skin);
		level.setSize(60, 40);
        level.setPosition((stage.getWidth()  - level.getWidth()) / 2, stage.getHeight() / 2);
        Label levelText = new Label("level", Assets.skin);
        levelText.setFontScale(2);
        levelText.setPosition((stage.getWidth()  - level.getWidth()) / 2 - levelText.getPrefWidth(), stage.getHeight() / 2);
        
        // 無敵ボタン
        immortalButton = new TextButton("off", Assets.skin);
        immortalButton.setSize(50, 50);
        immortalButton.setPosition(stage.getWidth() - immortalButton.getWidth(), 0);
        immortalButton.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
                immortal = !immortal;
                if (immortal) {
                	immortalButton.setText("on");
                } else {
                	immortalButton.setText("off");
                }
            }
        });
        Label immortalText = new Label("Immortal Mode", Assets.skin);
        immortalText.setFontScale(1);
        immortalText.setPosition(stage.getWidth() - immortalButton.getWidth() - immortalText.getPrefWidth(), 0);
        
        
        // GUIを配置
		stage.addActor(startButton);
		stage.addActor(exitButton);
		stage.addActor(level);
		stage.addActor(levelText);
		stage.addActor(immortalButton);
		stage.addActor(immortalText);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void show() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void resume() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void hide() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	
}
