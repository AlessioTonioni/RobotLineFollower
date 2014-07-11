package it.unibo.iot.robot.ddActuators.impl.GPIO;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

public abstract class GPIOMotor extends BaseMotor {

	protected GpioPinDigitalOutput plus;
	protected GpioPinDigitalOutput minus;

	public GPIOMotor(Pin plusPin, Pin minusPin) {
		this.plus = GpioFactory.getInstance().provisionDigitalOutputPin(plusPin, PinState.LOW);
		this.minus = GpioFactory.getInstance().provisionDigitalOutputPin(minusPin, PinState.LOW);
	}
}
