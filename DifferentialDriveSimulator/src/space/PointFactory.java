package space;

/**
 * Factory class to generate IPoint
 * @author Alessio Tonioni
 *
 */
public class PointFactory {
	private static PointFactory _instance;
	
	public static PointFactory getInstance(){
		if(_instance==null){
			_instance=new PointFactory();
		}
		return _instance;
	}
	
	/**
	 * Generate a 2D-Point instance from x and y coordinates
	 * @param x 
	 * @param y
	 * @return
	 */
	public IPoint getPoint(double x,double y){
		return new twoDPoint(x,y);
	}
}
