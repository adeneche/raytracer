package haktracer.shapes;

import haktracer.Ray;
import haktracer.Vec3;
import haktracer.materials.Texture;

public class Triangle extends Surface {

	public final Vec3 p0;
	public final Vec3 p1;
	public final Vec3 p2;
	
	public final Texture texture;
	
	public Triangle(Vec3 p0, Vec3 p1, Vec3 p2, Texture texture) {
		this.p0 = new Vec3(p0);
		this.p1 = new Vec3(p1);
		this.p2 = new Vec3(p2);
		this.texture = texture;
	}
	
	@Override
	public boolean hit(Ray r, float tmin, float tmax, float time,
			SurfaceHitRecord rec) {
		float tval;
		float A = p0.x() - p1.x();
		float B = p0.y() - p1.y();
		float C = p0.z() - p1.z();
		
		float D = p0.x() - p2.x();
		float E = p0.y() - p2.y();
		float F = p0.z() - p2.z();
		
		float G = r.getDirection().x();
		float H = r.getDirection().y();
		float I = r.getDirection().z();
		
		float J = p0.x() - r.getOrigin().x();
		float K = p0.y() - r.getOrigin().y();
		float L = p0.z() - r.getOrigin().z();
		
		float EIHF = E*I-H*F;
		float GFDI = G*F-D*I;
		float DHEG = D*H-E*G;
		
		float denom = (A*EIHF + B*GFDI + C*DHEG);
		
		float beta = (J*EIHF + K*GFDI + L*DHEG) / denom;
		
		if (beta <= 0f || beta >= 1f) return false;
		
		float AKJB = A*K - J*B;
		float JCAL = J*C - A*L;
		float BLKC = B*L - K*C;
		
		float gamma = (I*AKJB + H*JCAL + G*BLKC)/denom;
		if (gamma <= 0f || beta + gamma >= 1f) return false;
		
		tval = -(F*AKJB + E*JCAL + D*BLKC) / denom;
		if (tval >= tmin && tval <= tmax) {
			rec.t = tval;
			//rec.normal = unitVector(cross((p1 - p0), (p2 - p0)));
			Vec3.cross(p1.sub(p0), p2.sub(p0), rec.normal).normalize();
			rec.hitTex = texture;
			rec.hitPoint.set(r.pointAtParameter(tval));
			return true;
		}
		
		return false;
	}

	@Override
	public boolean shadowHit(Ray r, float tmin, float tmax, float time) {
		float tval;
		float A = p0.x() - p1.x();
		float B = p0.y() - p1.y();
		float C = p0.z() - p1.z();
		
		float D = p0.x() - p2.x();
		float E = p0.y() - p2.y();
		float F = p0.z() - p2.z();
		
		float G = r.getDirection().x();
		float H = r.getDirection().y();
		float I = r.getDirection().z();
		
		float J = p0.x() - r.getOrigin().x();
		float K = p0.y() - r.getOrigin().y();
		float L = p0.z() - r.getOrigin().z();
		
		float EIHF = E*I-H*F;
		float GFDI = G*F-D*I;
		float DHEG = D*H-E*G;
		
		float denom = (A*EIHF + B*GFDI + C*DHEG);
		
		float beta = (J*EIHF + K*GFDI + L*DHEG) / denom;
		
		if (beta <= 0f || beta >= 1f) return false;
		
		float AKJB = A*K - J*B;
		float JCAL = J*C - A*L;
		float BLKC = B*L - K*C;
		
		float gamma = (I*AKJB + H*JCAL + G*BLKC)/denom;
		if (gamma <= 0f || beta + gamma >= 1f) return false;
		
		tval = -(F*AKJB + E*JCAL + D*BLKC) / denom;
		return (tval >= tmin && tval <= tmax);
	}

}
