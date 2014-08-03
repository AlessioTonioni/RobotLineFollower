package it.unibo.lineFollower.controller;

import it.unibo.command.utility.CommandType;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.robot.IRobot;

//   __________
//   |        |  ->
//===| ROBOT  |============== LINE ===================
//   |________|  ->

/**
 * Implementation of an ILineFollowerController based on a state machine. The robot is over a line and tries
 * to follow it.
 * @author Alessio Tonioni
 *
 */
public class StateMachineMonoLineFollowerController extends AbstractStateMachineLineFollowerController {
	
	public StateMachineMonoLineFollowerController(RobotSpeedValue defaultSpeed, IRobot robotToControl, boolean isForward){
		super(defaultSpeed,robotToControl,isForward);
	}
	
	public StateMachineMonoLineFollowerController(IRobot robotToControl){
		super(robotToControl);
	}

	@Override
	protected void changeState(IDetection detection) {
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
			currentCmd=defaultCommand;
			return;
		}
		if(!eastOnLine){
			currentCmd=moveLeft;
			return;
		}
		else if(!westOnLine){
			currentCmd=moveRight;
			return;
		} else{
			currentCmd=stopCommand;
			return;
		}
		
		
	}
}
