package utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Prova {
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		if(args[0].equals("1")){
			FastDatagramSocket socket=new FastDatagramSocket('u',54321);
			System.out.println("socket_creata");
			socket.send("Prova", InetAddress.getByName("localhost"), 54322);
			System.out.println(socket.receiveString());
			System.out.println("muoio!!");
			socket.close();
		}else if(args[0].equals("2")){
			FastDatagramSocket socket=new FastDatagramSocket('u',54322);
			System.out.println("socket_creata");
			System.out.println(socket.receiveString());
			socket.send("Risposta");  //risposta senza parametri
			System.out.println("muoio!!");
			socket.close();
		}else if(args[0].equals("3")){
			ServerSocket sock=new ServerSocket(54321);
			System.out.println("bo");
			FastStreamSocket trans=new FastStreamSocket(sock.accept());
			System.out.println("socket_creata");
			System.out.println(trans.receiveString());
			trans.close();
			//trans.close();
		}else if(args[0].equals("4")){
			System.out.println("bbi");
			FastStreamSocket so=new FastStreamSocket("localhost", 54321);
			System.out.println("ba");
			so.send("fanculo");
			//so.close();
			try{so.receiveString();}catch(EOFException e){System.out.println("Esco dal ciclo");}
			System.out.println("termino");
		}else if(args[0].equals("-1")){
			File a=new File("saggezza.txt");
			File b=new File("saggezza_copia.txt");
			FileUtility.copy(a, b);
			System.out.println(FileUtility.getLine(new FileReader(b), 1));
			System.out.println(FileUtility.getParola(new FileReader(a), 22, " "));
		}
		
	}

}
