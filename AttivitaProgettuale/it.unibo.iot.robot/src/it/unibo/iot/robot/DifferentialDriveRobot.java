package it.unibo.iot.robot;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.robot.ddActuators.IDDActuators;

public class DifferentialDriveRobot implements IDifferentialDriveRobot {

	protected IDDActuators actuators;
	private RobotToWheelContext context;
	private IRobotToWhellStarategy robotToWhellStarategy;

	public DifferentialDriveRobot(IDDActuators actuators, String robotType) {
		this.actuators = actuators;
		context = new RobotToWheelContext();
		robotToWhellStarategy = context.getRTWStrategy(robotType);

	}

	public void execute(IRobotCommand command) {
//		System.out.println(command.getStringRep());

		IWheelCommand wheelCommand = robotToWhellStarategy.robotToWheelCommand(command);
//		System.out.println(wheelCommand.getStringRep());
		actuators.setWheelCommand(wheelCommand);
	}

	public void execute(IWheelCommand wheelCommand){
//		System.out.println(wheelCommand.getStringRep());
		actuators.setWheelCommand(wheelCommand);
	}
}