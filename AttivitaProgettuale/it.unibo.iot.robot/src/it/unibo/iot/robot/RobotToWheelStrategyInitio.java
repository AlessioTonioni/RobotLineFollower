package it.unibo.iot.robot;

import it.unibo.iot.models.robotCommands.RobotBackward;
import it.unibo.iot.models.robotCommands.RobotBackwardLeft;
import it.unibo.iot.models.robotCommands.RobotBackwardRight;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.iot.models.robotCommands.RobotForwardLeft;
import it.unibo.iot.models.robotCommands.RobotForwardRight;
import it.unibo.iot.models.robotCommands.RobotLeft;
import it.unibo.iot.models.robotCommands.RobotRight;
import it.unibo.iot.models.robotCommands.RobotStop;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.IWheelSpeed;
import it.unibo.iot.models.wheelCommands.SysKB;
import it.unibo.iot.models.wheelCommands.Wheel;
import it.unibo.iot.models.wheelCommands.WheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeed;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;

public class RobotToWheelStrategyInitio extends RobotToWheelSrategy {

	@Override
	IWheelCommand elaborate(RobotStop command) {
		IWheelSpeed wheelSpeed = new WheelSpeed(WheelSpeedValue.ZERO);
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), wheelSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), wheelSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotForward command) {
		IWheelSpeed wheelSpeed = toForwardSpeed(command.getSpeed());
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), wheelSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), wheelSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotBackward command) {
		IWheelSpeed wheelSpeed = toBackwardSpeed(command.getSpeed());
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), wheelSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), wheelSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotLeft command) {
		IWheelSpeed leftSpeed = SysKB.WHEEL_SPEED_RHIGH;
		IWheelSpeed rightSpeed = SysKB.WHEEL_SPEED_FHIGH;
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotRight command) {
		IWheelSpeed leftSpeed = SysKB.WHEEL_SPEED_FHIGH;
		IWheelSpeed rightSpeed = SysKB.WHEEL_SPEED_RHIGH; 
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotForwardRight command) {
		IWheelSpeed leftSpeed = SysKB.WHEEL_SPEED_FHIGH;
		IWheelSpeed rightSpeed = wheelSpeedDown(toForwardSpeed(command.getSpeed()));
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotForwardLeft command) {
		IWheelSpeed leftSpeed = wheelSpeedDown(toForwardSpeed(command.getSpeed()));
		IWheelSpeed rightSpeed = SysKB.WHEEL_SPEED_FHIGH; 
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotBackwardLeft command) {
		IWheelSpeed leftSpeed = SysKB.WHEEL_SPEED_RHIGH;
		IWheelSpeed rightSpeed = wheelSpeedDown(toBackwardSpeed((command.getSpeed())));
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	@Override
	IWheelCommand elaborate(RobotBackwardRight command) {
		IWheelSpeed leftSpeed = wheelSpeedDown(toBackwardSpeed(command.getSpeed()));
		IWheelSpeed rightSpeed = SysKB.WHEEL_SPEED_RHIGH;
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

}
