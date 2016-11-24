package game.gun;

import game.ammo.Ammo;
import game.spritesheet.Sprite;

public class PistolGun extends Gun {

	public PistolGun(int id, String name, Sprite sprite, int inBin, int maxBins, int binSize, int firerate, int acceptedAmmoId) {
		super(id, name, sprite, inBin, maxBins, binSize, firerate, acceptedAmmoId);
	}
}
