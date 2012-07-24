package htracer.lights;

import htracer.math.Point3;
import htracer.math.Vector3;
import htracer.samplers.MultiJittered;
import htracer.samplers.Sampler;
import htracer.utility.Constants;
import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;

public class AmbiantOccluder extends Ambient {

	private Vector3 u, v, w;
	Sampler sampler;
	RGBColor min_amount;
	
	public AmbiantOccluder() {
		u = new Vector3();
		v = new Vector3();
		w = new Vector3();
		min_amount = new RGBColor();
	}
	
	public void setSamples(int numSamples) {
		sampler = new MultiJittered(numSamples);
		sampler.mapSamplesToHemisphere(1);
	}
	
	@Override
	public Vector3 getDirection(ShadeRec sr) {
		Point3 sp = sampler.sampleHemisphere();
		return u.mul(sp.x).add( v.mul(sp.y)).add( w.mul(sp.z));
	}

	@Override
	public RGBColor L(ShadeRec sr) {
		w.set(sr.normal);
		// jitter up vector in case normal is vertical
		Vector3.cross(w, new Vector3(0.0072f, 1, 0.0034f), v);
		v.normalize();
		Vector3.cross(v, w, u);
		
		Ray shadowRay = new Ray(sr.hitPoint, getDirection(sr));
		
		if (inShadow(shadowRay, sr))
			return min_amount.mul(color).mul(ls);
		else
			return color.mul(ls);
	}

	@Override
	public boolean inShadow(Ray ray, ShadeRec sr) {
		return sr.w.compound.shadowHit(ray, Constants.kHugeValue);
	}
}
