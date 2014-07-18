package fitnessCalculator;

import java.io.IOException;

import controller.SimulationController;
import robotPositionToScore.DoubleCirconference;
import member.IPopulationMember;
import member.PIDPopulationMember;

public class PIDSimulatorScoreCalculator implements IScoreCalculator{

	private int numberOfStep=1000;
	private DoubleCirconference calculator;
	private SimulationController sim;

	public PIDSimulatorScoreCalculator(int simulationStep) throws IOException{
		calculator=new DoubleCirconference(145,0,125,175);
		sim=new SimulationController("mappa.jpg");
		sim.setScoreCalculator(calculator);
		numberOfStep=simulationStep;
	}

	@Override
	public void setFitness(IPopulationMember member)  {
		try{
			PIDPopulationMember m=(PIDPopulationMember)member;
			if(!member.isFitnessSetted()){
				sim.reset();
				sim.changeControllerCostants(m.getkProportional(), m.getkDerivative(), m.getkIntegral());
				member.setFitness(sim.startSimulation(numberOfStep, false));
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Errore nella configurazione!");
			System.exit(0);
		}
	}



}
