package game.spritesheet;

public class Sprite {

	public final int sizeX;
	public final int sizeY;
	public int[] pixels;
	private SpriteSheet sheet;
	private int spriteSheetX;
	private int spriteSheetY;

	public static Sprite grass = new Sprite(16, 16, 0, 0, SpriteSheet.spritesheet);
	public static Sprite box = new Sprite(16, 16, 16, 0, SpriteSheet.spritesheet);
	public static Sprite projectile = new Sprite(16, 16, 1, 1, SpriteSheet.spritesheet);
	public static Sprite cursor = new Sprite(16, 16, 1, 2, SpriteSheet.spritesheet);
	public static Sprite voidSprite = new Sprite(16, 16, 0, 16, SpriteSheet.spritesheet);
	public static Sprite voidSpriteAnimated = new Sprite(16, 16, 0, 2, SpriteSheet.spritesheet);
	
	public static Sprite player_up = new Sprite(32, 32, 0, 160, SpriteSheet.spritesheet);
	public static Sprite player_down = new Sprite(32, 32, 64, 160, SpriteSheet.spritesheet);
	public static Sprite player_left = new Sprite(32, 32, 96, 160, SpriteSheet.spritesheet);
	public static Sprite player_right = new Sprite(32, 32, 32, 160, SpriteSheet.spritesheet);
	
	public static Sprite player_up_1 = new Sprite(32, 32, 0, 192, SpriteSheet.spritesheet);
	public static Sprite player_up_2 = new Sprite(32, 32, 0, 224, SpriteSheet.spritesheet);
	
	public static Sprite player_down_1 = new Sprite(32, 32, 64, 192, SpriteSheet.spritesheet);
	public static Sprite player_down_2 = new Sprite(32, 32, 64, 224, SpriteSheet.spritesheet);
	
	public static Sprite player_right_1 = new Sprite(32, 32, 32, 192, SpriteSheet.spritesheet);
	public static Sprite player_right_2 = new Sprite(32, 32, 32, 224, SpriteSheet.spritesheet);
	
	public static Sprite player_left_1 = new Sprite(32, 32, 96, 192, SpriteSheet.spritesheet);
	public static Sprite player_left_2 = new Sprite(32, 32, 96, 224, SpriteSheet.spritesheet);

	
	public Sprite(int sizeX, int sizeY, int spriteSheetX, int spriteSheetY, SpriteSheet sheet) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.pixels = new int[this.sizeX * this.sizeY];
		this.spriteSheetX = spriteSheetX;
		this.spriteSheetY = spriteSheetY;
		this.sheet = sheet;
		this.load();
	}

	public Sprite(int sizeX, int sizeY, int color) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.pixels = new int[this.sizeX * this.sizeY];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < this.sizeX; y++) {
			for (int x = 0; x < this.sizeY; x++) {
				this.pixels[x + y * this.sizeX] = this.sheet.pixels[(x + this.spriteSheetX) + (y + this.spriteSheetY) * this.sheet.sizex];
			}
		}
	}

}
