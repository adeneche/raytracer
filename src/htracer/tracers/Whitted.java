package htracer.tracers;

import static htracer.utility.Constants.*;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;
import htracer.world.World;

public class Whitted extends Tracer {
	
	public Whitted(World wr) {
		super(wr);
	}
	
	@Override
	public RGBColor traceRay(Ray ray) {
		return traceRay(ray, 0);
	}

	@Override
	public RGBColor traceRay(Ray ray, int depth) {
		if (world.vp.maxDepth != -1 && depth > world.vp.maxDepth)
			return black;
		
		ShadeRec sr = new ShadeRec(world);
		
		if (world.compound.hit(ray, sr, kHugeValue)) {
			sr.depth = depth;
			sr.ray.set(ray);
			
			return sr.material.shade(sr);
		} else {
			return world.backgroundColor;
		}
	}

}
