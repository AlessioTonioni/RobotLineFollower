package generator;

import java.util.List;

import member.IPopulationMember;
import member.PIDPopulationMember;

public interface IGenerator {
	
	List<IPopulationMember> getNewGeneration(List<IPopulationMember> currentGen);
	List<IPopulationMember> getFirstGeneration(int size);
}
