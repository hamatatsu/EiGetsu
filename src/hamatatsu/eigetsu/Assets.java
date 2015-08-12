package hamatatsu.eigetsu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	private final static String FILE_MOVER_ATLAS = "mover/mover.atlas";
	private final static String FILE_BACKGROUND_ATLAS = "bg/background.atlas";
	private final static String FILE_SKIN = "skin/uiskin.json";
	
	private static AssetManager manager;
	
    private static TextureAtlas moverAtlas;
    private static TextureAtlas bgAtlas;
    
    public static TextureRegion playerTexture;
    public static TextureRegion pBulletTexture;
    public static TextureRegion ringTexture;
    public static TextureRegion eBullet1Texture;
    public static TextureRegion crescentTexture;
    public static TextureRegion bg1Texture;
    
    public static Skin skin;
    
    // 全てのアセットを読み込む
    public static void load() {
    	
        manager = new AssetManager();
        
        // 画像を読み込む
        manager.load(FILE_MOVER_ATLAS, TextureAtlas.class);
        manager.load(FILE_BACKGROUND_ATLAS, TextureAtlas.class);
        manager.load(FILE_SKIN, Skin.class);
        
        // 全てのアセットの読み込み完了まで待つ
        manager.finishLoading();
        
        // テクスチャアトラスを取得
        moverAtlas = manager.get(FILE_MOVER_ATLAS, TextureAtlas.class);
        bgAtlas = manager.get(FILE_BACKGROUND_ATLAS, TextureAtlas.class);
        
        //変数にセット
        playerTexture = moverAtlas.findRegion("player");
        pBulletTexture = moverAtlas.findRegion("pBullet");
        ringTexture = moverAtlas.findRegion("ring");
        eBullet1Texture = moverAtlas.findRegion("eBullet1");
        crescentTexture = moverAtlas.findRegion("crescent");
        bg1Texture = bgAtlas.findRegion("background1");
        
        skin = manager.get(FILE_SKIN, Skin.class);
        
    }

    // 全てのアセットを処分する
    public static void dispose() {
        manager.dispose();
    }
}
