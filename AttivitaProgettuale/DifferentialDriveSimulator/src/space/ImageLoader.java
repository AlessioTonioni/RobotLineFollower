package space;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	static int lineValue=-16777216; //black
	static int obstacleValue=-256;  //yellow
	static int emptyValue=-1;  //white
	
	public static int[][] loadImage(String filename) throws IOException{
		int[][] PixelArray;
        BufferedImage bufferimage=ImageIO.read(new File(filename));
        int height=bufferimage.getHeight();
        int width=bufferimage.getWidth();
        PixelArray=new int[width][height];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
            	int color=bufferimage.getRGB(i, j);
                PixelArray[i][j]=color;
            }
        }
        return PixelArray;
	}
	
	public static Checker[][] getMap(int[][] pixels){
		int maxHeight=pixels[0].length;
		int maxWidth=pixels.length;
		Checker[][] result=new Checker[maxWidth][maxHeight];
		for(int i=0; i<maxWidth; i++){
			for(int j=0; j<maxHeight; j++){
				result[i][j]=getChecker(pixels[i][j]);
			}
		}
		return result;
	}

	private static Checker getChecker(int i) {
		if(i==lineValue)
			return Checker.line;
		else if(i==obstacleValue)
			return Checker.obstacle;
		else
			return Checker.empty;
	}

	/*public static void main(String[] args) throws IOException{
		ImageLoader.getMap(ImageLoader.loadImage("prova.jpg"));
	}*/
}
