package it.unibo.iot.models.sensorData;

public class DistanceSensorData extends SensorData implements IDistanceSensorData {

	private DistanceValue distance;
	private int distanceCm;
	private String distanceStringRep;

	public DistanceSensorData(DistanceValue dst, DirectionValue direction) {
		super("DistanceSensor", direction);
		distance = dst;
		distanceCm = distance.getValue();
		distanceStringRep = distance.toString().toLowerCase();
	}

	public DistanceSensorData(int distanceCm, DirectionValue direction) {
		super("DistanceSensor", direction);
		this.distanceCm = distanceCm;
		if (distanceCm < DistanceValue.CLOSE.getValue()) {
			distance = DistanceValue.CLOSE;
		} else if (distanceCm < DistanceValue.MEDIUM.getValue()) {
			distance = DistanceValue.MEDIUM;
		} else {
			distance = DistanceValue.FAR;
		}
		distanceStringRep = "" + distanceCm;
	}

	public DistanceValue getDistance() {
		return distance;
	}

	@Override
	public String getValueStringRep() {
		return stringRep.replace("DET", getDistanceStringRep());
	}

	@Override
	public String getDistanceStringRep() {
		return distanceStringRep;
	}

	@Override
	public int getDistanceCM() {
		return distanceCm;
	}
}