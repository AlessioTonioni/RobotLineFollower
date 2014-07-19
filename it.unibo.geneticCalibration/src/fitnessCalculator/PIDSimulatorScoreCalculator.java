package fitnessCalculator;

import controller.SimulationController;
import robotPositionToScore.DoubleCirconference;
import member.IPopulationMember;
import member.PIDPopulationMember;

public class PIDSimulatorScoreCalculator implements IScoreCalculator{

	private int numberOfStep=0;
	private DoubleCirconference calculator;
	private SimulationController sim;
	private int millisForStep;

	public PIDSimulatorScoreCalculator(int simulationStep,int millisForStep,int defaultSpeed) throws Exception{
		calculator=new DoubleCirconference(145,0,125,175);
		sim=new SimulationController("mappa.jpg",defaultSpeed);
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
