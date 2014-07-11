package it.unibo.iot.sensors.detector;

import it.unibo.iot.models.sensorData.IDetection;

public interface IDetectorObserver {

	public void notify(IDetection detection);

}