package game.packet;

public class Packet04Projectile extends Packet {

	public String username;
	public int ammoId;
	public int xStarting, yStarting;
	public double dir;
	
	/**
	 * Used when received packet
	 */
	public Packet04Projectile(byte[] data) {
		super(04);
		String[] dataArray = this.readData(data).split("\\|");
		
		this.data = data;
		this.username = dataArray[0];
		this.ammoId = Integer.parseInt(dataArray[1]);
		this.xStarting = Integer.parseInt(dataArray[2]);
		this.yStarting = Integer.parseInt(dataArray[3]);
		this.dir = Double.parseDouble(dataArray[4]);
	}
	
	/**
	 * Used for sending packet
	 */
	public Packet04Projectile(String username, int ammoId, int x, int y, double dir) {
        super(00);
        this.username = username;
        this.ammoId = ammoId;
        this.xStarting = x;
        this.yStarting = y;
        this.dir = dir;
    }
	
	/**
	 * Used when sending packet
	 * @return
	 */
	public byte[] getDataForSending() {
		return ("04" + this.username + "|" + this.ammoId + "|" + this.xStarting + "|" + this.yStarting + "|" + this.dir + "| ").getBytes();
	}

}
