package game.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right;
	
	public void update() {
		this.up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
		this.down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
		this.left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
		this.right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}	
}