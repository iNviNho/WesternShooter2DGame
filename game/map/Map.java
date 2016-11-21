package game.map;

import game.player.Player;
import game.screen.Screen;
import game.spritesheet.Sprite;
import game.tile.Tile;

public class Map {

	public int widthSize;
	public int heightSize;
	public Tile tiles[];
	public int animations;
	
	public Map(int widthSize,int heightSize) {
		this.widthSize = widthSize;
		this.heightSize = heightSize;
		this.tiles = new Tile[widthSize * heightSize];
		this.fulfilTiles();
	}
	
	public void fulfilTiles() {
		
		MapLoader mapLoader = new MapLoader("Map1");
		mapLoader.fillMap(this);
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		
		int x0 = (xScroll / 16) - 2;
		int x1 = (xScroll + screen.width + 16) / 16;
		
		int y0 = (yScroll / 16) - 1;
		int y1 = (yScroll + screen.height + 16) / 16;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				
				this.findTile(x, y).renderTile(x << 4, y << 4, screen);
				
			}
		}
		
		
		
		
		
		
		
//		int xTileOffset = (player.x + screen.xMapOffset) / 16;
//		int yTileOffset = (player.y + screen.yMapOffset) / 16;
//		
//		for (int h = 0; h < screen.height / 16; h++) {
//			for (int w = -1; w < screen.width / 16; w++) {
//			
//				this.findTile(w - xTileOffset, h - yTileOffset).renderTile(w, h, screen, player);
//				
////				screen.pixels[0 + 0 * screen.width] = 0xffc5c5c5;
//			}
//		}		
	}
	
	public void update() {
//		if (this.animations < 7500) {
//			this.animations++;
//		} else {
//			this.animations = 0;
//		}
	}
	
	/**
	 * Find tile by tile x and tile y pos
	 */
	public Tile findTile(int tileXpos, int tileYpos) {
		
		if (tileXpos < 0 || tileYpos < 0) return this.tiles[1]; 
		if (tileXpos >= 38  || tileYpos >= 10) return this.tiles[1]; 
		
		Tile tile;
		try {
			tile = this.tiles[tileXpos + tileYpos * this.widthSize];
		} catch (ArrayIndexOutOfBoundsException e) {
			tile = this.tiles[1];
		}
		
		return tile;
	}
	
}