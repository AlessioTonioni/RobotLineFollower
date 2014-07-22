package robot;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.robot.RobotToWheelSrategy;
import it.unibo.iot.robot.RobotToWheelStrategyDefault;
/**
 * Mock robot sub class of IRobot used in simulated scenarios, the execution of the command only 
 * sets an internal field with the command. 
 * @author alessio
 *
 */
public class MockRobot implements IDifferentialDriveRobot {
	
	private IWheelCommand currentCmd=null;
	private RobotToWheelSrategy translator;
	
	public MockRobot(){
		translator=new RobotToWheelStrategyDefault();
	}
	
	public IWheelCommand getCurrentCmd(){
		return currentCmd;
	}
	
	/**
	 * Sets the current command to command
	 */
	@Override
	public void execute(IRobotCommand command) {
		currentCmd=translator.robotToWheelCommand(command);
	}

	/**
	 * Sets the current command to wheelCommand
	 */
	@Override
	public void execute(IWheelCommand wheelCommand) {
		currentCmd=wheelCommand;
	}

}
