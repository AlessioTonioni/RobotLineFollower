package controller;

import java.io.IOException;
import java.io.InputStreamReader;

import it.unibo.DifferentialDriveSimulator.configuration.MockConfiguration;
import it.unibo.DifferentialDriveSimulator.lineFollower.MockPIDController;
import it.unibo.DifferentialDriveSimulator.lineFollower.MockPIDControllerFinale;
import it.unibo.DifferentialDriveSimulator.robot.ColorLineSensorDDRobot;
import it.unibo.DifferentialDriveSimulator.robot.IDDRobot;
import it.unibo.DifferentialDriveSimulator.robot.LineSensorDDRobot;
import it.unibo.DifferentialDriveSimulator.robot.MockRobot;
import it.unibo.DifferentialDriveSimulator.robotPositionToScore.IRobotPositionToScore;
import it.unibo.DifferentialDriveSimulator.space.IMap;
import it.unibo.DifferentialDriveSimulator.space.ImageLoader;
import it.unibo.DifferentialDriveSimulator.space.twoDMap;
import it.unibo.DifferentialDriveSimulator.space.twoDPoint;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;
import it.unibo.lineFollower.commandTranslator.DDCommandTranslator;
import it.unibo.lineFollower.commandTranslator.ICommandTranslator;
import it.unibo.lineFollower.controller.ILineFollowerController;
import it.unibo.lineFollower.controller.PIDLineFollowerControllerFinale;
import it.unibo.lineFollower.errorUpdater.IErrorUpdater;
import it.unibo.lineFollower.errorUpdater.ThreeSensorErrorUpdater;
import it.unibo.lineFollower.errorUpdater.TwoLineSensorErrorUpdater;
/**
 * Class that coordinates the various part of the simulation engine to realize a command line simulator
 * @author Alessio Tonioni
 *
 */
public class SimulationController {
	private int simulatedTimeMillis;
	private IDDRobot simulatedRobot;
	private MockRobot puppet;
	private PIDLineFollowerControllerFinale controller;
	private IMap map;
	private IRobotPositionToScore calculator;
	 /**
	  * default constructor
	  * @param mapFileName name of the .jpg file containing the map in which the robot will move
	  * @param defaultSpeed default speed that the controller will use to move the robot
	  * @throws Exception thown if the file it's not founded or isn't well formatted
	  */
	public SimulationController(String mapFileName, int defaultSpeed) throws Exception{
		simulatedTimeMillis=0;
		map=createMap(mapFileName);
		simulatedRobot=createSimulatedRobot();
		puppet=createPuppetRobot();
		controller=createController(defaultSpeed);
	}
	
	/**
	 * sets the IRobotPositionToScore that associate an integer score to the robot position
	 * @param calculator transform the robot position in the map in an integer score
	 */
	public void setScoreCalculator(IRobotPositionToScore calculator){
		this.calculator=calculator;
	}
	
	/**
	 * Create a map from the jpg file passed as parameter
	 * @param mapFileName name of the file with the map
	 * @return IMap rappresenting the environment for the robot
	 * @throws IOException
	 */
	private IMap createMap(String mapFileName) throws IOException{
		return new twoDMap(ImageLoader.getMap(ImageLoader.loadImage(mapFileName)));
	}
	
	/**
	 * Creates the entities that simulate the physic robot and it's movement, for now the properties of the robot are hardcoded
	 * in the future they should be placed on an outside file.
	 * @return IDDRobot 
	 */
	private IDDRobot createSimulatedRobot(){  //robot simulato vero e proprio
		twoDPoint start=new twoDPoint(0,0);  //centro dell'asse delle ruote
		twoDPoint leftS=new twoDPoint(-2,3); //sensore di linea sinistro
		twoDPoint rightS=new twoDPoint(2,3); //sensore di linea destro
		twoDPoint colorS=new twoDPoint(0,3); //sensore colore
		
		double heading=Math.PI/2;   //orientamento
		double wheelRadius=2.5;     //raggio delle ruote
		double wheelDistance=10.5;  //lunghezza dell'asse delle ruote
		double wheelAngularSpeed=10.13;  //velocità angolare massima di rotazione delle ruote
		
		
		return new ColorLineSensorDDRobot(start, heading, wheelRadius, wheelDistance, 
				wheelAngularSpeed, map, leftS, rightS,colorS);
		
	}
	
