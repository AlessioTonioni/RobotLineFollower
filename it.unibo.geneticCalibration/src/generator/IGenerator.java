package generator;

import java.util.List;

import member.IPopulationMember;

public interface IGenerator {
	
	List<IPopulationMember> getNewGeneration(List<IPopulationMember> currentGen);
	List<IPopulationMember> getFirstGeneration(int size);
}
