package test;

import static org.junit.Assert.*;
import interfaces.ICommandReceiver;
import interfaces.ILineFollower;
import interfaces.RemoteDevice;
import it.unibo.iot.models.robotCommands.RobotForward;

import org.junit.Before;
import org.junit.Test;

public class RemoteDeviceTest {
	private ICommandReceiver receiver;
	private MyIRobot testRobot;
	private RemoteDevice remote;
	private ILineFollower follow;
	@Before
	public void setUp() throws Exception {
		receiver.setLineFollower(follow);
		receiver.setRobot(testRobot);
		remote.setsCommandReceiver(receiver);
	}

	@Test
	public void testGetterSetter() {
		assertEquals(remote.getCommandReceiver(),receiver);
		assertEquals(receiver.getLineFollower(),follow);
		assertEquals(receiver.getRobot(),testRobot);
	}
	
	@Test
	public void testsimpleCommand(){
		remote.sendCmd("cmd forward 50");
		receiver.executeRequest(receiver.receiveMessage());
		assert(testRobot.getCurrentCmd() instanceof RobotForward);
	}
	
	@Test
	public void testLineFollowing() throws InterruptedException{
		remote.sendCmd("cnt start");
		receiver.executeRequest(receiver.receiveMessage());
		Thread.sleep(1000);
		assert(testRobot.isOnLine());
	}

}
