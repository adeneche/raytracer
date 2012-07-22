package htracer.geometric;

import htracer.materials.Material;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public abstract class GeometricObject {

	protected Material material;
	
	public boolean shadows = true;
	
	public abstract boolean hit(Ray ray, ShadeRec sr);
	public abstract boolean shadowHit(Ray ray, ShadowOut so);
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material m) {
		material = m;
	}

	public static class ShadowOut {
		public float t;
	}
}
