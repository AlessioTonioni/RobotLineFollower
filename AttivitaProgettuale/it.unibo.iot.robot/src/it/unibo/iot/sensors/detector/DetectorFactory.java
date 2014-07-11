package it.unibo.iot.sensors.detector;

import it.unibo.iot.lowLevelImpl.SysKB;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.robot.ddActuators.impl.GPIO.StringToGPIOPin;
import it.unibo.iot.sensors.detector.impl.BrickPiLineDetector.BrickPiLineDetector;
import it.unibo.iot.sensors.detector.impl.GPIOLineDetector.GPIOLineDetector;
import it.unibo.iot.sensors.detector.impl.MEZDetector.MutualExclusionZoneDetector;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.pi4j.io.gpio.Pin;

public class DetectorFactory {

	public static Detector createGPIOLineDetector(DirectionValue direction, String configuration) {
		Properties pi4jPinConfigurationProperties = new Properties();
		try {
			pi4jPinConfigurationProperties.load(new FileInputStream("configuration/" + configuration + "/pi4jPinConfiguration.properties"));
		} catch (Exception e) {
			return null;
		}
		
		Pin lineSensorPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("lineSensorPin"));
		return new GPIOLineDetector(direction, lineSensorPin);
	}

	public static Detector[] createGPIOLineDetectors(String configuration) {
		Properties pi4jPinConfigurationProperties = new Properties();
		try {
			pi4jPinConfigurationProperties.load(new FileInputStream("configuration/" + configuration + "/pi4jPinConfiguration.properties"));
		} catch (Exception e) {
		}
		ArrayList<Detector> detectorsList = new ArrayList<Detector>();
		Pin lineSensorPin;
		DirectionValue[] val = DirectionValue.values();
		for (DirectionValue directionValue : val) {
			try {
				lineSensorPin = StringToGPIOPin.toPin(pi4jPinConfigurationProperties.getProperty("lineSensorPin_" + directionValue.name().toLowerCase()));
				detectorsList.add(new GPIOLineDetector(directionValue, lineSensorPin));
			} catch (Exception e) {
			}
		}
		Detector [] detectors = new Detector[detectorsList.size()];
		return detectorsList.toArray(detectors) ;
	}

	public static IDetectorObservable createBrickPiLineDetector() {
		return SysKB.getBrickPiIOManagerInstance();
	}

	public static IDetectorObservable createArduinoLineDetector() {
		return SysKB.getArduinoSerialIOManagerInstance();
	}

	public static Detector createMEZDetector(DirectionValue direction) {
		return new MutualExclusionZoneDetector(direction);
	}

	public static Detector createBrickPiLineDetector(DirectionValue direction) {
		return new BrickPiLineDetector(direction);
	}
}
