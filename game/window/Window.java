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

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Packet03Disconnect disconnectPacket = new Packet03Disconnect(this.player.name);
		this.client.sendData(disconnectPacket.getDataForSending());
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
