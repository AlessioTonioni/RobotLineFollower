package it.unibo.iot.models.sensorData;

public interface IDistanceSensorData extends ISensorData {

	DistanceValue getDistance();

	int getDistanceCM();

	String getDistanceStringRep();
}