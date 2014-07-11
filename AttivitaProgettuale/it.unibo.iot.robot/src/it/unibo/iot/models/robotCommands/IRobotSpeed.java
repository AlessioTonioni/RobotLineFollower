package it.unibo.iot.models.robotCommands;

public interface IRobotSpeed {

	public RobotSpeedValue getSpeed();

	public String getStringRep();

	public int getPercentageOfSpeed();

}