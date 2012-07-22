package htracer.utility;

import htracer.materials.Material;
import htracer.math.Point3;
import htracer.math.Vector3;
import htracer.world.World;

/**
 * Stores all the information that the raytracer needs to shade a ray-object hit point.
 *
 */
public class ShadeRec {

	public float t; // ray parameter

	public boolean hitAnObject; // did the ray hit an object ?
	public Point3 hitPoint; // world coordinates of hit point
	public Point3 localHitPoint; // for attaching textures to objects
	public Normal normal; // normal at hit point
	public World w; // world reference for shading
	
	public Material material; // nearest object's material
	public Ray ray; // required for specular highlights and area lights
	public int depth; // recursion depth
	public Vector3 dir; // for area lights
	
	public ShadeRec(World wr) {
		hitPoint= new Point3();
		localHitPoint = new Point3();
		normal = new Normal(0, 0, 1);
		w = wr;
		
		ray = new Ray();
		dir = new Vector3();
	}
	
	public ShadeRec(ShadeRec sr) {
		this(sr.w);
		set(sr);
	}
	
	public void set(ShadeRec sr) {
		hitAnObject = sr.hitAnObject;
		hitPoint.set(sr.hitPoint);
		localHitPoint.set(sr.localHitPoint);
		normal.set(sr.normal);
		ray.set(sr.ray);
		depth = sr.depth;
		dir.set(sr.dir);
		material = sr.material;
		t = sr.t;
	}
}
