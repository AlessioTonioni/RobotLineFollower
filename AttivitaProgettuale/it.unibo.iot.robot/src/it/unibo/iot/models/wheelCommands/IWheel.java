package it.unibo.iot.models.wheelCommands;

public interface IWheel {

	public String getID();
	
	public IWheelSpeed getSpeed();

	public String getStringRep();

}