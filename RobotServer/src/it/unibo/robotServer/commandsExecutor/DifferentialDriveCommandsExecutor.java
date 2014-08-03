package it.unibo.robotServer.commandsExecutor;

import it.unibo.command.utility.CommandFactory;
import it.unibo.command.utility.CommandType;
import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.lineFollower.commandTranslator.DDCommandTranslator;
import it.unibo.lineFollower.commandTranslator.ICommandTranslator;
import it.unibo.lineFollower.controller.ILineFollowerController;
import it.unibo.lineFollower.controller.PIDLineFollowerController;
import it.unibo.lineFollower.controller.PIDLineFollowerControllerFinale;
import it.unibo.lineFollower.controller.StateMachineBiLineFollowerController;
import it.unibo.lineFollower.controller.StateMachineMonoLineFollowerController;
import it.unibo.lineFollower.errorUpdater.IErrorUpdater;
import it.unibo.lineFollower.errorUpdater.TwoLineSensorErrorUpdater;
import it.unibo.robotServer.messages.controllerType;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Implementation of an ICommandExecutor for a IDifferentialDriveRobot.
 * @author Alessio Tonioni
 *
 */
public class DifferentialDriveCommandsExecutor implements ICommandsExecutor {

	//robot
	private IDifferentialDriveRobot robot;
	private ILineFollowerController controller;

	//Thread
	private Thread controllerThread;
	private boolean isRunning;

	public DifferentialDriveCommandsExecutor(IDifferentialDriveRobot robot) throws IOException{
		this.robot=robot;
	}

	/* (non-Javadoc)
	 * @see RobotServer.ICommandsExecutor#executeController(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void executeController(String cntType, String speed, String isForward) throws IOException, InterruptedException {
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

	/* (non-Javadoc)
	 * @see RobotServer.ICommandsExecutor#executeCommand(java.lang.String, java.lang.String)
	 */
	@Override
	public void executeCommand(String nextToken, String nextToken2) throws InterruptedException {
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
	
	/* (non-Javadoc)
	 * @see RobotServer.ICommandsExecutor#getRobot()
	 */
	@Override
	public IRobot getRobot(){
		return robot;
	}
	
	/* (non-Javadoc)
	 * @see RobotServer.ICommandsExecutor#setRobot(it.unibo.iot.robot.IRobot)
	 */
	/**
	 * Sets the robot to control only if it's an instance of IDifferentialDriveRobot
	 */
	@Override
	public void setRobot(IRobot robot){
		if(robot instanceof IDifferentialDriveRobot){
			this.robot=(IDifferentialDriveRobot)robot;
		} else {
			throw new IllegalArgumentException("Unable to control this type of robot");
		}
	}
}
