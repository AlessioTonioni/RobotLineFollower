package it.unibo.iot.models.robotCommands;

public class RobotSpeed implements IRobotSpeed {

	private RobotSpeedValue speed;
	private int percentage;

	public RobotSpeed(RobotSpeedValue speed) {
		this.speed = speed;
		this.percentage = speed.getNumValue();
	}

	public RobotSpeed(int percentage) {
		percentage = (percentage < 0) ? 0 : percentage;
		percentage = (percentage > 100) ? 100 : percentage;

		RobotSpeedValue speed = RobotSpeedValue.ROBOT_SPEED_HIGH;
		RobotSpeedValue[] rsv = RobotSpeedValue.values();
		for (RobotSpeedValue robotSpeedValue : rsv) {
			if (percentage <= robotSpeedValue.getNumValue() && robotSpeedValue.getNumValue() < speed.getNumValue()) {
				speed = robotSpeedValue;
			}
		}

		this.speed = speed;
		this.percentage = percentage;
	}

	public RobotSpeedValue getSpeed() {
		return speed;
	}

	public int getPercentageOfSpeed() {
		return percentage;
	}

	@Override
	public String getStringRep() {
		return "robotSpeed(SPD)".replace("SPD", speed.toString().toLowerCase());
	}
}