package it.unibo.iot.sensors.colorSensor;

import it.unibo.iot.models.sensorData.color.IColorSensorData;

public interface IColorSensorObserver {
	public void notify(IColorSensorData colorSensorData);
}
