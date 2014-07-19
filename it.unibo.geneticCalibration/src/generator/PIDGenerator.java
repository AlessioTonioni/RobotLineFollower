package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import member.IPopulationMember;
import member.PIDPopulationMember;

public class PIDGenerator extends AbstractGenerator{

	private Random rand;
	
	public PIDGenerator(){
		rand=new Random();
	}
	
	@Override
	protected IPopulationMember applyMutation(IPopulationMember son) {
		PIDPopulationMember s=(PIDPopulationMember)son;
		int mutation1=randInt(-1000,1000);
		int mutation2=randInt(-100,100);
		int mutation3=randInt(-100,100);
		
		int newKProportional=Math.abs(s.getkProportional()+mutation1);
		int newKIntegral=Math.abs(s.getkIntegral()+mutation2);
		int newKDerivative=Math.abs(s.getkDerivative()+mutation3);
		
		return new PIDPopulationMember(newKProportional, newKDerivative, newKIntegral);
	}

	@Override
	protected IPopulationMember applyCrossover(IPopulationMember father,IPopulationMember mother) {
		PIDPopulationMember f=(PIDPopulationMember)father;
		PIDPopulationMember m=(PIDPopulationMember)mother;
		
		double newK1= rand.nextDouble();
		double newK2= rand.nextDouble();
		double newK3= rand.nextDouble();
		
		int newKProportional=(newK1<=0.50)?f.getkProportional():m.getkProportional();
		int newKIntegral=(newK2<=0.50)?f.getkIntegral():m.getkIntegral();
		int newKDerivative=(newK3<=0.50)?f.getkDerivative():m.getkDerivative();
		
		return new PIDPopulationMember(newKProportional, newKDerivative, newKIntegral);
	}

	@Override
	protected IPopulationMember getRandomMember(List<IPopulationMember> currentGen) {
		double val=rand.nextDouble();
		int index=0;
		double probability=0.50; //probability of selceting the fittest or the index 0
		
		while(index<currentGen.size()){
			if(val<probability)
				return currentGen.get(index);
			else
				probability+=probability/2;
			index++;
		}
		return currentGen.get(index-1);
	}
	
	private int randInt(int min, int max) {
	    return rand.nextInt((max - min) + 1) + min;
	}

	@Override
	public List<IPopulationMember> getFirstGeneration(int size) {
		List<IPopulationMember> result=new ArrayList<IPopulationMember>();
		for(int i=0; i<size; i++){
			int kp=randInt(0,5000);
			int kd=randInt(0,1000);
			int ki=randInt(0,1000);
			
			result.add(new PIDPopulationMember(kp, kd, ki));
		}
		return result;
	}

}
