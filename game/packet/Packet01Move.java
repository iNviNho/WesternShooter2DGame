package game.packet;

public class Packet01Move extends Packet {

	public String username;
	public int x, y;
	
	/**
	 * Used when received packet
	 */
	public Packet01Move(byte[] data) {
		super(00);
		String[] dataArray = this.readData(data).split("\\|");
		
		this.data = data;
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}
	
	/**
	 * Used for sending packet
	 */
	public Packet01Move(String username, int x, int y) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }
	
	/**
	 * Used when sending packet
	 * @return
	 */
	public byte[] getDataForSending() {
		return ("01" + this.username + "|" + this.x + "|" + this.y + "| ").getBytes();
	}

}
