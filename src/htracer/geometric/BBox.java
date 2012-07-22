package htracer.geometric;

import htracer.math.Point3;
import htracer.utility.Constants;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class BBox extends GeometricObject {

	public float x0;
	public float x1;
	public float y0;
	public float y1;
	public float z0;
	public float z1;
	
	public BBox() {
		this(-1, 1, -1, 1, -1, 1);
	}
	
	public BBox(float x0, float x1, float y0, float y1, float z0, float z1) {
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		this.z0 = z0;
		this.z1 = z1;
	}
	
	public BBox(Point3 p0, Point3 p1) {
		this(p0.x, p1.x, p0.y, p1.y, p0.z, p1.z);
	}
	
	public BBox(BBox bbox) {
		set(bbox);
	}
	
	public void set(BBox bbox) {
		x0 = bbox.x0;
		x1 = bbox.x1;
		y0 = bbox.y0;
		y1 = bbox.y1;
		z0 = bbox.z0;
		z1 = bbox.z1;
	}
	
	@Override
	public boolean hit(Ray ray, ShadeRec sr) {
		float ox = ray.o.x; float oy = ray.o.y; float oz = ray.o.z;
		float dx = ray.d.x; float dy = ray.d.y; float dz = ray.d.z;
		
		float tx_min, ty_min, tz_min;
		float tx_max, ty_max, tz_max; 

		float a = 1.0f / dx;
		if (a >= 0) {
			tx_min = (x0 - ox) * a;
			tx_max = (x1 - ox) * a;
		}
		else {
			tx_min = (x1 - ox) * a;
			tx_max = (x0 - ox) * a;
		}
		
		float b = 1.0f / dy;
		if (b >= 0) {
			ty_min = (y0 - oy) * b;
			ty_max = (y1 - oy) * b;
		}
		else {
			ty_min = (y1 - oy) * b;
			ty_max = (y0 - oy) * b;
		}
		
		float c = 1.0f / dz;
		if (c >= 0) {
			tz_min = (z0 - oz) * c;
			tz_max = (z1 - oz) * c;
		}
		else {
			tz_min = (z1 - oz) * c;
			tz_max = (z0 - oz) * c;
		}
		
		float t0, t1;
		
		// find largest entering t value
		if (tx_min > ty_min)
			t0 = tx_min;
		else
			t0 = ty_min;
			
		if (tz_min > t0)
			t0 = tz_min;	
			
		// find smallest exiting t value
			
		if (tx_max < ty_max)
			t1 = tx_max;
		else
			t1 = ty_max;
			
		if (tz_max < t1)
			t1 = tz_max;
			
		if (t0 < t1 && t1 > Constants.kEpsilon) {
			sr.t = t0;
			
			Point3 temp = new Point3(x1-x0, y1-y0, z1-z0); // centre du box
			sr.normal.set(ray.o.sub(temp));
			if (t0 == tx_min) {
				sr.normal.y = sr.normal.z = 0;
			} else if (t0 == ty_min) {
				sr.normal.x = sr.normal.z = 0;
			} else {
				sr.normal.x = sr.normal.y = 0;
			}
			sr.normal.normalize();
			sr.material = getMaterial();
			sr.hitPoint = ray.o.add(ray.d.mul(t0));
			return true;
		}
		
		return false;
	}

	public boolean isInside(Point3 p) {
		return ((p.x > x0 && p.x < x1) && (p.y > y0 && p.y < y1) && (p.z > z0 && p.z < z1));
	}
}
