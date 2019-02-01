package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCServerStopStub;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) {
		
		Display display;
		Sensor sensor;
		
		RPCClient displayclient,sensorclient;
		
		System.out.println("Controller starting ...");
				
		RPCServerStopStub stopdisplay = new RPCServerStopStub();
		RPCServerStopStub stopsensor = new RPCServerStopStub();
		
		// create display and sensor object
		// create RPC clients for display device and sensor device
		// register RPC methods in the RPC layer
		displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);
		
		display = new Display();
		sensor = new Sensor();
		
		sensorclient.register(sensor);
		displayclient.register(display);
		// register stop methods in the RPC middleware
		displayclient.register(stopdisplay);
		sensorclient.register(stopsensor);
		// loop while reading from sensor and write to display via RPC
		String temp;
		for(int i = 0; i< N; i++){
			temp = new Integer(sensor.read()).toString();
			display.write(temp);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
