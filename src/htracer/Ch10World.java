package htracer;

import java.io.IOException;

import htracer.cameras.Pinhole;
import htracer.cameras.ThinLens;
import htracer.geometric.Sphere;
import htracer.samplers.MultiJittered;
import htracer.tracers.MultipleObjects;
import htracer.utility.Point3;
import htracer.utility.RGBColor;
import htracer.world.World;
import static htracer.utility.Constants.*;

public class Ch10World extends World {

	@Override
	public void build() {
		int numSamples = 25;
		
		vp.hres = 400;
		vp.vres = 400;
		vp.s = 0.5f;
		vp.setSamples(numSamples);
		
		backgroundColor.set(white);
		
		tracer = new MultipleObjects(this);  
		
		ThinLens thinLens = new ThinLens();
		thinLens.setSampler(new MultiJittered(numSamples));
		thinLens.eye.set(0, 6, 50);
		thinLens.lookat.set(0, 6, 0);
		thinLens.d = 40;
		thinLens.f = 98;
		thinLens.lensRadius = 1;
		thinLens.zoom = 10;
		thinLens.computeUVW();
		
		camera = thinLens;
		
		// colours

		RGBColor yellow = new RGBColor(1, 1, 0);
		RGBColor brown = new RGBColor(0.71f, 0.40f, 0.16f);							// brown
		RGBColor dark_green = new RGBColor(0, 0.41f, 0.41f);						// dark_green
		RGBColor orange = new RGBColor(1, 0.75f, 0);								// orange
		RGBColor green = new RGBColor(0, 0.6f, 0.3f);								// green
		RGBColor light_green = new RGBColor(0.65f, 1, 0.30f);						// light green
		RGBColor dark_yellow = new RGBColor(0.61f, 0.61f, 0);						// dark yellow
		RGBColor light_purple = new RGBColor(0.65f, 0.3f, 1);						// light purple
		RGBColor dark_purple = new RGBColor(0.5f, 0, 1);							// dark purple
		
		// spheres
		addObject(new Sphere(new Point3(-6, 6, -.5f), 6, yellow));
		addObject(new Sphere(new Point3( 1, 7, -24.5f), 7, brown));
		addObject(new Sphere(new Point3( 13, 7.5f, -48.5f), 7.5f, dark_green));
	}

	public static void main(String[] args) {
		World world = new Ch10World();
		world.build();
		world.renderScene();
		
		try {
			world.saveImage("chapter10.3.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
