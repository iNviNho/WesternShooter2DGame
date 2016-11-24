package game.ammo;

import game.spritesheet.Sprite;

public class Ammo {

	public int id;
	private String name;
	private Sprite sprite;
	private int damage;
	public int number;
	
	public Ammo(int id, String name, Sprite sprite, int damage, int number) {
		this.id = id;
		this.name = name;
		this.sprite = sprite;
		this.damage = damage;
		this.number = number;
	}

}
