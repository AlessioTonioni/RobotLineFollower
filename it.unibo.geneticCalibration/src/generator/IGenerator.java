package generator;

import java.util.List;

import member.IPopulationMember;
/**
 * Class used to generate the new generation of individuals at each evolutionary step.
 * @author Alessio Tonioni
 *
 */
public interface IGenerator {
	/**
	 * Creates the new generation from the old one, it takes the fittest member and tries to combine them to 
	 * create a better generation. A randomized mutation it's also introduced. The new generation has as much
	 * member as the old one.
	 * @param currentGen
	 * @return the new generation of individuals
	 */
	List<IPopulationMember> getNewGeneration(List<IPopulationMember> currentGen);
	/**
	 * Creates the first generation of individuals used to start the genetic algorithm
	 * @param size number of elements 
	 * @return
	 */
	List<IPopulationMember> getFirstGeneration(int size);
}
