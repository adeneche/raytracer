package htracer.geometric;

import htracer.math.Point3;
import htracer.utility.Normal;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class Plane extends GeometricObject {

	private Point3 a; // point through which plane passes
	private Normal n;

	private static final double kEpsilon = 0.001; // see Chapter 6

	public Plane() {
		a = new Point3();
		n = new Normal(0, 1, 0);
	}

	public Plane(Point3 p, Normal n) {
		this.a = p;
		this.n = n;
		this.n.normalize();
	}

	public Plane(Plane plane) {
		a = new Point3(plane.a);
		n = new Normal(plane.n);
	}

	@Override
	protected Plane clone() throws CloneNotSupportedException {
		return new Plane(this);
	}

	public Plane set(Plane plane) {
		a.set(plane.a);
		n.set(plane.n);
		return this;
	}

	@Override
	public boolean hit(Ray ray, ShadeRec sr) {
		float t = n.dot(a.sub(ray.o)) / n.dot(ray.d);
		System.out.println("t = " + t);

		if (t > kEpsilon) {
			sr.t = t;
			sr.normal = n;
			sr.hitPoint = ray.o.add(ray.d.mul(t));

			return true;
		}

		return false;
	}

}
