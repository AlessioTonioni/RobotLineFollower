package RobotServer;

import it.unibo.command.utility.CommandFactory;
import it.unibo.command.utility.CommandType;
import it.unibo.commandTranslator.DDCommandTranslator;
import it.unibo.commandTranslator.ICommandTranslator;
import it.unibo.errorUpdater.IErrorUpdater;
import it.unibo.errorUpdater.TwoLineSensorErrorUpdater;
import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.lineFollower.ILineFollowerController;
import it.unibo.lineFollower.PIDLineFollowerController;
import it.unibo.lineFollower.PIDLineFollowerControllerFinale;
import it.unibo.lineFollower.StateMachineBiLineFollowerController;
import it.unibo.lineFollower.StateMachineMonoLineFollowerController;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import commandsExecutor.ICommandsExecutor;

import messages.controllerType;

public class ListenerController {
	//connection sockets
	private ServerSocket master;
	private Socket cmdReceiver;
	private DataInputStream cmdStream;

	private ICommandsExecutor cmdExecutor;

	public ListenerController(ServerSocket socket,ICommandsExecutor ex) throws IOException{
		master = socket;
		cmdExecutor=ex;
	}

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
