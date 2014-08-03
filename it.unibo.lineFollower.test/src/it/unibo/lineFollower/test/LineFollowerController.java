package it.unibo.lineFollower.test;

import static org.junit.Assert.*;
import it.unibo.lineFollower.controller.ILineFollowerController;
import it.unibo.testRobot.ITestRobot;

import org.junit.Before;
import org.junit.Test;

public class LineFollowerController {
	private ILineFollowerController controllerToTest;
	private ITestRobot robot;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLineFollower() throws InterruptedException {
		Thread t=new Thread(controllerToTest);
		t.start();
		Thread.sleep(10000);
		controllerToTest.terminate();
		t.yield();
		assertTrue(robot.isInsideLine());
	}

}
