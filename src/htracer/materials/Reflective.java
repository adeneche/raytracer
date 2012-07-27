package htracer.materials;

import htracer.brdfs.PerfectSpecular;
import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class Reflective extends Material {

	PerfectSpecular specular_brdf;
	
	public Reflective(float kr) {
		specular_brdf = new PerfectSpecular();
		setKr(kr);
	}
	
	public void setKr(float k) {
		specular_brdf.setKr(k);
	}
	
	@Override
	public void set(Material material) {
		Reflective m = (Reflective)material;
		if (m.specular_brdf != null) specular_brdf.set(m.specular_brdf);
	}

	@Override
	public RGBColor shade(ShadeRec sr) {
		RGBColor L = new RGBColor();
		
		Vector3 wo = sr.ray.d.neg();
		Vector3 wi = new Vector3();
		RGBColor fr = specular_brdf.sampleF(sr, wo, wi);
		Ray reflectedRay = new Ray(sr.hitPoint, wi);
		
		L.sadd( sr.w.tracer.traceRay(reflectedRay, sr.depth + 1).mul(fr).mul(sr.normal.dot(wi)));
		
		return L;
	}

}
