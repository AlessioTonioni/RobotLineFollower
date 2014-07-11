package it.unibo.iot.models.robotCommands;

public enum RobotSpeedValue {
	ROBOT_SPEED_HIGH(100), ROBOT_SPEED_MEDIUM(70), ROBOT_SPEED_LOW(50), LIBERA(25);

	private int numValue;

	private RobotSpeedValue(int numValue) {
		this.numValue = numValue;
	}

	public int getNumValue() {
		return numValue;
	}

	public RobotSpeedValue setNumValue(int numValue) {
		this.numValue = numValue;
		return this;
	}

}