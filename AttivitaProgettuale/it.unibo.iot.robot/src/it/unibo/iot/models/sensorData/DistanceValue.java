package it.unibo.iot.models.sensorData;

public enum DistanceValue {
	CLOSE(12),
	FAR(20),
	MEDIUM(43);
	
	private int value;
	private DistanceValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

}