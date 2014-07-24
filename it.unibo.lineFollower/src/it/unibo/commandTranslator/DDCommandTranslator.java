package it.unibo.commandTranslator;

import it.unibo.command.utility.CommandFactory;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.IWheelSpeed;
import it.unibo.iot.models.wheelCommands.Wheel;
import it.unibo.iot.models.wheelCommands.WheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeed;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;

/**
 * Implementation of ICommandTransltaor for a two wheel Differential Drive IRobot
 * @author Alessio Tonioni
 *
 */
public class DDCommandTranslator implements ICommandTranslator{
	private int speed;
	private boolean isForward;
	
	private CommandFactory factory;
	
	protected IRobotCommand moveLeft;
	protected IRobotCommand moveRight;
	protected IRobotCommand defaultCommand;
	protected IRobotCommand stopCommand;
	
	/**
	 * Creates the object and sets the parameters
	 * @param defaultSpeed speed at which the robot it's supposed to move
	 * @param isForward true if the robot moves forward
	 */
	public DDCommandTranslator(int defaultSpeed, boolean isForward){
		speed=defaultSpeed;
		factory=CommandFactory.getInstance();
		factory.setSpeed(RobotSpeedValue.LIBERA.setNumValue(speed));
		this.isForward=isForward;
		setCorrectCommand();
	}
	
	private void setCorrectCommand(){
		if(isForward){
			moveLeft=factory.getCommand("FORWARDLEFT");
			moveRight=factory.getCommand("FORWARDRIGHT");
			defaultCommand=factory.getCommand("FORWARD");
		} else {
			moveLeft=factory.getCommand("BACKWARDLEFT");
			moveRight=factory.getCommand("BACKWARDRIGHT");
			defaultCommand=factory.getCommand("BACKWARD");
		}
		stopCommand=factory.getCommand("STOP");
	}
	
	@Override
	public IRobotCommand getRobotCommand(int turn) {
		if(turn==0)
			return defaultCommand;
		else if(turn>0)
			return moveRight;
		else
			return moveLeft;
					
	}

	@Override
	public IWheelCommand getWheelCommand(int turn) {
		IWheelSpeed rightSpeed;
		IWheelSpeed leftSpeed;

		//System.out.println("errore: "+error+" integrale:"+integral+" derivativo:"+derivative+" turn calcolata:" +turn);

		if(isForward){
			rightSpeed=new WheelSpeed(WheelSpeedValue.RWSETTABLE.setValue(speed+turn));
			leftSpeed=new WheelSpeed(WheelSpeedValue.LWSETTABLE.setValue(speed-turn));
		} else {
			rightSpeed=new WheelSpeed(WheelSpeedValue.RWSETTABLE.setValue(-speed+turn));
			leftSpeed=new WheelSpeed(WheelSpeedValue.LWSETTABLE.setValue(-speed-turn));
		}

		//System.out.println("lw: "+leftSpeed.getSpeed().getValue()+" rw:"+rightSpeed.getSpeed().getValue());
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}

	
}
