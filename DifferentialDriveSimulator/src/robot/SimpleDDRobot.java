package robot;

import space.IPoint;
import space.PointFactory;
/**
 * Simple simulated differential drive robot
 * @author Alessio Tonioni
 *
 */
public class SimpleDDRobot implements IDDRobot {

	protected IPoint leftWheelPosition; 	//position of the left wheel in cm
	protected IPoint rightWheelPosition;    //positoin of the right wheel in cm
	protected IPoint robotPosition;  		//position of the centre of mass in cm
	private double  heading;   				//heading angle in rad
	private double radius;          		//radius of the wheels in cm
	private double wheelDistance;         	//distance between wheels in cm
	private double fullSpeed;				//max speed foreach wheel in rad/s
	/**
	 * default constructor
	 * @param robotPosition: starting center of the wheel axis position
	 * @param heading: robot headings in radiants 
	 * @param radius: wheel radius in cm
	 * @param wheelDistance: wheel axis length in cm
	 * @param maxSpeed: max angular speed of the wheel
	 */
	public SimpleDDRobot(IPoint robotPosition, double heading, double radius, double wheelDistance, double maxSpeed) {
		super();
		this.robotPosition = robotPosition;
		this.heading = heading;
		this.radius=radius;
		this.wheelDistance=wheelDistance;
		this.fullSpeed=maxSpeed;
		updateComponentPosition();
	}

	protected void updateComponentPosition() {
		this.leftWheelPosition=PointFactory.getInstance().getPoint(robotPosition.getX()-wheelDistance/2,robotPosition.getY());
		this.rightWheelPosition=PointFactory.getInstance().getPoint(robotPosition.getX()+wheelDistance/2,robotPosition.getY());
	}

	@Override
	public IPoint getLeftWheelPosition() {
		return leftWheelPosition;
	}
	@Override
	public IPoint getRightWheelPosition() {
		return rightWheelPosition;
	}
	@Override
	public IPoint getRobotPosition() {
		return robotPosition;
	}
	@Override
	public double getHeading(){
		return heading;
	}

	
	@Override
	public void update_ddr(double vl,double vr, double dt){ //angular speed
		double d_x = (radius/2)*(vr+vl)*Math.cos(heading)*dt;
		double d_y = (radius/2)*(vr+vl)*Math.sin(heading)*dt;
		double d_heading = (radius/wheelDistance)*(vr-vl)*dt;

		this.heading += d_heading;
		this.heading = Math.atan2(Math.sin(this.heading), Math.cos(this.heading));    //transform heading into <-PI, PI> interval
		this.robotPosition.setX(this.robotPosition.getX()+d_x);   
		this.robotPosition.setY(this.robotPosition.getY()+d_y);  
		updateComponentPosition();
	}
	@Override
	public void update_unicycle(double v, double omega, double dt){
		double vr = (2*v + omega*this.wheelDistance)/(2*this.radius);
		double vl = (2*v - omega*this.wheelDistance)/(2*this.radius);
		update_ddr(vr, vl, dt);
	}

	@Override
	public void update_ddPercentage(double lPercentage, double rPercentage, double dt) {
		update_ddr(lPercentage*fullSpeed,rPercentage*fullSpeed, dt);		
	}

	@Override
	public void setPosition(IPoint position,double heading) {
		this.robotPosition = position;
		this.heading=heading;
		resetComponentPosition();
		
	}

	protected void resetComponentPosition() {
		updateComponentPosition();	
	}

}
