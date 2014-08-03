package it.unibo.DifferentialDriveSimulator.space;

/**
 * Two dimensional implementations of an IMap
 * @author Alessio Tonioni
 *
 */
public class twoDMap implements IMap{
	private int maxWidth;
	private int maxHeight;
	private Checker[][] map;
	
	/**
	 * Create an empty square map of maxSize edge, 
	 * the center of the coordinates system is in (maxSize/2,maxSize/2).
	 * @param maxSize dimension of the edge
	 */
	public twoDMap(int maxSize){ 
		this.maxWidth=maxSize;
		this.maxHeight=maxSize;
		map=new Checker[maxSize][maxSize];
		for(int i=0; i<maxSize; i++){
			for(int j=0; j<maxSize; j++)
				map[i][j]=Checker.empty;
		}
	}
	
	/**
	 * Create a map from a checker matrix, the center of the coordinates system is in the middle of the matrix.
	 * @param map
	 */
	public twoDMap(Checker[][] map){
		this.map=map;
		maxHeight=map[0].length;
		maxWidth=map.length;
	}
	
	private int toWidthIndex(int x) {
		return maxWidth/2+x;
	}
	
	private int toHeightIndex(int y){
		return maxHeight/2+y;
	}
	
	/**
	 * Returns the checker at the given position.
	 */
	@Override
	public Checker getChecker(IPoint position) {
		int xIndex=toWidthIndex((int)position.getX());
		int yIndex=toHeightIndex((int)position.getY());
		return map[xIndex][yIndex];
	}
	
	

}


