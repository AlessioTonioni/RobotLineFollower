package space;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Utility class to create an IMap from a .jpg file 
 * @author Alessio Tonioni
 *
 */
public class ImageLoader {
	static int lineValue=-16777216; //black
	static int obstacleValue=-256;  //yellow
	static int emptyValue=-1;  //white
	
	/**
	 * Read from "filename" an image and translate it to an int matrix rapresenting the color value of each pixel
	 * @param filename name of the file to load
	 * @return int matrix rapresenting the color value of each pixel of the given image
	 * @throws IOException if the file it's not found or can't be readed
	 */
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
	
	/**
	 * Create a Checker matrix from an integer matrix: translate color value in semantic checker value 
	 * using the static field of the class as a dictionary 
	 * @param pixels matrix to translate
	 * @return matrix with semantic value
	 */
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
