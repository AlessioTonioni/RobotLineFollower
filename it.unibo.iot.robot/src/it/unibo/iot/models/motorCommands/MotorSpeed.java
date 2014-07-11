package it.unibo.iot.models.motorCommands;

public class MotorSpeed implements IMotorSpeed {

	private MotorSpeedValue speed;
	private int percentage;

	public MotorSpeed(MotorSpeedValue speed) {
		this.speed = speed;
		this.percentage = speed.getValue();
	}

	public MotorSpeed(int percentage) {
		percentage = (percentage < 0) ? 0 : percentage;
		percentage = (percentage > 100) ? 100 : percentage;
		MotorSpeedValue speed = MotorSpeedValue.MOTOR_SPEED_HIGH;

		MotorSpeedValue[] msv = MotorSpeedValue.values();
		for (MotorSpeedValue motorSpeedValue : msv) {
			if (percentage <= motorSpeedValue.getValue() && motorSpeedValue.getValue() < speed.getValue() ) {
				speed = motorSpeedValue;
			}
		}

		this.percentage = percentage;
		this.speed = speed;
	}

	public MotorSpeedValue getSpeed() {
		return speed;
	}

	@Override
	public String getDefaultStringRep() {
		return "motorSpeed(SPD)".replace("SPR", speed.toString().toLowerCase());
	}

	@Override
	public int getPercentageOfSpeed() {
		// TODO Auto-generated method stub
		return percentage;
	}
}