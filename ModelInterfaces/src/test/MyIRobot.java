package test;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.robot.IRobot;

public interface MyIRobot extends IRobot{
	boolean isOnLine();
	IRobotCommand getCurrentCmd();
}
