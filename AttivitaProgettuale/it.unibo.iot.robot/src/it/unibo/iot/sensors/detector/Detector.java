package it.unibo.iot.sensors.detector;

import it.unibo.iot.models.sensorData.Detection;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.IDetection;

import java.util.HashSet;
import java.util.Set;

public abstract class Detector implements IDetector, IDetectorObservable, ILowLevelDetector {

	protected Set<IDetectorObserver> lineObservers;
	protected DirectionValue direction;
	protected String name;

	public Detector(String name, DirectionValue direction) {
		this.name = name;
		this.direction = direction;
		this.lineObservers = new HashSet<IDetectorObserver>();
	}

	public void addObserver(IDetectorObserver observer) {
		lineObservers.add(observer);
	}

	@Override
	public void removeObserver(IDetectorObserver observer) {
		lineObservers.remove(observer);
	}

	@Override
	public IDetection getDetection() {
		return new Detection(name, direction, getLowLevelDetection());
	}

	public abstract boolean getLowLevelDetection();

	public void setLowLevelDetection(boolean data) {
		IDetection sensor = new Detection(name, direction, data);
		for (IDetectorObserver observer : lineObservers) {
			observer.notify(sensor);
		}
	}
}