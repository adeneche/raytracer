package htracer.world;

public class ViewPlane {

	public int hres; //horizontal image resolution
	public int vres; //vertical image resolution
	public float s; // pixel size
	private float gamma; //monitor gamma factor
	private float invGamma; // one over gamma
	public boolean showOutOfGamut; // display RED if RGBColor out of gamut
	public int numSamples;
	
	public float getGamma() {
		return gamma;
	}
	
	public void setGamma(float gamma) {
		this.gamma = gamma;
		invGamma = 1/gamma;
	}
	
	public float getInvGamma() {
		return invGamma;
	}
	
	public ViewPlane() {
		hres = 400;
		vres = 400;
		s = 1;
		gamma = 1;
		invGamma = 1;
		showOutOfGamut = false;
		numSamples = 1;
	}
	
	public ViewPlane(ViewPlane vp) {
		hres = vp.hres;
		vres = vp.vres;
		s = vp.s;
		gamma = vp.gamma;
		invGamma = vp.invGamma;
		showOutOfGamut = vp.showOutOfGamut;
		numSamples = vp.numSamples;
	}
	
	public void set(ViewPlane vp) {
		hres = vp.hres;
		vres = vp.vres;
		s = vp.s;
		gamma = vp.gamma;
		invGamma = vp.invGamma;
		showOutOfGamut = vp.showOutOfGamut;
		numSamples = vp.numSamples;
	}
	
	
}
