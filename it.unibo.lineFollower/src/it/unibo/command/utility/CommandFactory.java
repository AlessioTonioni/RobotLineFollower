package it.unibo.command.utility;

import java.util.HashMap;

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
	
	public boolean isDefaultSpeedMode(){
		return defaultSpeedMode;
	}

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
	
	public IRobotCommand getCommand(CommandType tipo, int speedValue){
		IRobotCommand result=getCommand(tipo);
		result.getSpeed().getSpeed().setNumValue(speedValue);
		return result;
	}
	
	public IRobotCommand getCommand(String tipo){
		return this.getCommand(CommandType.valueOf(tipo.toUpperCase()));
	}
	
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
