package game.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import game.game.Game;
import game.packet.Packet00Login;
import game.packet.Packet01Move;
import game.player.Player;
import game.player.PlayerMP;

public class Client extends Thread {

	private InetAddress ipAddress;
	public int port;
	private DatagramSocket socket;
	private Game game;	
	
	public Client(Game game, String ipAddress) {
		
		this.game = game;
		this.askAndSetPort();
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void login(Player player) {
		
		Packet00Login packet = new Packet00Login(player.name, player.x, player.y);
		this.sendData(packet.getDataForSending());
	}
	
	/**
	 * Listener for packets got from server
	 */
	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(packet);
		}
	}
	
	private void parsePacket(DatagramPacket packet) {
		
		byte[] data = packet.getData();
		String message = new String(data).trim();
		int packetId = this.getPacketIdFromPacket(message);
		
		switch (packetId) {
		default:
		// now we have a new player
		case 00:
			Packet00Login packetLogin = new Packet00Login(packet.getData());
			System.out.println(packetLogin.getUsername() + " has connected ...");
			
			PlayerMP player = new PlayerMP(packetLogin.getUsername(), packetLogin.getX(), packetLogin.getY(), game.player);
			player.x = 20;
			player.y = 20;
			game.connectedPlayers.add(player);
			break;
		case 01:
			Packet01Move movePacket = new Packet01Move(packet.getData());
			this.movePlayer(movePacket);
			break;
		}
	}
	
	private void movePlayer(Packet01Move movePacket) {
		
		for (PlayerMP pMP : game.connectedPlayers) {
			
			if (pMP.name.equals(movePacket.username)) {
				pMP.x = movePacket.x;
				pMP.y = movePacket.y;
				break;
			}
		}
		
	}

	private int getPacketIdFromPacket(String message) {
		int packetId = Integer.parseInt(message.substring(0, 2));
		return packetId;
	}
	
	/**
	 * Sending data to server
	 * @param data whatever data we send to server
	 */
	public void sendData(byte[] data) {
		
		DatagramPacket packet = new DatagramPacket(data, data.length, this.ipAddress, this.port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void askAndSetPort() {
		
		boolean isInteger = false;
		while(!isInteger) {
			
			String port = JOptionPane.showInputDialog("Set port you want to connect to");
			
			if (isInteger(port)) {
				this.port = Integer.parseInt(port);
				isInteger = true;
			} else {
				System.out.println("Port must be integer, please try again ...");
			}
		}
		
	}
	
	private boolean isInteger(String s) {
		int radix = 10;
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

}
