package it.unibo.testRobot;

import space.IPoint;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.robot.IRobot;

public interface ITestRobot extends IRobot{

	IRobotCommand getCurrentCmd();
	IPoint getPosition();
	boolean isInsideLine();

}
