package it.unibo.iot.models.robotCommands;

import it.unibo.iot.utils.StringUtils;

public abstract class RobotCommand implements IRobotCommand {

	protected IRobotSpeed speed;

	public RobotCommand(IRobotSpeed speed) {
		this.speed = speed;
	}

	public IRobotSpeed getSpeed() {
		return speed;
	}

	public String getStringRep() {
		return "robotCommand(CMD(SPD))".replace("CMD",
				StringUtils.firstToLower(this.getClass().getSimpleName())).replace("SPD",
				speed.getStringRep());
	}
}// end Command