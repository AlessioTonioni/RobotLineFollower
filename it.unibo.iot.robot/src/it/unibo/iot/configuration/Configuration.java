package it.unibo.iot.configuration;

import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public abstract class Configuration implements IConfiguration {
	protected IRobot robot = null;
	protected IDetectorObservable lineDetector;
	protected IDetectorObservable [] lineDetectors;
	protected IDistanceObservable[] distanceSensors;
	protected IDDActuators ddActuators;
	protected String configuration;

	public Configuration(String configuration) {
		this.configuration = configuration;
		this.ddActuators = createDDActuators();
		this.robot = createRealRobot();
		this.lineDetector = createDetectorObservable();
		this.distanceSensors = createDistanceObservables();
		this.lineDetectors = createDetectorObservables();
	}
	@Override
	public IRobot getRealRobot() {
		return robot;
	}

	@Override
	public IDetectorObservable getLineDetectorObservable() {
		return lineDetector;
	}

	@Override
	public IDistanceObservable[] getDistanceObservables() {
		return distanceSensors;
	}

	@Override
	public IDDActuators getIddActuators() {
		return ddActuators;
	}
	@Override
	public IDetectorObservable [] getLineDetectorObservables(){
		return lineDetectors;
	}
	
	public IColorSensorObservable getColorSensorObservable(){
		return null;
	}
	protected abstract IRobot createRealRobot();

	protected abstract IDDActuators createDDActuators();

	protected abstract IDetectorObservable createDetectorObservable();
	protected abstract IDetectorObservable[] createDetectorObservables();

	protected abstract IDistanceObservable[] createDistanceObservables();

}
