package controller;

import java.io.IOException;

import lineFollower.MockPIDController;
import it.unibo.iot.models.motorCommands.IMotorSpeed;
import it.unibo.iot.models.motorCommands.MotorSpeed;
import it.unibo.iot.models.motorCommands.MotorState;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;
import it.unibo.iot.robot.IRobot;
import it.unibo.lineFollower.ILineFollowerController;
import robot.IDDRobot;
import robot.LineSensorDDRobot;
import robot.MockRobot;
import space.IMap;
import space.IPoint;
import space.ImageLoader;
import space.PointFactory;
import space.twoDMap;
import space.twoDPoint;

public class SimulationController {
	private int simulatedTimeMillis;
	private IDDRobot simulatedRobot;
	private MockRobot puppet;
	private ILineFollowerController controller;
	private IMap map;
	
	public SimulationController(String mapFileName) throws IOException{
		simulatedTimeMillis=0;
		map=createMap(mapFileName);
		simulatedRobot=createSimulatedRobot();
		puppet=createPuppetRobot();
		controller=createController();
	}
	
	public IMap createMap(String mapFileName) throws IOException{
		return new twoDMap(ImageLoader.getMap(ImageLoader.loadImage(mapFileName)));
	}
	
	public IDDRobot createSimulatedRobot(){
		twoDPoint start=new twoDPoint(0,0);
		twoDPoint leftS=new twoDPoint(-1,1);
		twoDPoint rightS=new twoDPoint(1,1);
		
		double heading=-3*Math.PI/4;
		double wheelRadius=2.5;
		double wheelDistance=10;
		double wheelAngularSpeed=10.13;
		
		
		return new LineSensorDDRobot(start, heading, wheelRadius, wheelDistance, 
				wheelAngularSpeed, map, leftS, rightS);
		
	}
	
	public MockRobot createPuppetRobot(){
		return new MockRobot();
	}
	
	public ILineFollowerController createController(){
		return new MockPIDController(RobotSpeedValue.ROBOT_SPEED_LOW, puppet, false);
	}
	
	public void startSimulation(){
		int lastTime=0;
		while(true){
			simulatedTimeMillis+=50;  //incremento il tempo simulato
			controller.doJob();
			
			IWheelCommand wheelCommand=puppet.getCurrentCmd();	//ottengo il comando da eseguire
			
			IWheel leftWheel = wheelCommand.getWheelByID(DDWheelID.LEFT.toString());
			double leftSpeedPercentage = toMotorSpeed(leftWheel.getSpeed().getSpeed());

			IWheel rightWheel = wheelCommand.getWheelByID(DDWheelID.RIGHT.toString());
			double rightSpeedPercentage = toMotorSpeed(rightWheel.getSpeed().getSpeed());
			
			//eseguo per un intervallo di tempo
			simulatedRobot.update_ddPercentage(rightSpeedPercentage, leftSpeedPercentage, (simulatedTimeMillis-lastTime)*0.001);
			//il controller calcola il comando da mettere in esecuzione in seguito
			
			System.out.println(simulatedRobot.getRobotPosition().getX()+" "+simulatedRobot.getRobotPosition().getY());
			lastTime=simulatedTimeMillis;
		}
	}

	private double toMotorSpeed(WheelSpeedValue speed) {
		return speed.getValue()*0.01;
	}
}
