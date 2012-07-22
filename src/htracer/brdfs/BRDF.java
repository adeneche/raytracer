package htracer.brdfs;

import htracer.math.Vector3;
import htracer.samplers.Sampler;
import htracer.utility.RGBColor;
import htracer.utility.ShadeRec;
import static htracer.utility.Constants.black;

public abstract class BRDF {

	protected Sampler sampler; // for indirect illumination
	
	public BRDF() {}
	
	public BRDF(BRDF brdf) {
		set(brdf);
	}
	
	public void set(BRDF rhs) {
		if (rhs.sampler != null) sampler = rhs.sampler.clone();
	}
	
	public void setSampler(Sampler sp) {
		sampler = sp;
		sampler.mapSamplesToHemisphere(1); // for perfect diffuse
	}
	
	public abstract RGBColor f(final ShadeRec sr, final Vector3 wo, final Vector3 wi);
	
	public RGBColor sampleF(final ShadeRec sr, final Vector3 wo, final Vector3 wi) {
		return black;
	}
	
	public SampleFOut sampleF(final ShadeRec sr, final Vector3 wo) {
		return new SampleFOut();
	}
	
	public RGBColor rho(final ShadeRec sr, final Vector3 wo) {
		return black;
	}
	
	public static class SampleFOut {
		public Vector3 wi = new Vector3();
		public float pdf;
		public RGBColor color = new RGBColor();
	}
}
