package htracer.tracers;

import static htracer.utility.Constants.*;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;
import htracer.world.World;

public class RayCast extends Tracer {
	
	public RayCast(World wr) {
		super(wr);
	}
	
	@Override
	public RGBColor traceRay(Ray ray) {
		ShadeRec sr = new ShadeRec(world);
		
		if (world.compound.hit(ray, sr, kHugeValue)) {
			sr.ray.set(ray);
			return sr.material.shade(sr);
		} else {
			return world.backgroundColor;
		}
	}

	@Override
	public RGBColor traceRay(Ray ray, int depth) {
		ShadeRec sr = new ShadeRec(world);
		
		if (world.compound.hit(ray, sr, kHugeValue)) {
			sr.ray.set(ray);
			return sr.material.shade(sr);
		} else {
			return world.backgroundColor;
		}
	}

}
