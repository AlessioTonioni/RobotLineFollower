package it.unibo.iot.configurator;

import it.unibo.iot.lowLevelImpl.SysKB;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.robot.DifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.robot.ddActuators.DDActuatorsFactory;
import it.unibo.iot.robot.ddActuators.IDDActuators;
import it.unibo.iot.sensors.detector.DetectorFactory;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.DistanceSensorDeviceFactory;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;
import it.unibo.iot.utils.DebugUtils;

import java.io.FileInputStream;
import java.util.Properties;

public class Configurator_old {
	private static IRobot robot = null;
	private static IDetectorObservable lineDetector;
	private static IDistanceObservable[] distanceSensors;

	private static void configure() {
		DebugUtils.debugOn();

		Properties hardwareConfigurationProperties = new Properties();
		try {
			hardwareConfigurationProperties.load(new FileInputStream("hardwareConfiguration.properties"));
		} catch (Exception e) {
		}
		String configuration = hardwareConfigurationProperties.getProperty("configuration").toLowerCase();

		IDDActuators actuators;
		if (configuration.equalsIgnoreCase("BBB")) {
			actuators = DDActuatorsFactory.createArduinoSerialDDActuators();
			lineDetector = SysKB.getArduinoSerialIOManagerInstance();
			distanceSensors = new IDistanceObservable[] { SysKB.getArduinoSerialIOManagerInstance() };
		} else if (!configuration.equalsIgnoreCase("NAT")) {
			lineDetector = DetectorFactory.createGPIOLineDetector(DirectionValue.NORTH,configuration);
			distanceSensors = new IDistanceObservable[] { DistanceSensorDeviceFactory.createGPIOLightDistanceSensorDevice() };
			actuators = DDActuatorsFactory.createDDActuators(configuration);
			if (configuration.equalsIgnoreCase("GOTO")) {
				lineDetector = SysKB.getArduinoSerialIOManagerInstance();
				distanceSensors = new IDistanceObservable[] { SysKB.getArduinoSerialIOManagerInstance() };
			}
		} else {
			actuators = DDActuatorsFactory.createBrickPiDDActuators();
			lineDetector = SysKB.getBrickPiIOManagerInstance();
			distanceSensors = new IDistanceObservable[] { SysKB.getBrickPiIOManagerInstance() };
		}

		robot = new DifferentialDriveRobot(actuators, configuration);
	}

	public static IRobot getRealRobot() {
		if (robot == null) {
			configure();
		}
		return robot;
	}

	public static IDistanceObservable[] getDistanceObservables() {
		if (robot == null) {
			configure();
		}
		return distanceSensors;
	}

	public static IDetectorObservable getLineSensorObservable() {
		if (robot == null) {
			configure();
		}
		return lineDetector;
	}
}
