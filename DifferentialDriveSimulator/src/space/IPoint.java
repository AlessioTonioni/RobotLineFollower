package space;

/**
 * Generic point in a 3d space identified as (x,y,z)
 * @author Alessio Tonioni
 *
 */
public interface IPoint {
	double getX();
	double getY();
	double getZ();
	void setX(double newVal);
	void setY(double newVal);
	void setZ(double newVal);
	
}
