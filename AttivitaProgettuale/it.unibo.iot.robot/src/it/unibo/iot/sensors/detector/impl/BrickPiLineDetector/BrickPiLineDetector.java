package it.unibo.iot.sensors.detector.impl.BrickPiLineDetector;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.sensors.detector.Detector;

import java.io.BufferedReader;
import java.io.IOException;

public class BrickPiLineDetector extends Detector {

	private BufferedReader reader;
	
	public BrickPiLineDetector(DirectionValue direction) {
		super("LineDetection", direction);
//		try {
//			Process process = Runtime.getRuntime().exec("python LEGO-Light_ON_sensor_Test.py");
//			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		new Thread() {
//			public void run() {
//				try {
//					String line = null;
//					while ((line = reader.readLine()) != null) {
//						setLowLevelDetection(line.equalsIgnoreCase("1"));
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();
	}

	@Override
	public boolean getLowLevelDetection() {
		try {
			return reader.readLine().equalsIgnoreCase("1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
