package it.unibo.lineFollower.controller;

import java.io.IOException;

import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.IRobotSpeed;
import it.unibo.iot.models.robotCommands.RobotBackward;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.iot.models.robotCommands.RobotForwardLeft;
import it.unibo.iot.models.robotCommands.RobotForwardRight;
import it.unibo.iot.models.robotCommands.RobotSpeed;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.robotCommands.RobotStop;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.models.sensorData.IDistanceSensorData;
import it.unibo.iot.models.sensorData.color.IColorSensorData;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.sensors.colorSensor.IColorSensorObserver;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObserver;

public class SimpleMain {
	private static SimpleMain instance=null;
	
	boolean eastOnLine=false;
	boolean westOnLine=false;
	IRobotCommand currentCmd=null;
	
	IRobotSpeed speed = new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_LOW);
	IRobotCommand forward = new RobotForward(speed);
	IRobotCommand backward = new RobotBackward(speed);
	IRobotCommand forwardEast= new RobotForwardLeft(speed);
	IRobotCommand forwardWest=new RobotForwardRight(speed);
	IRobotCommand stop = new RobotStop( new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_LOW));
	
	public SimpleMain(){
		currentCmd=this.stop;
	}
	
	public static SimpleMain getInstance(){
		if(instance==null)
			instance=new SimpleMain();
		return instance;
	}
	
	public void changeState(IDetection detection) {
		String msg=detection.getValueStringRep();
		System.out.println(msg);
		System.out.println(detection.getVal());
		System.out.println(detection.getName());
		System.out.println(detection.getDirection());
		
		switch (detection.getDirection()){
		case EAST:
			eastOnLine=detection.getVal();
			break;
		case WEST:
			westOnLine=detection.getVal();
			break;
		default:
			break;
		}
		
		if(eastOnLine && westOnLine){
			currentCmd=stop;
			return;
		}
		if(eastOnLine){
			currentCmd=forwardEast;
			return;
		}
		else if(westOnLine){
			currentCmd=forwardWest;
			return;
		} else{
			currentCmd=forward;
			return;
		}
		
	}
	
	public IRobotCommand getCurrentCmd(){
		return currentCmd;
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		/*IConfiguration conf = Configurator.getConfiguration();
		final SimpleMain myself=SimpleMain.getInstance();
		
		IDetectorObserver obsDetectorObserver = new IDetectorObserver() {
			
			@Override
			public void notify(IDetection detection) {
				myself.changeState(detection);
			}
		};
		IDetectorObservable [] detectorObservables = conf.getLineDetectorObservables();
		for (IDetectorObservable iDetectorObservable : detectorObservables) {
			iDetectorObservable.addObserver(obsDetectorObserver);
		}
		
		IDistanceObserver distanceObserver = new IDistanceObserver() {
			@Override
			public void notify(IDistanceSensorData distance) {
				System.out.println("********* " + distance.getValueStringRep());
			}
		};
		IDistanceObservable [] distanceObservables = conf.getDistanceObservables();
		for (IDistanceObservable iDistanceObservable : distanceObservables) {
			iDistanceObservable.addObserver(distanceObserver);
		}

		IColorSensorObserver colorObserver = new IColorSensorObserver() {
			
			@Override
			public void notify(IColorSensorData colorSensorData) {
				System.out.println("********* " + colorSensorData.getValueStringRep());
			}
		};
		
		conf.getColorSensorObservable().addObserver(colorObserver);
		
		IRobot robot= conf.getRealRobot();
		myself.currentCmd=myself.forward;

		while(true){
			robot.execute(myself.getCurrentCmd());
			Thread.sleep(250);
		}*/
		
		IConfiguration conf = Configurator.getConfiguration();
		IDifferentialDriveRobot robot=(IDifferentialDriveRobot)conf.getRealRobot();
		ILineFollowerController controller=new PIDLineFollowerController(RobotSpeedValue.ROBOT_SPEED_LOW, robot,false);
		((PIDLineFollowerController)controller).configure("costanti.txt");
		controller.run();
		
	}
}
