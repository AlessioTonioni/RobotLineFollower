package it.unibo.lineFollower.errorUpdater;

import it.unibo.iot.configuration.IConfiguration;


/**
 * Interfaces that extends IDetectorObserver used to update the tracking error from the sensors data
 * @author Alessio Tonioni
 *
 */
public interface IErrorUpdater {
	/**
	 * Returns the error at this moment, the error range is [-10,10] where -100 means totally off
	 * on the left and +100 means totally off on the right.
	 * @return
	 * @throws Exception throws exception if the errorUpdater has not been configured yet
	 */
	int getError() throws Exception;
	
	/**
	 * Connect himself to the sensors using conf 
	 * @param conf
	 */
	void configure(IConfiguration conf);
	
	/**
	 * True if the updater is connected to the sensors, false otherwise
	 * @return
	 */
	boolean isConfigured();
	
	/**
	 * reset the error
	 */
	void reset();
}
