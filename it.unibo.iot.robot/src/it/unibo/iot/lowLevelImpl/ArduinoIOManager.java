package it.unibo.iot.lowLevelImpl;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import it.unibo.iot.models.sensorData.Detection;
import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.models.sensorData.DistanceSensorData;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.models.sensorData.IDistanceSensorData;
import it.unibo.iot.sensors.colorSensor.IColorSensorObservable;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;
import it.unibo.iot.sensors.distanceSensor.IDistanceObservable;
import it.unibo.iot.sensors.distanceSensor.IDistanceObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ArduinoIOManager implements IProcessSerialActuators, IDetectorObservable, IDistanceObservable, SerialPortEventListener {
	private SerialPort serialPort;
	private BufferedReader input;
	private static OutputStream output;
	private static final String PORT_NAMES[] = { "/dev/ttyO2", // beaglebone
																// Arduino pro
																// mini 5v 16
																// MHz
			"/dev/ttyACM0", // GOTO Arduino micro pro
			"/dev/ttyAMA0" // Compass Arduino pro mini 3v 8MHz
	};

	/** Milliseconds to block while waiting for port open */
	public static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	public static final int DATA_RATE = 9600;

	private Set<IDistanceObserver> distanceObservers;
	private Set<IDetectorObserver> detectorObservers;

	private ColorSensorLowInp colorSensorLowInp;

	public ArduinoIOManager() {
		distanceObservers = new HashSet<IDistanceObserver>();
		detectorObservers = new HashSet<IDetectorObserver>();
		colorSensorLowInp = new ColorSensorLowInp();
		initialize();
	}

	private void initialize() {
		CommPortIdentifier portId = null;
		@SuppressWarnings("rawtypes")
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

			output = serialPort.getOutputStream();
			output.write("00".getBytes());
			output.write("00".getBytes());
			output.flush();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	private static synchronized void writeData(String data) {
		try {
			// writer.println(data);
			// writer.flush();
			output.write(data.getBytes());
			output.flush();

		} catch (Exception e) {
			System.out.println("could not write to port");
		}
	}

	@Override
	public void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

			String inputLine = null;
			try {
				inputLine = input.readLine();
				// System.out.println(inputLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				// System.out.println(inputLine);
				String sensor = inputLine.substring(0, inputLine.indexOf(" "));
				inputLine = inputLine.substring(inputLine.indexOf(" ") + 1);
				String position = inputLine.substring(0, inputLine.indexOf(" "));
				inputLine = inputLine.substring(inputLine.indexOf(" ") + 1);
				Integer inp = -1;

				DirectionValue dir = DirectionValue.NORTH;
				// System.out.println(sensor + "" + position + " " + inputLine);
				DirectionValue[] directionValues = DirectionValue.values();
				for (DirectionValue directionValue : directionValues) {
					if (position.equalsIgnoreCase(directionValue.name().toLowerCase().substring(0, 1))) {
						dir = directionValue;
					}
				}
				if (sensor.equalsIgnoreCase("us")) {
					inp = Integer.valueOf(inputLine);

					IDistanceSensorData distanceSensorData = new DistanceSensorData(inp, dir);
					for (IDistanceObserver observer : distanceObservers) {
						observer.notify(distanceSensorData);
					}
				} else if (sensor.equalsIgnoreCase("l")) {
					inp = Integer.valueOf(inputLine);

					IDetection detection = new Detection("", dir, inp == 1);
					for (IDetectorObserver observer : detectorObservers) {
						observer.notify(detection);
					}
				} else if (sensor.equalsIgnoreCase("rgb")) {
					//System.out.println(sensor + " " + position + " " + inputLine);
					colorSensorLowInp.setValue(position+ " " + inputLine);

				}
			} catch (Exception e) {
			}
		}
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
		writeData("" + leftMotor + "" + rightMotor);
	}

	public IColorSensorObservable getColorSensorObservable() {
		return colorSensorLowInp;
	}
}
