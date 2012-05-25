package haktracer;

public class Rgb {
	public float r;
	public float g;
	public float b;
	
	public Rgb(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Rgb() {
		this(0,0,0);
	}
	
	public Rgb(Rgb rgb) {
		this(rgb.r, rgb.g, rgb.b);
	}
	
	/**
	 * @return (-r, -g, -b)
	 */
	public Rgb neg() {
		return new Rgb(-r, -g, -b);
	}
	
	public Rgb mul(float k) {
		return new Rgb(k*r, k*g, k*b);
	}
	
	public Rgb div(float k) {
		return new Rgb(r/k, g/k, b/k);
	}
	
	public Rgb add(Rgb rgb) {
		return new Rgb(r+rgb.r, g+rgb.g, b+rgb.b);
	}
	
	public Rgb sub(Rgb rgb) {
		return new Rgb(r-rgb.r, g-rgb.g, b-rgb.b);
	}
	
	public Rgb mul(Rgb rgb) {
		return new Rgb(r*rgb.r, g*rgb.g, b*rgb.b);
	}
	
	public Rgb div(Rgb rgb) {
		return new Rgb(r/rgb.r, g/rgb.g, b/rgb.b);
	}

	public void sadd(Rgb rgb) {
		r += rgb.r;
		g += rgb.g;
		b += rgb.b;
	}

	public void ssub(Rgb rgb) {
		r -= rgb.r;
		g -= rgb.g;
		b -= rgb.b;
	}

	public void smul(Rgb rgb) {
		r *= rgb.r;
		g *= rgb.g;
		b *= rgb.b;
	}

	public void sdiv(Rgb rgb) {
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
	
	public void set(Rgb rgb) {
		r = rgb.r;
		g = rgb.g;
		b = rgb.b;
	}
	
	public void set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
}
