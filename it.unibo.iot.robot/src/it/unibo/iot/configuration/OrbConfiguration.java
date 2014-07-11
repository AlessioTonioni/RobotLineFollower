package it.unibo.iot.configuration;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.robot.DifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.DDActuatorsFactory;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.detector.DetectorFactory;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public class OrbConfiguration extends Configuration{

	public OrbConfiguration(String configuration) {
		super(configuration);
	}

	
	@Override
	protected IRobot createRealRobot() {
		robot = new DifferentialDriveRobot(this.getIddActuators(), configuration);
		return robot;
	}

	@Override
	protected IDDActuators createDDActuators() {
		ddActuators = DDActuatorsFactory.createDDActuators(configuration);
		return ddActuators;
	}

	@Override
	protected IDetectorObservable createDetectorObservable() {
		lineDetector = DetectorFactory.createGPIOLineDetector(DirectionValue.NORTH,configuration);
		return lineDetector; 
	}

	@Override
	protected IDistanceObservable[] createDistanceObservables() {
//		distanceSensors = new IDistanceObservable[] { DistanceSensorDeviceFactory.createGPIOLightDistanceSensorDevice() };
		return null; 
	}


	@Override
	protected IDetectorObservable[] createDetectorObservables() {
		// TODO Auto-generated method stub
		return null;
	}
}
