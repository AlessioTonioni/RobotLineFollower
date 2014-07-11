package it.unibo.iot.models.wheelCommands;

public class WheelCommand implements IWheelCommand {

	private IWheel[] wheels;

	public WheelCommand(IWheel... wheels) {
		this.wheels = wheels;
	}

	public String getStringRep(){
		StringBuilder sb = new StringBuilder("wheelCommand(");
		for(int i = 0; i < wheels.length; i++)
		{
			IWheel wheel = wheels[i];
			sb.append(wheel.getStringRep());
			if(i < wheels.length -1)
			{
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public IWheel getWheelByID(String id) {
		for(IWheel wheel : wheels)
		{
			if(wheel.getID().equalsIgnoreCase(id))
			{
				return wheel;
			}
		}
		return null;
	}
}