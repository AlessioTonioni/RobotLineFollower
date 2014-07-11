package it.unibo.iot.models.wheelCommands;

public interface IWheelCommand {

	String getStringRep();
	IWheel getWheelByID(String id);
}