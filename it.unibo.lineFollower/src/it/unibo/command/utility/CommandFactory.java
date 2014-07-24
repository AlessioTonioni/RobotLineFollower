package it.unibo.command.utility;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.IRobotSpeed;
import it.unibo.iot.models.robotCommands.RobotBackward;
import it.unibo.iot.models.robotCommands.RobotBackwardLeft;
import it.unibo.iot.models.robotCommands.RobotBackwardRight;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.iot.models.robotCommands.RobotForwardLeft;
import it.unibo.iot.models.robotCommands.RobotForwardRight;
import it.unibo.iot.models.robotCommands.RobotLeft;
import it.unibo.iot.models.robotCommands.RobotRight;
import it.unibo.iot.models.robotCommands.RobotSpeed;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.robotCommands.RobotStop;
/**
 * Factory class for ICommand instance
 * @author Alessio Tonioni
 *
 */
public class CommandFactory { 
	private static CommandFactory instance; 
	
	private IRobotSpeed speed ;
	private IRobotCommand forward;
	private IRobotCommand forwardRight;
	private IRobotCommand forwardLeft;
	private IRobotCommand backward;
	private IRobotCommand backwardRight;
	private IRobotCommand backwardLeft;
	private IRobotCommand left;
	private IRobotCommand right;
	private IRobotCommand stop;
	
	private boolean defaultSpeedMode=true;
	
	private CommandFactory(){
		speed=new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_LOW);
		buildAllCommand();
	}
	
	public static CommandFactory getInstance(){
		if(instance==null)
			instance=new CommandFactory();
		return instance;
	}
	
	/**
	 * Default speed mode means that all the command are at the same speed 
	 * @return
	 */
	public boolean isDefaultSpeedMode(){
		return defaultSpeedMode;
	}

	/**
	 * Get command from it's type, the speed is the one setted in setSpeed
	 * @param tipo type of command that you want
	 * @return an instance of ICommand corresponding to the queried CommandType
	 */
	public IRobotCommand getCommand(CommandType tipo){
		switch(tipo){
		case BACKWARD:
			return backward;
		case BACKWARDLEFT:
			return backwardLeft;
		case BACKWARDRIGHT:
			return backwardRight;
		case FORWARD:
			return forward;
		case FORWARDLEFT:
			return forwardLeft;
		case FORWARDRIGHT:
			return forwardRight;
		case LEFT:
			return left;
		case RIGHT:
			return right;
		case STOP:
			return stop;
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * Get command from it's type and speed 
	 * @param tipo type of command that you want
	 * @param speedValue speed of the returned command
	 * @return  an instance of ICommand corresponding to the queried CommandType
	 */
	public IRobotCommand getCommand(CommandType tipo, int speedValue){
		IRobotCommand result=getCommand(tipo);
		result.getSpeed().getSpeed().setNumValue(speedValue);
		return result;
	}
	
	/**
	 * Get command from it's String name, corresponding to the enum value of CommandType
	 * @param tipo type of command
	 * @return an instance of ICommand corresponding to the queried CommandType, with default speed
	 */
	public IRobotCommand getCommand(String tipo){
		return this.getCommand(CommandType.valueOf(tipo.toUpperCase()));
	}
	
	/**
	 * Sets the default speed for the command produced by the factory 
	 * @param newSpeedValue
	 */
	public void setSpeed(RobotSpeedValue newSpeedValue){
		if(speed.getSpeed()!=newSpeedValue){
			speed=new RobotSpeed(newSpeedValue);
			buildAllCommand();
		}
	}
	private void buildAllCommand() {
		forward=new RobotForward(speed);
		forwardRight=new RobotForwardRight(speed);
		forwardLeft=new RobotForwardLeft(speed);
		backward=new RobotBackward(speed);
		backwardLeft=new RobotBackwardLeft(speed);
		backwardRight=new RobotBackwardRight(speed);
		left=new RobotLeft(speed);
		right=new RobotRight(speed);
		stop=new RobotStop(speed);
	}
	
	/**
	 * Used to obtain the opposite of a given command
	 * @param currentCmd command to reverse
	 * @return the reversed command
	 */
	public IRobotCommand getOpposite(IRobotCommand currentCmd) {
		if(currentCmd instanceof RobotForwardRight)
			return forwardLeft;
		else if(currentCmd instanceof RobotForwardLeft)
			return forwardRight;
		else if(currentCmd instanceof RobotBackwardLeft)
			return backwardRight;
		else if(currentCmd instanceof RobotBackwardRight)
			return backwardLeft;
		return null;
	}
	
	/**
	 * Method used to change the execution mode of the factory from default speed to free speed.
	 */
	public void switchMode(){
		if(defaultSpeedMode){
			setSpeed(RobotSpeedValue.LIBERA);
			buildAllCommand();
		} else {
			setSpeed(RobotSpeedValue.ROBOT_SPEED_LOW);
			buildAllCommand();
		}
		defaultSpeedMode=!defaultSpeedMode;
	}
}
