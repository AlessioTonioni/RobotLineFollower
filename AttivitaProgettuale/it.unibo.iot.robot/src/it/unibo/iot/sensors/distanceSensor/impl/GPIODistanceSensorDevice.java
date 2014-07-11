package it.unibo.iot.sensors.distanceSensor.impl;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.DistanceSensorData;
import it.unibo.iot.models.sensorData.DistanceValue;
import it.unibo.iot.sensors.distanceSensor.DistanceSensorDevice;
import it.unibo.iot.sensors.distanceSensor.IDistanceObserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GPIODistanceSensorDevice extends DistanceSensorDevice {

	private int lastVal = 100;

	public GPIODistanceSensorDevice() {
		new Thread() {
			public void run() {
				try {
					Process p = Runtime.getRuntime().exec("./hc-sr04");
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while (!isInterrupted()) {
						String line = reader.readLine();
						Integer cm = Integer.valueOf(line);
						lastVal = cm;
						for (IDistanceObserver observer : observers) {
							observer.notify(new DistanceSensorData(toDistanceValue(cm), DirectionValue.NORTH));
						}
					}
				} catch (Exception e) {
				}

			}
		}.start();
	}

	private DistanceValue toDistanceValue(int cm) {
		return cm <= 20 ? DistanceValue.CLOSE : cm <= 40 ? DistanceValue.MEDIUM : DistanceValue.FAR;
	}

	@Override
	public int getLowLevelData() {
		return lastVal;
	}
}
