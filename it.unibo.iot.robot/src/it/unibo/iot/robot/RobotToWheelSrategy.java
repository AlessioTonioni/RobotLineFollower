package it.unibo.iot.robot;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.IRobotSpeed;
import it.unibo.iot.models.robotCommands.RobotBackward;
import it.unibo.iot.models.robotCommands.RobotBackwardLeft;
import it.unibo.iot.models.robotCommands.RobotBackwardRight;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.iot.models.robotCommands.RobotForwardLeft;
import it.unibo.iot.models.robotCommands.RobotForwardRight;
import it.unibo.iot.models.robotCommands.RobotLeft;
import it.unibo.iot.models.robotCommands.RobotRight;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.robotCommands.RobotStop;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.IWheelSpeed;
import it.unibo.iot.models.wheelCommands.WheelSpeed;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;

public abstract class RobotToWheelSrategy implements IRobotToWhellStarategy {

	@Override
	public IWheelCommand robotToWheelCommand(IRobotCommand command) {
		if (command instanceof RobotStop) {
			return elaborate((RobotStop) command);
		} else if (command instanceof RobotForward) {
			return elaborate((RobotForward) command);
		} else if (command instanceof RobotBackward) {
			return elaborate((RobotBackward) command);
		} else if (command instanceof RobotLeft) {
			return elaborate((RobotLeft) command);
		} else if (command instanceof RobotRight) {
			return elaborate((RobotRight) command);
		} else if (command instanceof RobotForwardRight) {
			return elaborate((RobotForwardRight) command);
		} else if (command instanceof RobotBackwardRight) {
			return elaborate((RobotBackwardRight) command);
		} else if (command instanceof RobotForwardLeft) {
			return elaborate((RobotForwardLeft) command);
		} else if (command instanceof RobotBackwardLeft) {
			return elaborate((RobotBackwardLeft) command);
		}
		return null;
	}

	abstract IWheelCommand elaborate(RobotStop command);

	abstract IWheelCommand elaborate(RobotForward command);

	abstract IWheelCommand elaborate(RobotForwardRight command);

	abstract IWheelCommand elaborate(RobotForwardLeft command);

	abstract IWheelCommand elaborate(RobotLeft command);

	abstract IWheelCommand elaborate(RobotRight command);

	abstract IWheelCommand elaborate(RobotBackward command);

	abstract IWheelCommand elaborate(RobotBackwardLeft command);

	abstract IWheelCommand elaborate(RobotBackwardRight command);

	protected IWheelSpeed toForwardSpeed(IRobotSpeed speed) {
		RobotSpeedValue robotVal = speed.getSpeed();
		WheelSpeedValue val = robotVal == RobotSpeedValue.ROBOT_SPEED_HIGH ? WheelSpeedValue.FHIGH
				: robotVal == RobotSpeedValue.ROBOT_SPEED_MEDIUM ? WheelSpeedValue.FMEDIUM : WheelSpeedValue.FLOW;
		return new WheelSpeed(val);
	}

	protected IWheelSpeed toBackwardSpeed(IRobotSpeed speed) {
		RobotSpeedValue robotVal = speed.getSpeed();
		WheelSpeedValue val = robotVal == RobotSpeedValue.ROBOT_SPEED_HIGH ? WheelSpeedValue.RHIGH 
				: robotVal == RobotSpeedValue.ROBOT_SPEED_MEDIUM ? WheelSpeedValue.RMEDIUM : WheelSpeedValue.RLOW;
		return new WheelSpeed(val);
	}

	protected IWheelSpeed wheelSpeedDown(IWheelSpeed speed) {
		WheelSpeedValue wsv = speed.getSpeed();
		WheelSpeedValue wsv_down=WheelSpeedValue.ZERO;
		switch (wsv) {
		case FHIGH: case FMEDIUM: case FLOW:
			wsv_down=WheelSpeedValue.FSETTABLE.setValue(wsv.getValue()/2 -5);
			break;
		case RHIGH: case RMEDIUM: case RLOW:
			 wsv_down = WheelSpeedValue.RSETTABLE.setValue(wsv.getValue()/2 +5);
			break;
		default:
			wsv_down = WheelSpeedValue.ZERO;
			break;
		}
		return new WheelSpeed(wsv_down);
	}
	
	protected IWheelSpeed wheelSpeedUp(IWheelSpeed speed) {
		WheelSpeedValue wsv = speed.getSpeed();
		WheelSpeedValue wsv_up=WheelSpeedValue.ZERO;
		switch (wsv) {
		case FHIGH: case FMEDIUM: case FLOW:
			wsv_up=WheelSpeedValue.FSETTABLE.setValue(wsv.getValue());
			break;
		case RHIGH: case RMEDIUM: case RLOW:
			 wsv_up = WheelSpeedValue.RSETTABLE.setValue(wsv.getValue());
			break;
		default:
			wsv_up = WheelSpeedValue.ZERO;
			break;
		}
		return new WheelSpeed(wsv_up);
	}

}
