package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class Sensor extends RPCStub {

	private byte RPCID = 1;
	
	public int read() {
		
		int temp;
		byte[] req = RPCUtils.marshallVoid(RPCID);
		byte[] resp = rmiclient.call(req);
		temp = RPCUtils.unmarshallInteger(resp);
		
		return temp;
	}
	
}
