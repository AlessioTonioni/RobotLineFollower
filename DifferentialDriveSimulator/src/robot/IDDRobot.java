package robot;

import space.IPoint;


public interface IDDRobot {

	IPoint getLeftWheelPosition();

	IPoint getRightWheelPosition();

	IPoint getRobotPosition();

	double getHeading();

	void update_ddr(double vr, double vl, double dt);

	void update_unicycle(double v, double omega, double dt);
	
	void update_ddPercentage(double rPercentage,double lPercentage, double dt);
	
	void setPosition(IPoint position,double heading);

}