package game.packet;

public class Packet03Disconnect extends Packet {

	public String username;
	
	public Packet03Disconnect(String username) {
		super(03);
		this.username = username;
	}
	
	public Packet03Disconnect(byte[] data) {
		super(03);
		String disconnectedUser = this.readData(data);
		
		this.data = data;
		this.username = disconnectedUser;
	}
	
	/**
	 * Used when sending packet
	 * @return
	 */
	public byte[] getDataForSending() {
		return ("03"+ this.username + "|").getBytes();
	}
	

}
