package game.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.game.Game;
import game.screen.Screen;

public class PlayerMP extends Player {
	
	public Player player;
	
	public PlayerMP(String name, int x, int y, Player player) {
		super(name, x, y);
		this.player = player;
	}
	
	public void render(Screen screen) {
		for (int pw = 0; pw < this.sprite.sizeX; pw++) {
			for (int ph = 0; ph < this.sprite.sizeY; ph++) {
				if (this.sprite.pixels[pw + ph * this.sprite.sizeX] != 0xffff00ff) {
					
						int xpos = this.x + pw - this.player.x + screen.xMapOffset;
						int ypos = this.y + ph - this.player.y + screen.yMapOffset;
						
						if (xpos < 0 || xpos >= screen.width) break;
						if (ypos < 0 || ypos >= screen.height) break;
						
						screen.pixels[ xpos + ypos  * screen.width] = this.sprite.pixels[pw + ph * this.sprite.sizeX];
				}
			}
		}
	}
	
	public void renderName(Graphics g, Game game) {		
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
		g.drawString(this.name.toUpperCase(), this.x * 3 - this.player.x * 3  + game.screenWidth / 2 - (this.name.length() * 10) + 45, this.y * 3 - this.player.y * 3  + game.screenHeight/ 2 - 50);		
	}

}
