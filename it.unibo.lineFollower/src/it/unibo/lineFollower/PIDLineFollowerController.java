package it.unibo.lineFollower;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import it.unibo.iot.configuration.IConfiguration;
import it.unibo.iot.configurator.Configurator;
import it.unibo.iot.models.robotCommands.RobotSpeedValue;
import it.unibo.iot.models.sensorData.IDetection;
import it.unibo.iot.models.wheelCommands.IWheel;
import it.unibo.iot.models.wheelCommands.IWheelCommand;
import it.unibo.iot.models.wheelCommands.IWheelSpeed;
import it.unibo.iot.models.wheelCommands.Wheel;
import it.unibo.iot.models.wheelCommands.WheelCommand;
import it.unibo.iot.models.wheelCommands.WheelSpeed;
import it.unibo.iot.models.wheelCommands.WheelSpeedValue;
import it.unibo.iot.robot.DDWheelID;
import it.unibo.iot.robot.IDifferentialDriveRobot;
import it.unibo.iot.sensors.detector.IDetectorObservable;
import it.unibo.iot.sensors.detector.IDetectorObserver;

public class PIDLineFollowerController implements ILineFollowerController {

	protected IDifferentialDriveRobot robot;
	protected RobotSpeedValue speed;
	protected boolean isForward;
	protected int millisSleep;

	protected int error;  
	//+10_____0_____-10

	protected int integral=0;
	protected int derivative=0;
	protected int lastError=0;

	protected int kProportional;
	protected int kDerivative;
	protected int kIntegral;

	private volatile boolean running = true;
	private boolean configured=false;

	public PIDLineFollowerController(RobotSpeedValue defaultSpeed, IDifferentialDriveRobot robot, boolean isForward){
		this.robot=robot;
		this.speed=defaultSpeed;
		this.isForward=isForward;
	}

	public void configure(String filename) throws IOException{
		File f=new File(filename);
		if(f.exists()){
			BufferedReader reader=new BufferedReader(new FileReader(f));
			String line=reader.readLine();
			String[] constant=line.split(":");
			System.out.println("Proporzionale:"+constant[0]+" derivativa:"+constant[1]+" integrale:"+constant[2]);
			kProportional=Integer.parseInt(constant[0]);
			kDerivative=Integer.parseInt(constant[1]);
			kIntegral=Integer.parseInt(constant[2]);
			millisSleep=Integer.parseInt(constant[3]);
			configured=true;
			reader.close();
		} else {
			System.out.println("File di configurazione non trovato!");
			System.exit(0);
		}
	}

	public void configure(int kProportional, int kDerivative, int kIntegral){
		error=0;
		integral=0;
		derivative=0;
		lastError=0;
		this.kProportional=kProportional;
		this.kDerivative=kDerivative;
		this.kIntegral=kIntegral;
		configured=true;
	}

	public void terminate() {
		running = false;
	}

	public void updateError(IDetection detection){
		switch (detection.getDirection()){
		case EAST:
			if(detection.getVal())
				error-=10;
			else
				error+=10;  //ho lasciato la linea, azzero l'errore relativo
			break;
		case WEST:
			if(detection.getVal())
				error+=10;
			else
				error-=10; //ho lasciato la linea, azzero l'errore relativo
			break;
		default:
			break;
		}
	}


	@Override
	public void setSpeed(RobotSpeedValue newSpeed) {
		speed=newSpeed;
	}

	@Override
	public void doJob(){
		run();
	}


	protected IWheelCommand calculateNewCommand() {
		derivative=error-lastError;
		integral=(error==0)?0:integral+error;
		int turn=((kProportional*error)+(kIntegral*integral)+(kDerivative*derivative))/1000;
		IWheelSpeed rightSpeed;
		IWheelSpeed leftSpeed;

		//System.out.println("errore: "+error+" integrale:"+integral+" derivativo:"+derivative+" turn calcolata:" +turn);

		lastError=error;
		if(isForward){
			rightSpeed=new WheelSpeed(WheelSpeedValue.RWSETTABLE.setValue(speed.getNumValue()+turn));
			leftSpeed=new WheelSpeed(WheelSpeedValue.LWSETTABLE.setValue(speed.getNumValue()-turn));
		} else {
			rightSpeed=new WheelSpeed(WheelSpeedValue.RWSETTABLE.setValue(-speed.getNumValue()+turn));
			leftSpeed=new WheelSpeed(WheelSpeedValue.LWSETTABLE.setValue(-speed.getNumValue()-turn));
		}

		//System.out.println("lw: "+leftSpeed.getSpeed().getValue()+" rw:"+rightSpeed.getSpeed().getValue());
		IWheel leftWheel = new Wheel(DDWheelID.LEFT.toString(), leftSpeed);
		IWheel rightWheel = new Wheel(DDWheelID.RIGHT.toString(), rightSpeed);
		return new WheelCommand(leftWheel, rightWheel);
	}


	@Override
	public void setForward(boolean isForward) {
		this.isForward=isForward;

	}

	@Override
	public void run() {
		if(configured){
			IConfiguration conf = Configurator.getConfiguration();
			final PIDLineFollowerController myself=this;

			IDetectorObserver obsDetectorObserver = new IDetectorObserver() {

				@Override
				public void notify(IDetection detection) {
					myself.updateError(detection);
				}
			};
			IDetectorObservable [] detectorObservables = conf.getLineDetectorObservables();
			for (IDetectorObservable iDetectorObservable : detectorObservables) {
				iDetectorObservable.addObserver(obsDetectorObserver);
			}

			System.out.println("ReadyToStart!");
			while(running){  
				try{
					robot.execute(calculateNewCommand());
					Thread.sleep(millisSleep);  
				}catch(Exception e){
					continue;
				}
			}
		} else {
			System.out.println("Controller not yet configured");
			return;
		}

	}

}
