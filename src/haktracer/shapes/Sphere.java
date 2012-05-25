package haktracer.shapes;

import haktracer.Ray;
import haktracer.Vec3;
import haktracer.materials.Texture;

public class Sphere extends Surface {

	public final Vec3 center;
	public final float radius;
	public final Texture texture;
	
	public Sphere(Vec3 center, float radius, Texture texture) {
		this.center = new Vec3(center);
		this.radius = radius;
		this.texture = texture;
	}
	
	@Override
	public boolean hit(Ray r, float tmin, float tmax, float time,
			SurfaceHitRecord rec) {
		Vec3 temp = r.getOrigin().sub(center);
		
		double a = r.getDirection().lenSq();
		double b = 2*Vec3.dot( r.getDirection() , temp );
		double c = temp.lenSq() - radius*radius;
		
		double discriminant = b*b- 4*a*c;
		
		// first check to see if ray intersects sphere
		if (discriminant > 0) {
			discriminant = Math.sqrt( discriminant );
			float t = (float) ((- b - discriminant) / 2*a);
			
			// now check for valid interval
			if (t < tmin)
				t = (float) ((- b + discriminant) / 2*a);
			if (t < tmin || t > tmax)
				return false;
			
			// we have a valid hit
			rec.t = t;
			// rec,normal = unit Vector (r. originO + t* r .directionO - center);
			Vec3.normalize(r.getOrigin().add(r.getDirection().mul(t).sub(center)), rec.normal);
			rec.hitTex = texture;
			rec.hitPoint = r.pointAtParameter(t);
			return true;
		}

		return false;
	}

	@Override
	public boolean shadowHit(Ray r, float tmin, float tmax, float time) {
		Vec3 temp = r.getOrigin().sub(center);
		
		double a = r.getDirection().lenSq();
		double b = 2*Vec3.dot( r.getDirection() , temp );
		double c = temp.lenSq() - radius*radius;
		
		double discriminant = b*b- 4*a*c;
		
		// first check to see if ray intersects sphere
		if (discriminant > 0) {
			discriminant = Math.sqrt( discriminant );
			float t = (float) ((- b - discriminant) / 2*a);
			
			// now check for valid interval
			if (t < tmin)
				t = (float) ((- b + discriminant) / 2*a);
			if (t < tmin || t > tmax)
				return false;
			
			// we have a valid hit
			return true;
		}

		return false;
	}

}
