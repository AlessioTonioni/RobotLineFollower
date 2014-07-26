package configuration;

import robot.ColorLineSensorDDRobot;
import robot.MockRobot;
import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public class MockConfiguration implements IConfiguration {

	private ColorLineSensorDDRobot simulatedRobot;
	
	public MockConfiguration(ColorLineSensorDDRobot robot){
		simulatedRobot=robot;
	}
	
	@Override
	public IRobot getRealRobot() {
		return new MockRobot();
	}

	@Override
	public IDetectorObservable getLineDetectorObservable() {
		return simulatedRobot;
	}

	@Override
	public IDetectorObservable[] getLineDetectorObservables() {
		return new IDetectorObservable[]{simulatedRobot};
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
		return simulatedRobot;
	}
	
}
