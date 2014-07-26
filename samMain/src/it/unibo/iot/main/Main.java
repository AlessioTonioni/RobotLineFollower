package it.unibo.iot.main;

import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.IRobotSpeed;
import it.unibo.iot.models.robotCommands.RobotBackward;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.iot.models.robotCommands.RobotSpeed;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.robotCommands.RobotStop;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.models.sensorData.IDistanceSensorData;
import it.unibo.iot.models.sensorData.color.IColorSensorData;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.sensors.colorSensor.IColorSensorObserver;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObserver;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		IRobotSpeed speed = new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_HIGH);
		IRobotCommand forward = new RobotForward(speed);
		IRobotCommand backward = new RobotBackward(speed);
		IRobotCommand stop = new RobotStop( new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_LOW));
		IConfiguration conf = Configurator.getConfiguration();
		/*IDetectorObserver obsDetectorObserver = new IDetectorObserver() {
			
			@Override
			public void notify(IDetection detection) {
				System.out.println("********* " + detection.getValueStringRep());
			}
		};

		IDetectorObservable [] detectorObservables = conf.getLineDetectorObservables();
		for (IDetectorObservable iDetectorObservable : detectorObservables) {
			iDetectorObservable.addObserver(obsDetectorObserver);
		}*/
		
		/*IDistanceObserver distanceObserver = new IDistanceObserver() {
			@Override
			public void notify(IDistanceSensorData distance) {
				System.out.println("********* " + distance.getValueStringRep());
			}
		};
		IDistanceObservable [] distanceObservables = conf.getDistanceObservables();
		for (IDistanceObservable iDistanceObservable : distanceObservables) {
			iDistanceObservable.addObserver(distanceObserver);
		}*/

		IColorSensorObserver colorObserver = new IColorSensorObserver() {
			
			@Override
			public void notify(IColorSensorData colorSensorData) {
				System.out.println("********* " + colorSensorData.getValueStringRep());
			}
		};
		
		conf.getColorSensorObservable().addObserver(colorObserver);
		
		IRobot robot= conf.getRealRobot();

		/*robot.execute(forward);
		Thread.sleep(500);	
		robot.execute(backward);
//		Thread.sleep(1000);
//		robot.execute(forward);
		Thread.sleep(500);	
		robot.execute(stop);*/
		Thread.sleep(50000);	
		robot.execute(stop);

	}
}
