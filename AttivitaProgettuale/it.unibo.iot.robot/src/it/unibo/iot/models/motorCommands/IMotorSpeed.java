package it.unibo.iot.models.motorCommands;

public interface IMotorSpeed {

	MotorSpeedValue getSpeed();
	String getDefaultStringRep();
	int getPercentageOfSpeed();
}