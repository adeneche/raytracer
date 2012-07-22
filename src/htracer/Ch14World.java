package htracer;

import static htracer.utility.Constants.brown;
import static htracer.utility.Constants.white;
import htracer.cameras.ThinLens;
import htracer.geometric.BBox;
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
		boolean draft = true;
		
		vp.hres = 400;
		vp.vres = 400;
		vp.s = 0.5f;
		vp.setSamples(draft ? 1:25);
		
		backgroundColor.set(white.mul(.75f));
		
		tracer = new RayCast(this);  
		
		ThinLens thinLens = new ThinLens();
		thinLens.setSampler(new MultiJittered(vp.numSamples));
		thinLens.eye.set(75, -125, 150);
		thinLens.lookat.set(0, 0, 0);
		thinLens.d = 40;
		thinLens.f = 125;
		thinLens.lensRadius = draft ? 0:1;
		thinLens.zoom = 3.5f;
		thinLens.computeUVW();
		
		camera = thinLens;
//		camera = new Pinhole();
		
		// LIGHTS
		lights.add(new PointLight(new Point3(0, -125, 150), 4f, white));
		
		// OBJECTS
		
		objects.add(newBBox(-100, 100, 0, 3, -100, 100, brown));
		
		int bwidth = 5;
		int num = 20;
		float pad = (200 - num*bwidth) / (num - 1);
		
		for (int r = 0; r < num; r++) {
			for (int c = 0; c < num; c++) {
				float height = (float) (Math.random()*20 + 50);
				float x = r*(bwidth+pad) - 100;
				float z = c*(bwidth+pad) - 100;
				objects.add(newBBox(x, x + bwidth, -height, 0, z, z + bwidth, 
					new RGBColor((float)Math.random(), (float)Math.random(), (float)Math.random())));
			}
		}
	}

	private BBox newBBox(float x0, float x1, float y0, float y1, float z0, float z1,
			RGBColor color) {
		BBox bbox = new BBox(x0, x1, y0, y1, z0, z1);
		bbox.setMaterial(new Matte(.2f, 0.8f, color));
		return bbox;
	}
	
	public static void main(String[] args) {
		World world = new Ch14World();
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
