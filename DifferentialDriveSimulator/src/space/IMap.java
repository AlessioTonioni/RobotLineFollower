package space;
/**
 * Rapresents the generic environment in whic a robot could move
 * @author Alessio Tonioni
 *
 */
public interface IMap {

	public Checker getChecker(IPoint position);
}
