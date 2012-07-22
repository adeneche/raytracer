package htracer.geometric;

import htracer.materials.Material;
import htracer.math.Point3;
import htracer.math.Vector3;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;
import static htracer.utility.Constants.kEpsilon;

public class Sphere extends GeometricObject {

	public Point3 center;
	public float radius;

	public Sphere() {
		center = new Point3();
		radius = 1;
	}

	public Sphere(Point3 center, float radius) {
		this.center = new Point3(center);
		this.radius = radius;
	}

	public Sphere(Point3 center, float radius, Material m) {
		this(center, radius);
		setMaterial(m);
	}
	
	public Sphere(Sphere sphere) {
		center = new Point3(sphere.center);
		radius = sphere.radius;
	}

	public void set(Sphere sphere) {
		center.set(sphere.center);
		radius = sphere.radius;
	}

	public void setCenter(Point3 center) {
		this.center.set(center);
	}

	public void setCenter(float x, float y, float z) {
		center.set(x, y, z);
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	@Override
	public boolean hit(Ray ray, ShadeRec sr) {
		float t;
		Vector3 temp = ray.o.sub(center);
		float a = ray.d.lenSq();
		float b = 2 * temp.dot(ray.d);
		float c = temp.lenSq() - radius * radius;
		float disc = b * b - 4 * a * c;

		if (disc < 0.0)
			return false;
		else {
			float e = (float) Math.sqrt(disc);
			float denom = 2 * a;
			t = (-b - e) / denom; // smaller root

			if (t <= kEpsilon) {
				t = (-b + e) / denom; // larger root
			}

			if (t > kEpsilon) {
				sr.t = t;
				sr.normal.set((temp.add(ray.d.mul(t))).mul(1 / radius));
				sr.hitPoint = ray.o.add(ray.d.mul(t));
				sr.material = getMaterial();
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean shadowHit(Ray ray, ShadowOut so) {
		if (!shadows) return false;
		
		float t;
		Vector3 temp = ray.o.sub(center);
		float a = ray.d.lenSq();
		float b = 2 * temp.dot(ray.d);
		float c = temp.lenSq() - radius * radius;
		float disc = b * b - 4 * a * c;

		if (disc < 0.0)
			return false;
		else {
			float e = (float) Math.sqrt(disc);
			float denom = 2 * a;
			t = (-b - e) / denom; // smaller root

			if (t <= kEpsilon) {
				t = (-b + e) / denom; // larger root
			}

			if (t > kEpsilon) {
				so.t = t;
				return true;
			}
		}

		return false;
	}

}
