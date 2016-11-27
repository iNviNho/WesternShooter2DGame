package game.window;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import game.client.Client;
import game.packet.Packet03Disconnect;
import game.player.Player;

public class Window implements WindowListener {

	private Client client;
	private Player player;
	
	public Window(Client client, Player player) {
		this.client = client;
		this.player = player;
	}

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowClosing(WindowEvent arg0) {
		Packet03Disconnect disconnectPacket = new Packet03Disconnect(this.player.name);
		this.client.sendData(disconnectPacket.getDataForSending());
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
	}

}
