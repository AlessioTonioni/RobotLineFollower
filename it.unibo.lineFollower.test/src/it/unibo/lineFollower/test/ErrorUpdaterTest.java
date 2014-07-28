package it.unibo.lineFollower.test;

import static org.junit.Assert.*;
import it.unibo.errorUpdater.IErrorUpdater;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.robot.IRobot;
import it.unibo.testRobot.ITestRobot;
import it.unibo.testRobot.ITestSensor;
import it.unibo.testRobot.MockTestConfiguration;

import org.junit.Before;
import org.junit.Test;

public class ErrorUpdaterTest {

	private IErrorUpdater errorUpdater;
	private ITestSensor sensors;
	private ITestRobot robot;
	private MockTestConfiguration conf;
	
	@Before
	public void setUp() throws Exception {
		conf=new MockTestConfiguration(robot,sensors);
	}

	@Test(expected=Exception.class)
	public void testNotConfigured() throws Exception {
		errorUpdater.getError();
	}
	
	@Test
	public void testError() throws Exception{
		errorUpdater.configure(conf);
		assertTrue(errorUpdater.isConfigured());
		assertEquals(0,errorUpdater.getError());
		sensors.raiseOnLine();
		assertNotEquals(0,errorUpdater.getError());
	}
	
	@Test
	public void testReset() throws Exception{
		errorUpdater.configure(conf);
		sensors.raiseOnLine();
		assertNotEquals(0,errorUpdater.getError());
		errorUpdater.reset();
		assertEquals(0,errorUpdater.getError());
	}

}
