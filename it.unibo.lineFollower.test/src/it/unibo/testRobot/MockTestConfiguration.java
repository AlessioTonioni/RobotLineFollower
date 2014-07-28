package it.unibo.testRobot;

import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;



public class MockTestConfiguration implements IConfiguration {
	private ITestRobot robot;
	private ITestSensor sensors;
	
	public MockTestConfiguration(ITestRobot robot, ITestSensor sensors){
		this.robot=robot;
		this.sensors=sensors;
	}
	
	@Override
	public IRobot getRealRobot() {
		return robot;
	}

	@Override
	public IDetectorObservable getLineDetectorObservable() {
		return sensors;
	}

	@Override
	public IDetectorObservable[] getLineDetectorObservables() {
		return new IDetectorObservable[]{sensors};
	}

	@Override
	public IDistanceObservable[] getDistanceObservables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDDActuators getIddActuators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IColorSensorObservable getColorSensorObservable() {
		return sensors;
	}

	
}
