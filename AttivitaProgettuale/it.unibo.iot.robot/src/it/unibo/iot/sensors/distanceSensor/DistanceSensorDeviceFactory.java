package it.unibo.iot.sensors.distanceSensor;

import it.unibo.iot.lowLevelImpl.SysKB;
import it.unibo.iot.sensors.distanceSensor.impl.GPIODistanceSensorDevice;
import it.unibo.iot.sensors.distanceSensor.impl.GPIOLightDistanceSensorDevice;

public class DistanceSensorDeviceFactory {

	public static DistanceSensorDevice createGPIODistanceSensorDevice() {
		return new GPIODistanceSensorDevice();
	}

	public static DistanceSensorDevice createGPIOLightDistanceSensorDevice() {
		return new GPIOLightDistanceSensorDevice();
	}

	public static DistanceSensorDevice createGPIOLightDistanceSensorDevice(String pinString) {
		return new GPIOLightDistanceSensorDevice(pinString);
	}

	public static IDistanceObservable createBrickPiDistanceSensorDevice() {
		return SysKB.getBrickPiIOManagerInstance();
	}
	public static IDistanceObservable createArduinoDistanceSensorDevice() {
		return SysKB.getArduinoSerialIOManagerInstance();
	}
}
