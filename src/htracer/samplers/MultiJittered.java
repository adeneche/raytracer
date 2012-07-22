package htracer.samplers;

import htracer.math.Point2;

public class MultiJittered extends Sampler {

	public MultiJittered(int n) {
		super(n);
	}
	
	public MultiJittered(int n, int ns) {
		super(n, ns);
	}
	
	public MultiJittered(MultiJittered sp) {
		super(sp);
	}
	
	@Override
	public Sampler clone() {
		return new MultiJittered(this);
	}

	/**
	 * 
	 */
	@Override
	public void generateSamples() {
		// num_samples needs to be a perfect square
		int n = (int)Math.sqrt((float)numSamples);
		float subcell_width = 1f / numSamples;
		
		// fill the samples array with dummy points to allow us to use the [ ] notation when we set the 
		// initial patterns

		for (int j = 0; j < numSamples * numSets; j++)
			samples[j] = new Point2();
			
		// distribute points in the initial patterns
		
		for (int p = 0; p < numSets; p++) 
			for (int i = 0; i < n; i++)		
				for (int j = 0; j < n; j++) {
					samples[i * n + j + p * numSamples].x = (i * n + j) * subcell_width + rng.nextFloat() * subcell_width;
					samples[i * n + j + p * numSamples].y = (j * n + i) * subcell_width + rng.nextFloat() * subcell_width;
				}
		
		// shuffle x coordinates

		for (int p = 0; p < numSets; p++) 
			for (int i = 0; i < n; i++)		
				for (int j = 0; j < n; j++) {
					int k = j + rng.nextInt(n - j); // rand_int(j, n - 1);
					float t = samples[i * n + j + p * numSamples].x;
					samples[i * n + j + p * numSamples].x = samples[i * n + k + p * numSamples].x;
					samples[i * n + k + p * numSamples].x = t;
				}

		// shuffle y coordinates
		
		for (int p = 0; p < numSets; p++)
			for (int i = 0; i < n; i++)		
				for (int j = 0; j < n; j++) {
					int k = j + rng.nextInt(n - j); // rand_int(j, n - 1);
					float t = samples[j * n + i + p * numSamples].y;
					samples[j * n + i + p * numSamples].y = samples[k * n + i + p * numSamples].y;
					samples[k * n + i + p * numSamples].y = t;
			}
	}

}
