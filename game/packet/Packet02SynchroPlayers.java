package game.packet;

import java.util.ArrayList;
import java.util.List;

import game.player.Player;
import game.player.PlayerMP;

public class Packet02SynchroPlayers extends Packet {

	public List<PlayerMP> players = new ArrayList<PlayerMP>();
	
	public Packet02SynchroPlayers(byte[] data) {
		super(02);
		this.data = data;
		this.parsePlayers();
	}
	
	public void parsePlayers() {
		
		String[] playersArray = this.readData(data).split("\\*");
		
		for (int i = 0; i < playersArray.length; i++) {
			
			String[] playerArray = playersArray[i].split("\\|");
			PlayerMP playerMP = new PlayerMP(playerArray[0], Integer.parseInt(playerArray[1]), Integer.parseInt(playerArray[2]), Integer.parseInt(playerArray[3]));
			this.players.add(playerMP);
		}
	}

}
