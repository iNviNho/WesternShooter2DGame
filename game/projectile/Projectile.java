package game.projectile;

import game.ammo.Ammo;
import game.map.Map;
import game.screen.Screen;
import game.spritesheet.Sprite;
import game.tile.Tile;

public class Projectile {

	protected final int xStarting, yStarting;
	public double x, y;
	public double nx, ny;
	protected double angle;
	
	protected Map map;
	protected Ammo ammo;
	
	public Projectile(int x, int y, double dir, Map map, Ammo ammo) {
		this.xStarting = x;
		this.yStarting = y;
		this.x = x;
		this.y = y;
		this.angle = dir;
		
		this.map = map;
		this.ammo = ammo;
		
		this.nx = this.ammo.speed * Math.cos(this.angle);
		this.ny = this.ammo.speed * Math.sin(this.angle);		
		
//		Media hit = new Media("file:///C:/Users/Vladino/workspace/Game/res/sound/desert.mp3");
//		MediaPlayer mediaPlayer = new MediaPlayer(hit);
//		mediaPlayer.setVolume(0.09);
//		mediaPlayer.play();
	}
	
	public void update() {
		this.move();
	}
	
	public boolean checkCollision(int x, int y, int size, int xOffset, int yOffset) {
		
		int tileX = 0;
		int tileY = 0;
		
		boolean solid = false;
		
		for (int c = 0; c < 4; c++) {
			
			int xt = ( x - c % 2 * size + xOffset) >> 4;
			int yt = ( y - c / 2 * size + yOffset) >> 4;
			
			if (this.map.findTile(xt, yt).isSolid()) {
				solid = true;
			}
		}
		
		return solid;
	}

	protected void move() {
		this.x += this.nx;
		this.y += this.ny;
	}
	
	public void render(Screen screen) {
		
		int xp = ((int)this.x) - screen.xOffset - 3;
		int yp = ((int)this.y) - screen.yOffset - 3;
		for (int y = 0; y < this.ammo.sprite.sizeY; y++) {
			int ya = y + yp;
			for (int x = 0; x < this.ammo.sprite.sizeX; x++) {
				int xa = x + xp;
				
				if (xa < -15 || xa >= screen.width || ya < 0 || ya >= screen.height) break;
				if (xa < 0) xa = 0;
				
				if (this.ammo.sprite.pixels[x + y * this.ammo.sprite.sizeX] == 0xffc5c5c5) continue;
				screen.pixels[xa + ya * screen.width] = this.ammo.sprite.pixels[x + y * this.ammo.sprite.sizeX];
				
			}
		}
		
	}
	
}