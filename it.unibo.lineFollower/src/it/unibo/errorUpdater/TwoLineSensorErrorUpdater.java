package it.unibo.errorUpdater;

import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;
import it.unibo.lineFollower.PIDLineFollowerController;

/**
 * Implementations for an IErrorUpdater for a two line sensors robot, the robot is between two 
 * black linees that serve as rails. 
 * @author Alessio Tonioni
 *
 */
public class TwoLineSensorErrorUpdater implements IErrorUpdater {

	private int error=0;
	private boolean configured=false;

	private void updateError(IDetection detection) {
		switch (detection.getDirection()){
		case EAST:
			if(detection.getVal())
				error-=100;
			else
				error+=100;  //ho lasciato la linea, azzero l'errore relativo
			break;
		case WEST:
			if(detection.getVal())
				error+=100;
			else
				error-=100; //ho lasciato la linea, azzero l'errore relativo
			break;
		default:
			break;
		}
	}

	@Override
	public int getError() throws Exception {
		if(configured)
			return error;
		else
			throw new Exception("ErrorUpdater not yet configured!");
	}

	@Override
	public void configure(IConfiguration conf) {
		IDetectorObserver obsDetectorObserver = new IDetectorObserver() {

			@Override
			public void notify(IDetection detection) {
				updateError(detection);
			}
		};
		IDetectorObservable [] detectorObservables = conf.getLineDetectorObservables();
		for (IDetectorObservable iDetectorObservable : detectorObservables) {
			iDetectorObservable.addObserver(obsDetectorObserver);
		}
		configured=true;
	}

	@Override
	public boolean isConfigured() {
		return configured;
	}

	@Override
	public void reset() {
		error=0;
	}

}
