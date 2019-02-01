package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class RPCUtils {

	public static byte[] marshallString(byte rpcid, String str) {

		int lengde = str.getBytes().length;
		byte[] encoded = new byte[1 + lengde];

		try {

			// marshall RPC identifier and string into byte array

			encoded[0] = rpcid;
			for (int i = 0; i < lengde; i++) {
				encoded[i + 1] = str.getBytes()[i];
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return encoded;

	}

	public static String unmarshallString(byte[] data) {

		String decoded = "";

		// unmarshall String contained in data into decoded
		try {
			decoded = new String(Arrays.copyOfRange(data, 1, data.length));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return decoded;
	}

	public static byte[] marshallVoid(byte rpcid) {

		// marshall RPC identifier in case of void type
		byte[] encoded = new byte[1];
		encoded[0] = rpcid;

		return encoded;
	}

	public static void unmarshallVoid(byte[] data) {

		// unmarshall void type

	}

	public static byte[] marshallBoolean(byte rpcid, boolean b) {

		byte[] encoded = new byte[2];

		encoded[0] = rpcid;

		if (b) {
			encoded[1] = 1;
		} else {
			encoded[1] = 0;
		}

		return encoded;
	}

	public static boolean unmarshallBoolean(byte[] data) {

		return (data[1] > 0);

	}

	public static byte[] marshallInteger(byte rpcid, int x) {
		
		// marshall RPC identifier and string into byte array
			return new byte[] {
				  rpcid,
				  (byte) (x >> 24),
				  (byte) (x >> 16),
				  (byte) (x >> 8),
				  (byte) (x >> 0)
		};
	
	}

	public static int unmarshallInteger(byte[] data) {

		// unmarshall integer contained in data
		return ByteBuffer.wrap(Arrays.copyOfRange(data, 1, data.length)).getInt();

	}
}
