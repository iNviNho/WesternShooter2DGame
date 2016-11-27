package game.map;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	
	public int getXChars() {
		
		int xChars = 0;
		
		char current;
		String line = this.txt.nextLine();
		for (int i = 0; i < 10000; i++) {
		
			try {
				current = line.charAt(i);
			} catch (StringIndexOutOfBoundsException e) {
				return i;
			}
			
		}
		
		return 10000;
	}

	public int getYChars() throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(this.mapName));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count + 1;
	    } finally {
	        is.close();
	    }
	}
	
	public void fillMap(Map map) {
		this.loadMap();
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
