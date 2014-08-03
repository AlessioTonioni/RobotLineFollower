package it.unibo.testRobot;

import it.unibo.DifferentialDriveSimulator.space.IPoint;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.robot.IRobot;

public interface ITestRobot extends IRobot{

	IRobotCommand getCurrentCmd();
	IPoint getPosition();
	boolean isInsideLine();

}
