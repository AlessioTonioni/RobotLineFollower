package it.unibo.commandTranslator;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
/**
 * Class able to translate from the turn computed by a PID controller to an attuable IRobotCommand
 * @author Alessio Tonioni
 *
 */
public interface ICommandTranslator {
	/**
	 * Translate the turn from a PID controller into a IRobotCommand
	 * @param turn
	 * @return
	 */
	IRobotCommand getRobotCommand(int turn);
	/**
	 * Translate the turn from a PID controller into a IWheelCommand executable by a DifferentialDriveRobot
	 * @param turn
	 * @return
	 */
	IWheelCommand getWheelCommand(int turn);
}
