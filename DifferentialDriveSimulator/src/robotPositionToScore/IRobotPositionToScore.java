package robotPositionToScore;

import space.IPoint;
/**
 * Associate an integer score to a certain robot positions
 * @author Alessio Tonioni
 *
 */
public interface IRobotPositionToScore {
	int calculateScore(IPoint robotPosition);
}
