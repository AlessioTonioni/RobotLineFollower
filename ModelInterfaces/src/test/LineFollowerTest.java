package test;

import static org.junit.Assert.*;
import interfaces.ILineFollower;

import org.junit.Before;
import org.junit.Test;

public class LineFollowerTest {
	private MyIRobot testRobot;
	private ILineFollower follower;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testRobotSet(){
		follower.setRobot(testRobot);
		assertEquals(follower.getRobot(),testRobot);
	}
	
	@Test
	public void testLineFollowing() throws InterruptedException {
		follower.setRobot(testRobot);
		follower.start();
		Thread.sleep(10000);
		assertTrue(testRobot.isOnLine());
		follower.terminate();
	}

}
