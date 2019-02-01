package no.hvl.dat110.rpc;

import java.net.Socket;

import no.hvl.dat110.messaging.*;

public class RPCClient {

	private MessagingClient msgclient;
	private Connection connection;

	public RPCClient(String server, int port) {

		msgclient = new MessagingClient(server, port);
	}

	public void register(RPCStub remote) {
		remote.register(this);
	}

	public void connect() {

		// connect using the underlying messaging layer connection
		
		connection = msgclient.connect();

	}

	public void disconnect() {

		// disconnect/close the underlying messaging connection
		if (connection != null) {
			connection.close();
		}
	}

	public byte[] call(byte[] rpcrequest) {
		
		if(connection == null) {
		connect();
		}
		Message melding = new Message(rpcrequest);
		connection.send(melding);

		Message recieved = connection.receive();
		byte[] rpcreply = recieved.getData();
		/* 
		 * Make a remote call on the RPC server by sending a request message and receive
		 * a reply message 
		 * rpcrequest is the marshalled rpcrequest from the client-stub rpcreply is the
		 * rpcreply to be unmarshalled by the client-stub
		 * 
		 */
		return rpcreply;

	}

}
