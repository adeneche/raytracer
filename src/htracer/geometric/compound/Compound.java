package htracer.geometric.compound;

import java.util.List;
import java.util.Vector;

import htracer.geometric.BBox;
import htracer.geometric.GeometricObject;
import htracer.materials.Material;
import htracer.utility.Normal;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;
import static htracer.utility.Constants.kHugeValue;

public class Compound extends GeometricObject {

	protected List<GeometricObject> objects;
	protected BBox bbox;
	
	public Compound() {
		objects = new Vector<GeometricObject>();
		bbox = new BBox();
	}
	
	public Compound(Compound c) {
		for (GeometricObject obj : c.objects) 
			add(obj);
	}
	
	public int size() {
		return objects.size();
	}
	
	public void add(GeometricObject obj) {
		objects.add(obj);
		BBox obb = obj.getBoundingBox();
		if (obb.x0 < bbox.x0) bbox.x0 = obb.x0;
		if (obb.x1 > bbox.x1) bbox.x1 = obb.x1;
		if (obb.y0 < bbox.y0) bbox.y0 = obb.y0;
		if (obb.y1 > bbox.y1) bbox.y1 = obb.y1;
		if (obb.z0 < bbox.z0) bbox.z0 = obb.z0;
		if (obb.z1 > bbox.z1) bbox.z1 = obb.z1;
	}
	
	
	@Override
	public BBox getBoundingBox() {
		return bbox;
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
	public boolean shadowHit(Ray ray, float dist) {

		for (GeometricObject obj : objects)
			if (obj.shadowHit(ray, dist)) {
				return true;
			}
		
		return false;
	}

}
