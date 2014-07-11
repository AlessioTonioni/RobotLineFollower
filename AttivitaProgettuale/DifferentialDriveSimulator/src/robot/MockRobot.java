package robot;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.robot.RobotToWheelSrategy;
import it.unibo.iot.robot.RobotToWheelStrategyDefault;

public class MockRobot implements IDifferentialDriveRobot {
	
	private IWheelCommand currentCmd=null;
	private RobotToWheelSrategy translator;
	
	public MockRobot(){
		translator=new RobotToWheelStrategyDefault();
	}
	
	public IWheelCommand getCurrentCmd(){
		return currentCmd;
	}
	
	@Override
	public void execute(IRobotCommand command) {
		currentCmd=translator.robotToWheelCommand(command);
	}

	@Override
	public void execute(IWheelCommand wheelCommand) {
		currentCmd=wheelCommand;
	}

}
