package htracer;

import htracer.geometric.Sphere;
import htracer.tracers.MultipleObjects;
import htracer.utility.Point3;
import htracer.utility.RGBColor;
import htracer.world.World;
import static htracer.utility.Constants.*;

public class Ch3World extends World {

	@Override
	public void build() {
		vp.hres = 400;
		vp.vres = 400;
		vp.s = 0.5f;
		
		backgroundColor.set(black);
		
		tracer = new MultipleObjects(this);  
		
		// colours

		RGBColor yellow = new RGBColor(1, 1, 0);
		RGBColor brown = new RGBColor(0.71f, 0.40f, 0.16f);								// brown
		RGBColor dark_green = new RGBColor(0, 0.41f, 0.41f);							// dark_green
		RGBColor orange = new RGBColor(1, 0.75f, 0);								// orange
		RGBColor green = new RGBColor(0, 0.6f, 0.3f);									// green
		RGBColor light_green = new RGBColor(0.65f, 1, 0.30f);							// light green
		RGBColor dark_yellow = new RGBColor(0.61f, 0.61f, 0);							// dark yellow
		RGBColor light_purple = new RGBColor(0.65f, 0.3f, 1);							// light purple
		RGBColor dark_purple = new RGBColor(0.5f, 0, 1);							// dark purple
		
		// spheres
		addObject(new Sphere(new Point3(5,3,0), 30, yellow));
		addObject(new Sphere(new Point3(45, -7, -60), 20, brown));
		addObject(new Sphere(new Point3(40, 43, -100), 17, dark_green));
		addObject(new Sphere(new Point3(-20, 28, -15), 20, orange));
		addObject(new Sphere(new Point3(-25, -7, -35), 27, green));
		addObject(new Sphere(new Point3(20, -27, -35), 25, light_green));
		addObject(new Sphere(new Point3(35, 18, -35), 22, green));
		addObject(new Sphere(new Point3(-57, -17, -50), 15, brown));
		addObject(new Sphere(new Point3(-47, 16, -80), 23, light_green));
		addObject(new Sphere(new Point3(-15, -32, -60), 22, dark_green));
		addObject(new Sphere(new Point3(-35, -37, -80), 22, dark_yellow));
		addObject(new Sphere(new Point3(10, 43, -80), 22, dark_yellow));
		addObject(new Sphere(new Point3(30, -7, -80), 10, dark_yellow));
		addObject(new Sphere(new Point3(-40, 48, -110), 18, dark_green));
		addObject(new Sphere(new Point3(-10, 53, -120), 18, brown));
		addObject(new Sphere(new Point3(-55, -52, -100), 10, light_purple));
		addObject(new Sphere(new Point3(5, -52, -100), 15, brown));
		addObject(new Sphere(new Point3(-20, -57, -120), 15, dark_purple));
		addObject(new Sphere(new Point3(55, -27, -100), 17, dark_green));
		addObject(new Sphere(new Point3(50, -47, -120), 15, brown));
		addObject(new Sphere(new Point3(70, -42, -150), 10, light_purple));
		addObject(new Sphere(new Point3(5, 73, -130), 12, light_purple));
		addObject(new Sphere(new Point3(66, 21, -130), 13, dark_purple));
		addObject(new Sphere(new Point3(72, -12, -140), 12, light_purple));
		addObject(new Sphere(new Point3(64, 5, -160), 11, green));
		addObject(new Sphere(new Point3(55, 38, -160), 12, light_purple));
		addObject(new Sphere(new Point3(-73, -2, -160), 12, light_purple));
		addObject(new Sphere(new Point3(30, -62, -140), 15, dark_purple));
		addObject(new Sphere(new Point3(25, 63, -140), 15, dark_purple));
		addObject(new Sphere(new Point3(-60, 46, -140), 15, dark_purple));
		addObject(new Sphere(new Point3(-30, 68, -130), 12, light_purple));
		addObject(new Sphere(new Point3(58, 56, -180), 11, green));
		addObject(new Sphere(new Point3(-63, -39, -180), 11, green));
		addObject(new Sphere(new Point3(46, 68, -200), 10, light_purple));
		addObject(new Sphere(new Point3(-3, -72, -130), 12, light_purple));
	}

	public static void main(String[] args) {
		World world = new Ch3World();
		world.build();
		world.renderScene();
		
		System.exit(0);
	}
}
