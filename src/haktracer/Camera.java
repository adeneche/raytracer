package haktracer;

public class Camera {

	public Vec3 center = new Vec3();
	public Vec3 corner = new Vec3();
	public Vec3 across = new Vec3();
	public Vec3 up = new Vec3();
	
	ONB uvw = new ONB();
	
	public float lens_radius;
	public float l;
	public float r;
	public float b;
	public float t;
	public float d;
	
	public Camera() {}
	
	public Camera(Camera cam) {
		center = new Vec3(cam.center);
		corner = new Vec3(cam.corner);
		across = new Vec3(cam.across);
		up = new Vec3(cam.up);
		
		lens_radius = cam.lens_radius;
		l = cam.l;
		r = cam.r;
		b = cam.b;
		t = cam.t;
		d = cam.d;
	}
	
	public Camera(Vec3 c, Vec3 gaze, Vec3 vup, float aperture, float left, float right, float bottom, float top, float distance) {
		center.set(c);
		d = distance;
		l = left;
		r = right;
		b = bottom;
		t = top;
		lens_radius = aperture/2;
		
//	      uvw.initFromWV( -gaze, vup );
		uvw.initFromWV(gaze.neg(), vup);
//	      corner = center + l*uvw.u() + b*uvw.v() - d*uvw.w();
		corner.set( center.add( uvw.u().mul(l)).add( uvw.v().mul(b)).sub( uvw.w().mul(d)));
//	      across = (r-l)*uvw.u();
		across.set(uvw.u()).smul(r-l);
//	      up = (t-b)*uvw.v();
		up.set(uvw.v()).smul(t-b);
	}
	
	/**
	 * and a are b the pixel positions
	 * xi1 and xi2 are the lens samples in the range (0,1)^2
	 */
	public Ray getRay(float a, float b, float xi1, float xi2) {
//	      Vector3 origin = center + 2.0F*(xi1-0.5F)*lens_radius*uvw.u() +
//        2.0F*(xi2-0.5F)*lens_radius*uvw.v();
		Vec3 origin = center.add( uvw.u().mul( 2*(xi1-.5f)*lens_radius)).add( uvw.v().mul( 2*(xi2-.5f)*lens_radius));
//	      Vector3 target = corner + across*a + up*b;
		Vec3 target = corner.add( across.mul(a)).add( up.mul(b));
		
//	      return Ray(origin, unitVector(target-origin));
		return new Ray(origin, Vec3.normalize(target.sub(origin)));
	}
	
	
}
