package htracer.materials;

import htracer.utility.RGBColor;
import htracer.utility.ShadeRec;
import static htracer.utility.Constants.black;

public abstract class Material {
	
	public abstract void set(Material material);
	
	public RGBColor shade(ShadeRec sr) { return black; }
	
	public RGBColor areLightShade(ShadeRec sr) { return black; }
	
	public RGBColor whittedShade(ShadeRec sr) { return black; }
}
