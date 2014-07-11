package it.unibo.iot.sensors.distanceSensor;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.DistanceSensorData;
import it.unibo.iot.models.sensorData.DistanceValue;
import it.unibo.iot.models.sensorData.IDistanceSensorData;

import java.util.HashSet;
import java.util.Set;

public abstract class DistanceSensorDevice implements IDistanceSensorDevice, IDistanceObservable, ILowLevelDistanceSensorDevice {

	private static final int LOW = 20;
	private static final int MEDIUM = 40;

	public Set<IDistanceObserver> observers;

	public DistanceSensorDevice() {
		observers = new HashSet<IDistanceObserver>();
	}

	@Override
	public void addObserver(IDistanceObserver observer) {
		observers.add(observer);
	}
	
	@Override
	public void removeObserver(IDistanceObserver observer) {
		observers.remove(observer);
	}

	public IDistanceSensorData getData(){
		int lowLevelData = getLowLevelData();
		return new DistanceSensorData(toDistanceValue(lowLevelData),DirectionValue.NORTH);
	}

	@Override
	public abstract int getLowLevelData();

	@Override
	public void setLowLevelData(int cm) {
		IDistanceSensorData sensor = new DistanceSensorData(toDistanceValue(cm),DirectionValue.NORTH);
		for(IDistanceObserver observer : observers)
		{
			observer.notify(sensor);
		}
	}

	private DistanceValue toDistanceValue(int cm) {
		return cm <= LOW ? DistanceValue.CLOSE : cm <= MEDIUM ? DistanceValue.MEDIUM : DistanceValue.FAR;
	}
}