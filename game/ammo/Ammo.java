package game.ammo;

import game.spritesheet.Sprite;

public class Ammo {

	public int id;
	private String name;
	public Sprite sprite;
	private int damage;
	public int speed;
	public int number;
	
	public Ammo(int id, String name, Sprite sprite, int damage, int speed, int number) {
		this.id = id;
		this.name = name;
		this.sprite = sprite;
		this.damage = damage;
		this.speed = speed;
		this.number = number;
	}

}
