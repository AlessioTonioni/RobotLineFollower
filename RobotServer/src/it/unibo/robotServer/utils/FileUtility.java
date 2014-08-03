package it.unibo.robotServer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.StringTokenizer;
/**
*
** A Series of utility to make easer and faster working with file in java
* Enjoy
* @author Alessio Tonioni
* @version 1.0 
*
*/
public class FileUtility {
	/*ritorna la linea richiesta, se il file non ha abbastanza righe => IOException*/
	public static String getLine(FileReader in, int line) throws IOException{ 
		BufferedReader r=new BufferedReader(in);
		String result="";
		for(int i=0; i<line; i++){
			if((result=r.readLine())==null){
				throw new IOException("fien del file raggiunta prima della riga selezionata");
			}
		}
		return result;
	}
	
	/*ritorna la parola richiesta, se il file non ha abbastanza parole IOException
	 * separatori: stringa che contiene tutti i separatori accettati per distinguere una parola dalla successiva
	 * es: "$_ " => separatori accettati " ", $, _ */
	public static String getParola(FileReader in, int n, String separatori) throws IOException{
		BufferedReader buf=new BufferedReader(in);
		int r=0;
		String line="";
		String res="";
		while((line=buf.readLine())!=null){
			StringTokenizer a=new StringTokenizer(line);
			while(a.hasMoreTokens()){
				res=a.nextToken(separatori);
				r++;
				if(r==n)return res;
			}
		}
			if(r<n){
				//throw new IOException("Parola non presente sul file"); 
				return "Parola non presente sul file";
			}
			//throw new IOException("Qualcosa non è andato per il verso giusto"); 
			return "Qualcosa non è andata per il verso giusto";
	}
	
	/*crea un copia del file _in in _out*/
	public static void copy(File _in, File _out) throws IOException{
		if(!_out.exists()) _out.createNewFile();
		int tmp;
		FileInputStream in=new FileInputStream(_in);
		FileOutputStream out=new FileOutputStream(_out);
		while((tmp=in.read())!=-1){
			out.write(tmp);
		}
		in.close();
		out.close();
	}
	
	/*elimina tutte le occorrenze del carattere obj sul file in*/
	public static void eliminacaratteri(File in, char obj) throws IOException {
		BufferedReader a=new BufferedReader(new FileReader(in));
		PrintWriter b=new PrintWriter(new FileWriter("temp.txt"));
		String temp="";
		while((temp=a.readLine())!=null){
			StringBuilder write=new StringBuilder();
			if(!(temp.contains(""+obj)))
				write.append(temp);
			else
				for(int i=0; i<temp.length(); i++){
					if(temp.charAt(i)!=obj)
						write.append(temp.charAt(i));
				}
			b.println(write.toString());
		}
		a.close();
		b.close();
		File t=new File(in.getName());
		t.delete();
		File r=new File("temp.txt");
		r.renameTo(t);
	}
	
	/*elimina tutte le occorrenze di obj nel file in*/
	public static void eliminaStringa(File in, String obj) throws IOException {
		BufferedReader a=new BufferedReader(new FileReader(in));
		PrintWriter b=new PrintWriter(new FileWriter("temp.txt"));
		String temp="";
		while((temp=a.readLine())!=null){
			if(temp.contains(obj)){temp=temp.replaceAll(obj,"");}
			b.println(temp);
		}
		a.close();
		b.close();
		File t=new File(in.getName());
		t.delete();
		File r=new File("temp.txt");
		r.renameTo(t);
	}
	
	/*rimpiazza tutte le occorrenze di target con replace*/
	public static void rimpiazzaStringa(File in, String target, String replace) throws IOException {
		BufferedReader a=new BufferedReader(new FileReader(in));
		PrintWriter b=new PrintWriter(new FileWriter("temp.txt"));
		String temp="";
		while((temp=a.readLine())!=null){
			if(temp.contains(target)){temp=temp.replaceAll(target,replace);}
			b.println(temp);
		}
		a.close();
		b.close();
		File t=new File(in.getName());
		t.delete();
		File r=new File("temp.txt");
		r.renameTo(t);
	}
	
	/*sposta un file dalla locazione source alla dest*/
	public static void sposta(File source, File dest) throws IOException{
		if(source.exists()){
			FileUtility.copy(source,dest);
			source.delete();
		}else{throw new IOException("File Sorgenete Inesistente");}
	}
	
	/*trasferisce dati da un inputstream ad un outputstream*/
	public static void trasferisciByte(InputStream in, OutputStream out) throws IOException{
		int temp;
		while((temp=in.read())>=0)
			out.write(temp);
	}
	
	/*trasferisce un file a righe da in a out*/
	public static void trasferisciLinea(Reader in, Writer out) throws IOException{
		BufferedReader r=new BufferedReader(in);
		PrintWriter p=new PrintWriter(out);
		String temp="";
		while((temp=r.readLine())!=null){
			p.println(temp);
		}
	}
	
}
