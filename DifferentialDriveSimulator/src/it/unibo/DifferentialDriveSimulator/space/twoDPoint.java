package it.unibo.DifferentialDriveSimulator.space;

/**
 * Implementation of an IPoint in a 2d world, the z-value will always be 0.
 * @author Alessio Tonioni
 *
 */
public class twoDPoint implements IPoint {
	private double x;
	private double y;
	
	public twoDPoint(double x, double y){
		this.x=x;
		this.y=y;
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		twoDPoint other = (twoDPoint) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setX(double newVal) {
		x=newVal;
	}

	@Override
	public void setY(double newVal) {
		y=newVal;
	}

	@Override
	public double getZ() {
		return 0;
	}

	@Override
	public void setZ(double newVal) {
		return;
	}
	
	

}
