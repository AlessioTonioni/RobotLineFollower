package it.unibo.iot.robot.ddActuators.impl.GPIO;

import it.unibo.iot.models.motorCommands.IMotorSpeed;

import com.pi4j.io.gpio.Pin;

public class GPIODCMotor extends GPIOMotor {

	public GPIODCMotor(Pin plusPin, Pin minusPin) {
		super(plusPin, minusPin);
	}

	@Override
	protected void executeCounterClockWise(IMotorSpeed speed) {
		plus.high();
		minus.low();
	}

	@Override
	protected void executeClockWise(IMotorSpeed speed) {
		plus.low();
		minus.high();
	}

	@Override
	protected void executeStop() {
		plus.low();
		minus.low();
	}
}