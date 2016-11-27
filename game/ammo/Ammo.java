package game.ammo;

import game.spritesheet.Sprite;
import javafx.scene.media.Media;

public class Ammo {

	public int id;
	private String name;
	public Sprite sprite;
	private int damage;
	public int speed;
	public int number;
	public Media media;
	
	public static final Media easyGunAmmoSound = new Media("file:///C:/Users/Vladino/workspace/WesternShooter/res/sounds/desert.mp3");
	
	public Ammo(int id, String name, Sprite sprite, int damage, int speed, int number, Media media) {
		this.id = id;
		this.name = name;
		this.sprite = sprite;
		this.damage = damage;
		this.speed = speed;
		this.number = number;
		this.media = media;
	}

}
