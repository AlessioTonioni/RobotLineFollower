package it.unibo.lineFollower;

import it.unibo.command.utility.CommandType;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.robot.IRobot;

//===================================LINE==============
//	 __________
//	 |        |  ->
//	 | ROBOT  |  ->
//	 |________|  ->
//==================================LINE===============

/**
 * Implementation of an ILineFollowerController based on a state machine. The robot is inside two lines
 * and tries not to overpass them.
 * @author Alessio Tonioni
 *
 */
public class StateMachineBiLineFollowerController extends AbstractStateMachineLineFollowerController {
	
	public StateMachineBiLineFollowerController(RobotSpeedValue defaultSpeed,
			IRobot robotToControl, boolean isForward) {
		super(defaultSpeed, robotToControl, isForward);
	}

	public StateMachineBiLineFollowerController(IRobot robotToControl) {
		super(robotToControl);
	}

	
	@Override
	protected void changeState(IDetection detection) throws InterruptedException{
		String msg=detection.getValueStringRep();
		System.out.println(msg);
		System.out.println(detection.getVal());
		System.out.println(detection.getName());
		System.out.println(detection.getDirection());

		switch (detection.getDirection()){
		case EAST:
			eastOnLine=detection.getVal();
			break;
		case WEST:
			westOnLine=detection.getVal();
			break;
		default:
			break;
		}

		if(eastOnLine && westOnLine){
			currentCmd=stopCommand;
			return;
		}
		if(eastOnLine || westOnLine){
			correcting=true;
			factory.setSpeed(RobotSpeedValue.LIBERA.setNumValue(25));
			if(eastOnLine){
				currentCmd=moveLeft;
				return;
			} else {
				currentCmd=moveRight;
				return;
			}
		}else{
			/*if(correcting){
				correcting=false;
				currentCmd=factory.getOpposite(currentCmd);
				robot.execute(currentCmd);
				Thread.sleep(500);
			}*/
			factory.setSpeed(RobotSpeedValue.ROBOT_SPEED_LOW);
			currentCmd=defaultCommand;
			return;

		}
	}


}
