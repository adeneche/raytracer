package htracer.geometric;

import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class Function extends GeometricObject {

	@Override
	public boolean hit(Ray ray, ShadeRec sr) {
		sr.t = 0;
		
		float x = ray.o.x + sr.w.vp.hres * .5f * sr.w.vp.s;
		float y = ray.o.y + sr.w.vp.vres * .5f * sr.w.vp.s;
		float v = (float) (.5 * (1 + Math.sin(x*x*y*y)));
		color.set(v, v, v);
		return true;
	}

}
