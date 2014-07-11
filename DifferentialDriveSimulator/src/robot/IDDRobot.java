package robot;

import space.IPoint;


public interface IDDRobot {

	public abstract IPoint getLeftWheelPosition();

	public abstract IPoint getRightWheelPosition();

	public abstract IPoint getRobotPosition();

	public abstract double getHeading();

	public abstract void update_ddr(double vr, double vl, double dt);

	public abstract void update_unicycle(double v, double omega, double dt);
	
	public abstract void update_ddPercentage(double rPercentage,double lPercentage, double dt);

}