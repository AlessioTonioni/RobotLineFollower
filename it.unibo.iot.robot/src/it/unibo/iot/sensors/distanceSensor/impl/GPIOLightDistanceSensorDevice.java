package it.unibo.iot.sensors.distanceSensor.impl;

import it.unibo.iot.robot.ddActuators.impl.GPIO.StringToGPIOPin;
import it.unibo.iot.sensors.distanceSensor.DistanceSensorDevice;

import java.io.FileInputStream;
import java.util.Properties;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;

public class GPIOLightDistanceSensorDevice extends DistanceSensorDevice {

	private GpioPinDigitalInput sensor;
	private boolean curState = false;

	public GPIOLightDistanceSensorDevice() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("pi4jPinConfiguration.properties"));
		} catch (Exception e) {
		}
		setPin(properties.getProperty("distanceSensorPin"));
	}

	public GPIOLightDistanceSensorDevice(String pinString) {
		setPin(pinString);
	}

	private void setPin(String pinString) {
		Pin pin = StringToGPIOPin.toPin(pinString);
		sensor = GpioFactory.getInstance().provisionDigitalInputPin(pin, PinPullResistance.PULL_DOWN);
		new Thread() {
			public void run() {

				while (!isInterrupted()) {
					try {
						Thread.sleep(50);
						boolean far = sensor.isLow();
						Thread.sleep(10);

						if (far == sensor.isLow()) {
							if (far == curState) {
							} else {
								curState = !curState;
								setLowLevelData(getCM(curState));
							}
						}
					} catch (Exception e) {
					}
				}
			}
		}.start();
		// sensor.addListener(new GpioPinListenerDigital() {
		// @Override
		// public void
		// handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent
		// event) {
		// try {
		//
		// //PinState pinState = event.getState();
		// try{
		// Thread.sleep(20);
		// if(sensor.isLow() == curState){
		// }else {
		// curState = !curState;
		// setLowLevelData(getCM(curState));
		// }
		// }
		// catch(Exception e){}
		// //setLowLevelData(getCM(pinState == PinState.LOW));
		//
		// } catch (Exception e) {
		//
		// }
		// }
		// });
	}

	@Override
	public int getLowLevelData() {
		return getCM(sensor.isLow());
	}

	private int getCM(boolean low) {
		return low ? 18 : 60;
	}
}
