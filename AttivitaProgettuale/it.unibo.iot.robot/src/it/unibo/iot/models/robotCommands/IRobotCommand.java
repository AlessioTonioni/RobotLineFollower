package it.unibo.iot.models.robotCommands;

/**
 * @author user
 * @version 1.0
 * @created 23-���-2014 13:21:58
 */
public interface IRobotCommand {

	public IRobotSpeed getSpeed();

	public String getStringRep();

}