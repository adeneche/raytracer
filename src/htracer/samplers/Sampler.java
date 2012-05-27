package htracer.samplers;

import htracer.Point2;
import htracer.utility.RNG;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public abstract class Sampler {
	
	public int numSamples; // num sample points in a pattern
	
	protected int numSets; // num sample sets (patterns) stored
	protected Point2[] samples; // sample points on a unit square
	protected int[] shuffledIndices; // shuffled samples array indices
	protected long count; // current number of sample points used
	protected int jump; // random index jump
	
	protected final RNG rng;
	
	public Sampler() {
		this(1, 83);
	}
	
	public Sampler (int num) {
		this(num, 83);
	}
	
	public Sampler(int num, int numSets) {
		numSamples = num;
		this.numSets = numSets;
		count = 0;
		jump = 0;
		samples = new Point2[numSamples*numSets];
		
		rng = RNG.instance();

		setupShuffledIndices();
		generateSamples();
	}

	/** generate sampel patterns in a unit square*/
	public abstract void generateSamples();
	
	/** shuffle the x coordinates of the points within each set */
	public void shuffle_x_coordinates() {
		for (int p = 0; p < numSets; p++)
			for (int i = 0; i <  numSamples - 1; i++) {
				int target = rng.nextInt(numSamples) + p * numSamples;
				float temp = samples[i + p * numSamples + 1].x;
				samples[i + p * numSamples + 1].x = samples[target].x;
				samples[target].x = temp;
			}
	}
	
	/** shuffle the y coordinates of the points within each set */
	public void shuffle_y_coordinates() {
		for (int p = 0; p < numSets; p++)
			for (int i = 0; i <  numSamples - 1; i++) {
				int target = rng.nextInt(numSamples) + p * numSamples;
				float temp = samples[i + p * numSamples + 1].y;
				samples[i + p * numSamples + 1].y = samples[target].y;
				samples[target].y = temp;
			}
	}

	/** setup the randomly shuffled indices */
	public void setupShuffledIndices() {
		shuffledIndices = new int[numSamples * numSets];
		List<Integer> indices = new Vector<Integer>();
		
		for (int j = 0; j < numSamples; j++)
			indices.add(j);
		
		int index = 0;
		for (int p = 0; p < numSets; p++) { 
			Collections.shuffle(indices);
			
			for (int j = 0; j < numSamples; j++)
				shuffledIndices[index++] = indices.get(j);
		}	
	}
	
	/** get next sample on unit square */
	public Point2 sampleUnitSquare() {
		if (count % numSamples == 0) // start of a new pixel
			jump = rng.nextInt(numSets) * numSamples;
		return samples[jump + shuffledIndices[jump + (int)(count++ % numSamples)]];
	}
}
