package game.map;

import java.io.IOException;

import game.player.Player;
import game.screen.Screen;
import game.spritesheet.Sprite;
import game.tile.Tile;

public class Map {

	public int widthSize;
	public int heightSize;
	public Tile tiles[];
	public int animations;
	
	public Map(String mapName) {
		
		MapLoader mapLoader = new MapLoader("Map1");
		
		try {
			this.widthSize = mapLoader.getXChars();
			this.heightSize = mapLoader.getYChars();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.tiles = new Tile[this.widthSize * this.heightSize];
		
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
		if (tileXpos >= this.widthSize  || tileYpos >= this.heightSize) return this.tiles[1]; 
		
		Tile tile;
		try {
			tile = this.tiles[tileXpos + tileYpos * this.widthSize];
		} catch (ArrayIndexOutOfBoundsException e) {
			tile = this.tiles[1];
		}
		
		return tile;
	}
	
}