package it.unibo.geneticCalibration.main;

import it.unibo.geneticCalibration.controller.GeneticEvolutionController;
import it.unibo.geneticCalibration.fitnessCalculator.IFitnessCalculator;
import it.unibo.geneticCalibration.fitnessCalculator.PIDSimulatorFitnessCalculator;
import it.unibo.geneticCalibration.generator.IGenerator;
import it.unibo.geneticCalibration.generator.PIDGenerator;
import it.unibo.geneticCalibration.member.PIDPopulationMember;

import java.io.File;

public class TestMain {
	public static void main(String[] args) throws Exception{
		int numberOfSimulatorSteps=0;
		int numberOfGeneticIteration=0;
		int generationSize=0;
		int millisForStep=0;
		int defaultSpeed=0;
		File logFile=null;
		
		if(args.length!=6)printUsage();
		try{
			numberOfGeneticIteration=Integer.parseInt(args[0]);
			generationSize=Integer.parseInt(args[1]);
			numberOfSimulatorSteps=Integer.parseInt(args[2]);
			millisForStep=Integer.parseInt(args[3]);
			defaultSpeed=Integer.parseInt(args[4]);
			logFile=new File(args[5]);
			if(!logFile.exists()) logFile.createNewFile();
		}catch(Exception e){
			printUsage();
		}
		
		IFitnessCalculator scoreCalculator=new PIDSimulatorFitnessCalculator(numberOfSimulatorSteps,millisForStep,defaultSpeed);
		IGenerator generator= new PIDGenerator();
		GeneticEvolutionController controller=new GeneticEvolutionController(scoreCalculator, generator);
		
		PIDPopulationMember result=(PIDPopulationMember)controller.doCompuation(numberOfGeneticIteration, generationSize, logFile);
		
		System.out.println("Computazione completata!");
		System.out.println("Individuo migliore: punteggio="+result.getFitness()+" kProp:"+
							result.getkProportional()+" kDeriv:"+result.getkDerivative()+" kInt:"+result.getkIntegral());
		
		scoreCalculator.setFitness(result);
		System.out.println("Controprova:"+result.getFitness());
	}
	
	private static void printUsage(){
		String usage="USAGE: PIDGeneticCalibrator  numberOfGeneticIterations populationSize numberOfSimulatorSteps millisForStep defaultSpeed logFile";
		System.out.println(usage);
		System.exit(0);
	}
}
