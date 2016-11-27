package game.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import game.ammo.Ammo;
import game.ammo.EasyGunAmmo;
import game.game.Game;
import game.packet.Packet00Login;
import game.packet.Packet01Move;
import game.packet.Packet02SynchroPlayers;
import game.packet.Packet03Disconnect;
import game.packet.Packet04Projectile;
import game.player.Player;
import game.player.PlayerMP;
import game.projectile.Projectile;

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
		
		Packet00Login packet = new Packet00Login(player.name, player.x, player.y, player.direction);
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
			break;
		// now we have a new player
		case 00:
			Packet00Login packetLogin = new Packet00Login(packet.getData());
			this.loginPlayer(packetLogin);
			break;
		case 01:
			Packet01Move movePacket = new Packet01Move(packet.getData());
			this.movePlayer(movePacket);
			break;
		case 02:
			Packet02SynchroPlayers synchroPacket = new Packet02SynchroPlayers(packet.getData());
			this.synchroPlayers(synchroPacket);
			break;
		case 03:
			Packet03Disconnect disconnectPacket = new Packet03Disconnect(packet.getData());
			this.disconnectPlayer(disconnectPacket);
			break;
		case 04:
			Packet04Projectile projectilePacket = new Packet04Projectile(packet.getData());
			this.newProjectile(projectilePacket);
			break;
		}
	}

	private void newProjectile(Packet04Projectile projectilePacket) {
		
		for (PlayerMP pMP : this.game.connectedPlayers) {
			if (pMP.name.equals(projectilePacket.username)) {
				
				Projectile projectile = new Projectile(projectilePacket.xStarting, projectilePacket.yStarting, projectilePacket.dir, this.game.map, new EasyGunAmmo());
				pMP.projectiles.add(projectile);
				break;
			}
		}
		
	}

	private void disconnectPlayer(Packet03Disconnect disconnectPacket) {
		
		for (PlayerMP pMP : this.game.connectedPlayers) {
			if (pMP.name.equals(disconnectPacket.username)) {
				this.game.connectedPlayers.remove(pMP);
				break;
			}
		}
		
	}

	private void synchroPlayers(Packet02SynchroPlayers synchroPacket) {
		
		for (PlayerMP pMP: synchroPacket.players) {
			if (!pMP.name.equals(this.game.player.name)) {
				this.game.connectedPlayers.add(pMP);	
			}
		}
		
	}

	private void movePlayer(Packet01Move movePacket) {
		
		for (PlayerMP pMP : game.connectedPlayers) {
			
			if (pMP.name.equals(movePacket.username)) {
				pMP.x = movePacket.x;
				pMP.y = movePacket.y;
				pMP.direction = movePacket.dir;
				pMP.checkSprite();
				break;
			}
		}
		
	}
	
	private void loginPlayer(Packet00Login packetLogin) {
		
		System.out.println("Napojil sa na " + packetLogin.x + " | " + packetLogin.y);
		PlayerMP player = new PlayerMP(packetLogin.username, packetLogin.x, packetLogin.y, packetLogin.dir);
		game.connectedPlayers.add(player);
		
		System.out.println(packetLogin.username + " has connected ...");
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
			
			String port = JOptionPane.showInputDialog("Set port you want to connect to", "1234");
			
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
