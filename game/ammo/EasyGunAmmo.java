package game.ammo;

import game.spritesheet.Sprite;

public class EasyGunAmmo extends Ammo {

	public EasyGunAmmo() {
		super(1, "Easy gun ammo", Sprite.easy_gun_ammo, 20, 7, 50, Ammo.easyGunAmmoSound);
	}

}
