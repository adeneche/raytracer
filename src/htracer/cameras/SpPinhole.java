package htracer.cameras;

import htracer.math.Point2;
import htracer.utility.RGBColor;
import htracer.utility.RNG;
import htracer.utility.Ray;
import htracer.world.ViewPlane;
import htracer.world.World;

public class SpPinhole extends Pinhole {

	@Override
	public void renderScene(World world) {
//		RGBColor L = new RGBColor();
		ViewPlane vp = new ViewPlane(world.vp);
		Ray ray = new Ray();
		Point2 pp = new Point2();
		Point2 sp; // sample point on a pixel

		vp.s /= zoom;
		ray.o.set(eye);
		
		// préparer le sample jump de chaque pixel
		int[] jumps = new int[vp.hres*vp.vres];
		for (int j = 0; j < jumps.length; j++) {
			jumps[j] = RNG.instance().nextInt(vp.sampler.getNumSets()) * vp.sampler.getNumSamples();
		}
		
		RGBColor[] pixels = new RGBColor[vp.hres*vp.vres];
		for (int j = 0; j < pixels.length; j++) {
			pixels[j] = new RGBColor();
		}

		System.out.print("Rendering: [");

		int total = vp.hres * vp.vres * vp.numSamples; // total number of ray
														// shots
		int current = 0; // current number of ray shots

		for (int j = 0; j < vp.numSamples; j++) {
			int pixel = 0;
			for (int r = 0; r < vp.vres; r++)
				// up
				for (int c = 0; c < vp.hres; c++) { // across
					sp = vp.sampler.sampleUnitSquare(jumps[pixel], j);
					pp.x = vp.s * (c - .5f * vp.hres + sp.x);
					pp.y = vp.s * (r - .5f * vp.vres + sp.y);
					ray.d.set(getDirection(pp));
					pixels[pixel].sadd(world.tracer.traceRay(ray));

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
				for (int r = 0; r < vp.vres; r++)
					for (int c = 0; c < vp.hres; c++) {
						world.displayPixel(r, c, pixels[pixel++].div(j + 1));
					}
				world.updateWindow(j);
			}
		}

		int pixel = 0;
		for (int r = 0; r < vp.vres; r++)
			for (int c = 0; c < vp.hres; c++) {
				world.displayPixel(r, c, pixels[pixel++].div(vp.numSamples));
			}
		world.updateWindow(vp.numSamples);

	}

	
}
