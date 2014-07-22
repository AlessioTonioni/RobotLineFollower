package robot;

import space.IPoint;

/**
 * Simulates the physical behavior of a differential drive robot in a simulated environment
 * @author Alessio Tonioni
 *
 */
public interface IDDRobot {

	IPoint getLeftWheelPosition();

	IPoint getRightWheelPosition();

	IPoint getRobotPosition();

	double getHeading();
	
/**
 * Update the robot position
 * @param velocityRight: right well angular speed
 * @param velocityLeft : left well angular speed
 * @param deltaT: time intervall
 */
	void update_ddr(double velocityRight, double velocityLeft, double deltaT);

	/**
	 * Update the robot position
	 * @param v: linear velocity of the robot 
	 * @param omega: angular velocity of the robot
	 * @param dt: time intervall
	 */
	void update_unicycle(double v, double omega, double dt);
	
	/**
	 * Update the robot position
	 * @param rPercentage: power percentage on right whell
	 * @param lPercentage: power percentage on left whell
	 * @param dt: time intervall
	 */
	void update_ddPercentage(double rPercentage,double lPercentage, double dt);
	
	void setPosition(IPoint position,double heading);

}