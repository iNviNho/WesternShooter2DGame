package game.screen;

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
	
}
