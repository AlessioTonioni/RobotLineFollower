package space;

public class twoDMap implements IMap{
	private int maxWidth;
	private int maxHeight;
	private Checker[][] map;
	
	//the map is a square of maxSize side, the origin of the system is in (maxSize/2,maxSize/2)
	public twoDMap(int maxSize){ 
		this.maxWidth=maxSize;
		this.maxHeight=maxSize;
		map=new Checker[maxSize][maxSize];
		for(int i=0; i<maxSize; i++){
			for(int j=0; j<maxSize; j++)
				map[i][j]=Checker.empty;
		}
	}
	
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
	
	@Override
	public Checker getChecker(IPoint position) {
		int xIndex=toWidthIndex((int)position.getX());
		int yIndex=toHeightIndex((int)position.getY());
		return map[xIndex][yIndex];
	}
	
	

}


