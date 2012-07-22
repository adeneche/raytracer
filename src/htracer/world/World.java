package htracer.world;

import htracer.cameras.Camera;
import htracer.geometric.GeometricObject;
import htracer.lights.Ambient;
import htracer.lights.Light;
import htracer.math.Point3;
import htracer.tracers.Tracer;
import htracer.utility.Constants;
import htracer.utility.Image;
import htracer.utility.Normal;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public abstract class World {

	public ViewPlane vp;
	public RGBColor backgroundColor;
	public Tracer tracer;
	public Camera camera;

	public Light ambient;
	public List<Light> lights;
	
	public Collection<GeometricObject> objects;
	
	private Image image;
	
	public World() {
		vp = new ViewPlane();
		backgroundColor = new RGBColor();
		objects = new Vector<GeometricObject>();
		ambient = new Ambient();
		lights = new Vector<Light>();
	}
	
	public ShadeRec hitObjects(Ray ray) {
		ShadeRec sr = new ShadeRec(this);
		float tmin = Constants.kHugeValue;
		Normal normal = new Normal();
		Point3 localHitPoint = new Point3();
		
		for (GeometricObject go : objects) {
			if (go.hit(ray, sr) && (sr.t < tmin)) {
				sr.hitAnObject = true;
				tmin = sr.t;
				sr.material = go.getMaterial();
				sr.hitPoint.set(ray.o.add(ray.d.mul(sr.t)));
				normal.set(sr.normal);
				localHitPoint.set(sr.localHitPoint);
			}
		}
		
		if (sr.hitAnObject) {
			sr.t = tmin;
			sr.normal.set(normal);
			sr.localHitPoint.set(localHitPoint);
		}
		
		return sr;
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
