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
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.sensors.detector.IDetectorObserver;


public class OrbMain {
	public static void main(String[] args) throws InterruptedException {
		IRobotSpeed speed = new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_HIGH);
		IRobotCommand forward = new RobotForward(speed);
		IRobotCommand backward = new RobotBackward(speed);
		IRobotCommand stop = new RobotStop( new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_LOW));
		IConfiguration conf = Configurator.getConfiguration();
		IDetectorObserver obsDetectorObserver = new IDetectorObserver() {
			
			@Override
			public void notify(IDetection detection) {
				System.out.println("********* " + detection.getValueStringRep());
			}
		};

		
		conf.getLineDetectorObservable().addObserver(obsDetectorObserver);

		IRobot robot= conf.getRealRobot();

		robot.execute(forward);
		Thread.sleep(1000);	
		robot.execute(backward);
//		Thread.sleep(1000);
//		robot.execute(forward);
		Thread.sleep(1000);	
		robot.execute(stop);
		Thread.sleep(50000);	
		robot.execute(stop);

	}
}
