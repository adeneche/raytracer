package htracer.geometric;

import htracer.materials.Material;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public abstract class GeometricObject {

	protected Material material;
	
	public boolean shadows = true;
	
	public abstract boolean hit(Ray ray, ShadeRec sr, float dist);
	
	public boolean shadowHit(Ray ray, float dist) {
		return hit(ray, null, dist);
	}
	
	public BBox getBoundingBox() {
		return new BBox();
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material m) {
		material = m;
	}
}
