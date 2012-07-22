package htracer.cameras;

import htracer.math.Point2;
import htracer.math.Vector3;
import htracer.utility.Ray;
import htracer.utility.RGBColor;
import htracer.world.ViewPlane;
import htracer.world.World;
import static htracer.utility.Constants.black;

public class Pinhole extends Camera {

	public float d; // view plane distance
	public float zoom; // zoom factor

	public Pinhole() {
		super();
		d = 500;
		zoom = 1;
	}

	@Override
	public void renderScene(World world) {

		RGBColor L = new RGBColor();
		ViewPlane vp = new ViewPlane(world.vp);
		Ray ray = new Ray();
		Point2 pp = new Point2();
		Point2 sp; // sample point on a pixel

		vp.s /= zoom;
		ray.o.set(eye);

		System.out.print("Rendering: [");
		long start = System.currentTimeMillis();
		int total = vp.hres * vp.vres * vp.numSamples; // total number of ray
														// shots
		int current = 0; // current number of ray shots

		for (int r = 0; r < vp.vres; r++)
			// up
			for (int c = 0; c < vp.hres; c++) { // across
				L.set(black);

				for (int j = 0; j < vp.numSamples; j++) {
					sp = vp.sampler.sampleUnitSquare();
					pp.x = vp.s * (c - .5f * vp.hres + sp.x);
					pp.y = vp.s * (r - .5f * vp.vres + sp.y);
					ray.d.set(getDirection(pp));
					L.sadd(world.tracer.traceRay(ray));

					current++;
				}

				L.sdiv(vp.numSamples); // average the colors
				world.displayPixel(r, c, L);

				if (current > (total / 10)) {
					System.out.print(".");
					current -= total / 10;
				}
			}

		System.out.println("]. Done in " + (System.currentTimeMillis() - start) / 1000 + "s");
	}

	public Vector3 getDirection(Point2 p) {
		// Vector3 dir = p.x * u + p.y * v - d * w;
		Vector3 dir = u.mul(p.x).add(v.mul(p.y)).sub(w.mul(d));
		dir.normalize();

		return dir;
	}
}
