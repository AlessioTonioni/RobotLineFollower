package it.unibo.iot.robot.ddActuators.impl.GPIO;

import it.unibo.iot.models.motorCommands.IMotorCommand;

public interface IBaseMotor {
	void execute(IMotorCommand command);
}
