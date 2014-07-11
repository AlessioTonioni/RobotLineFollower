package it.unibo.iot.models.sensorData;

public abstract class SensorData implements ISensorData {
	
	protected String stringRep = "detection(DET, DIRECTION)";
	
	private String name;
	protected DirectionValue direction;

	public SensorData(String name, DirectionValue direction) {
		this.name = name;
		this.direction = direction;
		this.stringRep = stringRep.replace("DIRECTION", direction.toString().toLowerCase());
	}

	public String getName() {
		return name;
	}

	public DirectionValue getDirection() {
		return direction;
	}
	
	@Override
	public abstract String getValueStringRep();
}