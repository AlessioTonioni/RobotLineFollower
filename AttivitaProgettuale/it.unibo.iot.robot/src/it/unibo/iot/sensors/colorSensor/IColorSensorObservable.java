package it.unibo.iot.sensors.colorSensor;

public interface IColorSensorObservable {
	public void addObserver(IColorSensorObserver observer);
	public void removeObserver(IColorSensorObserver observer);
}
