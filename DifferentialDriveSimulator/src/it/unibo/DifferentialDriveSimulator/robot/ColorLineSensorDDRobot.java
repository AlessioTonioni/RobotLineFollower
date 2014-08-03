package it.unibo.DifferentialDriveSimulator.robot;

import it.unibo.DifferentialDriveSimulator.space.Checker;
import it.unibo.DifferentialDriveSimulator.space.IMap;
import it.unibo.DifferentialDriveSimulator.space.IPoint;
import it.unibo.DifferentialDriveSimulator.space.PointFactory;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.color.Color;
import it.unibo.iot.models.sensorData.color.ColorSensorData;
import it.unibo.iot.models.sensorData.color.IColor;
import it.unibo.iot.models.sensorData.color.IColorSensorData;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.colorSensor.IColorSensorObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulated Differential Drive robot with two line sensors and a color sensor.
 * @author Alessio Tonioni
 *
 */
public class ColorLineSensorDDRobot extends LineSensorDDRobot implements IColorSensorObservable {
	private double deltaColorSensorX;
	private double deltaColorSensorY;
	
	private IPoint colorSensorPosition;
	private Checker lastChecker;
	
	protected List<IColorSensorObserver> colorObserver;
	
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
	 * @param colorSensor: color sensor position
	 */
	public ColorLineSensorDDRobot(IPoint robotPosition, double heading,
			double radius, double wheelDistance, double maxSpeed, IMap workingZone, 
			IPoint leftSensor, IPoint rightSensor, IPoint colorSensor) {
		super(robotPosition,heading,radius,wheelDistance,maxSpeed,workingZone,leftSensor,rightSensor);
		
		colorObserver=new ArrayList<IColorSensorObserver>();
		colorSensorPosition=colorSensor;
		deltaColorSensorX=this.getRobotPosition().getX()-colorSensor.getX();
		deltaColorSensorY=this.getRobotPosition().getY()-colorSensor.getY();
	}

	@Override
	public void update_ddr(double vl, double vr, double dt) {
		super.update_ddr(vl, vr, dt);
		Checker current=workingZone.getChecker(colorSensorPosition);
		if(current==Checker.line && lastChecker!=Checker.line){
			callNotifyColor(true);
			lastChecker=Checker.line;
		}else if(current!=lastChecker){
			callNotifyColor(false);
			lastChecker=Checker.empty;
		}
	}

	private void callNotifyColor(boolean isBlack) {
		IColor c;
		if(isBlack){
			c=new Color(0,0,0);
		} else {
			c=new Color(255,255,255);
		}
		IColorSensorData d=new ColorSensorData(c,DirectionValue.NORTH);
		for(IColorSensorObserver o:colorObserver){
			o.notify(d);
		}
	}

	@Override
	protected void updateComponentPosition() {
		super.updateComponentPosition();
		colorSensorPosition=PointFactory.getInstance().getPoint
				(robotPosition.getX()-deltaColorSensorX, robotPosition.getY()-deltaColorSensorY);
	}
	
	/**
	 * Method used to add observer at the color sensor, they will be notified when the sensor it's above a 
	 * different color in the simulated map.
	 */
	@Override
	public void addObserver(IColorSensorObserver obs){
		colorObserver.add(obs);
	}

	/**
	 * Method used to remove observer from the observer's list of the color sensor
	 */
	@Override
	public void removeObserver(IColorSensorObserver observer) {
		colorObserver.remove(observer);
	}



}
