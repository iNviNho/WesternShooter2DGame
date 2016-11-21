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
		
		
		
//		for (int x = 0; x < this.sprite.sizeX; x++) {
//			for (int y = 0; y < this.sprite.sizeY; y++) {
//				
//				// we setup x and y pos
//				int xpos;
//				// xpos rendering WAT
//				if ( player.x > 0) {
//					xpos = x + (w * 16) + (player.x % 16) ;
//				} else {
//					if ( (player.x % 16) == 0  ) {
//						xpos = x + (w * 16);
//					} else {
//						xpos = x + (w * 16) + (player.x % 16) + 16;
//					}
//				}
//				
//				
//				int ypos;
//				// ypos rendering WAT
//				if ( player.y > 0) {
//					ypos = y + (h * 16);
//				} else {
//					if ( (player.y % 16) == 0  ) {
//						ypos = y + (h * 16);
//					} else {
//						ypos = y + (h * 16) + (player.y % 16) + 16;
//					}
//				}
//				
////				xpos = x + w * 16;
//				if (xpos < 0 || xpos >= screen.width) break;
//				if (ypos < 0 || ypos >= screen.height) break;
//				
//				screen.pixels[xpos + ypos * screen.width] = this.sprite.pixels[x + y * this.sprite.sizeX];
//				
//			}
//		}
		
	}
	
	public Boolean isSolid() {
		return this.solid;
	}

}
