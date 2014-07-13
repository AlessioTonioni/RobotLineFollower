package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Enumeration;

import RobotServer.ListenerController;
import connection.Shouter;

public class RobotServer {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		String usage="Usage: RoboServer ipAdress defaultPort";
		int port;
		InetAddress serverIp;
		
		if(args.length!=2){
			System.out.println(usage);
			return;
		}
		try{
			port=Integer.parseInt(args[1]);
			serverIp=InetAddress.getByName(args[0]);
		} catch (Exception e){
			System.out.println(usage);
			return;
		}
		
		/*Shouter s=new Shouter(serverIp,12345,port);
		Thread shouterThread=new Thread(s);
		shouterThread.start();
		System.out.println("Shouter partito");*/
		
		System.out.println("Attesa Connesione...");
		ServerSocket server=new ServerSocket(port);
		//System.out.println(server.getLocalSocketAddress().toString());
		ListenerController controller=new ListenerController(server.accept());
		/*s.terminate();
		shouterThread.join();
		System.out.println("Shout terminato, pronto a ricevere comandi");*/
		System.out.println("Pronto a partire...");
		controller.doJob();
	}
}
