package it.unibo.robotServer.main;

import it.unibo.robotServer.utils.FastDatagramSocket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class cmdClient {
	public static void main(String args[]) throws IOException{
		FastDatagramSocket announcer=new FastDatagramSocket('m',54321);
		InetAddress multi=InetAddress.getByName("225.0.0.1");
		announcer.join(multi);

		System.out.println("Attesa server...");
		//System.out.println(announcer.receiveString());
		Socket clientSocket=connectToServer("192.168.1.6@54321");
		System.out.println("Connessione con il server stabilita");

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		while(true){
			System.out.println("Next command: ");
			String cmd=inFromUser.readLine();
			byte[] msg=cmd.getBytes("UTF-8");
			int size=msg.length;
			outToServer.write((byte)size);
			outToServer.write(msg);
			outToServer.flush();
		}
	}
	
	public static Socket connectToServer(String address) throws IOException{
		StringTokenizer cut=new StringTokenizer(address); 
		String ip=cut.nextToken("@");
		String port=cut.nextToken();
		System.out.println("Ricevuto: "+ip+'@'+port);
		InetAddress serverAddress=InetAddress.getByName(ip);
		int serverPort=Integer.parseInt(port.trim());
		return new Socket(serverAddress,serverPort);
	}
}
