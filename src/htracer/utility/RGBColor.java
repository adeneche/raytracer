package htracer.utility;

public class RGBColor {
	public float r;
	public float g;
	public float b;
	
	public RGBColor() {
		this(0);
	}
	
	public RGBColor(float c) {
		this.r = c;
		this.g = c;
		this.b = c;
	}
	
	public RGBColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public RGBColor(RGBColor rgb) {
		this(rgb.r, rgb.g, rgb.b);
	}
	
	public void set(RGBColor rgb) {
		r = rgb.r;
		g = rgb.g;
		b = rgb.b;
	}
	
	public void set(float v) {
		set(v, v, v);
	}
	
	public void set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public RGBColor add(RGBColor rgb) {
		return new RGBColor(r+rgb.r, g+rgb.g, b+rgb.b);
	}

	public void sadd(RGBColor rgb) {
		r += rgb.r;
		g += rgb.g;
		b += rgb.b;
	}
	
	public RGBColor mul(float k) {
		return new RGBColor(k*r, k*g, k*b);
	}
	
	public void smul(float k) {
		r *= k;
		g *= k;
		b *= k;
	}
	
	public RGBColor div(float k) {
		return new RGBColor(r/k, g/k, b/k);
	}

	public void sdiv(float k) {
		r /= k;
		g /= k;
		b /= k;
	}
	
	public RGBColor mul(RGBColor rgb) {
		return new RGBColor(r*rgb.r, g*rgb.g, b*rgb.b);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || !(obj instanceof RGBColor)) return false;
		
		RGBColor rgb = (RGBColor)obj;
		return r == rgb.r && g == rgb.g && b == rgb.b;
	}

	/**
	 * Raise components to a power
	 */
	public RGBColor powc(float p) {
		return new RGBColor((float)Math.pow(r, p), (float)Math.pow(g, p), (float)Math.pow(b, p));
	}
	
	/**
	 * Average of the components
	 */
	public float average() {
		return (r+g+b)/3;
	}

	
	
	/**
	 * @return (-r, -g, -b)
	 */
	public RGBColor neg() {
		return new RGBColor(-r, -g, -b);
	}
	
	public RGBColor sub(RGBColor rgb) {
		return new RGBColor(r-rgb.r, g-rgb.g, b-rgb.b);
	}
	
	public RGBColor div(RGBColor rgb) {
		return new RGBColor(r/rgb.r, g/rgb.g, b/rgb.b);
	}

	public void ssub(RGBColor rgb) {
		r -= rgb.r;
		g -= rgb.g;
		b -= rgb.b;
	}

	public void smul(RGBColor rgb) {
		r *= rgb.r;
		g *= rgb.g;
		b *= rgb.b;
	}

	public void sdiv(RGBColor rgb) {
		r /= rgb.r;
		g /= rgb.g;
		b /= rgb.b;
	}

	public void clamp() {
		if (r > 1) r = 1;
		if (g > 1) g = 1;
		if (b > 1) b = 1;
		
		if (r < 0) r = 0;
		if (g < 0) g = 0;
		if (b < 0) b = 0;
	}

}
