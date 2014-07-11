package it.unibo.iot.configuration;

import it.unibo.iot.robot.DifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.DDActuatorsFactory;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.colorSensor.ColorSensorFactory;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.detector.DetectorFactory;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.DistanceSensorDeviceFactory;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public class CompassConfiguration extends Configuration {

	public CompassConfiguration(String configuration) {
		super(configuration);
		// TODO Auto-generated constructor stub
	}

	
	protected IRobot createRealRobot() {
		robot = new DifferentialDriveRobot(getIddActuators(), configuration);
		return robot;
	}

	@Override
	protected IDDActuators createDDActuators() {
		ddActuators = DDActuatorsFactory.createDDActuators(configuration);
		return ddActuators;
	}

	@Override
	protected IDetectorObservable createDetectorObservable() {
		//lineDetector = DetectorFactory.createArduinoLineDetector();
		return null;
	}

	@Override
	protected IDistanceObservable[] createDistanceObservables() {
		distanceSensors = new IDistanceObservable[] { DistanceSensorDeviceFactory.createArduinoDistanceSensorDevice() };
		return distanceSensors;
	}

	@Override
	protected IDetectorObservable[] createDetectorObservables() {
		return lineDetectors = DetectorFactory.createGPIOLineDetectors(configuration);
	}

	public IColorSensorObservable getColorSensorObservable(){
		return ColorSensorFactory.createArduinoColorSensorObservable();
	}
}
