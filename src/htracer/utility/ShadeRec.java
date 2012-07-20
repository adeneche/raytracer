package htracer.utility;

import htracer.world.World;

/**
 * Stores all the information that the raytracer needs to shade a ray-object hit point.
 *
 */
public class ShadeRec {

	public float t; // hit distance from ray origin
	public boolean hitAnObject; // did the ray hit an object ?
	public Point3 localHitPoint; // world coordinates of hit point
	public Normal normal; // normal at hit point
	public RGBColor color; // used in chapter 3 only
	public World w; // world reference for shading
	
	public ShadeRec(World wr) {
		hitAnObject = false;
		localHitPoint= new Point3();
		normal = new Normal(0, 0, 1);
		color = new RGBColor();
		w = wr;
	}
	
	public ShadeRec(ShadeRec sr) {
		this(sr.w);
		set(sr);
	}
	
	public void set(ShadeRec sr) {
		hitAnObject = sr.hitAnObject;
		localHitPoint.set(sr.localHitPoint);
		normal.set(sr.normal);
		color.set(sr.color);
		t = sr.t;
	}
}
