package it.unibo.testRobot;

import it.unibo.iot.models.sensorData.color.IColor;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.detector.IDetectorObservable;

public interface ITestSensor extends IColorSensorObservable, IDetectorObservable {

	public void raiseOnLine();
	public void raiseColorChanged(IColor newColor);
}
