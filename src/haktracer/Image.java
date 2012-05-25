package haktracer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;


public class Image {

	private Rgb[][] raster;
	public int nx; // raster width */
	public int ny; // raster height */
	
	public Image() {
		
	}
	
	public Image(int width, int height) {
		nx = width;
		ny = height;
		raster = new Rgb[nx][];
		for (int i = 0; i < nx; i++) {
			raster[i] = new Rgb[ny];
			for (int j = 0; j < ny; j++) {
				raster[i][j] = new Rgb();
			}
		}
	}
	
	public Image(int width, int height, Rgb bg) {
		this(width, height);
		// assign bg to each element
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				raster[i][j].set(bg);
			}
		}
	}
	
	public boolean set(int x, int y, Rgb rgb) {
		if (x < 0 || x >= nx) return false;
		if (y < 0 || y >= ny) return false;
		
		raster[x][y].set(rgb);
		
		return true;
	}
	
	public Rgb get(int x, int y) {
		return raster[x][y];
	}
	
	public void writePNG(OutputStream out) throws IOException {
		BufferedImage image = new BufferedImage(nx, ny, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				int red = (int)(256*raster[i][j].r);
				int green = (int)(256*raster[i][j].g);
				int blue = (int)(256*raster[i][j].b);
				
				if (red > 255) red = 255;
				if (green > 255) green = 255;
				if (blue > 255) blue = 255;
				
				int rgb = red;
				rgb = (rgb << 8) + green;
				rgb = (rgb << 8) + blue;
				
				image.setRGB(i, j, rgb);
			}
		}
		
		ImageIO.write(image, "png", out);
	}
	
	public static Image readPNG(InputStream in) throws IOException {
		BufferedImage bimg = ImageIO.read(in);
		
		Image image = new Image(bimg.getWidth(), bimg.getHeight());

		for (int i = 0; i < image.nx; i++) {
			for (int j = 0; j < image.ny; j++) {
				int rgb = bimg.getRGB(i, j);
				float red = ((rgb >> 16) & 0xFF)/256f;
				float green = ((rgb >> 8) & 0xFF)/256f;
				float blue = (rgb & 0xFF)/256f;
				
				image.set(i, j, new Rgb(red, green, blue));
			}
		}
		
		return image;
	}
}
