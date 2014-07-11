package it.unibo.iot.models.motorCommands;

public enum MotorSpeedValue {
	MOTOR_SPEED_HIGH(100), MOTOR_SPEED_MEDIUM(70), MOTOR_SPEED_LOW(50), MOTOR_SPEED_SETTABLE(25);
	private int Value;

	private MotorSpeedValue(int Value) {
		this.Value = Value;
	}

	public int getValue() {
		return Value;
	}

	public MotorSpeedValue setValue(int value) {
		Value = value;
		return this;
	}

}