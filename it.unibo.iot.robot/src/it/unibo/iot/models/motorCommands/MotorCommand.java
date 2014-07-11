package it.unibo.iot.models.motorCommands;

public  class MotorCommand implements IMotorCommand {

	private IMotorSpeed speed;
	private MotorState state;

	
	public MotorCommand(IMotorSpeed speed, MotorState state) {
		
		this.state = state;
		this.speed = speed; 
	}

	@Override
	public IMotorSpeed getSpeed() {
		return speed;
	}
	
	@Override
	public MotorState getState() {
		return state;
	}
	

}