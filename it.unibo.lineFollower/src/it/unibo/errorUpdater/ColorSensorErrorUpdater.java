package it.unibo.errorUpdater;

import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.sensorData.color.IColorSensorData;
import it.unibo.iot.sensors.colorSensor.IColorSensorObserver;

/**
 * Implementation of an IErrorUpdater based on a single Color sensor, the robot is over a black line
 * on a white background and tries to follow the edge of said line. The first callback from the color 
 * sensor sets the color to follow, any clearer color means error on the left, any darker color means
 * error on the right, so the black line must be on the right of the sensor at the beginning.
 * @author Alessio Tonioni
 *
 */
public class ColorSensorErrorUpdater implements IErrorUpdater{
	private int error;
	private boolean configured=false;
	
	private int[] rgbToFollow;
	private int maxErrorFromWhite;
	private int maxErrorFromBlack;
	
	@Override
	public int getError() throws Exception {
		if(configured)
			return error;
		else
			throw new Exception("ErrorUpdater not yet configured!");
	}

	@Override
	public void configure(IConfiguration conf) {
		IColorSensorObserver colorObserver = new IColorSensorObserver() {

			@Override
			public void notify(IColorSensorData colorSensorData) {
				updateError(colorSensorData);
			}
		};

		conf.getColorSensorObservable().addObserver(colorObserver);
		configured=true;
	}

	@Override
	public boolean isConfigured() {
		return configured;
	}
	
	private void updateError(IColorSensorData colorSensorData){
		int Red=colorSensorData.getColor().getR();
		int Green=colorSensorData.getColor().getG();
		int Blue=colorSensorData.getColor().getB();
		if(rgbToFollow == null){
			rgbToFollow=new int[3];
			rgbToFollow[0]=Red;
			rgbToFollow[1]=Green;
			rgbToFollow[2]=Blue;
			maxErrorFromWhite=Math.max(Math.max(255-Red, 255-Green), 255-Blue);
			maxErrorFromBlack=Math.max(Math.max(Red, Green), Blue);
		}
		int RError=rgbToFollow[0]-Red;
		int GError=rgbToFollow[1]-Green;
		int BError=rgbToFollow[2]-Blue;
		
		int AverageError=(RError+GError+BError)/3;
		if(AverageError>0)
			error=AverageError*-100/maxErrorFromWhite;
		else
			error=AverageError*-100/maxErrorFromBlack;
		
	}



}
