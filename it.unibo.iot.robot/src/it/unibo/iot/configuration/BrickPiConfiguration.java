package it.unibo.iot.configuration;

import it.unibo.iot.robot.DifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.DDActuatorsFactory;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.detector.DetectorFactory;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.DistanceSensorDeviceFactory;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public class BrickPiConfiguration extends Configuration {

	public BrickPiConfiguration(String configuration) {
		super(configuration);
	}


	@Override
	protected IRobot createRealRobot() {
		return new DifferentialDriveRobot(getIddActuators(),configuration);
	}

	@Override
	protected IDDActuators createDDActuators() {
		ddActuators = DDActuatorsFactory.createBrickPiDDActuators();
		return ddActuators;
	}

	@Override
	protected IDetectorObservable createDetectorObservable() {
		lineDetector = DetectorFactory.createBrickPiLineDetector();
		return lineDetector;
	}

	@Override
	protected IDistanceObservable[] createDistanceObservables() {
		distanceSensors = new IDistanceObservable[] { DistanceSensorDeviceFactory.createBrickPiDistanceSensorDevice() };
		return null;
	}


	@Override
	protected IDetectorObservable[] createDetectorObservables() {
		return new IDetectorObservable[] {lineDetector};
	}

}
