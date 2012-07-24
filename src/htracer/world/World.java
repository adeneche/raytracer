package htracer.world;

import htracer.cameras.Camera;
import htracer.geometric.compound.Compound;
import htracer.lights.Ambient;
import htracer.lights.Light;
import htracer.tracers.Tracer;
import htracer.utility.Image;
import htracer.utility.RGBColor;

import java.awt.Canvas;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public abstract class World {

	public ViewPlane vp;
	public RGBColor backgroundColor;
	public Tracer tracer;
	public Camera camera;

	public Light ambient;
	public List<Light> lights;
	
	public Compound compound;
	
	protected Image image;
	public JFrame frame;
	
	public World() {
		super();
		
		vp = new ViewPlane();
		backgroundColor = new RGBColor();
		ambient = new Ambient();
		lights = new Vector<Light>();
		compound = new Compound();
	}
	
	public abstract void build();
	
	public void openWindow(int hres, int vres, boolean useFrame) {
		image = new Image(hres, vres);
		
		if (useFrame) {
			frame = new JFrame("Hak Tracer") {

				@Override
				public void paint(Graphics g) {
					g.drawImage(image.toBufferedImage(), 0, 22, null);
				}
				
			};
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(hres, vres+22);
			frame.setVisible(true);
		}
	}
	
	public void updateWindow(int sample) {
		if (frame != null) {
			frame.repaint();
			int progress = (sample*100) / vp.numSamples;
			frame.setTitle("Hak Tracer ("+progress+"%)");
		}
	}
	
	public void renderScene(boolean useFrame) {
		openWindow(vp.hres, vp.vres, useFrame);
		camera.computeUVW();
		camera.renderScene(this);
	}
	
	public void displayPixel(int row, int column, RGBColor color) {
		RGBColor mappedColor = max2one(color);
		
		if (vp.getGamma() != 1)
			mappedColor.powc(vp.getInvGamma());
		
		image.set(column, row, mappedColor);
	}
	
	private RGBColor max2one(final RGBColor c) {
		float maxValue = Math.max(c.r, Math.max(c.g, c.b));
		if (maxValue > 1)
			return c.mul(1/maxValue);
		else 
			return c;
	}
	
	public void saveImage(String fileName) throws FileNotFoundException, IOException {
		image.writePNG(new FileOutputStream(fileName));
	}
}
