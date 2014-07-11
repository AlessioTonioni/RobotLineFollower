package lineFollower;

import robot.LineSensorDDRobot;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.sensors.detector.IDetectorObserver;
import it.unibo.lineFollower.PIDLineFollowerController;

public class MockPIDController extends PIDLineFollowerController {

	public MockPIDController(RobotSpeedValue defaultSpeed,
			IDifferentialDriveRobot robot, boolean isForward) {
		super(defaultSpeed, robot, isForward);
		// TODO Auto-generated constructor stub
	}

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

	@Override
	public void doJob(){
		robot.execute(calculateNewCommand());
	}

}
