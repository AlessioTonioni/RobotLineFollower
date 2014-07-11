package it.unibo.iot.robot.ddActuators.impl.GPIO;

import it.unibo.iot.models.motorCommands.IMotorSpeed;
import it.unibo.iot.models.motorCommands.MotorSpeedValue;

import java.io.FileInputStream;
import java.util.Properties;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.wiringpi.SoftPwm;

public class GPIOSoftPWMDCMotor extends GPIOMotor {

	private int plusInt;
	private int minusInt;

	protected int high;
	protected int medium;
	protected int low;

	public GPIOSoftPWMDCMotor(Pin plusPin, Pin minusPin) {
		this(plusPin,minusPin,null);
	}
	
	public GPIOSoftPWMDCMotor(Pin plusPin, Pin minusPin, Pin enPin){
		this(plusPin,minusPin,enPin,null);
	}
	public GPIOSoftPWMDCMotor(Pin plusPin, Pin minusPin, Pin enPin,String pwmSpeed){
		super(plusPin, minusPin);
		plusInt = plusPin.getAddress();
		minusInt = minusPin.getAddress();
		com.pi4j.wiringpi.Gpio.wiringPiSetup();
		SoftPwm.softPwmCreate(plusInt, 0, 100);
		SoftPwm.softPwmCreate(minusInt, 0, 100);
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(pwmSpeed));
			high = Integer.parseInt(properties.getProperty("HIGH"));
			medium = Integer.parseInt(properties.getProperty("MEDIUM"));
			low = Integer.parseInt(properties.getProperty("LOW"));
		} catch (Exception e) {
			high = 100;
			medium = 50;
			low = 30;
		}
		if (enPin != null) {
			final GpioPinDigitalOutput en = GpioFactory.getInstance().provisionDigitalOutputPin(enPin, PinState.HIGH);
			en.high();
		}
		
	}

	@Override
	protected void executeClockWise(IMotorSpeed speed) {
		SoftPwm.softPwmWrite(minusInt, toIntSpeed(speed));
		SoftPwm.softPwmWrite(plusInt, 0);
	}

	@Override
	protected void executeCounterClockWise(IMotorSpeed speed) {
		SoftPwm.softPwmWrite(minusInt, 0);
		SoftPwm.softPwmWrite(plusInt, toIntSpeed(speed));
	}

	@Override
	protected void executeStop() {
		SoftPwm.softPwmWrite(plusInt, 0);
		SoftPwm.softPwmWrite(minusInt, 0);
	}

	protected int toIntSpeed(IMotorSpeed speed) {
		MotorSpeedValue speedValue=speed.getSpeed();
		switch(speedValue){
		case MOTOR_SPEED_HIGH:
			return high;
		case MOTOR_SPEED_LOW:
			return low;
		case MOTOR_SPEED_MEDIUM:
			return medium;
		case MOTOR_SPEED_SETTABLE:
			return speedValue.getValue();
		default:
			return low;
		}
		
	}
}