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
import java.net.Socket;
import java.util.StringTokenizer;

import messages.controllerType;

public class ListenerController {
	//connection sockets
	private Socket cmdReceiver;
	private DataInputStream cmdStream;

	//robot
	private IDifferentialDriveRobot robot;
	private ILineFollowerController controller;

	//Thread
	private Thread controllerThread;
	private boolean isRunning;

	public ListenerController(Socket socket) throws IOException{
		cmdReceiver = socket;
		cmdStream = new DataInputStream(cmdReceiver.getInputStream());
		IConfiguration conf = Configurator.getConfiguration();
		robot=(IDifferentialDriveRobot)conf.getRealRobot();

	}

	public void doJob() throws IOException, InterruptedException{
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
				executeCommand(cutter.nextToken(),cutter.nextToken());
			} else if(type.equalsIgnoreCase("cnt")){
				executeController(cutter.nextToken(),cutter.nextToken(),cutter.nextToken());
			}
			
		}
	}

	private void executeController(String cntType, String speed, String isForward) throws IOException, InterruptedException {
		killController();
		if(!CommandFactory.getInstance().isDefaultSpeedMode()){
			CommandFactory.getInstance().switchMode();
		}
		switch (controllerType.valueOf(cntType)){
		case PID:
			//System.out.println("PID "+getSpeed(speed).getNumValue()+" "+Boolean.getBoolean(isForward));
			controller=new PIDLineFollowerController(getSpeed(speed), robot,Boolean.parseBoolean(isForward));
			((PIDLineFollowerController)controller).configure("costanti.txt");
			break;
		case StateBiLine:
			controller=new StateMachineBiLineFollowerController(getSpeed(speed),robot,Boolean.parseBoolean(isForward));
			break;
		case StateMonoLine:
			controller=new StateMachineMonoLineFollowerController(getSpeed(speed),robot,Boolean.parseBoolean(isForward));
			break;
		case PIDFinale:
			ICommandTranslator c=new DDCommandTranslator(getSpeed(speed).getNumValue(),Boolean.parseBoolean(isForward),robot);
			IErrorUpdater e=new TwoLineSensorErrorUpdater();
			e.configure(Configurator.getConfiguration());
			controller=new PIDLineFollowerControllerFinale( e, c);
			break;
		default:
			return;	
		}
		controllerThread=new Thread(controller);
		isRunning=true;
		controllerThread.start();
	}

	private RobotSpeedValue getSpeed(String speed) {
		RobotSpeedValue result;
		try
		{
			result=RobotSpeedValue.LIBERA.setNumValue(Integer.parseInt(speed));
		} catch (Exception e){
			result=RobotSpeedValue.ROBOT_SPEED_LOW;
		}
		return result;
	}

	private void executeCommand(String nextToken, String nextToken2) throws InterruptedException {
		killController();
		IRobotCommand toExecute;
		int vel;
		if(CommandFactory.getInstance().isDefaultSpeedMode()){
			CommandFactory.getInstance().switchMode();
		}
		try
		{
			vel=Integer.parseInt(nextToken2);
			toExecute=CommandFactory.getInstance().getCommand(CommandType.valueOf(nextToken),vel);
			System.out.println(toExecute.getStringRep());
		}catch(Exception e){
			toExecute=CommandFactory.getInstance().getCommand(CommandType.STOP);
		}
		robot.execute(toExecute);
	}

	private void killController() throws InterruptedException {
		if(isRunning){
			controller.terminate();
			controllerThread.join();
			isRunning=false;
		}
	}
	
}
