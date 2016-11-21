package game.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import game.spritesheet.Sprite;
import game.tile.BoxTile;
import game.tile.GrassTile;
import game.tile.VoidTile;

public class MapLoader {

	private String mapName;
	private Scanner txt;
	
	public MapLoader(String mapName) {		
		this.mapName = System.getProperty("user.dir") + "/res/maps/" + mapName + ".txt";
		this.loadMap();
	}
	
	private void loadMap() {
		
		try {		
			this.txt = new Scanner(new File(this.mapName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void fillMap(Map map) {
		
        for (int ms = 0; ms < map.heightSize; ms++) {
        	// for every new line in map
        	String line = txt.nextLine();
        	for (int ws = 0; ws < map.widthSize; ws++) {
        		
        		char current;
        		try {
        			current = line.charAt(ws);
				} catch (StringIndexOutOfBoundsException e) {
					map.tiles[ws + ms * map.widthSize] = new VoidTile(Sprite.voidSprite, true);
					continue;
				}
        		
        		if (current == 'g') {
        			map.tiles[ws + ms * map.widthSize] = new GrassTile(Sprite.grass, false);
        		} else if (current == 'v') {
        			map.tiles[ws + ms * map.widthSize] = new VoidTile(Sprite.voidSprite, true);
        		} else if (current == 'b') {
        			map.tiles[ws + ms * map.widthSize] = new BoxTile(Sprite.box, true);
        		}
        		
			}
		}
        
	}
	
}
