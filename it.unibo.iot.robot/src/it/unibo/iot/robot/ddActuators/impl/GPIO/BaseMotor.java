package it.unibo.iot.robot.ddActuators.impl.GPIO;

import it.unibo.iot.models.motorCommands.IMotorCommand;
import it.unibo.iot.models.motorCommands.IMotorSpeed;
import it.unibo.iot.models.motorCommands.MotorState;

public abstract class BaseMotor implements IBaseMotor {

	@Override
	public void execute(IMotorCommand command) {
		MotorState state= command.getState(); 
		IMotorSpeed speed= command.getSpeed();

//			System.out.println(state.toString());
		if(state == MotorState.CW)
		{
			executeClockWise(speed);
		}
		if(state == MotorState.CCW)
		{
			executeCounterClockWise(speed);
		}
		if(state == MotorState.STOP)
		{
			executeStop();
		}
	}

	protected abstract void executeCounterClockWise(IMotorSpeed speed2);

	protected abstract void executeClockWise(IMotorSpeed speed2);
	
	protected abstract void executeStop();
}
