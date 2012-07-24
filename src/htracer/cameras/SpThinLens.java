package htracer.cameras;

import static htracer.utility.Constants.black;
import htracer.math.Point2;
import htracer.utility.RGBColor;
import htracer.utility.RNG;
import htracer.utility.Ray;
import htracer.world.ViewPlane;
import htracer.world.World;

public class SpThinLens extends ThinLens {

	@Override
	public void renderScene(World world) {

		// RGBColor L = new RGBColor();
		Ray ray = new Ray();
		ViewPlane vp = new ViewPlane(world.vp);
		int depth = 0;

		Point2 sp = new Point2(); // sample point in [0,1]x[0,1]
		Point2 pp = new Point2(); // sample point on a pixel
		Point2 dp = new Point2(); // sample point on unit disk
		Point2 lp = new Point2(); // sample point on lens

		vp.s /= zoom;

		// préparer le sample jump de chaque pixel
		int[] jumps = new int[vp.hres * vp.vres];
		for (int j = 0; j < jumps.length; j++) {
			jumps[j] = RNG.instance().nextInt(vp.sampler.getNumSets())
					* vp.sampler.getNumSamples();
		}

		RGBColor[] pixels = new RGBColor[vp.hres * vp.vres];
		for (int j = 0; j < pixels.length; j++) {
			pixels[j] = new RGBColor();
		}

		System.out.print("Rendering: [");

		int total = vp.hres * vp.vres * vp.numSamples; // total number of ray
														// shots
		int current = 0; // current number of ray shots

		for (int n = 0; n < vp.numSamples; n++) {
			int pixel = 0;
			for (int r = 0; r < vp.vres - 1; r++) // up
				for (int c = 0; c < vp.hres - 1; c++) { // across
				// L.set(black);

					sp.set(vp.sampler.sampleUnitSquare(jumps[pixel], n));
					pp.x = vp.s * (c - vp.hres / 2 + sp.x);
					pp.y = vp.s * (r - vp.vres / 2 + sp.y);

					dp.set(sampler.sampleUnitDisk(jumps[pixel], n));
					lp.set(dp.mul(lensRadius));

					ray.o.set(eye.add(u.mul(lp.x)).add(v.mul(lp.y)));
					ray.d.set(rayDirection(pp, lp));

//					L.sadd(world.tracer.traceRay(ray, depth));
					pixels[pixel].sadd(world.tracer.traceRay(ray, depth));

					current++;
					if (current > (total / 10)) {
						System.out.print(".");
						current -= total / 10;
					}
					
					pixel++;
				}
			
			// update window
			if (world.frame != null) {
				pixel = 0;
				for (int r = 0; r < vp.vres - 1; r++)
					for (int c = 0; c < vp.hres - 1; c++) {
						world.displayPixel(r, c, pixels[pixel++].div(n + 1));
					}
				world.updateWindow(n);
			}
		}

		int pixel = 0;
		for (int r = 0; r < vp.vres - 1; r++)
			for (int c = 0; c < vp.hres - 1; c++) {
				world.displayPixel(r, c, pixels[pixel++].div(vp.numSamples));
			}
		world.updateWindow(vp.numSamples);

	}

}
