package hamatatsu.eigetsu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StartScreen implements Screen {
	private Stage stage;
	private OrthographicCamera camera;
	private Viewport viewport;

	boolean immortal = false; // 無敵モード
	int difficulty;
	Image background;
	Image bgTexture;
	Image level;
	Image levelTexture;
	Label levelText;
	TextButton immortalButton;
	ImageButton levelButton;

	public StartScreen(final Game game, int dif) {
		System.out.print(true);
		camera = new OrthographicCamera(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT);
		viewport = new FitViewport(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT,
				camera);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		bgTexture = new Image(Assets.bg0Texture);
		if (dif >= 5) {
			difficulty = dif + 8;
		} else {
			difficulty = dif;
		}

		// 背景を配置
		background = bgTexture;
		stage.addActor(background);

		// タイトルを配置
		Image title = new Image(Assets.titleTexture);
		title.setSize(500, 250);
		title.setPosition((stage.getWidth() - title.getWidth()) / 2,
				stage.getHeight() / 6 * 4);
		stage.addActor(title);

		// レベル表示
		level = new Image(Assets.crescentTexture);
		level.setSize(150, 150);
		level.setPosition((stage.getWidth() - level.getWidth()) / 2,
				stage.getHeight() / 2 - level.getHeight() / 2);
		levelText = new Label(null, Assets.skin);
		levelText.setSize(150, 150);
		levelText.setFontScale(3);
		levelText.setPosition(stage.getWidth() / 2 - 25, stage.getHeight() / 2
				- levelText.getHeight() / 2);

		// レベル変更ボタン
		TextButton upButton = new TextButton(null, Assets.skin);
		upButton.setSize(150, 200);
		upButton.setPosition(
				stage.getWidth() / 7 * 5 - upButton.getWidth() / 2,
				stage.getHeight() / 2 - upButton.getHeight() / 2);
		upButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				difficulty++;
				if (difficulty == -8) {
					difficulty = 1;
				} else if (difficulty <= 1) {
					difficulty = 2;
				}
			}
		});
		TextButton downButton = new TextButton(null, Assets.skin);
		downButton.setSize(150, 200);
		downButton.setPosition(stage.getWidth() / 7 * 2 - downButton.getWidth()
				/ 2, stage.getHeight() / 2 - downButton.getHeight() / 2);
		downButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				difficulty--;
				if (difficulty == 12) {
					difficulty = 3;
					bgTexture = new Image(Assets.bg0Texture);
				} else if (difficulty >= 3 && difficulty < 13) {
					difficulty = 2;
				} else if (difficulty < -9) {
					difficulty++;
				}
			}
		});

		// スタートボタンを作成
		TextButton startButton = new TextButton(null, Assets.skin);
		startButton.setSize(300, 100);
		startButton.setPosition(
				(stage.getWidth() - startButton.getWidth()) / 2,
				stage.getHeight() / 7 * 2 - startButton.getHeight() / 2);
		startButton.getLabel().setFontScale(1.5f);
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// try {
				// difficulty = Integer.parseInt(level.getText());
				// } catch(NumberFormatException e) {
				// level.setText("1");
				// difficulty = 1;
				// }
				if (difficulty > 13) {
					difficulty -= 8;
				} else if (difficulty < 1 && difficulty != -9) {
					difficulty = 1;
				} else if (difficulty > 3 && difficulty != 13) {
					difficulty = 3;
				} else
					switch (difficulty) {
					case -9:
						immortal = true;
						difficulty = 2;
						break;
					case 13:
						difficulty = 5;
						break;
					}
				// プレイ画面に移行
				game.setScreen(new PlayScreen(game, difficulty, immortal));
			}
		});

		// 終了ボタンを作成
		TextButton exitButton = new TextButton(null, Assets.skin);
		exitButton.setSize(300, 100);
		exitButton.setPosition((stage.getWidth() - exitButton.getWidth()) / 2,
				stage.getHeight() / 7 - exitButton.getHeight() / 2);
		exitButton.getLabel().setFontScale(1.5f);
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// アプリ終了
				Gdx.app.exit();
			}
		});

		// ラベル
		Image start = new Image(Assets.kaishiTexture);
		start.setSize(100, 50);
		start.setPosition((stage.getWidth() - start.getWidth()) / 2,
				stage.getHeight() / 7 * 2 - start.getHeight() / 2);
		Image exit = new Image(Assets.syuryouTexture);
		exit.setSize(100, 50);
		exit.setPosition((stage.getWidth() - exit.getWidth()) / 2,
				stage.getHeight() / 7 - exit.getHeight() / 2);
		Image i = new Image(Assets.iTexture);
		i.setSize(50, 50);
		i.setPosition(stage.getWidth() * 2 / 7 - i.getWidth() / 2,
				stage.getHeight() / 2 - i.getHeight() / 2);
		Image nan = new Image(Assets.nanTexture);
		nan.setSize(50, 50);
		nan.setPosition(stage.getWidth() * 5 / 7 - nan.getWidth() / 2,
				stage.getHeight() / 2 - nan.getHeight() / 2);

		// // 無敵ボタン
		// immortalButton = new TextButton("off", Assets.skin);
		// immortalButton.setSize(50, 50);
		// immortalButton.setPosition(
		// stage.getWidth() - immortalButton.getWidth(), 0);
		// immortalButton.addListener(new ChangeListener() {
		// @Override
		// public void changed(ChangeEvent event, Actor actor) {
		// immortal = !immortal;
		// if (immortal) {
		// immortalButton.setText("on");
		// } else {
		// immortalButton.setText("off");
		// }
		// }
		// });
		// Label immortalText = new Label("Immortal Mode", Assets.skin);
		// immortalText.setFontScale(1);
		// immortalText.setPosition(stage.getWidth() - immortalButton.getWidth()
		// - immortalText.getPrefWidth(), 0);

		// GUIを配置
		stage.addActor(startButton);
		stage.addActor(exitButton);
		stage.addActor(level);
		stage.addActor(levelText);
		stage.addActor(upButton);
		stage.addActor(downButton);
		stage.addActor(start);
		stage.addActor(exit);
		stage.addActor(i);
		stage.addActor(nan);
		// stage.addActor(immortalButton);
		// stage.addActor(immortalText);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		// レベル表示変更
		if (difficulty > 13) {
			levelTexture = new Image(Assets.bloodMoonTexture);
			bgTexture = new Image(Assets.bg3Texture);
			levelText.setText("" + (difficulty - 12));
		} else {
			levelText.setText("");
			if (difficulty == -9) {
				levelTexture = new Image(Assets.newMoonTexture);
			} else if (difficulty == 13) {
				levelTexture = new Image(Assets.bloodMoonTexture);
				bgTexture = new Image(Assets.bg3Texture);
			} else if (difficulty >= 3) {
				levelTexture = new Image(Assets.fullMoonTexture);
			} else if (difficulty == 2) {
				levelTexture = new Image(Assets.halfMoonTexture);
			} else if (difficulty <= 1) {
				levelTexture = new Image(Assets.crescentTexture);
			}
		}
		level.setDrawable(levelTexture.getDrawable());
		background.setDrawable(bgTexture.getDrawable());
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
