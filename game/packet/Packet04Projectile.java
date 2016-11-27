package game.packet;

public class Packet04Projectile extends Packet {

	public String username;
	public int xStarting, yStarting, dir, speed;
	
//	/**
//	 * Used when received packet
//	 */
//	public Packet01Move(byte[] data) {
//		super(00);
//		String[] dataArray = this.readData(data).split("\\|");
//		
//		this.data = data;
//		this.username = dataArray[0];
//		this.x = Integer.parseInt(dataArray[1]);
//		this.y = Integer.parseInt(dataArray[2]);
//		this.dir = Integer.parseInt(dataArray[3]);
//	}
//	
	/**
	 * Used for sending packet
	 */
	public Packet04Projectile(String username, int x, int y, int dir) {
        super(00);
        this.username = username;
//        this.x = x;
//        this.y = y;
        this.dir = dir;
    }
//	
//	/**
//	 * Used when sending packet
//	 * @return
//	 */
//	public byte[] getDataForSending() {
//		return ("01" + this.username + "|" + this.x + "|" + this.y + "|" + this.dir + "| ").getBytes();
//	}

}
