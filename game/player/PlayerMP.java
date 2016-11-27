package game.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.game.Game;
import game.screen.Screen;

public class PlayerMP extends Entity {
	
	public PlayerMP(String name, int x, int y, int direction) {
		super(name, x, y);
	}

	public void render(Screen screen) {
		this.renderPlayer(screen);
	}

	@Override
	protected void renderPlayer(Screen screen) {
		for (int pw = 0; pw < this.sprite.sizeX; pw++) {
			for (int ph = 0; ph < this.sprite.sizeY; ph++) {
				if (this.sprite.pixels[pw + ph * this.sprite.sizeX] != 0xffff00ff) {
					
						int xpos = this.x + pw - this.x + screen.xMapOffset;
						int ypos = this.y + ph - this.y + screen.yMapOffset;
						
						if (xpos < 0 || xpos >= screen.width) break;
						if (ypos < 0 || ypos >= screen.height) break;
						
						screen.pixels[ xpos + ypos  * screen.width] = this.sprite.pixels[pw + ph * this.sprite.sizeX];
				}
			}
		}
	}

	@Override
	protected void renderProjectiles(Screen screen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderName(Graphics g, Game game) {
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
//		g.drawString(this.name.toUpperCase(), this.x * 3 - this.x * 3  + game.screenWidth / 2 - (this.name.length() * 10) + 45, this.y * 3 - this.y * 3  + game.screenHeight/ 2 - 50);		
	}

	@Override
	public void checkSprite() {
		
	}

}
