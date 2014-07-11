package it.unibo.iot.sensors.distanceSensor;

public interface IDistanceObservable {

	void addObserver(IDistanceObserver observer);
	void removeObserver(IDistanceObserver observer);
}