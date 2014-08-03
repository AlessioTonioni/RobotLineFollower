package it.unibo.geneticCalibration.generator;

import it.unibo.geneticCalibration.member.IPopulationMember;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGenerator implements IGenerator{

	public List<IPopulationMember> getNewGeneration(List<IPopulationMember> currentGen){
		int dim=currentGen.size();
		List<IPopulationMember> newGen=new ArrayList<IPopulationMember>();

		//list currentGen ordered decrescente

		newGen.add(currentGen.get(0)); //readd the fittest from this gen
		dim--;
		while(dim>0){
			IPopulationMember father=getRandomMember(currentGen);
			IPopulationMember mother=getRandomMember(currentGen);

			IPopulationMember son=applyCrossover(father,mother);
			IPopulationMember finalMember=applyMutation(son);

			newGen.add(finalMember);
			dim--;
		}
		return newGen;
	}
	


	protected abstract IPopulationMember applyMutation(IPopulationMember son);
	protected abstract IPopulationMember applyCrossover(IPopulationMember father,IPopulationMember mother); 
	protected abstract IPopulationMember getRandomMember(List<IPopulationMember> currentGen);

}
