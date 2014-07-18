package member;

public interface IPopulationMember extends Comparable<IPopulationMember> {
	int getFitness();
	void setFitness(int value);
	boolean isFitnessSetted();
}
