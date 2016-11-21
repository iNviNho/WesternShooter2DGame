package game.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import game.client.Client;
import game.game.Game;
import game.keyboard.Keyboard;
import game.map.Map;
import game.packet.Packet01Move;
import game.screen.Screen;
import game.spritesheet.Sprite;
import game.tile.Tile;

public class Player {

	public String name;
	public int x;
	public int y;
	public int dir = 2;
	public Keyboard key;
	protected Sprite sprite;
	public boolean walking = false;
	public int animations = 0;
	public Map map;
	private Client client;
	
	public Player(Keyboard key, Map map, String name) {
		this.x = new Random().nextInt(100);
		this.y = new Random().nextInt(100);
		this.key = key;
		this.sprite = Sprite.player_down;
		this.map = map;
		this.name = name;
	}
	
	public Player(int x, int y, Keyboard key, Map map, Client client, String name) {
		this.x = x;
		this.y = y;
		this.key = key;
		this.sprite = Sprite.player_down;
		this.map = map;
		this.name = name;
		this.client = client;
	}
	
	public Player(String name, int x, int y) {
		this.sprite = Sprite.player_down;
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		
		if (this.animations < 7500) {
			this.animations++;
		} else {
			this.animations = 0;
		}
		
		int y = 0;
		int x = 0;
		
		if (this.key.up) {
			y--;
			this.dir = 0;
		}
		if (this.key.down) {
			y++;
			this.dir = 2;
		}
		if (this.key.left) {
			x--;
			this.dir = 3;
		}
		if (this.key.right) {
			x++;
			this.dir = 1;
		}

		if (x != 0 || y!= 0) {
			this.move(x, y);
			this.checkSprite();
		}
	}
	
	public void move(int x, int y) {
		
		if (!this.checkCollision(x, 0)) {
			this.x = this.x + x;
			this.handleMove();
			
		}
		if (!this.checkCollision(0, y)) {
			this.y = this.y + y;
			this.handleMove();
		}
		
		if (x != 0 || y != 0) {
			this.walking = true;
		} else {
			this.walking = false;
		}
		
	}
	
	private void handleMove() {
		Packet01Move packetMove = new Packet01Move(this.name, this.x, this.y);
		System.out.println(this.name + " position " + this.x + " | " + this.y );
		client.sendData(packetMove.getDataForSending());		
	}

	private boolean checkCollision(int x, int y) {
		
		int tileX = 0;
		int tileY = 0;
		
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			tileX = (this.x + x + (c % 2 * 16 + 7)) / 16;
			tileY = (this.y + y + (c / 2 * 12 + 19)) / 16;
			
			Tile tile = this.map.findTile(tileX, tileY);
			if (tile.isSolid()) {
				solid = true;
			}
		}
		
		return solid;
	}
	
	public void render(Screen screen) {
		for (int pw = 0; pw < this.sprite.sizeX; pw++) {
			for (int ph = 0; ph < this.sprite.sizeY; ph++) {
				if (this.sprite.pixels[pw + ph * this.sprite.sizeX] != 0xffff00ff) {
						screen.pixels[ (pw + screen.xMapOffset) + (ph + screen.yMapOffset)  * screen.width] = this.sprite.pixels[pw + ph * this.sprite.sizeX];
				}
			}
		}
	}
	
	public void checkSprite() {
		
		if (this.dir == 0) {
			this.sprite = Sprite.player_up;
			if (this.walking) {
				if (this.animations % 20 > 10) {
					this.sprite = Sprite.player_up_1;
				} else {
					this.sprite = Sprite.player_up_2;
				}
			}
		}
		if (this.dir == 1) {
			this.sprite = Sprite.player_right;
			if (this.walking) {
				if (this.animations % 20 > 10) {
					this.sprite = Sprite.player_right_1;
				} else {
					this.sprite = Sprite.player_right_2;
				}
			}
		}
		if (this.dir == 2) {
			this.sprite = Sprite.player_down;
			if (this.walking) {
				if (this.animations % 20 > 10) {
					this.sprite = Sprite.player_down_1;
				} else {
					this.sprite = Sprite.player_down_2;
				}
			}
		}
		if (this.dir == 3) {
			this.sprite = Sprite.player_left;
			if (this.walking) {
				if (this.animations % 20 > 10) {
					this.sprite = Sprite.player_left_1;
				} else {
					this.sprite = Sprite.player_left_2;
				}
			}
		}

	}

	public void renderName(Graphics g, Game game) {		
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
		g.drawString(this.name.toUpperCase(), game.screenWidth / 2 - (this.name.length() * 10) + 45, game.screenHeight / 2 - 50);		
	}
}
