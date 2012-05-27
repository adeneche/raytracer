package htracer.samplers;

import htracer.Point2;
import htracer.utility.RNG;

public class Jittered extends Sampler {

	public Jittered(int num, int numsets) {
		super(num, numsets);
	}
	
	public Jittered(int num) {
		super(num);
	}
	
	@Override
	public void generateSamples() {
		int n = (int)Math.sqrt(numSamples);
		
		int index = 0;
		for (int p = 0;  p < numSets; p++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					Point2 sp = new Point2((k + rng.nextFloat()) / n, (j + rng.nextFloat()) / n);
					samples[index++] = sp;
				}
			}
		}
	}

}
