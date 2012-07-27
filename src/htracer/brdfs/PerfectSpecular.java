package htracer.brdfs;

import htracer.math.Vector3;
import htracer.utility.Constants;
import htracer.utility.RGBColor;
import htracer.utility.ShadeRec;

public class PerfectSpecular extends BRDF {

	private float kr; // reflection coefficient
	private RGBColor cr; //reflection color
	
	public PerfectSpecular() {
		kr = 1;
		cr = new RGBColor(1);
	}
	
	public PerfectSpecular(PerfectSpecular spec) {
		kr = spec.kr;
		cr = new RGBColor(spec.cr);
	}
	
	public void set(PerfectSpecular rhs) {
		kr = rhs.kr;
		cr.set(rhs.cr);
	}
	
	public void setKr(float k) {
		kr = k;
	}
	
	public void setCr(RGBColor c) {
		cr.set(c);
	}

	@Override
	public RGBColor f(ShadeRec sr, Vector3 wo, Vector3 wi) {
		return Constants.black;
	}

	/**
	 * this computes wi: the direction of perfect mirror reflection
	 * it's called from from the functions Reflective::shade and Transparent::shade.
	 * the fabs in the last statement is for transparency
	 */
	@Override
	public SampleFOut sampleF(final ShadeRec sr, final Vector3 wo) {
//		float ndotwo = sr.normal * wo;
//		wi = -wo + 2.0 * sr.normal * ndotwo; 
//		pdf = fabs(sr.normal * wi);
//		return (kr * cr);  
		float ndotwo = sr.normal.dot(wo);
		
		SampleFOut out = new SampleFOut();
		out.wi.set(wo.neg().add( sr.normal.toVector3().mul(2 * ndotwo))); 
		out.pdf = Math.abs(sr.normal.dot(out.wi));
		out.color = cr.mul(kr);
		
		return out;
	}

	
	@Override
	public RGBColor sampleF(ShadeRec sr, Vector3 wo, Vector3 wi) {
		float ndotwo = sr.normal.dot(wo);
		wi.set(wo.neg().add( sr.normal.mul(2*ndotwo).toVector3()));
		
		return cr.mul(kr / sr.normal.dot(wi));
	}

	@Override
	public RGBColor rho(ShadeRec sr, Vector3 wo) {
		return Constants.black;
	}
	
	
}
