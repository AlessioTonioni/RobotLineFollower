package it.unibo.lineFollower.commandTranslator;

import it.unibo.command.utility.CommandFactory;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelSpeed;
import it.unibo.iot.models.wheelCommands.Wheel;
import it.unibo.iot.models.wheelCommands.WheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeed;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.robot.IRobot;

/**
 * Implementation of ICommandTransltaor for a two wheel Differential Drive IRobot
 * @author Alessio Tonioni
 *
 */
public class DDCommandTranslator implements ICommandTranslator{
	private int speed;
	private boolean isForward;
	private IDifferentialDriveRobot robot;
	
	private CommandFactory factory;
	
	/**
	 * Creates the object and sets the parameters
	 * @param defaultSpeed speed at which the robot it's supposed to move
	 * @param isForward true if the robot moves forward
	 */
	public DDCommandTranslator(int defaultSpeed, boolean isForward, IDifferentialDriveRobot robot){
		speed=defaultSpeed;
		factory=CommandFactory.getInstance();
		factory.setSpeed(RobotSpeedValue.LIBERA.setNumValue(speed));
		this.isForward=isForward;
		this.robot=robot;
	}

	@Override
	public void executeRobotCommand(int turn) {
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
		robot.execute(new WheelCommand(leftWheel, rightWheel));
		
	}

	@Override
	public void setSpeed(int speedPercentage) {
		this.speed=speedPercentage;
	}

	@Override
	public int getSpeed() {
		return this.speed;
	}

	@Override
	public void setRobot(IRobot robot) {
		if(robot instanceof IDifferentialDriveRobot)
			this.robot=(IDifferentialDriveRobot)robot;
	}

	@Override
	public IRobot getRobot() {
		return robot;
	}

	@Override
	public void setForward(boolean isForward) {
		this.isForward=isForward;
	}

	@Override
	public boolean isForward() {
		return isForward;
	}

	
}
