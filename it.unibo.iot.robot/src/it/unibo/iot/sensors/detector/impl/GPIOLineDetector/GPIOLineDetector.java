package it.unibo.iot.sensors.detector.impl.GPIOLineDetector;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.sensors.detector.Detector;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class GPIOLineDetector extends Detector {

	private static final long LIMIT = 1;
	private long millis = 0;
	private GpioPinDigitalInput sensor;

	public GPIOLineDetector(DirectionValue direction, Pin lineSensorPin) {
		super("LineDetection", direction);

		sensor = GpioFactory.getInstance().provisionDigitalInputPin(lineSensorPin, PinPullResistance.PULL_DOWN);
		sensor.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				try {
					if (System.currentTimeMillis() - millis > LIMIT) {
						PinState pinState = event.getState();
						setLowLevelDetection(pinState == PinState.HIGH);
						millis = System.currentTimeMillis();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean getLowLevelDetection() {
		return sensor.isHigh();
	}
}
