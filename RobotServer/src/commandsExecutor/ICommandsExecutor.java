package commandsExecutor;

import it.unibo.iot.robot.IRobot;

import java.io.IOException;

public interface ICommandsExecutor {

	public abstract void executeController(String cntType, String speed,String isForward) throws IOException, InterruptedException;

	public abstract void executeCommand(String cmd, String speed)
			throws InterruptedException;

	public abstract IRobot getRobot();

	public abstract void setRobot(IRobot robot);

}