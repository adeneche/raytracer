package htracer;

import static htracer.utility.Constants.*;
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

public class Ch14World extends World {

	@Override
	public void build() {
		boolean draft = false;
		boolean occlusion = true;
		
		vp.hres = 400;
		vp.vres = 400;
		vp.s = 0.5f;
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
		thinLens.zoom = 3.5f;
		thinLens.computeUVW();
		
		camera = thinLens;
//		camera = new Pinhole();
		
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
		
		int bwidth = 25;
		int num = 4;
		float pad = (200 - num*bwidth) / (num - 1);
		
		for (int r = 0; r < num; r++) {
			for (int c = 0; c < num; c++) {
				float height = (float) (Math.random()*20 + 50);
				float x = r*(bwidth+pad) - 100;
				float z = c*(bwidth+pad) - 100;
				objects.add(newBBox(x, x + bwidth, -height, 0, z, z + bwidth, gray));
			}
		}
	}

	private BBox newBBox(float x0, float x1, float y0, float y1, float z0, float z1,
			RGBColor color) {
		BBox bbox = new BBox(x0, x1, y0, y1, z0, z1);
		bbox.setMaterial(new Matte(.5f, 0.5f, color));
		return bbox;
	}
	
	public static void main(String[] args) {
		World world = new Ch14World();
		world.build();
		world.renderScene();
		
		try {
			world.saveImage("chapter17.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
