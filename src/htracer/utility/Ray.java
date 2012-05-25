package htracer.utility;

public class Ray {
	public Point3 o; // origin
	public Vector3 d; // direction
	public float tmin;
	
	public Ray() {
		o = new Point3();
		d = new Vector3(0, 0, 1);
	}
	
	public Ray(Point3 origin, Vector3 direction) {
		o = new Point3(origin);
		d = new Vector3(direction);
	}
	
	public Ray(Ray ray) {
		o = new Point3(ray.o);
		d = new Vector3(ray.d);
		tmin = ray.tmin;
	}
	
	public void set(Ray ray) {
		o.set(ray.o);
		d.set(ray.d);
		tmin = ray.tmin;
	}
}
