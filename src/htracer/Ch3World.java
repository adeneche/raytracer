package htracer;

import static htracer.utility.Constants.*;
import htracer.cameras.ThinLens;
import htracer.geometric.BBox;
import htracer.geometric.Sphere;
import htracer.samplers.MultiJittered;
import htracer.tracers.MultipleObjects;
import htracer.utility.Point3;
import htracer.utility.RGBColor;
import htracer.world.World;

import java.io.IOException;

public class Ch3World extends World {

	@Override
	public void build() {
		
		vp.hres = 400;
		vp.vres = 400;
		vp.s = 0.5f;
		vp.setSamples(25);
		
		backgroundColor.set(white);
		
		MultipleObjects motracer = new MultipleObjects(this);  
		motracer.ambiant = .25f;
		
		tracer = motracer;
		
		ThinLens thinLens = new ThinLens();
		thinLens.setSampler(new MultiJittered(vp.numSamples));
		thinLens.eye.set(75, -125, 100);
		thinLens.lookat.set(20, 0, 0);
		thinLens.d = 40;
		thinLens.f = 280;
		thinLens.lensRadius = 2;
		thinLens.zoom = 2.5f;
		thinLens.computeUVW();
		
		camera = thinLens;
//		camera = new Pinhole();
		
		
		// spheres
		addObject(sphere2box(new Sphere(new Point3(5,3,0), 30, yellow)));
//		addObject(newBBox(-100, 95, 33, 36, -50, 50, brown));
		addObject(sphere2box(new Sphere(new Point3(45, -7, -60), 20, brown)));
		addObject(sphere2box(new Sphere(new Point3(40, 43, -100), 17, dark_green)));
		addObject(sphere2box(new Sphere(new Point3(-20, 28, -15), 20, orange)));
		addObject(sphere2box(new Sphere(new Point3(-25, -7, -35), 27, green)));
		addObject(sphere2box(new Sphere(new Point3(20, -27, -35), 25, light_green)));
		addObject(sphere2box(new Sphere(new Point3(35, 18, -35), 22, green)));
		addObject(sphere2box(new Sphere(new Point3(-57, -17, -50), 15, brown)));
		addObject(sphere2box(new Sphere(new Point3(-47, 16, -80), 23, light_green)));
		addObject(sphere2box(new Sphere(new Point3(-15, -32, -60), 22, dark_green)));
		addObject(sphere2box(new Sphere(new Point3(-35, -37, -80), 22, dark_yellow)));
		addObject(sphere2box(new Sphere(new Point3(10, 43, -80), 22, dark_yellow)));
		addObject(sphere2box(new Sphere(new Point3(30, -7, -80), 10, dark_yellow)));
		addObject(sphere2box(new Sphere(new Point3(-40, 48, -110), 18, dark_green)));
		addObject(sphere2box(new Sphere(new Point3(-10, 53, -120), 18, brown)));
		addObject(sphere2box(new Sphere(new Point3(-55, -52, -100), 10, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(5, -52, -100), 15, brown)));
		addObject(sphere2box(new Sphere(new Point3(-20, -57, -120), 15, dark_purple)));
		addObject(sphere2box(new Sphere(new Point3(55, -27, -100), 17, dark_green)));
		addObject(sphere2box(new Sphere(new Point3(50, -47, -120), 15, brown)));
		addObject(sphere2box(new Sphere(new Point3(70, -42, -150), 10, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(5, 73, -130), 12, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(66, 21, -130), 13, dark_purple)));
		addObject(sphere2box(new Sphere(new Point3(72, -12, -140), 12, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(64, 5, -160), 11, green)));
		addObject(sphere2box(new Sphere(new Point3(55, 38, -160), 12, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(-73, -2, -160), 12, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(30, -62, -140), 15, dark_purple)));
		addObject(sphere2box(new Sphere(new Point3(25, 63, -140), 15, dark_purple)));
		addObject(sphere2box(new Sphere(new Point3(-60, 46, -140), 15, dark_purple)));
		addObject(sphere2box(new Sphere(new Point3(-30, 68, -130), 12, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(58, 56, -180), 11, green)));
		addObject(sphere2box(new Sphere(new Point3(-63, -39, -180), 11, green)));
		addObject(sphere2box(new Sphere(new Point3(46, 68, -200), 10, light_purple)));
		addObject(sphere2box(new Sphere(new Point3(-3, -72, -130), 12, light_purple)));
	}

	private BBox newBBox(int x0, int x1, int y0, int y1, int z0, int z1,
			RGBColor color) {
		BBox bbox = new BBox(x0, x1, y0, y1, z0, z1);
		bbox.color.set(color);
		return bbox;
	}

	static BBox sphere2box(Sphere sphere) {
		Point3 center = sphere.center;
		Point3 size = new Point3(sphere.radius);
		
		BBox bbox = new BBox(new Point3(center.sub(size).toPoint()), new Point3(center.add(size)));
		bbox.color.set(sphere.color);
		
		return bbox;
	}
	
	public static void main(String[] args) {
		World world = new Ch3World();
		world.build();
		world.renderScene();
		
		try {
			world.saveImage("chapter9.2.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
