package it.unibo.iot.configuration;

import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public interface IConfiguration {
	public IRobot getRealRobot();

	public IDetectorObservable getLineDetectorObservable();
	public IDetectorObservable [] getLineDetectorObservables();

	public IDistanceObservable[] getDistanceObservables();
	
	public IDDActuators getIddActuators();
	
	public IColorSensorObservable getColorSensorObservable();

}
