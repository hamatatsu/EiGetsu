package hamatatsu.eigetsu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen implements Screen {
	private Game game;
	
	// ステージ
	private Stage stage;
	// スプライトバッチ
	private SpriteBatch batch;
	// グループ
	private Group guiGroup;
	
	// 現在のスコア
    private int score;
    // スコア表示ラベル
    private Label scoreLabel;
    // ゲームオーバーのフラグ
	
	
	public PlayScreen(Game game, int difficulty) {
		this.game = game;
		
		setupStage();
		setupGUI();
		
	}
	
    private void setupStage() {
        // ステージを作成
    	stage = new Stage(new FitViewport(720, 1280));
		Gdx.input.setInputProcessor(stage);
    }
    
    private void setupGUI() {
        guiGroup = new Group();
        stage.addActor(guiGroup);
     
        scoreLabel = new Label("SCORE: 0", Assets.skin);
        scoreLabel.setPosition(10, stage.getHeight() - scoreLabel.getPrefHeight());
        guiGroup.addActor(scoreLabel);
    }
    
// 得点
    private void addScore(int n) {
        score += n;
        scoreLabel.setText("SCORE: " + score);
    }
    
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// ステージ初期化
		stage.act();
		// ステージ描画
		stage.draw();
		
	}

	
	@Override
	public void show() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	@Override
	public void resize(int width, int height) {
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
		batch.dispose();
	}
	

}