	/**
	 * Create the software rappresentation of the simulated robot, it's a subClas of IRobot 
	 * @return a subclass of IRobot rappresenting the software robot
	 */
	private MockRobot createPuppetRobot(){ //robot in cui il controller mette i comandi da eseguire
		return new MockRobot();
	}
	
	/**
	 * Create the PIDController that will be used to controll the simulated robot
	 * @param defaultSpeed default speed that the controller will use to move the robot
	 * @return an istance of ILineFollowerController
	 * @throws Exception
	 */
	private PIDLineFollowerControllerFinale createController(int defaultSpeed) throws Exception{
		if(defaultSpeed<0 || defaultSpeed>100) throw new Exception("Velocità del controller non valida");
		
		ICommandTranslator c=new DDCommandTranslator(defaultSpeed,true,puppet);
		IErrorUpdater e=new TwoLineSensorErrorUpdater();
		e.configure(new MockConfiguration((ColorLineSensorDDRobot)simulatedRobot));
		MockPIDControllerFinale m= new MockPIDControllerFinale(e, c);
		m.configure("costanti.txt");
		return m;
	}
	
	/**
	 * used to change the three constants that change how the controller work.
	 * @param kProportional
	 * @param kDerivative
	 * @param kIntegral
	 */
	public void changeControllerCostants(int kProportional, int kDerivative, int kIntegral){
		controller.configure(kProportional, kDerivative, kIntegral);
	}
	
	/**
	 * Restore the initial position of the simulated robot and put the simulated time to 0
	 */
	public void reset(){
		simulatedTimeMillis=0;
		simulatedRobot.setPosition(new twoDPoint(0,0),Math.PI/2);
	}
	
	/**
	 * perform a full simulation of the behave of teh simulated robot
	 * @param numberOfSteps number of elementary steps that will be simulated
	 * @param verbose true for printing robot position at each step
	 * @param millisForTick millisecond foreach elementary step simulated
	 * @return the score associated with the simulation, 0 if the robot exited the map.
	 * @throws IOException
	 */
	public int startSimulation(int numberOfSteps,boolean verbose, int millisForTick) throws IOException {
		int score=0;
		if(calculator==null){
			throw new IOException("Manca il calcola punti");
		}
		int lastTime=0;
		//InputStreamReader in=new InputStreamReader(System.in);
		while(numberOfSteps>0){
			//incremento il tempo simulato
			simulatedTimeMillis+=millisForTick;  
			
			//il controller calcola il comando da mettere in esecuzione in seguito
			controller.run();
			
			//ottengo il comando da eseguire
			IWheelCommand wheelCommand=puppet.getCurrentCmd();	
			
			
			IWheel leftWheel = wheelCommand.getWheelByID(DDWheelID.LEFT.toString());
			double leftSpeedPercentage = toMotorSpeed(leftWheel.getSpeed().getSpeed());

			IWheel rightWheel = wheelCommand.getWheelByID(DDWheelID.RIGHT.toString());
			double rightSpeedPercentage = toMotorSpeed(rightWheel.getSpeed().getSpeed());
			
			
			//eseguo per un intervallo di tempo
			try{
				simulatedRobot.update_ddPercentage(rightSpeedPercentage, leftSpeedPercentage, (simulatedTimeMillis-lastTime)*0.001);
			}catch(Exception e){
				//System.out.println("Robot Fuori Mappa");
				return score;
			}
			lastTime=simulatedTimeMillis;
			
			if(verbose){
				System.out.println("Posizione: x:"+simulatedRobot.getRobotPosition().getX()+" y:"+simulatedRobot.getRobotPosition().getY());
				System.out.println("Comando eseguito: l:"+leftSpeedPercentage+" r:"+rightSpeedPercentage);
			}
			//System.out.println("Continua?");
			//in.read();
			
			score+=calculator.calculateScore(simulatedRobot.getRobotPosition());
			numberOfSteps--;
		}
		return score;
	}

	private double toMotorSpeed(WheelSpeedValue speed) {
		return speed.getValue()*0.01;
	}
}
