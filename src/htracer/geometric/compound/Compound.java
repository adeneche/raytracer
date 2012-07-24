package htracer.geometric.compound;

import java.util.List;
import java.util.Vector;

import htracer.geometric.GeometricObject;
import htracer.materials.Material;
import htracer.utility.Normal;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;
import static htracer.utility.Constants.kHugeValue;

public class Compound extends GeometricObject {

	protected List<GeometricObject> objects;
	
	public Compound() {
		objects = new Vector<GeometricObject>();
	}
	
	public Compound(Compound c) {
		objects.addAll(c.objects);
	}
	
	public int size() {
		return objects.size();
	}
	
	public void add(GeometricObject obj) {
		objects.add(obj);
	}
	
	public void clear() {
		objects.clear();
	}
	
	public void setMaterial(Material mat) {
		for (GeometricObject obj : objects) {
			obj.setMaterial(mat);
		}
	}
	
	@Override
	public boolean hit(Ray ray, ShadeRec sr) {
		Normal normal = new Normal();
		boolean	hit = false;
		float tmin = kHugeValue;
		
		for (GeometricObject obj : objects)
			if (obj.hit(ray, sr) && (sr.t < tmin)) {
				hit	= true;
				tmin = sr.t;
				sr.material	= obj.getMaterial();
				normal.set(sr.normal);
			}
		
		if (hit) {
			sr.t = tmin;
			sr.normal.set(normal);
			sr.hitPoint.set(ray.o.add(ray.d.mul(tmin)));
		}
		
		return (hit);
	}

	@Override
	public boolean shadowHit(Ray ray, ShadowOut so, float dist) {

		for (GeometricObject obj : objects)
			if (obj.shadowHit(ray, so, dist)) {
				return true;
			}
		
		return false;
	}

}
