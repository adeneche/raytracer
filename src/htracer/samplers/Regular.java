package htracer.samplers;

import htracer.Point2;

public class Regular extends Sampler {

	public Regular(int num) {
		super(num);
	}
	
	public Regular(int num, int numsets) {
		super(num, numsets);
	}
	
	@Override
	public void generateSamples() {
		int n = (int) Math.sqrt(numSamples);

		int index = 0;
		for (int j = 0; j < numSets; j++)
			for (int p = 0; p < n; p++)		
				for (int q = 0; q < n; q++)
					samples[index++] = new Point2((q + 0.5f) / n, (p + 0.5f) / n);
	}

}
