package it.unibo.DifferentialDriveSimulator.robot;

import it.unibo.DifferentialDriveSimulator.space.Checker;
import it.unibo.DifferentialDriveSimulator.space.IMap;
import it.unibo.DifferentialDriveSimulator.space.IPoint;
import it.unibo.DifferentialDriveSimulator.space.PointFactory;
import it.unibo.iot.models.sensorData.Detection;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulated differential drive robot with two line sensors
 * @author Alessio Tonioni
 *
 */
public class LineSensorDDRobot extends SimpleDDRobot implements IDetectorObservable{
	protected double deltaRightSensorX;
	protected double deltaRightSensorY;
	protected double deltaLeftSensorX;
	protected double deltaLeftSensorY;
	
	protected IMap workingZone;
	protected List<IDetectorObserver> lineObservers;
	protected IPoint leftLineSensorPosition;
	protected IPoint rightLineSensorPosition;
	
	protected boolean leftOnLine=false;
	protected boolean rightOnLine=false;
	
	/**
	 * default constructor
	 * @param robotPosition: starting center of the wheel axis position
	 * @param heading: robot headings in radiants 
	 * @param radius: wheel radius in cm
	 * @param wheelDistance: wheel axis length in cm
	 * @param maxSpeed: max angular speed of the wheel
	 * @param workingZone: map in which the robot moves
	 * @param leftSensor: left line sensor position
	 * @param rightSensor: right line sensor position
	 */
	public LineSensorDDRobot(IPoint robotPosition, double heading,
			double radius, double wheelDistance, double maxSpeed, IMap workingZone, 
			IPoint leftSensor, IPoint rightSensor) {
		super(robotPosition, heading, radius, wheelDistance, maxSpeed);
		this.workingZone=workingZone;
		lineObservers=new ArrayList<IDetectorObserver>();
		this.leftLineSensorPosition=leftSensor;
		this.rightLineSensorPosition=rightSensor;
		deltaRightSensorX=this.getRobotPosition().getX()-rightLineSensorPosition.getX();
		deltaRightSensorY=this.getRobotPosition().getY()-rightLineSensorPosition.getY();
		deltaLeftSensorX=this.getRobotPosition().getX()-leftLineSensorPosition.getX();
		deltaLeftSensorY=this.getRobotPosition().getY()-leftLineSensorPosition.getY();
	}
	
	@Override
	public void update_ddr(double vl,double vr, double dt){
		super.update_ddr(vl, vr, dt);
		boolean newLeft=false;
		boolean newRight=false;
		Checker current=workingZone.getChecker(leftLineSensorPosition);
		if(current==Checker.line){
			//System.out.println("left line detected");
			newLeft=true;
		}
		current=workingZone.getChecker(rightLineSensorPosition);
		if(current==Checker.line){
			//System.out.println("right line detected");
			newRight=true;
		}
		callNotify(newLeft,newRight);
	}
	
	private void callNotify(boolean newLeft, boolean newRight) {
		if(newLeft!=leftOnLine){
			leftOnLine=newLeft;
			Detection detection=new Detection("line", DirectionValue.WEST, leftOnLine);
			for(IDetectorObserver s: lineObservers){
				s.notify(detection);
			}
		}
		if(newRight!=rightOnLine){
			rightOnLine=newRight;
			Detection detection=new Detection("line", DirectionValue.EAST, rightOnLine);
			for(IDetectorObserver s: lineObservers){
				s.notify(detection);
			}
		}
	}

	/**
	 * Add observer that will be notified when one of the sensor enter a line or left a line
	 * @param observer
	 */
	@Override
	public void addObserver(IDetectorObserver observer){
		lineObservers.add(observer);
	}
	
	@Override
	protected void updateComponentPosition() {
		super.updateComponentPosition();
		leftLineSensorPosition=PointFactory.getInstance().getPoint
				(robotPosition.getX()-deltaLeftSensorX, robotPosition.getY()-deltaLeftSensorY);
		rightLineSensorPosition=PointFactory.getInstance().getPoint
				(robotPosition.getX()-deltaRightSensorX, robotPosition.getY()-deltaRightSensorY);
		
	}
	
	@Override
	protected void resetComponentPosition(){
		updateComponentPosition();
		leftOnLine=false;
		rightOnLine=false;
	}


	@Override
	/**
	 * Remove observer from the line detector's observer list
	 */
	public void removeObserver(IDetectorObserver observer) {
		lineObservers.remove(observer);
		
	}
	



	
	
	

}
