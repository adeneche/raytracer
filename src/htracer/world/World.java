package htracer.world;

import htracer.geometric.GeometricObject;
import htracer.tracers.Tracer;
import htracer.utility.Constants;
import htracer.utility.Image;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public abstract class World {

	public ViewPlane vp;
	public RGBColor backgroundColor;
	public Tracer tracer;

	public Collection<GeometricObject> objects;
	
	private Image image;
	
	public World() {
		vp = new ViewPlane();
		backgroundColor = new RGBColor();
		objects = new Vector<GeometricObject>();
	}
	
	public void addObject(GeometricObject go) {
		objects.add(go);
	}
	
	public ShadeRec hitBareBonesObjects(Ray ray) {
		ShadeRec sr = new ShadeRec(this);
		
		ray.tmin = Constants.kHugeValue;
		// sr.t = Constants.kHugeValue;
		
		for (GeometricObject go : objects) {
			if (go.hit(ray, sr) && (sr.t < ray.tmin)) {
				sr.hitAnObject = true;
				ray.tmin = sr.t;
				sr.color.set(go.color);
			}
		}
		
		return sr;
	}
	
	public abstract void build();
	
	public void renderScene() {
		RGBColor pixelColor;
		Ray ray = new Ray();
		float zw = 100;
		float x,y;
		
		openWindow(vp.hres, vp.vres);
		ray.d.set(0,0,-1);
		
		for (int r = 0; r < vp.vres; r++) { // up
			for (int c = 0; c < vp.hres; c++) { // across
				x = vp.s * (c - .5f * (vp.hres - 1));
				y = vp.s * (r - .5f * (vp.vres - 1));
				ray.o.set(x, y, zw);
				pixelColor = tracer.traceRay(ray);
				displayPixel(r, c, pixelColor);
			}
		}
		
		try {
			image.writePNG(new FileOutputStream("image1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openWindow(int hres, int vres) {
		image = new Image(hres, vres);
	}
	
	public void displayPixel(int row, int column, RGBColor color) {
		color.powc(vp.getInvGamma());
		image.set(column, row, color);
	}
}
