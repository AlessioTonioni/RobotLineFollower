package it.unibo.lineFollower;

import it.unibo.command.utility.CommandFactory;
import it.unibo.command.utility.CommandType;
import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;

public abstract class AbstractStateMachineLineFollowerController implements ILineFollowerController {
	protected IRobotCommand currentCmd;
	protected IRobot robot;

	protected IRobotCommand moveLeft;
	protected IRobotCommand moveRight;
	protected IRobotCommand defaultCommand;
	protected IRobotCommand stopCommand;

	protected boolean eastOnLine=false;
	protected boolean westOnLine=false;
	protected boolean isForward=true;

	protected boolean correcting=false;

	protected CommandFactory factory;

	private volatile boolean running = true;

	protected AbstractStateMachineLineFollowerController(RobotSpeedValue defaultSpeed, IRobot robotToControl, boolean isForward){
		factory=CommandFactory.getInstance();
		factory.setSpeed(defaultSpeed);
		robot=robotToControl;
		this.isForward=isForward;
		setCorrectCommand();
	}

	protected  AbstractStateMachineLineFollowerController(IRobot robotToControl){
		this(RobotSpeedValue.ROBOT_SPEED_LOW,robotToControl,true);
	}

	public void terminate() {
		running = false;
	}
	
	private void setCorrectCommand(){
		if(isForward){
			moveLeft=factory.getCommand("FORWARDLEFT");
			moveRight=factory.getCommand("FORWARDRIGHT");
			defaultCommand=factory.getCommand("FORWARD");
		} else {
			moveLeft=factory.getCommand("BACKWARDLEFT");
			moveRight=factory.getCommand("BACKWARDRIGHT");
			defaultCommand=factory.getCommand("BACKWARD");
		}
		stopCommand=factory.getCommand("STOP");
	}

	public void setSpeed(RobotSpeedValue newSpeed){
		factory.setSpeed(newSpeed);
	}

	public void setForward(boolean isForward){
		this.isForward=isForward;
		setCorrectCommand();
	}

	public void doJob(){
		run();
	}
	
	@Override
	public void run() {
		IConfiguration conf = Configurator.getConfiguration();
		final AbstractStateMachineLineFollowerController myself=this;

		IDetectorObserver obsDetectorObserver = new IDetectorObserver() {

			@Override
			public void notify(IDetection detection) {
				try {
					myself.changeState(detection);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		IDetectorObservable [] detectorObservables = conf.getLineDetectorObservables();
		for (IDetectorObservable iDetectorObservable : detectorObservables) {
			iDetectorObservable.addObserver(obsDetectorObserver);
		}

		this.currentCmd=defaultCommand; 

		while(running){
			robot.execute(currentCmd);
			//Thread.sleep(250);
		}
	}

	protected abstract void changeState(IDetection detection) throws InterruptedException;

}
