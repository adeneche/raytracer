package htracer.world;

import htracer.Point2;
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
import static htracer.utility.Constants.black;

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
		System.out.print("Rendering: [");
		long start = System.currentTimeMillis();
		
		RGBColor pixelColor = new RGBColor();
		Ray ray = new Ray();
		float zw = 100;
		Point2 pp = new Point2();
		Point2 sp;
		
		openWindow(vp.hres, vp.vres);
		ray.d.set(0,0,-1);
		
		int total = vp.hres*vp.vres*vp.numSamples; // total number of ray shots
		int current = 0; // current number of ray shots
		
		for (int r = 0; r < vp.vres; r++) { // up
			for (int c = 0; c < vp.hres; c++) { // across
				pixelColor.set(black);
				
				for (int j = 0; j < vp.numSamples; j++) {
					sp = vp.sampler.sampleUnitSquare();
					pp.x = vp.s * (c - .5f * vp.hres + sp.x);
					pp.y = vp.s * (r - .5f * vp.vres + sp.y);
					ray.o.set(pp.x, pp.y, zw);
					pixelColor.sadd(tracer.traceRay(ray));

					current++;
				}
				
				pixelColor.sdiv(vp.numSamples); // average the colors
				displayPixel(r, c, pixelColor);
				
				if (current > (total/10)) {
					System.out.print(".");
					current -= total/10;
				}
			}
		}
		
		System.out.println("]. Done in " + (System.currentTimeMillis()-start)/1000 + "s");
	}
	
	public void openWindow(int hres, int vres) {
		image = new Image(hres, vres);
	}
	
	public void displayPixel(int row, int column, RGBColor color) {
		color.powc(vp.getInvGamma());
		image.set(column, row, color);
	}
	
	public void saveImage(String fileName) throws FileNotFoundException, IOException {
		image.writePNG(new FileOutputStream(fileName));
	}
}
