package space;

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
