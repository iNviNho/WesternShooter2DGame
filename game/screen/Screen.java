package game.screen;

import game.spritesheet.Sprite;

public class Screen {

	public int width;
	public int height;
	public int[] pixels;
	public int xMapOffset;
	public int yMapOffset;
	public int xOffset, yOffset;
	public int yScroll;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		
		this.setMapOffset();
	}
	
	private void setMapOffset() {
		this.xMapOffset = this.width / 2;
		this.yMapOffset = this.height / 2;
	}
	
	public void clear() {
		for (int i = 0; i< this.pixels.length; i++) {
			this.pixels[i] = 0xff000000;
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void renderCursor(int x, int y) {
		
		if (x < 0 || y < 30 || (x / 3) > this.width - 4 || (y / 3) > this.height - 4) {
			return;
		}
		
		Sprite cursor = Sprite.cursor;
		
		for (int cursorX = 0; cursorX < cursor.sizeX; cursorX++) {
			for (int cursorY = 0; cursorY < cursor.sizeY; cursorY++) {
				if (cursor.pixels[cursorX + cursorY * cursor.sizeX] != 0xffc5c5c5) {
					this.pixels[( (x / 3) + cursorX - 8) + ( (y / 3) + cursorY - 8) * this.width] = cursor.pixels[cursorX + cursorY * cursor.sizeX];
				}
			}
		}
		
	}
	
}
