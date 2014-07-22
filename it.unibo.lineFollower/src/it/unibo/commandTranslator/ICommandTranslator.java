package it.unibo.commandTranslator;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.wheelCommands.IWheelCommand;

public interface ICommandTranslator {
	IRobotCommand getRobotCommand(int turn);
	IWheelCommand getWheelCommand(int turn);
}
