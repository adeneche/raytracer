package htracer.world;

import htracer.cameras.Camera;
import htracer.geometric.compound.Compound;
import htracer.lights.Ambient;
import htracer.lights.Light;
import htracer.tracers.Tracer;
import htracer.utility.Image;
import htracer.utility.RGBColor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public abstract class World extends Compound {

	public ViewPlane vp;
	public RGBColor backgroundColor;
	public Tracer tracer;
	public Camera camera;

	public Light ambient;
	public List<Light> lights;
	
	private Image image;
	
	public World() {
		super();
		
		vp = new ViewPlane();
		backgroundColor = new RGBColor();
		ambient = new Ambient();
		lights = new Vector<Light>();
	}
	
	public abstract void build();
	
	public void openWindow(int hres, int vres) {
		image = new Image(hres, vres);
	}
	
	public void renderScene() {
		openWindow(vp.hres, vp.vres);
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
