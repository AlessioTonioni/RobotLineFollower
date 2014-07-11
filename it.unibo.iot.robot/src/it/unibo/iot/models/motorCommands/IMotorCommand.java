package it.unibo.iot.models.motorCommands;

public interface IMotorCommand {

	MotorState getState();
	IMotorSpeed getSpeed();
}