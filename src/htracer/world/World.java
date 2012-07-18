package htracer.world;

import htracer.cameras.Camera;
import htracer.geometric.GeometricObject;
import htracer.tracers.Tracer;
import htracer.utility.Constants;
import htracer.utility.Image;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public abstract class World {

	public ViewPlane vp;
	public RGBColor backgroundColor;
	public Tracer tracer;
	public Camera camera;

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
		ShadeRec minSr = new ShadeRec(this);
		
		ray.tmin = Constants.kHugeValue;
		// sr.t = Constants.kHugeValue;
		
		for (GeometricObject go : objects) {
			if (go.hit(ray, sr) && (sr.t < ray.tmin)) {
				minSr.set(sr); 
				minSr.hitAnObject = true;
				minSr.color.set(go.color);
				ray.tmin = sr.t;
			}
		}
		
		return minSr;
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
		color.powc(vp.getInvGamma());
		image.set(column, row, color);
	}
	
	public void saveImage(String fileName) throws FileNotFoundException, IOException {
		image.writePNG(new FileOutputStream(fileName));
	}
}
