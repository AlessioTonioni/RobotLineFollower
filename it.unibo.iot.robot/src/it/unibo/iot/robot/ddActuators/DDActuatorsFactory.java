package it.unibo.iot.robot.ddActuators;

import it.unibo.iot.lowLevelImpl.SysKB;
import it.unibo.iot.robot.ddActuators.impl.Arduino.ArduinoSerialDDActuators;
import it.unibo.iot.robot.ddActuators.impl.BrickPi.BrickPiDDActuators;
import it.unibo.iot.robot.ddActuators.impl.GPIO.GPIOSoftPWMDCMotor;
import it.unibo.iot.robot.ddActuators.impl.GPIO.IBaseMotor;
import it.unibo.iot.robot.ddActuators.impl.GPIO.StringToGPIOPin;

import java.io.FileInputStream;
import java.util.Properties;

import com.pi4j.io.gpio.Pin;

public class DDActuatorsFactory {

	public static IDDActuators createDDActuators(String robotName) {
		IBaseMotor leftMotor;
		IBaseMotor rightMotor;
		Properties pi4jPinConfigurationProperties = new Properties();
		try {
			pi4jPinConfigurationProperties.load(new FileInputStream("configuration/" + robotName + "/pi4jPinConfiguration.properties"));
		} catch (Exception e) {
		}

		Pin leftPlusPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("leftPlusPin"));
		Pin leftMinusPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("leftMinusPin"));
		Pin leftEnPin = null;
		try {
			leftEnPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("leftEnPin"));
		} catch (Exception e) {
			leftEnPin = null;
		}

		Pin rightPlusPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("rightPlusPin"));
		Pin rightMinusPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("rightMinusPin"));
		Pin rightEnPin = null;
		try {
			rightEnPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("rightEnPin"));
		} catch (Exception e) {
			rightEnPin = null;
		}
		rightMotor = new GPIOSoftPWMDCMotor(rightPlusPin, rightMinusPin, rightEnPin,"configuration/" + robotName + "/pwmspeed.properties");
		leftMotor = new GPIOSoftPWMDCMotor(leftPlusPin, leftMinusPin, leftEnPin,"configuration/" + robotName + "/pwmspeed.properties");

		return new MotorBasedDDActuators(rightMotor, leftMotor);
	}

	public static IDDActuators createBrickPiDDActuators() {
		return new BrickPiDDActuators(SysKB.getBrickPiIOManagerInstance());
	}

	public static IDDActuators createArduinoSerialDDActuators() {
		return new ArduinoSerialDDActuators(SysKB.getArduinoSerialIOManagerInstance());
	}
}