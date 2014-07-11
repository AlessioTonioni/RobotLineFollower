package it.unibo.iot.configuration;

import it.unibo.iot.robot.DifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.DDActuatorsFactory;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.detector.DetectorFactory;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.DistanceSensorDeviceFactory;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public class ArduinoConfiguration extends Configuration {

	public ArduinoConfiguration(String configuration) {
		super(configuration);
	}

	@Override
	protected IDDActuators createDDActuators() {
		ddActuators = DDActuatorsFactory.createArduinoSerialDDActuators();
		return ddActuators;
	}

	@Override
	protected IRobot createRealRobot() {
		robot = new DifferentialDriveRobot(getIddActuators(), configuration);
		return robot;
	}

	@Override
	protected IDetectorObservable createDetectorObservable() {
		lineDetector = DetectorFactory.createArduinoLineDetector();
		return lineDetector;
	}

	@Override
	protected IDistanceObservable[] createDistanceObservables() {
		distanceSensors = new IDistanceObservable[] {DistanceSensorDeviceFactory.createArduinoDistanceSensorDevice()};
		return null;
	}

	@Override
	protected IDetectorObservable[] createDetectorObservables() {
		return new IDetectorObservable[] {lineDetector};
	}

}
