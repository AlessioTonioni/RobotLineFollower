package it.unibo.lineFollower;

import it.unibo.iot.models.robotCommands.RobotSpeedValue;

/**
 * Active class that controls the robot with the aim of following a line. Extends runnable so it does 
 * it's computation and control in an indipendent thread.
 * @author Alessio Tonioni
 *
 */
public interface ILineFollowerController extends Runnable{
	/**
	 * Sets if the robot move forward (true) or backward (false) 
	 * @param isForward 
	 */
	void setForward(boolean isForward);
	/**
	 * Sets the speed at which the robot it's supposed to move
	 * @param newSpeed
	 */
	void setSpeed(RobotSpeedValue newSpeed);
	/**
	 * Stop the execution of the controller and terminates teh thread.
	 */
	void terminate();
	
}
