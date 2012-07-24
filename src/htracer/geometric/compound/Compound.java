package htracer.geometric.compound;

import htracer.geometric.BBox;
import htracer.geometric.GeometricObject;
import htracer.materials.Material;
import htracer.materials.Matte;
import htracer.math.Point3;
import htracer.utility.Constants;
import htracer.utility.Normal;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

import java.util.List;
import java.util.Vector;

public class Compound extends GeometricObject {

	protected List<GeometricObject> objects;
	protected BBox bbox;
	
	Material debugMat = new Matte(0, 1, new RGBColor(0, 0, 1));
	
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
	public boolean hit(Ray ray, ShadeRec sr, float dist) {
		if (sr == null) return shadowHit(ray, dist);
		
		Material material = null;
		Normal normal = new Normal();
		Point3 hitPoint = new Point3();
		boolean	hit = false;
		float tmin = dist;
		
		for (GeometricObject obj : objects)
			if (obj.hit(ray, sr, tmin)) {
				hit	= true;
				tmin = sr.t;
				material = sr.material;
				hitPoint.set(sr.hitPoint);
				normal.set(sr.normal);
			}
		
		if (hit) {
			sr.t = tmin;
			sr.normal.set(normal);
			sr.material = material;
			sr.hitPoint.set(hitPoint);
		}
		
		return (hit);
	}
	
	@Override
	public boolean shadowHit(Ray ray, float dist) {
		
		for (GeometricObject obj : objects)
			if (obj.shadowHit(ray, dist)) return true;
		
		return false;
	}

}
