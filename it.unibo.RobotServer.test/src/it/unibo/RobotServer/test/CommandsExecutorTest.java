package it.unibo.RobotServer.test;

import static org.junit.Assert.*;

import java.io.IOException;

import it.unibo.iot.models.robotCommands.IRobotCommand;
import it.unibo.iot.models.robotCommands.RobotForward;
import it.unibo.iot.models.robotCommands.RobotStop;
import it.unibo.iot.robot.IRobot;
import it.unibo.testRobot.ITestRobot;

import org.junit.Before;
import org.junit.Test;

import commandsExecutor.ICommandsExecutor;

public class CommandsExecutorTest {

	private ITestRobot testRobot;
	private ICommandsExecutor commandsExecutor;
	@Before
	public void setUp() throws Exception {
		commandsExecutor.setRobot(testRobot);
	}

	@Test
	public void testGetRobot(){
		commandsExecutor.setRobot(testRobot);
		assertEquals(testRobot,commandsExecutor.getRobot());
	}
	@Test
	public void testCmdExecution() throws InterruptedException {
		commandsExecutor.executeCommand("FORWARD", "50");
		IRobotCommand currentCmd=testRobot.getCurrentCmd();
		assertTrue(currentCmd instanceof RobotForward);
		assertEquals(currentCmd.getSpeed().getPercentageOfSpeed(),50);
	}
	
	@Test
	public void testCntExecution() throws IOException, InterruptedException{
		commandsExecutor.executeController("PIDFinale", "50", "true");
		Thread.sleep(100);
		IRobotCommand currentCmd=testRobot.getCurrentCmd();
		assertTrue(currentCmd instanceof RobotForward);
		assertEquals(currentCmd.getSpeed().getPercentageOfSpeed(),50);
		
		//new commands kill the controller
		commandsExecutor.executeCommand("STOP", "10");
		currentCmd=testRobot.getCurrentCmd();
		assertTrue(currentCmd instanceof RobotStop);
		assertEquals(currentCmd.getSpeed().getPercentageOfSpeed(),10);
	}

}
