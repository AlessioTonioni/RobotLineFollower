package it.unibo.iot.robot.ddActuators;

import it.unibo.iot.models.motorCommands.IMotorSpeed;
import it.unibo.iot.models.motorCommands.MotorCommand;
import it.unibo.iot.models.motorCommands.MotorSpeed;
import it.unibo.iot.models.motorCommands.MotorSpeedValue;
import it.unibo.iot.models.motorCommands.MotorState;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;
import it.unibo.iot.robot.ddActuators.impl.GPIO.IBaseMotor;

public class MotorBasedDDActuators implements IDDActuators {

	private IBaseMotor rightMotor;
	private IBaseMotor leftMotor;

	public MotorBasedDDActuators(IBaseMotor rightMotor, IBaseMotor leftMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}

	@Override
	public void setWheelCommand(IWheelCommand wheelCommand) {
		try {
			IWheel leftWheel = wheelCommand.getWheelByID(DDWheelID.LEFT.toString());
			MotorState leftState = toMotorState(leftWheel.getSpeed().getSpeed());
			IMotorSpeed leftSpeed = new MotorSpeed(toMotorSpeed(leftWheel.getSpeed().getSpeed()));

			IWheel rightWheel = wheelCommand.getWheelByID(DDWheelID.RIGHT.toString());
			MotorState rightState = toMotorState(rightWheel.getSpeed().getSpeed());
			IMotorSpeed rightSpeed = new MotorSpeed(toMotorSpeed(rightWheel.getSpeed().getSpeed()));
			leftMotor.execute(new MotorCommand(leftSpeed,leftState));
			rightMotor.execute(new MotorCommand(rightSpeed,rightState));
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	protected MotorState toMotorState(WheelSpeedValue value) {
		if (value == WheelSpeedValue.FHIGH || value == WheelSpeedValue.FMEDIUM || value == WheelSpeedValue.FLOW || value==WheelSpeedValue.FSETTABLE) {
			return MotorState.CW;
		}
		if (value == WheelSpeedValue.RHIGH || value == WheelSpeedValue.RMEDIUM || value == WheelSpeedValue.RLOW || value==WheelSpeedValue.RSETTABLE) {
			return MotorState.CCW;
		}
		return MotorState.STOP;
	}

	protected MotorSpeedValue toMotorSpeed(WheelSpeedValue value) {
		if (value == WheelSpeedValue.FHIGH || value == WheelSpeedValue.RHIGH) {
			return MotorSpeedValue.MOTOR_SPEED_HIGH;
		}
		if (value == WheelSpeedValue.FMEDIUM || value == WheelSpeedValue.RMEDIUM) {
			return MotorSpeedValue.MOTOR_SPEED_MEDIUM;
		}
		if (value == WheelSpeedValue.FLOW || value == WheelSpeedValue.RLOW) {
			return MotorSpeedValue.MOTOR_SPEED_LOW;
		}
		if(value==WheelSpeedValue.FSETTABLE ){
			return MotorSpeedValue.MOTOR_SPEED_SETTABLE.setValue(value.getValue());
		}
		if( value==WheelSpeedValue.RSETTABLE){
			return MotorSpeedValue.MOTOR_SPEED_SETTABLE.setValue(-value.getValue());
		}
		return  MotorSpeedValue.MOTOR_SPEED_LOW;
	}
}