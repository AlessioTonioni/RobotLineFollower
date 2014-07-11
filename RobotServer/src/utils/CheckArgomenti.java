package utils;

import java.io.File;
/**
*
** Class created to make faster arguments check at the beginning of a program
* Enjoy.
* @author Alessio Tonioni
* @version 1.0 
*
*/
public class CheckArgomenti {
	/*tutte le funzioni sono in doppia modalità:
	 * kill=true -> in caso di fallimento di un chek l'applicazione viene terminata
	 * kill=false -> in caso di fallimento di un check ritorna false, ma nont emrina l'esecuzione
	 * usage: Stringa da stampare in caso di check fallito, precede la system.exit*/
	public static boolean chekNumber(String[] args, int length, String usage, boolean kill){  //controllo numero argomenti
		if(args.length!=length){
			if(kill){
				System.out.println("Numero argomenti non valido. Usage: "+usage);
				System.exit(1);}
			return false;
		}
		return true;
	}
	public static boolean checkFile(String nome, String usage, boolean kill){  //controllo file esistente
		File temp=new File(nome);
		if(!temp.exists()){
			if(kill){
				System.out.println("file inesistente. Usage: "+usage);
				System.exit(1);}
			return false;
		}
		return true;
	}
	public static boolean checkDirectory(String nome, String usage, boolean kill){  //controllo directory esistente
		File temp=new File(nome);
		if(!temp.exists() || !(temp.isDirectory())){
			if(kill){
				System.out.println("file inesistente. Usage: "+usage);
				System.exit(1);}
			return false;
		}
		return true;
	}
	public static boolean chekPorta(String porta, String usage, boolean kill){ //controllo numero porta valido
		try{
			int number=Integer.parseInt(porta);
			if(number<0 || number>65535){
				if(kill){
					System.out.println("Numero di porta non valido. Usage: "+usage);
					System.exit(1);}
				return false;
			}
			return true;
		}catch(NumberFormatException e){
			if(kill){
				System.out.println("Numero di porta non valido. Usage: "+usage);
				System.exit(1);}
			return false;
		}
	}
	public static boolean checkClasseD(String ip, String usage, boolean kill){ //controllo indirizzo ip di classe d
		String temp=ip.substring(0, ip.indexOf("."));
		try{
			int n=Integer.parseInt(temp);
			if(n>224 && n<239) return true;
			if(kill){
				System.out.println("indirizzo non di classe d. Usage: usage");
				System.exit(1);
			}
			return false;
		}catch(NumberFormatException e){
			if(kill){
				System.out.println("indirizzo non di classe d. Usage: usage");
				System.exit(1);}
			return false;
		}
	}
	
	

}
