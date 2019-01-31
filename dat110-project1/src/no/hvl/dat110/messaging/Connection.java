package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

public class Connection {

	private DataOutputStream outStream; // for writing bytes to the TCP connection
	private DataInputStream inStream; // for reading bytes from the TCP connection
	private Socket socket; // socket for the underlying TCP connection

	public Connection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		// encapsulate the data contained in the message and write to the output stream
		try {
			outStream.write(message.encapsulate());			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Message receive() {
		// read a segment from the input stream and decapsulate into message
		Message message = new Message();
		byte[] recvbuf = new byte[MessageConfig.SEGMENTSIZE];

		try {
			inStream.read(recvbuf, 0, recvbuf.length);
			message.decapsulate(recvbuf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return message;

	}

	// close the connection by closing streams and the underlying socket
	public void close() {

		try {

			outStream.close();
			inStream.close();

			socket.close();
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}