package htracer.lights;

import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.ShadeRec;

public abstract class Light {

	protected boolean shadows;
	
	public abstract Vector3 getDirection(ShadeRec sr);
	
	public abstract RGBColor L(ShadeRec sr);
	
}
