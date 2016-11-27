package game.tile;

import game.player.Player;
import game.screen.Screen;
import game.spritesheet.Sprite;

public class Tile {
	
	public Sprite sprite;
	public boolean solid;
	
	public Tile(Sprite sprite, Boolean solid) {
		this.sprite = sprite;
		this.solid = solid;
	}
	
	public void renderTile(int xp, int yp, Screen screen) {
		xp = xp - screen.xOffset;
		yp = yp - screen.yOffset;
		for (int y = 0; y < this.sprite.sizeY; y++) {
			int ya = y + yp;
			for (int x = 0; x < this.sprite.sizeX; x++) {
				int xa = x + xp;
				
				if (xa < -15 || xa >= screen.width || ya < 0 || ya >= screen.height) break;
				if (xa < 0) xa = 0;
				
				screen.pixels[xa + ya * screen.width] = this.sprite.pixels[x + y * this.sprite.sizeX];
				
			}
		}
	}
	
	public Boolean isSolid() {
		return this.solid;
	}

}
