package htracer;

import static htracer.utility.Constants.*;
import htracer.cameras.Pinhole;
import htracer.cameras.SpPinhole;
import htracer.cameras.SpThinLens;
import htracer.cameras.ThinLens;
import htracer.geometric.BBox;
import htracer.geometric.compound.Grid;
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
	static boolean useFrame = false;
	
	@Override
	public void build() {
		
		vp.hres = draft ? 400:800;
		vp.vres = draft ? 300:600;
		vp.s = draft ? 1:0.5f;
		vp.setSamples(draft ? 1:64);
		
		backgroundColor.set(white.mul(.75f));
		
		tracer = new RayCast(this);  
		
		ThinLens thinLens = new SpThinLens();
		thinLens.setSampler(new MultiJittered(vp.numSamples));
		thinLens.eye.set(75, -125, 150);
		thinLens.lookat.set(0, 0, 0);
		thinLens.d = 40;
		thinLens.f = 125;
		thinLens.lensRadius = draft ? 0:2;
		thinLens.zoom = 6;
		camera = thinLens;
		
//		Pinhole pinhole = new SpPinhole();
//		pinhole.eye.set(75, -125, 150);
//		pinhole.lookat.set(0, 0, 0);
//		pinhole.d = 40;
//		pinhole.zoom = 6;
//		camera = pinhole;
		
		
		// LIGHTS
		Light.globalShadows = !draft;
		lights.add(new PointLight(new Point3(0, -125, 150), 4f, white));
		
		if (!draft && occlusion) {
			AmbiantOccluder ao = new AmbiantOccluder();
			ao.setSamples(25);
			ambient = ao;
		}
		
		// OBJECTS
		compound = new Grid();
		
		compound.add(newBBox(-100, 100, 0, 3, -100, 100, gray));
		
		int bwidth = 10;
		int num = 10;
		float pad = (200 - num*bwidth) / (num - 1);
		
		//TODO g�n�rer chaque box al�atoirement dans son carr� (multi-jittering)
		for (int r = 0; r < num; r++) {
			for (int c = 0; c < num; c++) {
				float height = (float) (Math.random()*20 + 50);
				float x = r*(bwidth+pad) - 100;
				float z = c*(bwidth+pad) - 100;
				compound.add(newBBox(x, x + bwidth, -height, 0, z, z + bwidth, gray));
			}
		}
		
		System.out.println("Seting up cells...");
		((Grid)compound).setupCells();
	}

	
	@Override
	public void renderScene(boolean useFrame) {
		long start = System.currentTimeMillis();

		super.renderScene(useFrame);

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
		world.renderScene(!draft && useFrame);
		
		try {
			world.saveImage("chapter22." + (draft ? "d": (occlusion ? "o":"")) + "(" + duration + "s).png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (world.frame == null) System.exit(0);
	}
}
