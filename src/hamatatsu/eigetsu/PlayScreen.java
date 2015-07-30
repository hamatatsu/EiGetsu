package hamatatsu.eigetsu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
	private Game game;
	
	// カメラ
	private OrthographicCamera camera;
	// ビューポート
	private Viewport viewport;
	// ステージ
	private Stage stage;
	// スプライトバッチ
	private SpriteBatch batch;
	// グループ
	private Group guiGroup;
	// スプライト
	private Mover player;
	private Array bullets;
	// 現在のスコア
    private int score;
    // GUI
    private Label scoreLabel;
    private TextButton menuButton;
    // ゲームオーバー
    private boolean isGameOver = false;
	
	
	public PlayScreen(Game game, int difficulty) {
		this.game = game;
		
		setupCamera();
		setupStage();
		setupSprite();
		setupGUI();
		
	}
	
// カメラ設定
	private void setupCamera() {
    	camera = new OrthographicCamera(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT);
    	viewport = new FitViewport(EiGetsuGame.WIDTH, EiGetsuGame.HEIGHT, camera);
	}
	
// ステージ設定
    private void setupStage() {
        // ステージを作成
    	stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
    }
    
// スプライト設定
    private void setupSprite() {
    	// スプライトバッチ作成
    	batch = new SpriteBatch();
    	player = new Player();
    }
    
// ＧＵＩ設定
    private void setupGUI() {
        guiGroup = new Group();
        stage.addActor(guiGroup);
        
        // スコアラベル
        scoreLabel = new Label("SCORE: 0", Assets.skin);
        scoreLabel.setPosition(10, stage.getHeight() - scoreLabel.getHeight() - 5);
        scoreLabel.setFontScale(2);
        guiGroup.addActor(scoreLabel);
        
        // メニューボタン
        menuButton = new TextButton("MENU", Assets.skin);
        menuButton.setSize(100, 50);
        menuButton.setPosition(stage.getWidth() - menuButton.getWidth(), stage.getHeight() - menuButton.getHeight());
        guiGroup.addActor(menuButton);
    }
    
// 得点
    private void addScore(int n) {
        score += n;
        scoreLabel.setText("SCORE: " + score);
    }
    
	@Override
	public void render(float delta) {
		if (! isGameOver) {
			// 入力処理
			input();
		}
		// 描画処理
		draw();
		
		
	}
	
	// 描画処理
	private void draw() {
		// 画面初期化
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// ステージ初期化
		stage.act();
		
		// スプライト描画
		batch.begin();
		batch.draw(Assets.bg1Texture, 0, 0);
		player.draw(batch);
		batch.end();
		
		// ステージ描画
		stage.draw();
	}
	
	// 入力処理
	private void input() {
		// 移動距離
        float dx = 0, dy = 0, delta = Gdx.graphics.getDeltaTime();
     
        // 矢印キーで移動
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx = -delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx = delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy = delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy = -delta;
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
	
	@Override
	public void show() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
		batch.dispose();
	}
	

}
