package game.gun;

import game.ammo.Ammo;
import game.spritesheet.Sprite;

public class Gun {

	public int id;
	private String name;
	private Sprite sprite;
	public int inBin;
	public int binSize;
	private int maxBins;
	private int firerate;
	public int acceptedAmmoId;
	
	public Gun(int id, String name, Sprite sprite, int inBin, int maxBins, int binSize, int firerate, int acceptedAmmoId) {
		this.id = id;
		this.name = name;
		this.sprite = sprite;
		this.inBin = inBin;
		this.maxBins = maxBins;
		this.binSize = binSize;
		this.firerate = firerate;
		this.acceptedAmmoId = acceptedAmmoId;
	}

	public String getBinInfo(Ammo ammo) {
		
		String info = Integer.toString(this.inBin) + " / "+ Integer.toString(ammo.number);
		
		return info;
	}

	public boolean hasAmmoInBin() {
		if (this.inBin > 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
