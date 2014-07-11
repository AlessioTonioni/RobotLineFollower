package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
*
** Class created to make easier open and use uni/multicast socket 
* Enjoy
* @author Alessio Tonioni
* @version 1.0 
*
*/


public class FastDatagramSocket {
	
	private DatagramSocket socket=null;
	private DatagramPacket packet=null;
	private byte[] data=new byte[10000];
	private ByteArrayInputStream in=null;
	private InputStream ob_in=null;
	private ByteArrayOutputStream out=null;
	private OutputStream ob_out=null;
	private boolean subscribed=false;
	
	//---------------------------------costruttori------------------------------------------------------------
	/*
	 *Vari costruttori a seconda dei parametri, 
	 *mode -> indica il funzionamento: u->unicast, m->multicast, altri valori ->illegalArgumentException
	 *port->numero di porta al quale agganciare la socket 
	 *ip->indirizzo al quale legare la socket, solo per le unicast!!!
	 */
	
	public FastDatagramSocket(int port, InetAddress ip) throws SocketException{  //automaticamente unicast!!!
			socket=new DatagramSocket(port,ip);
			packet=new DatagramPacket(data, data.length);
	}
	public FastDatagramSocket(char mode, int port) throws IOException{ 
		if(mode=='m'){
			socket=new MulticastSocket(port);
			packet=new DatagramPacket(data, data.length);
		} 
		else if(mode=='u'){
			socket=new DatagramSocket(port);
			packet=new DatagramPacket(data, data.length);
		} else {throw new IllegalArgumentException();}
	}
	
	public FastDatagramSocket(char mode) throws IOException{
		if (mode=='m'){
			socket=new MulticastSocket();
			packet=new DatagramPacket(data, data.length);
		} else if(mode=='u'){
			socket=new DatagramSocket();
			packet=new DatagramPacket(data, data.length);
		} else {throw new IllegalArgumentException();}
	}
	
	public FastDatagramSocket() throws IOException{
		this('u');
	}
	
	
	
	//-----------------metodi x multicast-------------------------
	/*Prima di fare qualsiasi operazione con la multicast è necessario fare un join ad un InetAddress di classse d
	 * Cioè che inizia con un byte compreso fra [224-239], es:230.0.0.1*/
	
	public void join(InetAddress group) throws IOException{//per unirsi ad un gruppo
		if(socket instanceof MulticastSocket){
			((MulticastSocket)socket).joinGroup(group);
			subscribed=true;
		}
	}
	public void leave(InetAddress group) throws IOException{//per lasciare un gruppo
		if(socket instanceof MulticastSocket){
			((MulticastSocket)socket).leaveGroup(group);
			subscribed=false;
		}
	}
	
	
	//--------------------metodi di scrittura-----------------------------
	/*metodi di scrittura ed invio per stringhe, interi, object o array di byte generici;
	 *arg ->argomento
	 *dest -> ip destinatario
	 *port ->porta destinatario 
	 *a seconda dell'argomento uso un apposito stream writer*/
	public void send(String arg, InetAddress dest, int port) throws IOException{ 
		if(out==null){  //non si puo usare objectoutputstream si inacsina con gli header
			out=new ByteArrayOutputStream();
		}else{out.reset();}
		if(ob_out instanceof ObjectOutputStream || ob_out==null){
			ob_out=new DataOutputStream(out);
		}
		((DataOutputStream)ob_out).writeUTF(arg);
		ob_out.flush();
		packet.setAddress(dest);
		packet.setPort(port);
		packet.setData(out.toByteArray());
		socket.send(packet);
	}
	public void send(int arg, InetAddress dest, int port) throws IOException{
		if(out==null){
			out=new ByteArrayOutputStream();
		}else{out.reset();}
		if(ob_out instanceof ObjectOutputStream || ob_out==null){
			ob_out=new DataOutputStream(out);
		}
		((DataOutputStream)ob_out).writeInt(arg);
		ob_out.flush();
		packet.setAddress(dest);
		packet.setPort(port);
		packet.setData(out.toByteArray());
		socket.send(packet);
	}
	public void send(Object arg, InetAddress dest, int port) throws IOException{
		if(out==null){
			out=new ByteArrayOutputStream();
		}else{out.reset();}
		if(ob_out!=null){ob_out.close();}
		ob_out=new ObjectOutputStream(out);
		((ObjectOutputStream) ob_out).writeObject(arg);
		ob_out.flush();
		packet.setAddress(dest);
		packet.setPort(port);
		packet.setData(out.toByteArray());
		socket.send(packet);
	}
	public void send(byte[] arg, InetAddress dest, int port) throws IOException{
		packet.setAddress(dest);
		packet.setPort(port);
		packet.setData(arg);
		socket.send(packet);
	}
	/*metodi senza indirizzo servono solo per le risposte, 
	 * indirizzano automaticamente il pacchetto al mittente dell'ultimo pacchetto ricevuto;
	 *  se invocate senza aver prima ricevuto un pacchetto --> exception*/
	public void send(String arg) throws IOException{
		if(packet.getAddress()==null || packet.getPort()==-1){
			throw new IOException("pacchetto senza destinazione!!, usare il metodo senza indirizzo solo nel caso di risposta");
		}
		if(out==null||!(ob_out instanceof DataOutputStream)){
			out=new ByteArrayOutputStream();
			ob_out=new DataOutputStream(out);
		}else{out.reset();}
		((DataOutputStream)ob_out).writeUTF(arg);
		ob_out.flush();
		packet.setData(out.toByteArray());
		socket.send(packet);
	}
	public void send(int arg) throws IOException{
		if(packet.getAddress()==null || packet.getPort()==-1){
			throw new IOException("pacchetto senza destinazione!!, usare il metodo senza indirizzo solo nel caso di risposta");
		}
		if(out==null||!(ob_out instanceof DataOutputStream)){
			out=new ByteArrayOutputStream();
			ob_out=new DataOutputStream(out);
		}else{out.reset();}
		((DataOutputStream)ob_out).writeInt(arg);
		ob_out.flush();
		packet.setData(out.toByteArray());
		socket.send(packet);
	}
	public void send(Object arg) throws IOException{
		if(packet.getAddress()==null || packet.getPort()==-1){
			throw new IOException("pacchetto senza destinazione!!, usare il metodo senza indirizzo solo nel caso di risposta");
		}
		if(out==null){
			out=new ByteArrayOutputStream();
		}else{out.reset();}
		ob_out=new ObjectOutputStream(out);
		ob_out.flush();
		packet.setData(out.toByteArray());
		socket.send(packet);
	}
	public void send(byte[] arg) throws IOException{
		if(packet.getAddress()==null || packet.getPort()==-1){
			throw new IOException("pacchetto senza destinazione!!, usare il metodo senza indirizzo solo nel caso di risposta");
		}
		packet.setData(arg);
		socket.send(packet);
	}
	
