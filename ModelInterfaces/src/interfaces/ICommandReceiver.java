package interfaces;

import it.unibo.iot.robot.IRobot;

public interface ICommandReceiver {
	String receiveMessage();
	void executeRequest(String message);
	void setRobot(IRobot robot);
	IRobot getRobot();
	void setLineFollower(ILineFollower follow);
	ILineFollower getLineFollower();
}
