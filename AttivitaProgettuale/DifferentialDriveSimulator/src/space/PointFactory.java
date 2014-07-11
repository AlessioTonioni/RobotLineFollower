package space;

public class PointFactory {
	private static PointFactory _instance;
	
	public static PointFactory getInstance(){
		if(_instance==null){
			_instance=new PointFactory();
		}
		return _instance;
	}
	
	public IPoint getPoint(double x,double y){
		return new twoDPoint(x,y);
	}
}
