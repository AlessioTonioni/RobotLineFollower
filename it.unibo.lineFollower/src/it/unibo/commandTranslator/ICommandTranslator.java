package it.unibo.commandTranslator;

import it.unibo.iot.robot.IRobot;
/**
 * Class able to translate from the turn computed by a PID controller to an attuable IRobotCommand
 * @author Alessio Tonioni
 *
 */
public interface ICommandTranslator {
	/**
	 * Translate the turn from a PID controller into a proper command for the robot and execute it
	 * @param turn
	 * @return
	 */
	void executeRobotCommand(int turn);

	/**
	 * Sets the default speed at which the robot it's supposed to move
	 * @param speedPercentage
	 */
	void setSpeed(int speedPercentage);
	
	int getSpeed();
	
	/**
	 * Sets the robot to control 
	 * @param robot
	 */
	void setRobot(IRobot robot);
	
	IRobot getRobot();
	
	/**
	 * sets if the robot is moving forward or backward
	 * @param isForward
	 */
	void setForward(boolean isForward);
	
	boolean isForward();
}
