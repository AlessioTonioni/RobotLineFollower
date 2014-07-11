package it.unibo.iot.robot;

public enum DDWheelID {
	
	LEFT(1), RIGHT(0);
	
	private int val;
	
	private DDWheelID(int val)
	{
		this.val = val;
	}
	
	public int getVal()
	{
		return val;
	}
}
