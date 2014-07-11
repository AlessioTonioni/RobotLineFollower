package it.unibo.iot.robot;

import it.unibo.iot.models.wheelCommands.IWheelCommand;

public interface IDifferentialDriveRobot extends IRobot {

	public void execute(IWheelCommand wheelCommand);
}