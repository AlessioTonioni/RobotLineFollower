package it.unibo.iot.models.wheelCommands;

public class WheelSpeed implements IWheelSpeed {

	private WheelSpeedValue speed;
	private int percentage;

	public WheelSpeed(WheelSpeedValue speed) {
		this.speed = speed;
		this.percentage = speed.getValue();
	}

	public WheelSpeed(int percentage) {
		percentage = (percentage < 0) ? 0 : percentage;
		percentage = (percentage > 100) ? 100 : percentage;

		WheelSpeedValue speed = WheelSpeedValue.FHIGH;

		WheelSpeedValue[] wsv = WheelSpeedValue.values();
		for (WheelSpeedValue wheelSpeedValue : wsv) {
			if (percentage <=  wheelSpeedValue.getValue()  && wheelSpeedValue.getValue() < speed.getValue()  ) {
				speed = wheelSpeedValue;
			}
		}

		this.speed = speed;
		this.percentage = percentage;
	}

	@Override
	public WheelSpeedValue getSpeed() {
		return speed;
	}

	@Override
	public String getSpeedStringRep() {
		return "wheelSpeed(SPD)".replace("SPD", speed.toString().toLowerCase());
	}

	@Override
	public int getPercentageOfSpeed() {
		return percentage;
	}
}