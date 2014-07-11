package it.unibo.iot.models.sensorData.color;

public class Color implements IColor {

	private int R;
	private int G;
	private int B;

	public Color(int R, int G, int B) {
		this.R = R;
		this.G = G;
		this.B = B;
	}

	public int getR() {
		return R;
	}

	public int getG() {
		return G;
	}

	public int getB() {
		return B;
	}

	@Override
	public String getStringRep() {
		return "" + R + " " + G + " " + B;
	}
}// end ObjectPosition