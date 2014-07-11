package it.unibo.iot.sensors.distanceSensor;

public interface ILowLevelDistanceSensorDevice {

	int getLowLevelData();
	void setLowLevelData(int cm);
}
