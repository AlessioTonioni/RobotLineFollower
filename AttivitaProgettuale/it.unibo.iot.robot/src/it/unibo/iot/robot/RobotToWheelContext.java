package it.unibo.iot.robot;

public class RobotToWheelContext {
	private IRobotToWhellStarategy robotToWhellStarategy;

	public IRobotToWhellStarategy getRTWStrategy(String robotType) {
		if (robotType.equalsIgnoreCase("initio")) {
			if (robotToWhellStarategy == null || !(robotToWhellStarategy instanceof RobotToWheelStrategyInitio)) {
				robotToWhellStarategy = new RobotToWheelStrategyInitio();
			}
		} else if (robotToWhellStarategy == null) {
			robotToWhellStarategy = new RobotToWheelStrategyDefault();
		}
		return robotToWhellStarategy;
	}
}
