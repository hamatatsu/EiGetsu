package hamatatsu.eigetsu;


public class Player extends Mover {
	public static final float MAX_SPEED = 500;
	private final float START_X = 360;
	private final float START_Y = 100;
	private final float SIZE_X = 100;
	private final float SIZE_Y = 100;
	
	public Player() {
		super(Assets.playerTexture);
		setSize(SIZE_X, SIZE_Y);
		setPosition(START_X - SIZE_X / 2, START_Y - SIZE_Y / 2);
	}
	
	public void move(float dx, float dy) {
	    translate(dx * MAX_SPEED, dy * MAX_SPEED);
	     
	    // 画面外に出ないようにする
	    if (getX() < 0) {
	        setX(0);
	    } else if (getX() > EiGetsuGame.WIDTH - SIZE_X) {
	        setX(EiGetsuGame.WIDTH - SIZE_X);
	    }
	    if (getY() < 0) {
	        setY(0);
	    } else if (getY() > EiGetsuGame.HEIGHT - SIZE_Y) {
	        setY(EiGetsuGame.HEIGHT - SIZE_Y);
	    }
		
	}

}
