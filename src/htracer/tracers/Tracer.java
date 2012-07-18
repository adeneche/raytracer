package htracer.tracers;

import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.world.World;

public abstract class Tracer {

	protected World world;
	
	public Tracer(World wr) {
		world = wr;
	}
	
	public abstract RGBColor traceRay(Ray ray);

	public RGBColor traceRay(Ray ray, int depth) {
		if (world.vp.maxDepth != -1 && depth > world.vp.maxDepth)
			return world.backgroundColor ;
		return traceRay(ray);
	}
}
