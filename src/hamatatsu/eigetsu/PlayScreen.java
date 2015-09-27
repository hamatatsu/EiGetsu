package hamatatsu.eigetsu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
	private Game game;
	public static int difficulty;// peacefull:0 easy:1 normal:2 hard:3

	// カメラ
	private OrthographicCamera camera;
	// ビューポート
	private Viewport viewport;
	// ステージ
	private Stage stage;
	private Stage pauseStage;
	// スプライトバッチ
	private SpriteBatch batch;

	// 背景
	public static Texture bgTexture;
	// グループ
	private Group guiGroup;
	// スプライト
	public static Mover player;
	private Array<Mover> pBulletArray;
	private Array<Mover> enemyArray;
	private Array<Mover> eBulletArray;
	// 現在のスコア
	private int score;
	// 残機
	private int life;
	// 敵
	private float spawnTimer;
	private float pSpawnTimer;
	private boolean isBoss;
	// GUI
	private Label scoreLabel;
	private TextButton pauseButton;
	private Touchpad joystick;
	// 無敵モード
	private boolean immortal;

	public static int gameStatus; // 0:Playing 1:Pause 2:GameOver

	public PlayScreen(Game game, int difficulty, boolean immortal) {
		this.game = game;
		this.immortal = immortal;
		PlayScreen.difficulty = difficulty;
		gameStatus = 0;
		life = 4;
		setupCamera();
		setupStage();
		setupSprite();
		setupGUI();
	}

	// カメラ設定
	private void setupCamera() {
		camera = new OrthographicCamera(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT);
		viewport = new FitViewport(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT,
				camera);
	}

	// ステージ設定
	private void setupStage() {
		// ステージを作成
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		pauseStage = new Stage(viewport);
		bgTexture = Assets.bg0Texture;
	}

	// スプライト設定
	private void setupSprite() {
		// スプライトバッチ作成
		batch = new SpriteBatch();
		pBulletArray = new Array<Mover>();
		player = new Player(pBulletArray);
		eBulletArray = new Array<Mover>();
		enemyArray = new Array<Mover>();
		// 敵
		spawnTimer = 0;
		isBoss = false;
	}

	// GUI設定
	private void setupGUI() {
		guiGroup = new Group();
		stage.addActor(guiGroup);

		// スコアラベル
		scoreLabel = new Label("SCORE: 0", Assets.skin);
		scoreLabel.setPosition(10, stage.getHeight() - scoreLabel.getHeight()
				- 5);
		scoreLabel.setFontScale(2);
		guiGroup.addActor(scoreLabel);

		// ポーズボタン
		pauseButton = new TextButton("PAUSE", Assets.skin);
		pauseButton.setSize(100, 50);
		pauseButton.setPosition(stage.getWidth() - pauseButton.getWidth(),
				stage.getHeight() - pauseButton.getHeight());
		pauseButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// 切り替え
				if (gameStatus == 0) {
					pause();
				} else {
					resume();
				}
			}
		});
		guiGroup.addActor(pauseButton);

		// ジョイスティック
		joystick = new Touchpad(0, Assets.skin);
		joystick.setSize(stage.getWidth() * 2 / 5, stage.getWidth() * 2 / 5);
		joystick.setPosition(stage.getWidth() * 3 / 10, 0);
		guiGroup.addActor(joystick);

		// 残機
		for (int i = 0; i < life - 1; i++) {
			Image lifeTexture = new Image(Assets.playerTexture);
			lifeTexture.setSize(40, 40);
			lifeTexture.setPosition(lifeTexture.getWidth() * i, 0);
			stage.addActor(lifeTexture);
		}

		// ボーズ画面
		// PAUSING
		TextButton pausing = new TextButton("PAUSING", Assets.skin);
		pausing.setSize(300, 100);
		pausing.setPosition((stage.getWidth() - pausing.getWidth()) / 2,
				stage.getHeight() / 3 * 2);
		pausing.getLabel().setFontScale(2.5f);
		// RESUME
		TextButton resumeButton = new TextButton("RESUME", Assets.skin);
		resumeButton.setSize(300, 100);
		resumeButton.setPosition(
				(stage.getWidth() - resumeButton.getWidth()) / 2,
				stage.getHeight() / 2);
		resumeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				resume();
			}
		});
		// go title
		TextButton titleButton = new TextButton("TITLE", Assets.skin);
		titleButton.setSize(300, 100);
		titleButton.setPosition(
				(stage.getWidth() - titleButton.getWidth()) / 2,
				stage.getHeight() / 3);
		titleButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new StartScreen(game, difficulty));
			}
		});
		pauseStage.addActor(pausing);
		pauseStage.addActor(resumeButton);
		pauseStage.addActor(titleButton);
	}

	@Override
	public void render(float delta) {
		// 画面初期化
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		stage.act();
		// 入力処理
		input();
		// 衝突処理
		checkCollision();
		// 敵の生成
		spawnEnemy(delta);
		// スプライト描画
		batch.begin();
		batch.draw(bgTexture, 0, 0);
		// プレイヤー
		if (gameStatus != 2) {
			player.act(delta);
			player.draw(batch);
		}
		// 弾
		if (pBulletArray != null) {
			for (int pBulletIndex = 0; pBulletIndex < pBulletArray.size; pBulletIndex++) {
				Mover pBullet = pBulletArray.get(pBulletIndex);
				pBullet.act(delta);
				if (pBullet.getY() > EiGetsuGame.HEIGHT) {
					pBulletArray.removeIndex(pBulletIndex);
				} else {
					pBullet.draw(batch);
				}
			}
		}
		// 敵弾
		if (eBulletArray != null) {
			for (int eBulletIndex = 0; eBulletIndex < eBulletArray.size; eBulletIndex++) {
				Mover eBullet = eBulletArray.get(eBulletIndex);
				eBullet.act(delta);
				if (eBullet.getY() < 0 - eBullet.getHeight()
						|| eBullet.getY() > EiGetsuGame.HEIGHT
						|| eBullet.getX() < 0 - eBullet.getWidth()
						|| eBullet.getX() > EiGetsuGame.WIDTH) {
					eBulletArray.removeIndex(eBulletIndex);
				} else {
					eBullet.draw(batch);
				}
			}
		}
		// 敵
		if (enemyArray != null) {
			for (int enemyIndex = 0; enemyIndex < enemyArray.size; enemyIndex++) {
				Mover enemy = enemyArray.get(enemyIndex);
				enemy.act(delta);
				if (enemy.getY() < 0 - enemy.getHeight()) {
					enemyArray.removeIndex(enemyIndex);
				} else {
					enemy.draw(batch);
				}
			}
		}
		batch.end();

		// ステージ描画
		stage.draw();
		for (int i = 0; i < life; i++) {

		}
		if (gameStatus == 1) {
			pauseStage.act();
			pauseStage.draw();
		}
	}

	// 入力処理
	private void input() {
		if (gameStatus > 0) {
			return;
		}
		// 移動距離
		float dx = 0, dy = 0, delta = Gdx.graphics.getDeltaTime();

		// ジョイスティックで移動
		float joystickX = joystick.getKnobPercentX();
		float joystickY = joystick.getKnobPercentY();
		dx = delta * joystickX;
		dy = delta * joystickY;

		// タッチで発射
		if (Gdx.input.isTouched()) {
			player.shoot();
		}

		// 減速
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			dx *= 0.5;
			dy *= 0.5;
		}

		// 移動
		player.move(dx, dy);

		// 発射
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			player.shoot();
		}

	}

	// 衝突判定
	private void checkCollision() {
		if (gameStatus < 0) {
			return;
		}
		if (eBulletArray != null) {
			for (int eBulletIndex = 0; eBulletIndex < eBulletArray.size; eBulletIndex++) {
				Mover eBullet = eBulletArray.get(eBulletIndex);

				// 敵弾と自機
				if (Collision.isCollided(eBullet, player)) {
					eBulletArray.removeIndex(eBulletIndex);
					if (!immortal) {
						life--;
						// ゲームオーバー
						if (life == 0) {
							GameOver();
						} else if (life > 0) {
						stage.getActors().removeIndex(
								stage.getActors().size - 1);
						}
					}
					return;
				}
			}

			if (pBulletArray != null) {
				for (int pBulletIndex = 0; pBulletIndex < pBulletArray.size; pBulletIndex++) {
					Mover pBullet = pBulletArray.get(pBulletIndex);
					if (enemyArray != null) {
						for (int enemyIndex = 0; enemyIndex < enemyArray.size; enemyIndex++) {
							Mover enemy = enemyArray.get(enemyIndex);
							// 敵と弾
							if (Collision.isCollided(enemy, pBullet)) {
								// スコアを加算
								if (!immortal) {
									addScore(100);
								}
								// 敵の体力を減少
								enemy.hp--;
								// 敵を削除
								if (enemy.hp == 0) {
									enemyArray.removeIndex(enemyIndex);
								}
								// 弾を削除
								pBulletArray.removeIndex(pBulletIndex);
								break;
							}
						}
					}
				}
			}
		}
	}

	// 得点
	private void addScore(int n) {
		score += n;
		scoreLabel.setText("SCORE: " + score);
	}

	// 敵の生成
	private void spawnEnemy(float delta) {
		if (spawnTimer > 30) {
			if (!isBoss) {
				bgTexture = Assets.bg1Texture;
				enemyArray.add(new Crescent(eBulletArray));
				isBoss = true;
			}
			// クリア判定
			if (enemyArray.size == 0) {
				GameClear();
			}
			return;
		}
		if (gameStatus == 1) {
			return;
		}
		spawnTimer += delta;
		if (spawnTimer % 1 > pSpawnTimer) {

			pSpawnTimer = spawnTimer % 1f;
			return;
		}
		switch (MathUtils.random(2)) {
		case 0:
			enemyArray.add(new Ring(eBulletArray, MathUtils.random(0,
					EiGetsuGame.WIDTH - Ring.WIDTH)));
			break;
		case 1:
			enemyArray.add(new Bee(eBulletArray, MathUtils.random(0,
					EiGetsuGame.WIDTH - Bee.WIDTH)));
			break;
		case 2:
			enemyArray.add(new Bird(eBulletArray, MathUtils.random(0,
					EiGetsuGame.WIDTH - Ring.WIDTH)));
			break;
		}
		pSpawnTimer = 0;
	}

	public void GameOver() {
		if (gameStatus == 2) {
			return;
		}

		Label gameOverLabel = new Label("GAME OVER", Assets.skin);
		gameOverLabel.setFontScale(2);
		gameOverLabel.setPosition(
				stage.getWidth() / 2 - gameOverLabel.getPrefWidth() / 2,
				stage.getHeight() / 2 - gameOverLabel.getPrefHeight() / 2);
		stage.addActor(gameOverLabel);

		scoreLabel
				.setPosition(stage.getWidth() / 2 - scoreLabel.getPrefWidth()
						/ 2,
						stage.getHeight() * 2 / 5 - scoreLabel.getPrefHeight()
								/ 2);

		// 3秒後にスタート画面に戻る
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				game.setScreen(new StartScreen(game, difficulty));
			}
		}, 3);
		gameStatus = 2;
	}

	private void GameClear() {
		gameStatus = -1;

		Label gameClearLabel = new Label("GAME CLEAR", Assets.skin);
		gameClearLabel.setFontScale(2);
		gameClearLabel.setPosition(
				stage.getWidth() / 2 - gameClearLabel.getPrefWidth() / 2,
				stage.getHeight() / 2 - gameClearLabel.getPrefHeight() / 2);
		stage.addActor(gameClearLabel);

		scoreLabel
				.setPosition(stage.getWidth() / 2 - scoreLabel.getPrefWidth()
						/ 2,
						stage.getHeight() * 2 / 5 - scoreLabel.getPrefHeight()
								/ 2);

		// 3秒後にスタート画面に戻る
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				game.setScreen(new StartScreen(game, difficulty));
			}
		}, 7);

	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		pauseButton.setText("RESUME");
		gameStatus = 1;
		Gdx.input.setInputProcessor(pauseStage);
	}

	@Override
	public void resume() {
		pauseButton.setText("PAUSE");
		gameStatus = 0;
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}
}
