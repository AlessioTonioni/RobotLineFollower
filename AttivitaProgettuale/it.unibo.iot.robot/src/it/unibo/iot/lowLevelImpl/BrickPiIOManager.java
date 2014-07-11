package it.unibo.iot.lowLevelImpl;

import it.unibo.iot.models.sensorData.Detection;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.DistanceSensorData;
import it.unibo.iot.models.sensorData.DistanceValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.models.sensorData.IDistanceSensorData;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class BrickPiIOManager implements IProcessSerialActuators, IDetectorObservable, IDistanceObservable {

	private PrintWriter writer;
	private BufferedReader reader;
	private Set<IDistanceObserver> distanceObservers;
	private Set<IDetectorObserver> detectorObservers;

	public BrickPiIOManager() {
		distanceObservers = new HashSet<IDistanceObserver>();
		detectorObservers = new HashSet<IDetectorObserver>();
		try {
			Process process = Runtime.getRuntime().exec("python brickpiiomanager.py");
			writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread() {
			public void run() {
				writer.write("44");
				writer.write("\n");
				writer.flush();
				while (!isInterrupted()) {
					try {
						String line = reader.readLine();
						line = line.toLowerCase();
						if (line.contains("d")) {
							Integer cm = Integer.valueOf(line.substring(2));
							DistanceValue distanceValue = cm <= 12 ? DistanceValue.CLOSE : cm <= 20 ? DistanceValue.MEDIUM : DistanceValue.FAR;
							IDistanceSensorData distanceSensorData = new DistanceSensorData(distanceValue, DirectionValue.NORTH);
							for (IDistanceObserver distanceObserver : distanceObservers) {
								distanceObserver.notify(distanceSensorData);
							}
						}
						if (line.contains("l")) {
							boolean l = line.contains("1");
							IDetection lineDetection = new Detection("Line", DirectionValue.NORTH, l);
							for (IDetectorObserver detectorObserver : detectorObservers) {
								detectorObserver.notify(lineDetection);
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
						try {
							sleep(3000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	@Override
	public void addObserver(IDistanceObserver observer) {
		distanceObservers.add(observer);
	}

	@Override
	public void removeObserver(IDistanceObserver observer) {
		distanceObservers.remove(observer);
	}

	@Override
	public void addObserver(IDetectorObserver observer) {
		detectorObservers.add(observer);
	}

	@Override
	public void removeObserver(IDetectorObserver observer) {
		detectorObservers.remove(observer);
	}

	@Override
	public void setWheelCommand(int leftMotor, int rightMotor) {
		String motorCombinationString = "" + rightMotor + "" + leftMotor;
		writer.write(motorCombinationString);
		writer.write("\n");
		writer.flush();
	}

}
