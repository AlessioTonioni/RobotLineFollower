package it.unibo.iot.lowLevelImpl;

import java.util.HashSet;
import java.util.Set;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.color.Color;
import it.unibo.iot.models.sensorData.color.ColorSensorData;
import it.unibo.iot.models.sensorData.color.IColor;
import it.unibo.iot.models.sensorData.color.IColorSensorData;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.colorSensor.IColorSensorObserver;

public class ColorSensorLowInp implements IColorSensorObservable, IColorSensorLowInp {
	private Set<IColorSensorObserver> observers;
	private IColorSensorData colorSensorData;

	public ColorSensorLowInp() {
		observers = new HashSet<IColorSensorObserver>();
	}

	@Override
	public void addObserver(IColorSensorObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(IColorSensorObserver observer) {
		observers.remove(observers);
	}

	public void setValue(String detection) {
		String position = detection.substring(0, detection.indexOf(" "));
		String colorString = detection.substring(detection.indexOf(" ") + 1);
		IColor color = stringToColor(colorString);
		DirectionValue direction = stringToDirectionValue(position);
		if (color != null) {
			colorSensorData = new ColorSensorData(color, direction);
			for (IColorSensorObserver obs : observers) {
				obs.notify(colorSensorData);
			}
		}
	}

	private DirectionValue stringToDirectionValue(String position) {
		DirectionValue dir = DirectionValue.NORTH;

		DirectionValue[] directionValues = DirectionValue.values();
		for (DirectionValue directionValue : directionValues) {
			if (position.equalsIgnoreCase(directionValue.name().toLowerCase().substring(0, 1))) {
				dir = directionValue;
			}
		}
		return dir;
	}

	private IColor stringToColor(String colorSting) {
		IColor color = null;
		String R = colorSting.substring(0, colorSting.indexOf(" "));
		colorSting = colorSting.substring(colorSting.indexOf(" ") + 1);
		String G = colorSting.substring(0, colorSting.indexOf(" "));
		colorSting = colorSting.substring(colorSting.indexOf(" ") + 1);
		String B = colorSting.substring(colorSting.indexOf(" ") + 1);
		try {
			int r = Integer.parseInt(R);
			int g = Integer.parseInt(G);
			int b = Integer.parseInt(B);
			color = new Color(r, g, b);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return color;
	}

}
