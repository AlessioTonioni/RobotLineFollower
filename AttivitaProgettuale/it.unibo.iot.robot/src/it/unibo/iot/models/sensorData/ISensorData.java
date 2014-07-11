package it.unibo.iot.models.sensorData;

public interface ISensorData {

	String getName();

	String getValueStringRep();

	DirectionValue getDirection();
}