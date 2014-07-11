package main;

import java.io.IOException;

import controller.SimulationController;
import robot.IDDRobot;
import robot.SimpleDDRobot;
import space.twoDPoint;

public class simpleMain {
	public static void main(String[] args) throws IOException{
		//Everything in cm except for wheel velocity in rad/s and heading in rad
		
		/*twoDPoint start=new twoDPoint(0,0);
		double heading=Math.PI/2;
		double wheelRadius=2.5;
		double wheelDistance=10;
		double wheelAngularSpeed=10.13;
		
		IDDRobot robot=new SimpleDDRobot(start,heading, wheelRadius, wheelDistance, wheelAngularSpeed);
		
		System.out.println(robot.getHeading()+" "+robot.getRobotPosition().getX()+" "+robot.getRobotPosition().getY());
		
		robot.update_ddPercentage(1, 1, 1.17);
		
		System.out.println(robot.getHeading()+" "+robot.getRobotPosition().getX()+" "+robot.getRobotPosition().getY());
		*/
	
		SimulationController sim=new SimulationController("mappa.jpg");
		sim.startSimulation();
	}
}
