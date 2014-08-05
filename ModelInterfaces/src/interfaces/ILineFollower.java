package interfaces;

import it.unibo.iot.robot.IRobot;

public interface ILineFollower {
	void setRobot(IRobot robot);
	IRobot getRobot();
	void start();
	void terminate();
}
