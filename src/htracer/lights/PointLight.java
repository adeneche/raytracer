package htracer.lights;

import htracer.geometric.GeometricObject;
import htracer.geometric.GeometricObject.ShadowOut;
import htracer.math.Point3;
import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class PointLight extends Light {

	public float ls;
	public RGBColor color;
	public Point3 location;
	
	public PointLight(Point3 location, float ls, RGBColor color) {
		this.location = new Point3(location);
		this.ls = ls;
		this.color = new RGBColor(color);
	}
	
	@Override
	public Vector3 getDirection(ShadeRec sr) {
		return location.sub(sr.hitPoint).hat();
	}

	@Override
	public RGBColor L(ShadeRec sr) {
		return color.mul(ls);
	}

	@Override
	public boolean inShadow(Ray ray, ShadeRec sr) {
		float d = location.len(ray.o);
		ShadowOut so = new ShadowOut();
		
		for (GeometricObject go : sr.w.objects) {
			if (go.shadowHit(ray, so) && so.t < d)
				return true;
		}
		return false;
	}

}
