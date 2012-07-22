package htracer.brdfs;

import htracer.math.Point3;
import htracer.math.Vector3;
import htracer.utility.RGBColor;
import htracer.utility.ShadeRec;
import static htracer.utility.Constants.invPI;

public class Lambertian extends BRDF {

	private float kd;
	private RGBColor cd;
	
	public Lambertian() {
		kd = 0;
		cd = new RGBColor();
	}
	
	public Lambertian(Lambertian lamb) {
		kd = lamb.kd;
		cd = new RGBColor(lamb.cd);
	}
	
	public void set(Lambertian rhs) {
		kd = rhs.kd;
		cd.set(rhs.cd);
	}
	
	public void setKa(float k) {
		kd = k;
	}
	
	public void setKd(float k) {
		kd = k;
	}
	
	public void setCd(RGBColor c) {
		cd.set(c);
	}
	
	public void setCd(float r, float g, float b) {
		cd.set(r, g, b);
	}

	@Override
	public RGBColor f(ShadeRec sr, Vector3 wo, Vector3 wi) {
		// there is no sampling here
		return cd.mul(kd*invPI);
	}

	/**
	 * Generates a direction by sampling the hemisphere with a cosine distribution.<br>
	 * this is called in path_shade for any material with a diffuse shading component.<br>
	 * the samples have to be stored with a cosine distribution.
	 */
	@Override
	public SampleFOut sampleF(final ShadeRec sr, final Vector3 wo) {
		Vector3 w = sr.normal.toVector3();
		Vector3 v = Vector3.cross(new Vector3(0.0034f, 1, 0.0071f), w);
		v.normalize();
		Vector3 u = Vector3.cross(v, w);
		
		Point3 sp = sampler.sampleHemisphere();
		
		SampleFOut out = new SampleFOut();
		out.wi.set( u.mul(sp.x).add( v.mul(sp.y) ).add( w.mul(sp.z) ) );
		out.wi.normalize();
		out.pdf = sr.normal.dot(out.wi) * invPI;
		out.color = cd.mul(kd*invPI);
		
		return out;
	}

	@Override
	public RGBColor rho(ShadeRec sr, Vector3 wo) {
		return cd.mul(kd);
	}
	
	
}
