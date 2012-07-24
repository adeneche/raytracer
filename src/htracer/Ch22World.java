package htracer;

import static htracer.utility.Constants.*;
import htracer.cameras.Pinhole;
import htracer.cameras.ThinLens;
import htracer.geometric.BBox;
import htracer.lights.AmbiantOccluder;
import htracer.lights.Light;
import htracer.lights.PointLight;
import htracer.materials.Matte;
import htracer.math.Point3;
import htracer.samplers.MultiJittered;
import htracer.tracers.RayCast;
import htracer.utility.RGBColor;
import htracer.world.World;

import java.io.IOException;

public class Ch22World extends World {
	static boolean draft = false;
	static boolean occlusion = true;
	static long duration;
	
	@Override
	public void build() {
		
		vp.hres = draft ? 400:800;
		vp.vres = draft ? 300:600;
		vp.s = draft ? 1:0.5f;
		vp.setSamples(draft ? 1:64);
		
		backgroundColor.set(white.mul(.75f));
		
		tracer = new RayCast(this);  
		
		ThinLens thinLens = new ThinLens();
		thinLens.setSampler(new MultiJittered(vp.numSamples));
		thinLens.eye.set(75, -125, 150);
		thinLens.lookat.set(0, 0, 0);
		thinLens.d = 40;
		thinLens.f = 125;
		thinLens.lensRadius = draft ? 0:2;
		thinLens.zoom = 6;
		
		Pinhole pinhole = new Pinhole();
		pinhole.eye.set(75, -125, 150);
		pinhole.lookat.set(0, 0, 0);
		pinhole.d = 40;
		pinhole.zoom = 6;
		
//		camera = thinLens;
		camera = pinhole;
		
		// LIGHTS
		Light.globalShadows = !draft;
		lights.add(new PointLight(new Point3(0, -125, 150), 4f, white));
		
		if (!draft && occlusion) {
			AmbiantOccluder ao = new AmbiantOccluder();
			ao.setSamples(25);
			ambient = ao;
		}
		
		// OBJECTS
		
		objects.add(newBBox(-100, 100, 0, 3, -100, 100, gray));
		
		int bwidth = 5;
		int num = 10;
		float pad = (200 - num*bwidth) / (num - 1);
		
		//TODO générer chaque box aléatoirement dans son carré (multi-jittering)
		for (int r = 0; r < num; r++) {
			for (int c = 0; c < num; c++) {
				float height = 50; //(float) (Math.random()*20 + 50);
				float x = r*(bwidth+pad) - 100;
				float z = c*(bwidth+pad) - 100;
				objects.add(newBBox(x, x + bwidth, -height, 0, z, z + bwidth, gray));
			}
		}
	}

	
	@Override
	public void renderScene() {
		long start = System.currentTimeMillis();

		super.renderScene();

		duration = (System.currentTimeMillis() - start) / 1000;
		System.out.println("]. Done in " + duration + "s");
	}


	private BBox newBBox(float x0, float x1, float y0, float y1, float z0, float z1,
			RGBColor color) {
		BBox bbox = new BBox(x0, x1, y0, y1, z0, z1);
		bbox.setMaterial(new Matte(.5f, 0.5f, color));
		return bbox;
	}
	
	public static void main(String[] args) {
		World world = new Ch22World();
		world.build();
		world.renderScene();
		
		try {
			world.saveImage("chapter22p2." + (draft ? "d": (occlusion ? "o":"")) + "(" + duration + "s).png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
