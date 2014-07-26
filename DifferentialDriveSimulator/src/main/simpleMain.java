package main;


import controller.SimulationController;
import robotPositionToScore.DoubleCirconference;

public class simpleMain {
	public static void main(String[] args) throws Exception{
		//Everything in cm except for wheel velocity in rad/s and heading in rad
		
		/*twoDPoint start=new twoDPoint(0,0);
		double heading=Math.PI/2;
		double wheelRadius=2.5;
		double wheelDistance=10;
		double wheelAngularSpeed=10.13;
		
		IDDRobot robot=new SimpleDDRobot(start,heading, wheelRadius, wheelDistance, wheelAngularSpeed);
		
		System.out.println(robot.getHeading()+" "+robot.getRobotPosition().getX()+" "+robot.getRobotPosition().getY());
		
		robot.update_ddPercentage(0.5, 0, 1.17);
		
		System.out.println(robot.getHeading()+" "+robot.getRobotPosition().getX()+" "+robot.getRobotPosition().getY());*/
		DoubleCirconference c=new DoubleCirconference(145,0,125,175);
		SimulationController sim=new SimulationController("mappa.jpg",50);
		sim.setScoreCalculator(c);
		System.out.println(sim.startSimulation(40000,false,30));
	}
}
