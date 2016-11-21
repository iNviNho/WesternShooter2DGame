package game.packet;

public class Packet00Login extends Packet {

	private byte[] data;
	private String username;
	private int x, y;
	
	/**
	 * Used when received packet
	 */
	public Packet00Login(byte[] data) {
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
	public Packet00Login(String username, int x, int y) {
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
		return ("00" + this.username + "|" + this.getX() + "|" + this.getY()).getBytes();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
