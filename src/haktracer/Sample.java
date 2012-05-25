package haktracer;

public class Sample {
	private static RNG rng = RNG.instance();
	
	public static Vec2[] samples(int numSamples) {
		Vec2[] samples = new Vec2[numSamples];
		for (int i = 0; i < samples.length; i++) {
			samples[i] = new Vec2();
		}

		return samples;
	}
	
	// 2D sampling
	public static void random(Vec2[] samples) {
		for (int i = 0; i < samples.length; i++) {
			samples[i].es[0] = rng.nextFloat();
			samples[i].es[1] = rng.nextFloat();
		}
	}
	
	/** assumes samples.length is a perfect square*/
	public static void jitter(Vec2[] samples) {
		int sqrt_samples = (int)Math.sqrt(samples.length);
		for (int i = 0; i < sqrt_samples; i++) {
			for (int j = 0; j < sqrt_samples; j++) {
				float x = (i + rng.nextFloat()) / sqrt_samples;
				float y = (j + rng.nextFloat()) / sqrt_samples;
				samples[i*sqrt_samples+j].es[0] = x;
				samples[i*sqrt_samples+j].es[1] = y;
			}
		}
	}
	
	public static void nrooks(Vec2[] samples) {
		for (int i = 0; i < samples.length; i++) {
			samples[i].es[0] = (i + rng.nextFloat()) / samples.length;
			samples[i].es[1] = (i + rng.nextFloat()) / samples.length;
		}
		// shuffle the x coords
		for (int i = samples.length - 2; i > 0; i--) {
			int target = rng.nextInt(i);
			float temp = samples[i+1].x();
			samples[i+1].es[0] = samples[target].x();
			samples[target].es[0] = temp;
		}
	}
	
	public static void multiJitter(Vec2[] samples) {
		for (int i = 0; i < samples.length; i++) {
			
		}
	}
	
	public static void shuffle(Vec2[] samples) {
		for (int i = 0; i < samples.length; i++) {
			
		}
	}
	
	public static void boxFilter(Vec2[] samples) {
		for (int i = 0; i < samples.length; i++) {
			
		}
	}
	
	public static void tentFilter(Vec2[] samples) {
		for (int i = 0; i < samples.length; i++) {
			
		}
	}
	
	public static void cubicSplineFilter(Vec2[] samples) {
		for (int i = 0; i < samples.length; i++) {
			
		}
	}
	
	// 1D Sampling
	public static void random(float[] samples) {
		for (int i = 0; i < samples.length; i++) {
			samples[i] = rng.nextFloat();
		}
	}
	
	public static void jitter(float[] samples) {
		for (int i = 0; i < samples.length; i++) {
			samples[i] = (i + rng.nextFloat()) / samples.length;
			
		}
	}
	
	public static void shuffle(float[] samples) {
		for (int i = 0; i < samples.length; i++) {
			
		}
	}
	
	static float solve(float r) {
		float u = r;
		for (int i = 0; i < 5; i++)
			u = (11*r + u*u*(6 + u*(8 - 9*u))) / (4 + 12*u*(1 + u*(1 - u)));
		
		return u;
	}
	
	static float cubicFilter(float x) {
		if (x < 1f / 24)
			return (float) (Math.pow(24 * x, 0.25f) - 2);
		else if (x < 0.5)
			return solve(24 * (x - 1f / 24) / 11) - 1;
		else if (x < 23f / 24)
			return 1 - solve(24 * (23f / 24 - x) / 11);
		else
			return (float) (2 - Math.pow(24 * (1 - x), 0.25f));
	}
}
