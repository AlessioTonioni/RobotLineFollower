package it.unibo.iot.robot;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.wheelCommands.IWheelCommand;

public interface IRobotToWhellStarategy {
	IWheelCommand robotToWheelCommand(IRobotCommand command);
}
