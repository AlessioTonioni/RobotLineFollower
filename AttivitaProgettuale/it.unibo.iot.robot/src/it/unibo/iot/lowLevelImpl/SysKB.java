package it.unibo.iot.lowLevelImpl;


public class SysKB {
	private static BrickPiIOManager brickPiIOManager;
	private static ArduinoIOManager arduinoIOManager;

	public static BrickPiIOManager getBrickPiIOManagerInstance() {
		if (brickPiIOManager == null) {
			brickPiIOManager = new BrickPiIOManager();
		}
		return brickPiIOManager;
	}

	public static ArduinoIOManager getArduinoSerialIOManagerInstance() {
		if (arduinoIOManager == null) {
			arduinoIOManager = new ArduinoIOManager();
		}
		return arduinoIOManager;
	}
	
}
