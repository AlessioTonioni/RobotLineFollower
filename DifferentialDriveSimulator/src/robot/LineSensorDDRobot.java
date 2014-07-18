package robot;

import it.unibo.iot.models.sensorData.Detection;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.sensors.detector.IDetectorObserver;

import java.util.ArrayList;
import java.util.List;

import space.Checker;
import space.IMap;
import space.IPoint;
import space.PointFactory;

public class LineSensorDDRobot extends SimpleDDRobot{
	private double deltaRightSensorX;
	private double deltaRightSensorY;
	private double deltaLeftSensorX;
	private double deltaLeftSensorY;
	
	private IMap workingZone;
	private List<IDetectorObserver> observers;
	private IPoint leftLineSensorPosition;
	private IPoint rightLineSensorPosition;
	
	private boolean leftOnLine=false;
	private boolean rightOnLine=false;
	
	
	public LineSensorDDRobot(IPoint robotPosition, double heading,
			double radius, double wheelDistance, double maxSpeed, IMap workingZone, 
			IPoint leftSensor, IPoint rightSensor) {
		super(robotPosition, heading, radius, wheelDistance, maxSpeed);
		this.workingZone=workingZone;
		observers=new ArrayList<IDetectorObserver>();
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
			for(IDetectorObserver s: observers){
				s.notify(detection);
			}
		}
		if(newRight!=rightOnLine){
			rightOnLine=newRight;
			Detection detection=new Detection("line", DirectionValue.EAST, rightOnLine);
			for(IDetectorObserver s: observers){
				s.notify(detection);
			}
		}
	}

	public void addObserver(IDetectorObserver observer){
		observers.add(observer);
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
	



	
	
	

}
