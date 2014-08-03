package it.unibo.robotServer.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/**
*
** Class created to make faster the use of java's socket 
* Enjoy.
* @author Alessio Tonioni
* @version 1.0 
*
*/
public class FastStreamSocket {
	private Socket sock=null;
	private ObjectInputStream in=null;
	private ObjectOutputStream out=null;
	
	//----------------------------------------costruttori--------------------------------------------
	/*vari tipi di costruttori a partire da indirizzi, e porte o da socket pre-esistenti*/
	public FastStreamSocket(Socket sock) throws IOException{
		this.sock=sock;
		out=new ObjectOutputStream(this.sock.getOutputStream());  //se inverti l'apertura scoppia tutto!!!, java indegnamente inutile
		in=new ObjectInputStream(this.sock.getInputStream());
	}
	public FastStreamSocket(InetAddress remoteAddress, int remotePort, InetAddress localAddress, int localPort) throws IOException{
		sock=new Socket(remoteAddress, remotePort, localAddress, localPort);
		out=new ObjectOutputStream(sock.getOutputStream());
		in=new ObjectInputStream(sock.getInputStream());
	}
	public FastStreamSocket(InetAddress remoteAddress, int remotePort) throws IOException{
		sock=new Socket(remoteAddress, remotePort);
		out=new ObjectOutputStream(sock.getOutputStream());
		in=new ObjectInputStream(sock.getInputStream());
	}
	public FastStreamSocket(String remoteHost, int remotePort) throws UnknownHostException, IOException{
		sock=new Socket(remoteHost,remotePort);
		out=new ObjectOutputStream(sock.getOutputStream());
		in=new ObjectInputStream(sock.getInputStream());
		
	}
	
	//--------------------------------------------send------------------------------------
	/*spediscono l'argomento al destinatario*/
	public void send(String msg) throws IOException{
		out.writeUTF(msg);
		out.flush();
	}
	public void send(int msg) throws IOException{
		out.writeInt(msg);
		out.flush();
	}
	public void send(Object msg) throws IOException{
		out.writeObject(msg);
		out.flush();
	}
	public void send(byte[] msg) throws IOException{
		out.write(msg);
		out.flush();
	}
	
	//--------------------------------------receive---------------------------------------
	/*leggono dalla socket il valore, 
	 * sospensive se socket vuota,
	 * exception se socket chiusa da altro lato.*/
	public String receiveString() throws IOException{
		return in.readUTF();
	}
	public int receiveInt() throws IOException{
		return in.readInt();
	}
	public Object receiveObject() throws ClassNotFoundException, IOException{
		return in.readObject();
	}
	public byte[] receivebyte(int size) throws IOException{
		byte[] result=new byte[size];
		for(int i=0; i<size; i++){
			result[i]=in.readByte();
		}
		return result;
	}
	
	//-----------------------------------getters/setters----------------------------------------
	public Socket getSock() {
		return sock;
	}
	public void setSock(Socket sock) {
		this.sock = sock;
	}
	public ObjectInputStream getIn() {
		return in;
	}
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	public ObjectOutputStream getOut() {
		return out;
	}
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	
	//----------------------------metodi accessori---------------------------------------------------
	/*permettono di chiudere selettivamente il canale di input/output o tutta la socket*/
	public void shutdownInput() throws IOException{
		sock.shutdownInput();
	}
	public void shutdownOutput() throws IOException{
		sock.shutdownOutput();
	}
	public void close() throws IOException{
		sock.close();
	}
	
	
}
