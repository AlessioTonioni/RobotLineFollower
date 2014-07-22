package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import fitnessCalculator.IFitnessCalculator;
import generator.IGenerator;
import member.IPopulationMember;
import member.PIDPopulationMember;
/**
 * Class that execute a generic evolutionary computation algorithm.
 * @author Alessio Tonioni
 *
 */
public class GeneticEvolutionController{
	
	private IFitnessCalculator fitnessCalculator;
	private IGenerator generator;
	
	/**
	 * Default constructor
	 * @param fitnessCalculator object used to calculate the fitness of each individual
	 * @param generator object used to generate individuals for each generation
	 */
	public GeneticEvolutionController(IFitnessCalculator fitnessCalculator,IGenerator generator) {
		super();
		this.fitnessCalculator = fitnessCalculator;
		this.generator = generator;
	}
	
	public IFitnessCalculator getScoreCalculator() {
		return fitnessCalculator;
	}
	public void setScoreCalculator(IFitnessCalculator scoreCalculator) {
		this.fitnessCalculator = scoreCalculator;
	}
	public IGenerator getGenerator() {
		return generator;
	}
	public void setGenerator(IGenerator generator) {
		this.generator = generator;
	}
	
	/**
	 * Main execution method used to start the evolutionary computation that tries to find the best solution
	 * for the given problem. The best solution it's the one with the highest fitness.
	 * @param numberOfIterations number of evolutionary computation before giving the solution.
	 * @param generationSize number of individuals in each iteration
	 * @param log file to write reports of each iteration
	 * @return the fittest individual found after the setted iteration
	 * @throws IOException unable to open the log file or to write in it
	 */
	public IPopulationMember doCompuation(int numberOfIterations, int generationSize, File log) throws IOException{
		BufferedWriter writer=new BufferedWriter(new FileWriter(log));
		IPopulationMember fittest=null;
		List<IPopulationMember> generation=generator.getFirstGeneration(generationSize);
		for(int i=0; i<numberOfIterations; i++){
			for(IPopulationMember p:generation){
				fitnessCalculator.setFitness(p);
			}
			Collections.sort(generation);
			doLog(generation,i,writer);	
			fittest=generation.get(0);
			
			generation=generator.getNewGeneration(generation);
		}
		writer.close();
		return fittest;
	}

	private void doLog(List<IPopulationMember> generation, int i,
			BufferedWriter writer) throws IOException {
		writer.write("-------------------------------------------------------\n");
		writer.write("generazione "+i+"\n");
		for(IPopulationMember member:generation){
			PIDPopulationMember m=(PIDPopulationMember)member;
			writer.write("membro della popolazione kP:"+m.getkProportional()+" kD:"+m.getkDerivative()+" kI:"+m.getkIntegral());
			writer.write(" score:"+m.getFitness()+"\n");
		}
		writer.write("Individuo migliore iterazione "+i+": punteggio="+generation.get(0).getFitness()+"\n");
		
		
	}


	
	
}
