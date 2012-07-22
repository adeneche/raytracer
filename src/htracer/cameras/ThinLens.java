package htracer.cameras;

import static htracer.utility.Constants.black;
import htracer.math.Point2;
import htracer.math.Vector3;
import htracer.samplers.Sampler;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.world.ViewPlane;
import htracer.world.World;

public class ThinLens extends Camera {

	private Sampler sampler;	// sampler object
	public float lensRadius; 	// lens radius
	public float d;			// view plane distance
	public float f;			// focal plane distance
	public float zoom;			// zoom factor
	
	@Override
	public void renderScene(World world) {
		RGBColor L = new RGBColor();
		Ray ray = new Ray();
		ViewPlane vp = new ViewPlane(world.vp);
		int depth = 0;
		
		Point2 sp = new Point2(); // sample point in [0,1]x[0,1]
		Point2 pp = new Point2(); // sample point on a pixel
		Point2 dp = new Point2(); // sample point on unit disk
		Point2 lp = new Point2(); // sample point on lens
		
		vp.s /= zoom;

		System.out.print("Rendering: [");
		long start = System.currentTimeMillis();
		int total = vp.hres * vp.vres * vp.numSamples; // total number of ray shots
		int current = 0; // current number of ray shots

		for (int r = 0; r < vp.vres - 1; r++) // up
			for (int c = 0; c < vp.hres - 1; c++) { // across
				L.set(black);
				
				for (int n = 0; n < vp.numSamples; n++) {
					sp.set(vp.sampler.sampleUnitSquare());
					pp.x = vp.s * (c - vp.hres / 2 + sp.x);
					pp.y = vp.s * (r - vp.vres / 2 + sp.y);
					
					dp.set(sampler.sampleUnitDisk());
					lp.set(dp.mul(lensRadius));
					
					ray.o.set(eye.add( u.mul(lp.x)).add( v.mul(lp.y)));
					ray.d.set( rayDirection(pp, lp));
					
					L.sadd(world.tracer.traceRay(ray, depth));

					current++;
				}
				
				L.sdiv(vp.numSamples);
				L.smul(exposureTime);
				world.displayPixel(r, c, L);

				if (current > (total / 10)) {
					System.out.print(".");
					current -= total / 10;
				}
			}

		System.out.println("]. Done in " + (System.currentTimeMillis() - start)
				/ 1000 + "s");

	}

	public void setSampler(Sampler sampler) {
		this.sampler = sampler;
		sampler.mapSamplesToUnitDisk();
	}
	
	public Vector3 rayDirection(Point2 pixelPoint, Point2 lensPoint) {
		Point2 p = pixelPoint.mul(f / d); // hit point on focal plane

		Vector3 dir = u.mul(p.x - lensPoint.x).add(v.mul(p.y - lensPoint.y)).sub(w.mul(f));
		dir.normalize();

		return dir;
	}
}
