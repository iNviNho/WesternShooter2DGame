package game.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.game.Game;
import game.projectile.Projectile;
import game.screen.Screen;
import game.spritesheet.Sprite;

public class PlayerMP extends Entity {
	
	public PlayerMP(String name, int x, int y, int direction) {
		super(name, x, y);
	}

	public void render(Screen screen) {
		this.renderPlayer(screen);
		this.renderProjectiles(screen);
	}

	protected void renderPlayer(Screen screen) {
		for (int pw = 0; pw < this.sprite.sizeX; pw++) {
			for (int ph = 0; ph < this.sprite.sizeY; ph++) {
				if (this.sprite.pixels[pw + ph * this.sprite.sizeX] != 0xffff00ff) {

//					System.out.println("Renderujem ho na " + this.x + "| " +this.y);
					
//						System.out.println(this.x + pw - 100 + screen.xMapOffset);
						int xpos = this.x + pw - (screen.xOffset + (screen.width / 2)) + screen.xMapOffset;
						int ypos = this.y + ph - (screen.yOffset + (screen.height / 2)) + screen.yMapOffset;
						
						if (xpos < 0 || xpos >= screen.width) break;
						if (ypos < 0 || ypos >= screen.height) break;
						
						screen.pixels[ xpos + ypos  * screen.width] = this.sprite.pixels[pw + ph * this.sprite.sizeX];
				}
			}
		}
	}
	
	protected void renderProjectiles(Screen screen) {
		for (int i = 0; i < this.projectiles.size(); i++) {
			this.projectiles.get(i).render(screen);
		}
	}

	@Override
	public void renderName(Graphics g, Game game) {
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
		g.drawString(this.name.toUpperCase(), this.x * 3 - this.x * 3  + game.screenWidth / 2 - (this.name.length() * 10) + 45, this.y * 3 - this.y * 3  + game.screenHeight/ 2 - 50);		
	}

	public void checkSprite() {
		if (this.direction == 0) {
			this.sprite = Sprite.player_up;
			if (this.isWalking) {
				if (this.walkingTicks % 20 > 10) {
					this.sprite = Sprite.player_up_1;
				} else {
					this.sprite = Sprite.player_up_2;
				}
			}
		}
		if (this.direction == 1) {
			this.sprite = Sprite.player_right;
			if (this.isWalking) {
				if (this.walkingTicks % 20 > 10) {
					this.sprite = Sprite.player_right_1;
				} else {
					this.sprite = Sprite.player_right_2;
				}
			}
		}
		if (this.direction == 2) {
			this.sprite = Sprite.player_down;
			if (this.isWalking) {
				if (this.walkingTicks % 20 > 10) {
					this.sprite = Sprite.player_down_1;
				} else {
					this.sprite = Sprite.player_down_2;
				}
			}
		}
		if (this.direction == 3) {
			this.sprite = Sprite.player_left;
			if (this.isWalking) {
				if (this.walkingTicks % 20 > 10) {
					this.sprite = Sprite.player_left_1;
				} else {
					this.sprite = Sprite.player_left_2;
				}
			}
		}
	}
	
	public void updateProjectiles() {
		for (int i = 0; i < this.projectiles.size(); i++) {
			
			Projectile projectile = this.projectiles.get(i); 
			
			if (!projectile.checkCollision((int) (projectile.x + projectile.nx), (int) (projectile.y + projectile.ny), 7, 5, 4)) {
				projectile.update();
			} else {
				this.projectiles.remove(i);
			}
		}
	}

}
