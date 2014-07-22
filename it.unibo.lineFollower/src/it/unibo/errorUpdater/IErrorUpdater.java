package it.unibo.errorUpdater;

import it.unibo.iot.sensors.detector.IDetectorObserver;

public interface IErrorUpdater extends IDetectorObserver{
	int getError();
}
