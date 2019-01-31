package no.hvl.dat110.messaging;
import no.hvl.dat110.messaging.MessageConfig;

public class Message {

	private byte[] payload;

	public Message(byte[] payload) {
		//check for length within boundary
		if (payload.length <= MessageConfig.SEGMENTSIZE) {
			this.payload = payload; 
		} 
	}

	public Message() {
		super();
	}

	public byte[] getData() {
		return this.payload; 
	}

	public byte[] encapsulate() {
		
		byte[] encoded;
		
		// encapsulate/encode the payload of the message
		encoded = new byte[MessageConfig.SEGMENTSIZE];
		encoded[0] = (byte) payload.length;
		for (int i=0; i < payload.length; i++) {
			encoded[i + 1] = payload[i];
		}
		
		return encoded;
		
	}

	public void decapsulate(byte[] received) {
		int length = (int) received[0];
		payload = new byte[length];
		// decapsulate data in received and put in payload
		
		for (int i=0; i < payload.length; i++) {
			payload[i] = received[i +1];
		}
	  
	}
}
