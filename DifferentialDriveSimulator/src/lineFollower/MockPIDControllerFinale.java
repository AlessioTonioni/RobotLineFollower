package lineFollower;

import it.unibo.commandTranslator.ICommandTranslator;
import it.unibo.errorUpdater.IErrorUpdater;
import it.unibo.lineFollower.PIDLineFollowerControllerFinale;

public class MockPIDControllerFinale extends PIDLineFollowerControllerFinale {

	public MockPIDControllerFinale(IErrorUpdater errorUpdater,
			ICommandTranslator commandTranslator) {
		super(errorUpdater, commandTranslator);
		// TODO Auto-generated constructor stub
	}

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
