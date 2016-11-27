package game.player;

import java.awt.Graphics;

import game.game.Game;
import game.screen.Screen;
import game.spritesheet.Sprite;

public abstract class Entity {

	public String name;
	public int x;
	public int y;
	public int direction;
	protected Sprite sprite;

	public boolean isWalking = false;
	public int walkingTicks = 0;
	
	public Entity(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		
		this.direction = 2;
		this.sprite = Sprite.player_down;
	}
	
	public abstract void render(Screen screen);
	
	protected abstract void renderPlayer(Screen screen);
	
	protected abstract void renderProjectiles(Screen screen);
	
	public abstract void renderName(Graphics g, Game game);
	
	public abstract void checkSprite();

}
