package fitnessCalculator;

import member.IPopulationMember;

public interface IScoreCalculator {
	void setFitness(IPopulationMember member);
}
