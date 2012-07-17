package htracer.tracers;

import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;
import htracer.world.World;

public class MultipleObjects extends Tracer {

	public MultipleObjects(World wr) {
		super(wr);
	}
	
	@Override
	public RGBColor traceRay(Ray ray) {
		ShadeRec sr = world.hitBareBonesObjects(ray);
		
		if (sr.hitAnObject) {
			return sr.color;
		} else {
			return world.backgroundColor;
		}
	}

	@Override
	public RGBColor traceRay(Ray ray, int depth) {
		if (world.vp.maxDepth != -1 && depth > world.vp.maxDepth)
			return world.backgroundColor ;
		return traceRay(ray);
	}

}
