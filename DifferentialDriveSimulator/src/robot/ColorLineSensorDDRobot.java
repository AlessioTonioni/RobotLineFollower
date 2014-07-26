package robot;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.color.Color;
import it.unibo.iot.models.sensorData.color.ColorSensorData;
import it.unibo.iot.models.sensorData.color.IColor;
import it.unibo.iot.models.sensorData.color.IColorSensorData;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.colorSensor.IColorSensorObserver;

import java.util.ArrayList;
import java.util.List;

import space.Checker;
import space.IMap;
import space.IPoint;
import space.PointFactory;

public class ColorLineSensorDDRobot extends LineSensorDDRobot implements IColorSensorObservable {
	private double deltaColorSensorX;
	private double deltaColorSensorY;
	
	private IPoint colorSensorPosition;
	private Checker lastChecker;
	
	protected List<IColorSensorObserver> colorObserver;
	
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
	
	public void addObserver(IColorSensorObserver obs){
		colorObserver.add(obs);
	}

	@Override
	public void removeObserver(IColorSensorObserver observer) {
		colorObserver.remove(observer);
	}



}
