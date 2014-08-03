package it.unibo.geneticCalibration.member;

/**
 * Generic evolutionary generation member, the only important attributes it's his fitness.
 * @author Alessio Tonioni
 *
 */
public interface IPopulationMember extends Comparable<IPopulationMember> {
	int getFitness();
	void setFitness(int value);
	boolean isFitnessSetted();
}
