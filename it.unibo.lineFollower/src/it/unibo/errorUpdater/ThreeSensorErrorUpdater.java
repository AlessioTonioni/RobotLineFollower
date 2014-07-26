package it.unibo.errorUpdater;

import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.models.sensorData.color.IColorSensorData;
import it.unibo.iot.sensors.colorSensor.IColorSensorObserver;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;

/**
 * Implementation of an IErrorUpdater that uses one color sensor in the middle and two line sensor 
 * on the side to follow two black line as rails.
 * @author Alessio Tonioni
 *
 */
public class ThreeSensorErrorUpdater implements IErrorUpdater{

	private boolean configured=false;
	private int error=0;
	
	@Override
	public int getError() throws Exception {
		return error;
	}

	@Override
	public void configure(IConfiguration conf) {
		IDetectorObserver obsDetectorObserver = new IDetectorObserver() {

			@Override
			public void notify(IDetection detection) {
				updateLineError(detection);
			}

		};
		IDetectorObservable [] detectorObservables = conf.getLineDetectorObservables();
		for (IDetectorObservable iDetectorObservable : detectorObservables) {
			iDetectorObservable.addObserver(obsDetectorObserver);
		}
		
		IColorSensorObserver colorObserver = new IColorSensorObserver() {

			@Override
			public void notify(IColorSensorData colorSensorData) {
				updateColorError(colorSensorData);
			}
		};

		conf.getColorSensorObservable().addObserver(colorObserver);
		
		configured=true;
	}

	@Override
	public boolean isConfigured() {
		return configured;
	}
	
	private void updateLineError(IDetection detection) {
		//System.out.println("line detected: "+detection.getDirection()+detection.getVal());
		switch (detection.getDirection()){
		case EAST:
			if(detection.getVal())
				error-=50;
			else
				error+=50;  //ho lasciato la linea, azzero l'errore relativo
			break;
		case WEST:
			if(detection.getVal())
				error+=50;
			else
				error-=50; //ho lasciato la linea, azzero l'errore relativo
			break;
		default:
			break;
		}
	}
	
	private void updateColorError(IColorSensorData colorSensorData){
		//System.out.println("Colro Detected: "+colorSensorData.getColor().getStringRep());
		int Red=colorSensorData.getColor().getR();
		int Green=colorSensorData.getColor().getG();
		int Blue=colorSensorData.getColor().getB();
		
		if(Red==0 && Green==0 && Blue==0){  //black detected
			if(error<0)
				error-=50;
			else
				error+=50;
		}
		else if(Math.abs(error)>50){  //black leaved
			if(error>0)
				error+=50;
			else
				error-=50;
		}
		if(error>100 ){
			error=100;
		} else if(error<-100){
			error=-100;
		}
	}


}
