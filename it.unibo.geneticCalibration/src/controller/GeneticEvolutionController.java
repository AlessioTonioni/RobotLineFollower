package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import fitnessCalculator.IScoreCalculator;
import generator.IGenerator;
import member.IPopulationMember;
import member.PIDPopulationMember;

public class GeneticEvolutionController{
	
	private IScoreCalculator scoreCalculator;
	private IGenerator generator;
	
	
	public GeneticEvolutionController(IScoreCalculator scoreCalculator,
			IGenerator generator) {
		super();
		this.scoreCalculator = scoreCalculator;
		this.generator = generator;
	}
	
	public IScoreCalculator getScoreCalculator() {
		return scoreCalculator;
	}
	public void setScoreCalculator(IScoreCalculator scoreCalculator) {
		this.scoreCalculator = scoreCalculator;
	}
	public IGenerator getGenerator() {
		return generator;
	}
	public void setGenerator(IGenerator generator) {
		this.generator = generator;
	}
	
	public IPopulationMember doCompuation(int numberOfIterations, int generationSize, File log) throws IOException{
		BufferedWriter writer=new BufferedWriter(new FileWriter(log));
		IPopulationMember fittest=null;
		List<IPopulationMember> generation=generator.getFirstGeneration(generationSize);
		for(int i=0; i<numberOfIterations; i++){
			for(IPopulationMember p:generation){
				scoreCalculator.setFitness(p);
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
