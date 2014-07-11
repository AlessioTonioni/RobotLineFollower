package it.unibo.iot.sensors.distanceSensor;

import it.unibo.iot.models.sensorData.IDistanceSensorData;

public interface IDistanceObserver {

	public void notify(IDistanceSensorData distance);

}