	/*metodo resend rispedisce l'ultimo pacchetto, se non è correttamente inizializzato spara un eccezione*/
	public void resend() throws IOException{
		if(packet.getAddress()==null || packet.getPort()==-1 || packet.getData()==null){
			throw new IOException("impossibile fare resend pacchetto non inizializzato");
		}
		socket.send(packet);
	}

	
	//--------------------------------metodi di lettura--------------------------
	/*metodi complementari a quelli di send, 
	 * la receiveOnject ritorna un oggetto generico da castare appropiatamente!!*/
	
	public String receiveString() throws IOException{
		if(socket instanceof MulticastSocket && !subscribed){
			throw new IOException("effettuare la sottoscrizione ad un gruppo prima di usare una multicast per ricevere!!");
		}
		packet.setData(data, 0 , data.length);
		socket.receive(packet);
		in=new ByteArrayInputStream(packet.getData(),0,packet.getLength());
		if(ob_in==null || ob_in instanceof ObjectInputStream){
			ob_in=new DataInputStream(in);}
		ob_in.reset();
		String result=((DataInputStream) ob_in).readUTF();
		in.close();
		return result;
	}
	public int receiveInt() throws IOException{
		if(socket instanceof MulticastSocket && !subscribed){
			throw new IOException("effettuare la sottoscrizione ad un gruppo prima di usare una multicast per ricevere!!");
		}
		packet.setData(data, 0 , data.length);
		socket.receive(packet);
		in=new ByteArrayInputStream(packet.getData(),0,packet.getLength());
		if(ob_in==null || ob_in instanceof ObjectInputStream){
			ob_in=new DataInputStream(in);}
		ob_in.reset();
		int result=((DataInputStream) ob_in).readInt();
		in.close();
		return result;
	}
	public Object receiveObject() throws IOException, ClassNotFoundException{
		if(socket instanceof MulticastSocket && !subscribed){
			throw new IOException("effettuare la sottoscrizione ad un gruppo prima di usare una multicast per ricevere!!");
		}
		packet.setData(data, 0 , data.length);
		socket.receive(packet);
		in=new ByteArrayInputStream(packet.getData(),0,packet.getLength());
		if(ob_in!=null){ob_in.close();}
		ob_in=new ObjectInputStream(in);
		Object result=((ObjectInputStream) ob_in).readObject();
		in.close();
		return result;
	}
	
	//-------------------------metodi accessori--------------------
	public void close(){ 
		socket.close();
	}
	public void setTimeout(int timeout) throws SocketException{
		socket.setSoTimeout(timeout);
	}
	
	//-----------------------getters/setters-------------------------
	public DatagramSocket getSocket() {
		return socket;
	}
	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}
	public DatagramPacket getPacket() {
		return packet;
	}
	public void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}
	public boolean isSubscribed() {
		return subscribed;
	}
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
	

	
	

}
