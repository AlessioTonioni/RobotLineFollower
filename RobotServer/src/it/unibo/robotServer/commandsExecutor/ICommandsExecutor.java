package it.unibo.robotServer.commandsExecutor;

import it.unibo.iot.robot.IRobot;

import java.io.IOException;

/**
 * Commands executor, translates the command type into real action executed by the real robot.
 * @author Alessio Tonioni
 *
 */
public interface ICommandsExecutor {

	/**
	 * Creates an ILineFollowerController and starts it. At the return from this method the robot should 
	 * automaticaly follow the line on the ground.
	 * @param cntType type of the controller
	 * @param speed default speed 
	 * @param isForward true if the robot moves forward
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public abstract void executeController(String cntType, String speed,String isForward) throws IOException, InterruptedException;

	/**
	 * Execute a single simple command, at the return from this method the robot is executing the given 
	 * command and continues till another command is put in execution. 
	 * @param cmd command to execute
	 * @param speed speed for the command
	 * @throws InterruptedException
	 */
	public abstract void executeCommand(String cmd, String speed)
			throws InterruptedException;

	/**
	 * Returns the IRobot that it is controlled.
	 * @return
	 */
	public abstract IRobot getRobot();

	/**
	 * Sets the IRobot to control
	 * @param robot
	 */
	public abstract void setRobot(IRobot robot);

}