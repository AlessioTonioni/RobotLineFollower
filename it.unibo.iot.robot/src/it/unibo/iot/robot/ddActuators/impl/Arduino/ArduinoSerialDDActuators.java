package it.unibo.iot.robot.ddActuators.impl.Arduino;

import it.unibo.iot.lowLevelImpl.IProcessSerialActuators;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;
import it.unibo.iot.robot.ddActuators.IDDActuators;

public class ArduinoSerialDDActuators implements IDDActuators {

	private IProcessSerialActuators actuators;
	
	public ArduinoSerialDDActuators(IProcessSerialActuators actuators) {
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
			return 9;
		case RMEDIUM:
			return 7;
		case RLOW:
			return 5;	
		case FLOW:
			return -5;
		case FMEDIUM:
			return -7;
		case FHIGH:
			return -9;
		default:
			return 0;
		}
	}
}
