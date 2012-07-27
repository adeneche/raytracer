package htracer.cameras;

import htracer.math.Point3;
import htracer.math.Vector3;
import htracer.world.World;

public abstract class Camera {

	public Point3 eye;
	public Point3 lookat;
	public Point3 up;
	public float ra;
	public float exposureTime;
	
	protected Vector3 u, v, w; // orthonormal basis vectors
	
	public Camera() {
		eye = new Point3(0, 0, 500);
		lookat = new Point3();
		up = new Point3(0, 1, 0);
		ra = 0;
		
		u = new Vector3(1, 0, 0);
		v = new Vector3(0, 1, 0);
		w = new Vector3(0, 0, 1);
		
		exposureTime = 1;
	}
	
	public abstract void renderScene(World world);

	public void computeUVW() {
		computeUVW(eye, lookat, up, u, v, w);
	}
	
	/**
	 * This computes an orthornormal basis given the view point, lookat point, and up vector
	 */
	public static void computeUVW(Point3 eye, Point3 lookat, Point3 up, Vector3 u, Vector3 v, Vector3 w) {
		//w = eye - lookat;
		//w.normalize();
		w.set(eye.sub(lookat));
		w.normalize();
		
		//u = up ^ w;
		Vector3.cross(up.asVector(), w, u);
		u.normalize();
		
		//v = w ^ u;
		Vector3.cross(w, u, v);

		// take care of the singularity by hardwiring in specific camera orientations
		
		if (eye.x == lookat.x && eye.z == lookat.z && eye.y > lookat.y) { // camera looking vertically down
			u.set(0, 0, 1);
			v.set(1, 0, 0);
			w.set(0, 1, 0);	
		}
		
		if (eye.x == lookat.x && eye.z == lookat.z && eye.y < lookat.y) { // camera looking vertically up
			u.set(1, 0, 0);
			v.set(0, 0, 1);
			w.set(0, -1, 0);
		}
	}
}
