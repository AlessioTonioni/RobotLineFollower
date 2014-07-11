package it.unibo.iot.models.wheelCommands;

public class Wheel implements IWheel {

	private IWheelSpeed speed;
	protected String id;

	public Wheel(String id, IWheelSpeed speed) {
		this.id = id;
		this.speed = speed;
	}

	@Override
	public IWheelSpeed getSpeed() {
		return speed;
	}

	@Override
	public String getStringRep() {
		return "wheel(ID, SPD)".replace("SPD", speed.getSpeedStringRep()).replace("ID", id);
	}

	@Override
	public String getID() {
		return id;
	}
}