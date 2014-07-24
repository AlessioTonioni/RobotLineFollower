package robotPositionToScore;

import space.IPoint;

public class DoubleCirconference  implements IRobotPositionToScore{
	public double centerX;
	public double centerY;
	
	public double outerRadiusSquare;
	public double innerRadiusSquare;
	
	public double lastPositionX=0;
	public double lastPositionY=0;
	
	public DoubleCirconference(double centerX, double centerY, double innerRadius, double outerRadius){
		this.centerX=centerX;
		this.centerY=centerY;
		
		outerRadiusSquare=outerRadius*outerRadius;
		innerRadiusSquare=innerRadius*innerRadius;
	}
	
	public int calculateScore(IPoint robotPosition) {
		int score=0;
		double robotX=robotPosition.getX();
		double robotY=robotPosition.getY();
		
		if(isInsideOuterCirconference(robotX, robotY) && isOutFromInnerCirconference(robotX, robotY) 
				&& hasMoved(robotPosition)){
			score=1;
			if(robotPosition.getY()>0 && (robotPosition.getX()>lastPositionX))
				score+=1;
			else if(robotPosition.getY()>0 && (robotPosition.getX()<lastPositionX))
				score+=1;
		}
		lastPositionX=robotPosition.getX();
		lastPositionY=robotPosition.getY();
		return score;
	}


	private boolean hasMoved(IPoint robotPosition) {
		return lastPositionX!=robotPosition.getX() || lastPositionY!=robotPosition.getY();
	}

	private boolean isInsideOuterCirconference(double x, double y){
		return Math.pow(x-centerX, 2)+Math.pow(y-centerY, 2)<=outerRadiusSquare;
	}
	
	private boolean isOutFromInnerCirconference(double x,double y){
		return Math.pow(x-centerX, 2)+Math.pow(y-centerY, 2)>=innerRadiusSquare;
	}

}
