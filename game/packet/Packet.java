package game.packet;

public abstract class Packet {

	public byte[] data;
    private int packetId;

    public Packet(int packetId) {
    	this.packetId = packetId;
    }
    
    public int getPacketId() {
    	return this.packetId;
    }
    
    public String readData(byte[] data) {
    	String message = new String(data).trim();
    	return message.substring(2);
    }
    
}
