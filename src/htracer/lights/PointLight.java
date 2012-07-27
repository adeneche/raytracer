package htracer.lights;

import htracer.math.Point3;
import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.RNG;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class PointLight extends Light {

	public float ls;
	public RGBColor color;
	public Point3 location;
	public float radius;
	
	public PointLight(Point3 location, float ls, RGBColor color, float radius) {
		this.location = new Point3(location);
		this.ls = ls;
		this.color = new RGBColor(color);
		this.radius = radius;
	}
	
	@Override
	public Vector3 getDirection(ShadeRec sr) {
		Point3 loc = location;
		if (radius > 0) {
			RNG rng = RNG.instance();
			Point3 randPnt = new Point3(rng.nextFloat(), rng.nextFloat(), rng.nextFloat());
			loc = new Point3(location).add(randPnt.mul(2).sub(Point3.ONE).mul(radius));
		}
		
		return loc.sub(sr.hitPoint).hat();
	}

	@Override
	public RGBColor L(ShadeRec sr) {
		return color.mul(ls);
	}

	@Override
	public boolean inShadow(Ray ray, ShadeRec sr) {
		float d = location.len(ray.o);
		return sr.w.compound.shadowHit(ray, d);
	}

}

