package game.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.ammo.Ammo;
import game.ammo.EasyGunAmmo;
import game.client.Client;
import game.game.Game;
import game.gun.EasyGun;
import game.gun.Gun;
import game.keyboard.Keyboard;
import game.map.Map;
import game.mouse.Mouse;
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
	private Mouse mouse;
	protected Sprite sprite;
	public boolean walking = false;
	public int animations = 0;
	public Map map;
	private Client client;
	
	public List<Gun> guns = new ArrayList<Gun>();
	public List<Ammo> ammos = new ArrayList<Ammo>();
	private int activeGunId;
	
	public Player(Keyboard key, Map map, String name) {
		this.x = new Random().nextInt(100);
		this.y = new Random().nextInt(100);
		this.key = key;
		this.sprite = Sprite.player_down;
		this.map = map;
		this.name = name;
	}
	
	public Player(String name, int x, int y) {
		this.sprite = Sprite.player_down;
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public Player(int x, int y, Mouse mouse, Keyboard key, Map map, Client client, String name) {
		this.x = x;
		this.y = y;
		this.mouse = mouse;
		this.key = key;
		this.sprite = Sprite.player_down;
		this.map = map;
		this.name = name;
		this.client = client;
		
		Gun gun = new EasyGun();
		this.guns.add(gun);
		this.activeGunId = gun.id;
		
		Ammo ammo = new EasyGunAmmo();
		this.ammos.add(ammo);
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
		
		if (this.mouse.getButton() == 1) {
			
			double dx = Mouse.getX() - (this.x * 3);
			double dy = Mouse.getY() - (this.y * 3);
			
			double dir = Math.atan2(dy, dx);
			
			this.shoot(this.x, this.y, dir);
		}

		if (x != 0 || y!= 0) {
			this.checkSprite();
			this.move(x, y);
		}
	}
	
	private void shoot(int x2, int y2, double dir2) {		
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
		Packet01Move packetMove = new Packet01Move(this.name, this.x, this.y, this.dir);
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
	
	public Gun getActiveGun() {
		
		Gun gun = null;
		for (int i = 0; i <= this.guns.size(); i++) {
			if (this.guns.get(i).id == this.activeGunId) {
				gun = this.guns.get(i);
				break;
			}
		}
		
		return gun;
	}
	
	public int getAmmoInBin() {
		return this.getActiveGun().inBin;
	}
	
	public Ammo getAmmoToActiveGun() {
		
		Ammo ammo = null;
		for (int i = 0; i <= this.ammos.size(); i++) {
			if (this.ammos.get(i).id == this.getActiveGun().acceptedAmmoId) {
				ammo = this.ammos.get(i);
				break;
			}
		}
		
		return ammo;
	}

	public void renderName(Graphics g, Game game) {		
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
		g.drawString(this.name.toUpperCase(), game.screenWidth / 2 - (this.name.length() * 10) + 45, game.screenHeight / 2 - 90);		
	}

	public void renderCurrentGunInfo(Graphics g, Game game) {
		g.drawString(this.getActiveGun().getBinInfo(this.getAmmoToActiveGun()) , 30, game.screenHeight - 200);
	}
}
