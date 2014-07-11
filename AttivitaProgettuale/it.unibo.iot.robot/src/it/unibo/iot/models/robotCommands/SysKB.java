package it.unibo.iot.models.robotCommands;
 
 

public class SysKB {

 
	public static final IRobotSpeed ROBOT_SPEED_LOW = new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_LOW);
	public static final IRobotSpeed ROBOT_SPEED_MEDIUM = new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_MEDIUM);
	public static final IRobotSpeed ROBOT_SPEED_HIGH = new RobotSpeed(RobotSpeedValue.ROBOT_SPEED_HIGH);

	public static final IRobotCommand FORWARD = new RobotForward(ROBOT_SPEED_LOW);
	public static final IRobotCommand BACKWARD = new RobotBackward(ROBOT_SPEED_LOW);
	public static final IRobotCommand LEFT = new RobotLeft(ROBOT_SPEED_LOW);
	public static final IRobotCommand RIGHT = new RobotRight(ROBOT_SPEED_LOW);

	public static final IRobotCommand STOP = new RobotStop(ROBOT_SPEED_LOW);
 }
