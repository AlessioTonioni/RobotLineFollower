package it.unibo.iot.models.sensorData.color;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.SensorData;

public class ColorSensorData extends SensorData implements IColorSensorData {

	private IColor color;

	public ColorSensorData(IColor color, DirectionValue direction) {
		super("ColorObjectSensor", direction);
		this.color = color;
	}

	public IColor getColor() {
		return color;
	}

	@Override
	public String getValueStringRep() {
		return stringRep.replace("DET", color.getStringRep());
	}
}// end ColorObjectSensor