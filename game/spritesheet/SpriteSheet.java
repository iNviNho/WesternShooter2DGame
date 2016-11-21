package game.spritesheet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public int sizex, sizey;
	public int[] pixels;

	public static SpriteSheet spritesheet = new SpriteSheet("/textures/spritesheet.png", 256, 256);
	
	public SpriteSheet(String path, int sizex, int sizey) {
		this.path = path;
		this.sizex = sizex;
		this.sizey = sizey;
		this.pixels = new int[sizex * sizey];
		this.load();
	}
	
	/**
	 * Convert image given in path to pixels
	 */
	private void load()  {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(this.path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, this.pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
