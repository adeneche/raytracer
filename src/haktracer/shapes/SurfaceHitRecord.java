package haktracer.shapes;

import haktracer.Vec3;
import haktracer.materials.Texture;

public class SurfaceHitRecord {
	/** Ray hits at p = Ray.origin() + r*Ray.direction() */
	public float t;
	public Vec3 normal;
	public Texture hitTex;
	public Vec3 hitPoint;
	
	public SurfaceHitRecord() {
		normal = new Vec3();
		hitPoint = new Vec3();
	}
}
