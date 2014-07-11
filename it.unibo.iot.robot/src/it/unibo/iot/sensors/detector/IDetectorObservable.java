package it.unibo.iot.sensors.detector;

public interface IDetectorObservable {

	public void addObserver(IDetectorObserver observer);
	public void removeObserver(IDetectorObserver observer);
}