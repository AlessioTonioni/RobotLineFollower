package it.unibo.iot.robot;

import it.unibo.iot.models.robotCommands.IRobotCommand;

public interface IRobot {

	public void execute(IRobotCommand command);

}