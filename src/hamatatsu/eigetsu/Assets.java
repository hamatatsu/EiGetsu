package hamatatsu.eigetsu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	private final static String FILE_MOVER_ATLAS = "mover/mover.atlas";
	private final static String FILE_SKIN = "skin/uiskin.json";

	private static AssetManager manager;

	private static TextureAtlas moverAtlas;

	public static TextureRegion playerTexture;
	public static TextureRegion pBulletTexture;
	public static TextureRegion ringTexture;
	public static TextureRegion beeTexture;
	public static TextureRegion birdTexture;
	public static TextureRegion eBullet1Texture;
	public static TextureRegion eBullet2Texture;
	public static TextureRegion eBullet3Texture;
	public static TextureRegion crescentTexture;
	public static TextureRegion halfMoonTexture;
	public static TextureRegion fullMoonTexture;
	public static TextureRegion newMoonTexture;
	public static TextureRegion bloodMoonTexture;

	public static Texture titleTexture;
	public static Texture bg0Texture;
	public static Texture bg1Texture;
	public static Texture bg2Texture;
	public static Texture bg3Texture;

	public static Skin skin;

	// 全てのアセットを読み込む
	public static void load() {

		manager = new AssetManager();

		// 画像を読み込む
		manager.load(FILE_MOVER_ATLAS, TextureAtlas.class);
		manager.load(FILE_SKIN, Skin.class);
		manager.load("bg/title.png", Texture.class);
		manager.load("bg/background0.png", Texture.class);
		manager.load("bg/background1.png", Texture.class);
		manager.load("bg/background2.png", Texture.class);
		manager.load("bg/background3.png", Texture.class);

		// 全てのアセットの読み込み完了まで待つ
		manager.finishLoading();

		// テクスチャアトラスを取得
		moverAtlas = manager.get(FILE_MOVER_ATLAS, TextureAtlas.class);

		// 変数にセット
		playerTexture = moverAtlas.findRegion("player");
		pBulletTexture = moverAtlas.findRegion("pBullet");
		ringTexture = moverAtlas.findRegion("ring");
		beeTexture = moverAtlas.findRegion("bee");
		birdTexture = moverAtlas.findRegion("bird");
		eBullet1Texture = moverAtlas.findRegion("eBullet1");
		eBullet2Texture = moverAtlas.findRegion("eBullet2");
		eBullet3Texture = moverAtlas.findRegion("eBullet3");
		crescentTexture = moverAtlas.findRegion("crescent");
		halfMoonTexture = moverAtlas.findRegion("half_moon");
		fullMoonTexture = moverAtlas.findRegion("full_moon");
		newMoonTexture = moverAtlas.findRegion("new_moon");
		bloodMoonTexture = moverAtlas.findRegion("blood_moon");
		titleTexture = manager.get("bg/title.png");
		bg0Texture = manager.get("bg/background0.png");
		bg1Texture = manager.get("bg/background1.png");
		bg2Texture = manager.get("bg/background2.png");
		bg3Texture = manager.get("bg/background3.png");
		skin = manager.get(FILE_SKIN, Skin.class);

	}

	// 全てのアセットを処分する
	public static void dispose() {
		manager.dispose();
	}
}
