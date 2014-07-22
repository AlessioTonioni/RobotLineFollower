package it.unibo.commandTranslator;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.IWheelSpeed;
import it.unibo.iot.models.wheelCommands.Wheel;
import it.unibo.iot.models.wheelCommands.WheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeed;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;

public class DDCommandTranslator implements ICommandTranslator{
	private int speed;
	private boolean isForward;
	
	public DDCommandTranslator(int defaultSpeed, boolean isForward){
		speed=defaultSpeed;
		this.isForward=isForward;
	}
	
	@Override
	public IRobotCommand getRobotCommand(int turn) {
		// TODO Auto-generated method stub
		return null;
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
