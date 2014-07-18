package it.unibo.iot.models.wheelCommands;

public enum WheelSpeedValue {
	FHIGH(100), FMEDIUM(70), FLOW(50), ZERO(0), RHIGH(-100), RMEDIUM(-70), RLOW(-50),
	FSETTABLE(25), RSETTABLE(-25), LWSETTABLE(0), RWSETTABLE(0);
	private int Value;

	private WheelSpeedValue(int Value) {
		this.Value = Value;
	}

	public int getValue() {
		return Value;
	}

	public WheelSpeedValue setValue(int value) {
		if(value>100)
			value=100;
		if(value<-100)
			value=-100;
		Value = value;
		return this;
	}

}