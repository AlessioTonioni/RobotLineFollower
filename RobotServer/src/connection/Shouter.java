package connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import utils.FastDatagramSocket;

public class Shouter implements Runnable {
	
	private FastDatagramSocket socket;
	private InetAddress ip;
	private int serverPort;
	
	
	private volatile boolean running=true;
	
	
	public void terminate(){
		running=false;
	}
	
	public Shouter(InetAddress ip, int port, int serverPort) throws IOException{
		this.ip=ip;
		this.serverPort=serverPort;
		socket=new FastDatagramSocket('m',port);
		InetAddress multi=InetAddress.getByName("230.0.0.1");
		socket.join(multi);
	}
	
	public Shouter(InetAddress ip,int serverPort) throws IOException{
		this(ip,33333,serverPort);
	}
	
	@Override
	public void run() {
		String msg="";
		msg = ip.getHostAddress()+"@"+serverPort;
		while(running){
			try {
				socket.send(msg,InetAddress.getByName("230.0.0.1"), 54321);
				System.out.println(msg+" sent");
				Thread.sleep(500);
			} catch (IOException e) {
				continue;
			} catch (InterruptedException e) {
				continue;
			}
		}
		
	}
	
}
