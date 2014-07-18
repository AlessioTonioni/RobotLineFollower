package main;

import java.io.File;
import java.io.IOException;

import member.PIDPopulationMember;
import controller.GeneticEvolutionController;
import fitnessCalculator.IScoreCalculator;
import fitnessCalculator.PIDSimulatorScoreCalculator;
import generator.IGenerator;
import generator.PIDGenerator;

public class TestMain {
	public static void main(String[] args) throws IOException{
		int numberOfSimulatorSteps=0;
		int numberOfGeneticIteration=0;
		int generationSize=0;
		File logFile=null;
		
		if(args.length!=4)printUsage();
		try{
			numberOfGeneticIteration=Integer.parseInt(args[0]);
			numberOfSimulatorSteps=Integer.parseInt(args[1]);
			generationSize=Integer.parseInt(args[2]);
			logFile=new File(args[3]);
			if(!logFile.exists()) logFile.createNewFile();
		}catch(Exception e){
			printUsage();
		}
		
		IScoreCalculator scoreCalculator=new PIDSimulatorScoreCalculator(numberOfSimulatorSteps);
		IGenerator generator= new PIDGenerator();
		GeneticEvolutionController controller=new GeneticEvolutionController(scoreCalculator, generator);
		
		PIDPopulationMember result=(PIDPopulationMember)controller.doCompuation(numberOfGeneticIteration, generationSize, logFile);
		
		System.out.println("Computazione completata!");
		System.out.println("Individuo migliore: punteggio="+result.getFitness()+" kProp:"+
							result.getkProportional()+" kInt"+result.getkIntegral()+" kDeriv:"+result.getkDerivative());
		
	}
	
	private static void printUsage(){
		String usage="TestMain numberOfGeneticIterations numberOFSimulatorSteps populationSize logFile";
		System.out.println(usage);
		System.exit(0);
	}
}
