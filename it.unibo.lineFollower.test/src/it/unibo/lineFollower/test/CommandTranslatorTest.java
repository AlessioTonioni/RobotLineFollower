package it.unibo.lineFollower.test;

import static org.junit.Assert.*;
import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotBackward;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.lineFollower.commandTranslator.ICommandTranslator;
import it.unibo.testRobot.ITestRobot;

import org.junit.Before;
import org.junit.Test;

public class CommandTranslatorTest {

	private ICommandTranslator commandTranslator;
	private ITestRobot robot;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSetterGetter() {
		commandTranslator.setRobot(robot);
		assertEquals(robot,commandTranslator.getRobot());
		
		commandTranslator.setSpeed(50);
		assertEquals(50,commandTranslator.getSpeed());
		
		commandTranslator.setForward(true);
		assertTrue(commandTranslator.isForward());
	}
	
	@Test
	public void testTranslation(){
		commandTranslator.executeRobotCommand(0);
		IRobotCommand cmd=robot.getCurrentCmd();
		assertEquals(cmd.getSpeed().getPercentageOfSpeed(),commandTranslator.getSpeed());
		if(commandTranslator.isForward())
			assertTrue(cmd instanceof RobotForward);
		else
			assertTrue(cmd instanceof RobotBackward);
	}

}
