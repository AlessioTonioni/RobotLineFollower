package it.unibo.iot.models.sensorData;

public class Detection extends SensorData implements IDetection {

	private boolean val;

	public Detection(String name, DirectionValue direction, boolean val) {
		super("LineSensor", direction);
		this.val = val;
	}

	public boolean getVal() {
		return val;
	}

	@Override
	public String getValueStringRep() {
		String value = val ? "Detected" : "Left";
		return stringRep.replace("DET", "line" + value);
	}
}