package lineFollower;

import it.unibo.commandTranslator.ICommandTranslator;
import it.unibo.errorUpdater.IErrorUpdater;
import it.unibo.lineFollower.PIDLineFollowerControllerFinale;

/**
 * Sub-class of PIDLineFollowerControllerFinale used in a simulated environment
 * @author Alessio Tonioni
 *
 */
public class MockPIDControllerFinale extends PIDLineFollowerControllerFinale {

	public MockPIDControllerFinale(IErrorUpdater errorUpdater,
			ICommandTranslator commandTranslator) {
		super(errorUpdater, commandTranslator);
		// TODO Auto-generated constructor stub
	}

	/**
	 * The run method only does one iteration, so it can be used in an environment at simulated time
	 */
	@Override
	public void run() {
		if(configured){
			try{
				this.error=errorUpdater.getError();
				int turn=calculateTurn();
				/*if(error!=0){
					System.out.println("turn:"+turn+" error: "+error);
				}*/
				commandTranslator.executeRobotCommand(turn);
			} catch (Exception e){
				return;
			}
		} else {
			System.out.println("Controller not configured!");
			return;
		}
	}

}
