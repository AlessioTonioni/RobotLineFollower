package it.unibo.robotServer.RobotServer;

import it.unibo.robotServer.commandsExecutor.ICommandsExecutor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Implementation of a simple tcp server, waits for a connection request, accepts it and then starts to receive
 * command in this form: 1 byte for the size of the message, the message as a UTF-8 String.
 * After receiving a message it interprets it and use an ICommandsExecutor to make the robot execute the action.
 * @author Alessio Tonioni
 *
 */
public class ListenerController {
	//connection sockets
	private ServerSocket master;
	private Socket cmdReceiver;
	private DataInputStream cmdStream;

	private ICommandsExecutor cmdExecutor;

	/**
	 * Default constructor
	 * @param socket server socket on which to wait for connection request
	 * @param ex executor of the command
	 * @throws IOException
	 */
	public ListenerController(ServerSocket socket,ICommandsExecutor ex) throws IOException{
		master = socket;
		cmdExecutor=ex;
	}

	/**
	 * wait for the establishment of a connection and then starts to read and interpret the messages.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void doJob() throws IOException, InterruptedException{
		cmdReceiver=master.accept();
		cmdStream=new DataInputStream(cmdReceiver.getInputStream());
		while(true){
			int dim=cmdStream.read();
			//cmdStream.read(); cmdStream.read(); cmdStream.read();
			byte[] cmd=new byte[dim];
			//System.out.println(dim);
			int readed=cmdStream.read(cmd);
			//System.out.println(readed);
			String cmdToExecute=new String(cmd,"UTF-8");
			System.out.println(cmdToExecute);
			StringTokenizer cutter=new StringTokenizer(cmdToExecute);
			String type=cutter.nextToken();
			if(type.equalsIgnoreCase("cmd")){
				cmdExecutor.executeCommand(cutter.nextToken(),cutter.nextToken());
			} else if(type.equalsIgnoreCase("cnt")){
				cmdExecutor.executeController(cutter.nextToken(),cutter.nextToken(),cutter.nextToken());
			}
			
		}
	}




	
}
