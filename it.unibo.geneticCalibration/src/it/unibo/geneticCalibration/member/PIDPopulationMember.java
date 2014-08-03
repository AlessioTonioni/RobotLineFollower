package it.unibo.geneticCalibration.member;

/**
 * This individual is described as the 3 constant that caracterize a PID controller.
 * @author Alessio Tonioni
 *
 */
public class PIDPopulationMember implements IPopulationMember {
	private int kProportional;
	private int kDerivative;
	private int kIntegral;
	
	private int fitness;
	private boolean setted=false;
	

	
	public PIDPopulationMember(int kProportional, int kDerivative, int kIntegral) {
		super();
		this.kProportional = kProportional;
		this.kDerivative = kDerivative;
		this.kIntegral = kIntegral;
		this.fitness=0;
	}


	public int getkProportional() {
		return kProportional;
	}


	public int getkDerivative() {
		return kDerivative;
	}


	public int getkIntegral() {
		return kIntegral;
	}



	public void setFitness(int score) {
		this.fitness = score;
		setted=true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + kDerivative;
		result = prime * result + kIntegral;
		result = prime * result + kProportional;
		result = prime * result + fitness;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PIDPopulationMember other = (PIDPopulationMember) obj;
		if (kDerivative != other.kDerivative)
			return false;
		if (kIntegral != other.kIntegral)
			return false;
		if (kProportional != other.kProportional)
			return false;
		if (fitness != other.fitness)
			return false;
		return true;
	}

	public int getFitness() {
		return fitness;
	}


	@Override
	public int compareTo(IPopulationMember o) {
		return Integer.compare(this.getFitness(), o.getFitness())*-1;
	}


	@Override
	public boolean isFitnessSetted() {
		return setted;
	}


}