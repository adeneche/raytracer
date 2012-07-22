package htracer.materials;

import htracer.brdfs.Lambertian;
import htracer.lights.Light;
import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class Matte extends Material {

	private Lambertian ambient_brdf;
	private Lambertian diffuse_brdf;
	
	public Matte() {
		ambient_brdf = new Lambertian();
		diffuse_brdf = new Lambertian();
	}
	
	public Matte(float ka, float kd, RGBColor c) {
		this();
		setKa(ka);
		setKd(kd);
		setCd(c);
	}
	
	public Matte(Matte m) {
		if (m.ambient_brdf != null) ambient_brdf = new Lambertian(m.ambient_brdf);
		if (m.diffuse_brdf != null) diffuse_brdf = new Lambertian(m.diffuse_brdf);
	}

	@Override
	public void set(Material material) {
		Matte m = (Matte) material;
		if (m.ambient_brdf != null) ambient_brdf.set(m.ambient_brdf);
		if (m.diffuse_brdf != null) diffuse_brdf.set(m.diffuse_brdf);
	}
	
	public void setKa(float k) {
		ambient_brdf.setKa(k);
	}
	
	public void setKd(float k) {
		diffuse_brdf.setKd(k);
	}
	
	public void setCd(RGBColor c) {
		ambient_brdf.setCd(c);
		diffuse_brdf.setCd(c);
	}
	
	public void setCd(float r, float g, float b) {
		ambient_brdf.setCd(r, g, b);
		diffuse_brdf.setCd(r, g, b);
	}

	@Override
	public RGBColor shade(ShadeRec sr) {
		Vector3	wo = sr.ray.d.neg();
		RGBColor L = ambient_brdf.rho(sr, wo).mul(sr.w.ambient.L(sr));
		
		for (Light light : sr.w.lights) {
			Vector3 wi = light.getDirection(sr);    
			float ndotwi = sr.normal.dot(wi);
		
			if (ndotwi > 0.0) {
				boolean inShadow = false;
				
				if (light.castShadows()) {
					Ray shadowRay = new Ray(sr.hitPoint, wi);
					inShadow = light.inShadow(shadowRay, sr);
				}
				
				if (!inShadow) {
					L.sadd( diffuse_brdf.f(sr, wo, wi).mul( light.L(sr).mul(ndotwi)) );
				}
			}
		}
		
		return L;
	}

}
