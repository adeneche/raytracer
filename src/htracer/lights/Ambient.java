package htracer.lights;

import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class Ambient extends Light {

	public float ls;
	public RGBColor color;
	
	public Ambient() {
		super();
		ls = 1;
		color = new RGBColor(1); // white
	}
	
	@Override
	public Vector3 getDirection(ShadeRec sr) {
		return new Vector3();
	}

	@Override
	public RGBColor L(ShadeRec sr) {
		return color.mul(ls);
	}

	@Override
	public boolean inShadow(Ray ray, ShadeRec sr) {
		return false;
	}
	
	

}
