package it.unibo.iot.sensors.detector.impl.MEZDetector;

import it.unibo.iot.models.sensorData.DirectionValue;
import it.unibo.iot.sensors.detector.Detector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MutualExclusionZoneDetector extends Detector {

	private Process process;
	private PrintWriter writer;
	private BufferedReader reader;

	public MutualExclusionZoneDetector(DirectionValue direction) {
		super("MEZDetection", direction);
	}

	@Override
	public boolean getLowLevelDetection() {
		return getColorDetection();
	}

	private boolean getColorDetection() {
		String line = "";
		if (writer != null) {
			try {
				Thread.sleep(1650);
			} catch (Exception e) {
			}
		}
		try {
			writer.print("\n");
			writer.flush();
			line = reader.readLine();
		} catch (Exception e) {
			return false;
		}
		return line.contains("1");
	}

	public void startProcess() {
		stopProcess();
		try {
			Runtime.getRuntime().exec("bash meCameraSet.sh").waitFor();
		} catch (Exception e) {
		}
		try {
			process = Runtime.getRuntime().exec("./DetectColor");
			writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		} catch (Exception e) {
		}
	}

	public void stopProcess() {
		try {
			process.destroy();
			writer = null;
		} catch (Exception e) {
			writer = null;
		}
	}
}
