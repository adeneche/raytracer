package htracer.geometric;

import htracer.materials.Material;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public abstract class GeometricObject {

	protected Material material;
	
	public abstract boolean hit(Ray ray, ShadeRec sr);
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material m) {
		material = m;
	}
}
