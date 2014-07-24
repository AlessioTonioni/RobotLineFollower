package it.unibo.errorUpdater;


/**
 * Interfaces that extends IDetectorObserver used to update the tracking error from the sensors data
 * @author Alessio Tonioni
 *
 */
public interface IErrorUpdater {
	/**
	 * Returns the error in this moment
	 * @return
	 * @throws Exception throws exception if the errorUpdater has not been configured yet
	 */
	int getError() throws Exception;
	
	/**
	 * Connect itself to the sensors
	 */
	void configure();
	
	/**
	 * True if the updater is connected to the sensors, false otherwise
	 * @return
	 */
	boolean isConfigured();
}
