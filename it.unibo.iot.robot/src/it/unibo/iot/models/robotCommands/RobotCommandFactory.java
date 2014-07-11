package it.unibo.iot.models.robotCommands;
 
public class RobotCommandFactory {
	
	public static IRobotCommand createRobotCommandFromString(
			String robotCommandStringRep) {
		
		robotCommandStringRep = robotCommandStringRep.toLowerCase();
		
		if (robotCommandStringRep.contains("forward")) {
			return SysKB.FORWARD;
		}
		if (robotCommandStringRep.contains("backward")) {
			return SysKB.BACKWARD;
		}
		if (robotCommandStringRep.contains("left")) {
			return SysKB.LEFT;
		}
		if (robotCommandStringRep.contains("right")) {
			return SysKB.RIGHT;
		}
		if (robotCommandStringRep.contains("stop")) {
			return SysKB.STOP;
		}
 		return null;
	}
}
