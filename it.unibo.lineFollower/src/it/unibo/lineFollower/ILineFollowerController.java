package it.unibo.lineFollower;

import it.unibo.iot.models.robotCommands.RobotSpeedValue;

public interface ILineFollowerController extends Runnable{
	void setForward(boolean isForward);
	void setSpeed(RobotSpeedValue newSpeed);
	void doJob();
	void terminate();
}
