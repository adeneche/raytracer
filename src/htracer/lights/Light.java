package htracer.lights;

import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public abstract class Light {
	public static boolean globalShadows = true;
	
	protected boolean shadows = true;
	
	public boolean castShadows() { return globalShadows && shadows; }
	
	public abstract Vector3 getDirection(ShadeRec sr);
	
	public abstract RGBColor L(ShadeRec sr);
	
	public abstract boolean inShadow(final Ray ray, final ShadeRec sr);
}
