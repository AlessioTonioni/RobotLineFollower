package it.unibo.iot.sensors.colorSensor;

import it.unibo.iot.lowLevelImpl.SysKB;

public class ColorSensorFactory {
	public static IColorSensorObservable createArduinoColorSensorObservable(){
		return SysKB.getArduinoSerialIOManagerInstance().getColorSensorObservable();
	}
}
