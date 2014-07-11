package it.unibo.iot.robot.ddActuators.impl.BrickPi;

import it.unibo.iot.lowLevelImpl.IProcessSerialActuators;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;
import it.unibo.iot.robot.ddActuators.IDDActuators;

public class BrickPiDDActuators implements IDDActuators {

	private IProcessSerialActuators actuators;
	
	public BrickPiDDActuators(IProcessSerialActuators actuators) {
		this.actuators = actuators;
	}

	@Override
	public void setWheelCommand(IWheelCommand wheelCommand) {
		try {
			int leftMotor = wheelSpeedValueToInt(wheelCommand.getWheelByID(DDWheelID.LEFT.toString()).getSpeed().getSpeed());
			int rightMotor = wheelSpeedValueToInt(wheelCommand.getWheelByID(DDWheelID.RIGHT.toString()).getSpeed().getSpeed());
			actuators.setWheelCommand(leftMotor, rightMotor);
		} catch (Exception e) {
		}
	}

	private int wheelSpeedValueToInt(WheelSpeedValue wheelSpeedValue) {
		switch (wheelSpeedValue) {
		case RHIGH:
			return 1;
		case RMEDIUM:
			return 2;
		case RLOW:
			return 3;
		case FLOW:
			return 5;
		case FMEDIUM:
			return 6;
		case FHIGH:
			return 7;
		default:
			return 4;
		}
	}
}
