package it.unibo.geneticCalibration.fitnessCalculator;

import it.unibo.geneticCalibration.member.IPopulationMember;

/**
 * Class that associate a score("fitness") to a population member given a certain metric.
 * @author Alessio Tonioni
 *
 */
public interface IFitnessCalculator {
	/**
	 * Calculate the fitness for the member passed as arguments and sets it
	 * @param member
	 */
	void setFitness(IPopulationMember member);
}
