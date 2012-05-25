package htracer.geometric;

import static htracer.utility.Constants.red;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public abstract class GeometricObject {

	public RGBColor color = new RGBColor(red); // only used in chapter 3
	
	public abstract boolean hit(Ray ray, ShadeRec sr);
}
