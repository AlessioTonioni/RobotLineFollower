package it.unibo.iot.models.wheelCommands;

public interface IWheelSpeed {
	public WheelSpeedValue getSpeed();
	public String getSpeedStringRep();
	public int getPercentageOfSpeed();
}