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
import game.packet.Packet04Projectile;
import game.projectile.Projectile;
import game.screen.Screen;
import game.spritesheet.Sprite;
import game.tile.Tile;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player extends Entity {
	
	public Keyboard key;
	private Mouse mouse;	
	
	private Client client;
	
	private Map map;
	private Game game;
	
	// list of guns that player has
	public List<Gun> guns = new ArrayList<Gun>();
	private int activeGunId;
	// list of all ammos that player has
	public List<Ammo> ammos = new ArrayList<Ammo>();

	private boolean mayShoot = true;
	private int mayShootTick = 0;
	
	private boolean mayReload = true;
	private int mayReloadTick = 0;
	
	public Player(String name, int x, int y, Mouse mouse, Keyboard key, Game game, Map map, Client client) {
		super(name, x, y);
		this.mouse = mouse;
		this.key = key;
		this.game = game;
		this.map = map;
		this.client = client;
		
		this.addDefaultGun();
		this.addDefaultAmmo();
	}
	
	public void addDefaultGun() {
		Gun gun = new EasyGun();
		this.guns.add(gun);
		this.activeGunId = gun.id;
	}
	
	public void addDefaultAmmo() {
		Ammo ammo = new EasyGunAmmo();
		this.ammos.add(ammo);
	}	
	
	public void update() {
		
		this.checkMovementKey();
		this.checkShooting();
		this.checkReload();
		
	}
	
	private void checkMovementKey() {
		
		int x = 0;
		int y = 0;
		
		if (this.key.up) {
			y--;
			this.direction = 0;
		}
		if (this.key.down) {
			y++;
			this.direction = 2;
		}
		if (this.key.left) {
			x--;
			this.direction = 3;
		}
		if (this.key.right) {
			x++;
			this.direction = 1;
		}
		
		if (x != 0 || y!= 0) {
			this.tryMove(x, y);
		}
		
	}
	
	private void tryMove(int x, int y) {
		
		if (!this.checkCollision(x, 0)) {
			this.move(x, 0);
			this.sendMovePacket();
		}
		if (!this.checkCollision(0, y)) {
			this.move(0, y);
			this.sendMovePacket();
		}
		
		if (x != 0 || y != 0) {
			this.isWalking = true;
		} else {
			this.isWalking = false;
		}
		
		this.checkSprite();
	}
	
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	private void sendMovePacket() {
		Packet01Move packetMove = new Packet01Move(this.name, this.x, this.y, this.direction);
		client.sendData(packetMove.getDataForSending());		
	}
	
	private void checkShooting() {
		
		if (this.mouse.getButton() == 1 && this.mayShoot) {			
			this.shoot();
			this.mayShoot = false;
		}
		
		if (!this.mayShoot) {
			this.mayShootTick++;
			if (this.mayShootTick > 10) {
				this.mayShoot = true;
				this.mayShootTick = 0;
			}
		}
		
		this.updateProjectiles();
	}
	
	/**
	 * Performs shoot
	 */
	private void shoot() {	
		if (this.getActiveGun().hasAmmoInBin()) {
			
			double dx = (this.mouse.getX() - 48) - (this.game.screenWidth / 2);
			double dy = (this.mouse.getY() - 8) - (this.game.screenHeight / 2);
			
			double dir = Math.atan2(dy, dx);
			
			Projectile projectile = new Projectile(this.x + 16, this.y + 16, dir, this.map, this.getAmmoToActiveGun());
			this.projectiles.add(projectile);
			
			this.getActiveGun().inBin -= 1;
			
			MediaPlayer mediaPlayer = new MediaPlayer(this.getAmmoToActiveGun().media);
			mediaPlayer.setVolume(0.1);
			mediaPlayer.play();
			
			this.sendShootPacket(projectile);
		}
	}
	
	private void sendShootPacket(Projectile projectile) {
		Packet04Projectile projectilePacket = new Packet04Projectile(this.name, this.getAmmoToActiveGun().id, projectile.xStarting, projectile.yStarting, projectile.angle);
		this.client.sendData(projectilePacket.getDataForSending());
	}

	/**
	 * Reloading logic 
	 */
	private void checkReload() {
		if (this.key.r && this.mayReload) {			
			this.reloadGun();
			this.mayReload = false;
			return;
		}
		
		if (!this.mayReload) {
			this.mayReloadTick++;
			if (this.mayReloadTick > 10) {
				this.mayReload = true;
				this.mayReloadTick = 0;
			}
		}
	}
	
	/**
	 * Reloads active gun
	 */
	private void reloadGun() {
		
		if (this.getAmmoToActiveGun().number > this.getActiveGun().binSize) {
			this.getActiveGun().inBin = this.getActiveGun().binSize;
			this.getAmmoToActiveGun().number -= this.getActiveGun().binSize;
			this.getActiveGun().inBin = this.getActiveGun().binSize;
		} else {
			this.getActiveGun().inBin = this.getAmmoToActiveGun().number;
			
			this.getAmmoToActiveGun().number = 0;
		}
		
	}
	
	private void updateProjectiles() {
		for (int i = 0; i < this.projectiles.size(); i++) {
			
			Projectile projectile = this.projectiles.get(i); 
			
			if (!projectile.checkCollision((int) (projectile.x + projectile.nx), (int) (projectile.y + projectile.ny), 7, 5, 4)) {
				projectile.update();
			} else {
				this.projectiles.remove(i);
			}
		}
	}
	
	public void render(Screen screen) {
		this.renderPlayer(screen);
		this.renderProjectiles(screen);
	}
	
	protected void renderPlayer(Screen screen) {
		for (int pw = 0; pw < this.sprite.sizeX; pw++) {
			for (int ph = 0; ph < this.sprite.sizeY; ph++) {
				if (this.sprite.pixels[pw + ph * this.sprite.sizeX] != 0xffff00ff) {
						screen.pixels[ (pw + screen.xMapOffset) + (ph + screen.yMapOffset)  * screen.width] = this.sprite.pixels[pw + ph * this.sprite.sizeX];
				}
			}
		}
	}
	
	protected void renderProjectiles(Screen screen) {
		for (int i = 0; i < this.projectiles.size(); i++) {
			this.projectiles.get(i).render(screen);
		}
	}
	
	public void renderName(Graphics g, Game game) {		
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
		g.drawString(this.name.toUpperCase(), game.screenWidth / 2 - (this.name.length() * 10) + 45, game.screenHeight / 2 - 90);		
	}

	public void renderCurrentGunInfo(Graphics g, Game game) {
		g.drawString(this.getActiveGun().getBinInfo(this.getAmmoToActiveGun()) , 30, game.screenHeight - 200);
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

	public void checkSprite() {
		
		if (this.isWalking) {
			this.walkingTicks++;
			
			// if user is walking more than 1 minute we set walkingTicks
			// to 0 for some safety reason
			if (this.walkingTicks > 3600) {
				this.walkingTicks = 0;
			}
		} else {
			this.walkingTicks = 0;
		}
		
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
	
	/**
	 * Return currently active gun
	 * @return Gun
	 */
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
	
	/**
	 * Returns number of ammo in active guns bin
	 * @return int
	 */
	public int getAmmoInBin() {
		return this.getActiveGun().inBin;
	}
	
	/**
	 * Returns active gun ammo
	 * @return Ammo
	 */
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
}
