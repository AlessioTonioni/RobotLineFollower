package it.unibo.iot.robot.ddActuators;
import it.unibo.iot.models.wheelCommands.IWheelCommand;

public interface IDDActuators {

	public void setWheelCommand(IWheelCommand wheelCommand);

}