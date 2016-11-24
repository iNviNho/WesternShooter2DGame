package game.packet;

public class Packet00Login extends Packet {

	private byte[] data;
	public String username;
	public int x, y, dir;
	
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
	public Packet00Login(String username, int x, int y, int dir) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
	
	/**
	 * Used when sending packet
	 * @return
	 */
	public byte[] getDataForSending() {
		return ("00" + this.username + "|" + this.x + "|" + this.y + "|" + this.dir).getBytes();
	}
}
