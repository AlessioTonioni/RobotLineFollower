package it.unibo.DifferentialDriveSimulator.lineFollower;

import it.unibo.DifferentialDriveSimulator.robot.LineSensorDDRobot;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.sensors.detector.IDetectorObserver;
import it.unibo.lineFollower.controller.PIDLineFollowerController;
/**
 * sub-class of PIDLineFollowerController used in a simulated environment
 * @author Alessio Tonioni
 *
 */
public class MockPIDController extends PIDLineFollowerController {

	/**
	 * standard constructor
	 * @param defaultSpeed default speed at which the robot will move
	 * @param robot instance of the IDifferencialDriveRobot to control
	 * @param isForward true if the robot move forward
	 */
	public MockPIDController(RobotSpeedValue defaultSpeed, IDifferentialDriveRobot robot, boolean isForward) {
		super(defaultSpeed, robot, isForward);
		// TODO Auto-generated constructor stub
	}

	/**
	 * register himself as an observer for the LineSensorDDRobot target
	 * @param target 
	 */
	public void setObserver(LineSensorDDRobot target){
		final PIDLineFollowerController myself=this;

		IDetectorObserver obsDetectorObserver = new IDetectorObserver() {

			@Override
			public void notify(IDetection detection) {
				myself.updateError(detection);
			}
		};
		target.addObserver(obsDetectorObserver);
	}

	/**
	 * calculate the next command and execute it on the simulated robot
	 */
	@Override
	public void run(){
		robot.execute(calculateNewCommand());
	}

}
