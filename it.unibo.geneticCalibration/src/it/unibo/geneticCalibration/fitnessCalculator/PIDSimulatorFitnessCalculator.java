package it.unibo.geneticCalibration.fitnessCalculator;

import it.unibo.DifferentialDriveSimulator.robotPositionToScore.DoubleCirconference;
import it.unibo.geneticCalibration.member.IPopulationMember;
import it.unibo.geneticCalibration.member.PIDPopulationMember;
import controller.SimulationController;
/**
 * Implementation of the fitness calculator used to calibrate a PID controller, associate a score to a controller 
 * simulating their behavior over a fixed number of milliseconds and giving them a score based on how much they are
 * able to follow a line.
 * @author Alessio Tonioni
 *
 */
public class PIDSimulatorFitnessCalculator implements IFitnessCalculator{

	private int numberOfStep=0;
	private DoubleCirconference calculator;
	private SimulationController sim;
	private int millisForStep;

	/**
	 * Default constructor
	 * @param simulationStep number of simulated step for each controller
	 * @param millisForStep milliseconds for each step
	 * @param defaultSpeed default speed of the controller
	 * @throws Exception 
	 */
	public PIDSimulatorFitnessCalculator(int simulationStep,int millisForStep,int defaultSpeed) throws Exception{
		calculator=new DoubleCirconference(145,0,125,175);
		sim=new SimulationController("mappa.jpg",defaultSpeed);
		sim.setScoreCalculator(calculator);
		numberOfStep=simulationStep;
		this.millisForStep=millisForStep;
	}

	@Override
	public void setFitness(IPopulationMember member)  {
		try{
			PIDPopulationMember m=(PIDPopulationMember)member;
			if(!member.isFitnessSetted()){
				sim.reset();
				sim.changeControllerCostants(m.getkProportional(), m.getkDerivative(), m.getkIntegral());
				int score=sim.startSimulation(numberOfStep, false,millisForStep);
				member.setFitness(score);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Errore nella configurazione!");
			System.exit(0);
		}
	}



}
