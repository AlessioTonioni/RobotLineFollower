package genetic;

import space.IPoint;

public class DoubleCirconference implements IScoreCalculator {
	public double centerX;
	public double centerY;
	
	public double outerRadiusSquare;
	public double innerRadiusSquare;
	
	
	public DoubleCirconference(double centerX, double centerY, double innerRadius, double outerRadius){
		this.centerX=centerX;
		this.centerY=centerY;
		
		outerRadiusSquare=outerRadius*outerRadius;
		innerRadiusSquare=innerRadius*innerRadius;
	}
	
	@Override
	public int calculateScore(IPoint robotPosition) {
		double robotX=robotPosition.getX();
		double robotY=robotPosition.getY();
		
		if(isInsideOuterCirconference(robotX, robotY) && isOutFromInnerCirconference(robotX, robotY))
			return 1;
		else
			return 0;
	}
	
	private boolean isInsideOuterCirconference(double x, double y){
		return Math.pow(x-centerX, 2)+Math.pow(y-centerY, 2)<=outerRadiusSquare;
	}
	
	private boolean isOutFromInnerCirconference(double x,double y){
		return Math.pow(x-centerX, 2)+Math.pow(y-centerY, 2)>=innerRadiusSquare;
	}

}
