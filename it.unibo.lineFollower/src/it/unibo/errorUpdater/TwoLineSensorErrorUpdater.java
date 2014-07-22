package it.unibo.errorUpdater;

import it.unibo.iot.models.sensorData.IDetection;

public class TwoLineSensorErrorUpdater implements IErrorUpdater {

	private int error=0;
	@Override
	public void notify(IDetection detection) {
		switch (detection.getDirection()){
		case EAST:
			if(detection.getVal())
				error-=10;
			else
				error+=10;  //ho lasciato la linea, azzero l'errore relativo
			break;
		case WEST:
			if(detection.getVal())
				error+=10;
			else
				error-=10; //ho lasciato la linea, azzero l'errore relativo
			break;
		default:
			break;
		}
	}

	@Override
	public int getError() {
		return error;
	}

}
