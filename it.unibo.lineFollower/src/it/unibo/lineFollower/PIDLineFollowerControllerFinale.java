package it.unibo.lineFollower;

import javax.security.auth.login.Configuration;

import it.unibo.commandTranslator.ICommandTranslator;
import it.unibo.errorUpdater.IErrorUpdater;
import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;

/**
 * Implementation of an ILineFollowerController based on a PID controller which uses an external 
 * ICommandTranslator to obtain the proper robot command and an IErrorUpdater to obtain the current 
 * tracking error
 * @author Alessio Tonioni
 *
 */
public class PIDLineFollowerControllerFinale extends PIDLineFollowerController{
	protected IErrorUpdater errorUpdater;
	protected ICommandTranslator commandTranslator;
	
	/**
	 * default constructor
	 * @param robot 
	 * @param errorUpdater 
	 * @param commandTranslator
	 */
	public PIDLineFollowerControllerFinale( IErrorUpdater errorUpdater, ICommandTranslator commandTranslator){
		super(null,null,false);
		this.errorUpdater=errorUpdater;
		this.commandTranslator=commandTranslator;
	}
	
	@Override
	public void run(){
		if(configured){		
			System.out.println("ReadyToStart!");
			while(running){  
				try{
					this.error=errorUpdater.getError();
					int turn=calculateTurn();
					commandTranslator.executeRobotCommand(turn);
					Thread.sleep(millisSleep);  
				}catch(Exception e){
					continue;
				}
			}
		} else {
			System.out.println("Controller not yet configured");
			return;
		}
	}
	
}
