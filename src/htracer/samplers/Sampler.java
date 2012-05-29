package htracer.samplers;

import htracer.Point2;
import htracer.utility.Point3;
import htracer.utility.RNG;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import static htracer.utility.Constants.PI;

public abstract class Sampler {

	public int numSamples; // num sample points in a pattern

	protected int numSets; // num sample sets (patterns) stored
	protected Point2[] samples; // sample points on a unit square
	protected Point2[] diskSamples; // sample points on a unit disk
	protected Point3[] hemisphereSamples; //sample points on a unit sphere
	
	protected int[] shuffledIndices; // shuffled samples array indices
	protected long count; // current number of sample points used
	protected int jump; // random index jump

	protected final RNG rng;

	public Sampler() {
		this(1, 83);
	}

	public Sampler(int num) {
		this(num, 83);
	}

	public Sampler(int num, int numSets) {
		numSamples = num;
		this.numSets = numSets;
		count = 0;
		jump = 0;
		samples = new Point2[numSamples * numSets];

		rng = RNG.instance();

		setupShuffledIndices();
		generateSamples();
	}

	/** generate sampel patterns in a unit square */
	public abstract void generateSamples();

	/** shuffle the x coordinates of the points within each set */
	public void shuffle_x_coordinates() {
		for (int p = 0; p < numSets; p++)
			for (int i = 0; i < numSamples - 1; i++) {
				int target = rng.nextInt(numSamples) + p * numSamples;
				float temp = samples[i + p * numSamples + 1].x;
				samples[i + p * numSamples + 1].x = samples[target].x;
				samples[target].x = temp;
			}
	}

	/** shuffle the y coordinates of the points within each set */
	public void shuffle_y_coordinates() {
		for (int p = 0; p < numSets; p++)
			for (int i = 0; i < numSamples - 1; i++) {
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

	/**
	 * Maps the 2D sample points in the square [-1,1] X [-1,1] to a unit disk,
	 * using Peter Shirley's concentric map function
	 */
	public void mapSamplesToUnitDisk() {
		int size = samples.length;
		float r, phi; // polar coordinates
		Point2 sp = new Point2(); // sample point on unit disk

		diskSamples = new Point2[size];

		for (int j = 0; j < size; j++) {
			// map sample point to [-1, 1] X [-1,1]

			sp.x = 2 * samples[j].x - 1;
			sp.y = 2 * samples[j].y - 1;

			if (sp.x > -sp.y) { // sectors 1 and 2
				if (sp.x > sp.y) { // sector 1
					r = sp.x;
					phi = sp.y / sp.x;
				} else { // sector 2
					r = sp.y;
					phi = 2 - sp.x / sp.y;
				}
			} else { // sectors 3 and 4
				if (sp.x < sp.y) { // sector 3
					r = -sp.x;
					phi = 4 + sp.y / sp.x;
				} else { // sector 4
					r = -sp.y;
					if (sp.y != 0.0) // avoid division by zero at origin
						phi = 6 - sp.x / sp.y;
					else
						phi = 0;
				}
			}

			phi *= PI / 4.0;

			diskSamples[j].x = (float) (r * Math.cos(phi));
			diskSamples[j].y = (float) (r * Math.sin(phi));
		}

		// samples.erase(samples.begin(), samples.end());
	}

	/**
	 * Maps the 2D sample points to 3D points on a unit hemisphere with a cosine power
	 * density distribution in the polar angle
	 */
	public void mapSamplesToHemisphere(float exp) {
		int size = samples.length;
		hemisphereSamples = new Point3[size];
			
		for (int j = 0; j < size; j++) {
			float cos_phi = (float)Math.cos(2.0 * PI * samples[j].x);
			float sin_phi = (float)Math.sin(2.0 * PI * samples[j].x);	
			float cos_theta = (float)Math.pow((1.0 - samples[j].y), 1.0 / (exp + 1.0));
			float sin_theta = (float)Math.sqrt (1.0 - cos_theta * cos_theta);
			float pu = sin_theta * cos_phi;
			float pv = sin_theta * sin_phi;
			float pw = cos_theta;
			hemisphereSamples[j] = new Point3(pu, pv, pw); 
		}
	}

	/** get next sample on unit square */
	public Point2 sampleUnitSquare() {
		if (count % numSamples == 0) // start of a new pixel
			jump = rng.nextInt(numSets) * numSamples;
		return samples[jump
				+ shuffledIndices[jump + (int) (count++ % numSamples)]];
	}
	
	public Point2 sampleUnitDisk() {
		if (count % numSamples == 0)  									// start of a new pixel
			jump = rng.nextInt(numSets) * numSamples;
		
		return diskSamples[jump + shuffledIndices[jump + (int)(count++ % numSamples)]];
	}

	public Point3 sampleHemisphere() {
		if (count % numSamples == 0)  									// start of a new pixel
			jump = rng.nextInt(numSets) * numSamples;
		
		return hemisphereSamples[jump + shuffledIndices[jump + (int)(count++ % numSamples)]];
	}
}
