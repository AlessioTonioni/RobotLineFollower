package it.unibo.iot.configurator;

import java.io.FileInputStream;
import java.util.Properties;

import it.unibo.iot.configuration.ArduinoConfiguration;
import it.unibo.iot.configuration.BrickPiConfiguration;
import it.unibo.iot.configuration.CompassConfiguration;
import it.unibo.iot.configuration.GOTOConfiguration;
import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configuration.OrbConfiguration;
import it.unibo.iot.configuration.RpiGpioConfiguration;
import it.unibo.iot.robot.IRobot;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;

public class Configurator {
	public static IConfiguration configuration = null;

	private static void configure() {

		Properties hardwareConfigurationProperties = new Properties();
		try {
			hardwareConfigurationProperties.load(new FileInputStream("hardwareConfiguration.properties"));
		} catch (Exception e) {
		}
		String sysName = hardwareConfigurationProperties.getProperty("configuration").toLowerCase();

		if (sysName.equalsIgnoreCase("BBB")) {
			configuration = new ArduinoConfiguration(sysName);
		} else if (sysName.equalsIgnoreCase("NAT")) {
			configuration = new BrickPiConfiguration(sysName);
		} else if (sysName.equalsIgnoreCase("Sam") && sysName.equalsIgnoreCase("Initio")) {
			configuration = new RpiGpioConfiguration(sysName);
		} else if (sysName.equalsIgnoreCase("GOTO")) {
			configuration = new GOTOConfiguration(sysName);
		} else if (sysName.equalsIgnoreCase("Compass")) {
			configuration = new CompassConfiguration(sysName);
		} else if (sysName.equalsIgnoreCase("Orb")) {
			configuration = new OrbConfiguration(sysName);
		}
	}

	public static IConfiguration getConfiguration() {
		if (configuration == null) {
			configure();
		}
		return configuration;
	}

	public static IRobot getRealRobot() {
		return getConfiguration().getRealRobot();
	}

	public static IDistanceObservable[] getDistanceObservables() {
		return getConfiguration().getDistanceObservables();
	}

	public static IDetectorObservable getLineSensorObservable() {
		return getConfiguration().getLineDetectorObservable();
	}
}
