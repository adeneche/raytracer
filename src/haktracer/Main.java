package haktracer;

import haktracer.materials.SimpleTexture;
import haktracer.shapes.Surface;
import haktracer.shapes.SurfaceHitRecord;
import haktracer.shapes.Triangle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public class Main {

	private static void render(Image im, Collection<Surface> scene, Rgb bgColor, int numSamp, Camera cam) {
		final int numsSampSq = numSamp*numSamp;
		
		Vec2[] samples = Sample.samples(numsSampSq);
		Vec2[] fsamples = Sample.samples(numsSampSq);
		Rgb color = new Rgb();
		SurfaceHitRecord record = new SurfaceHitRecord();
		boolean isHit;
		float tmax; // max valid t parameter
		
		// loop over pixels
		for (int i = 0; i < im.nx; i++) {
			for (int j = 0; j < im.ny; j++) {
				color.set(0, 0, 0);
				
				Sample.nrooks(samples);
				Sample.nrooks(fsamples);
				for (int k = 0; k < numsSampSq; k++) {
					tmax = 100000;
					isHit = false;

					Ray ray = cam.getRay((i + samples[k].x())/im.nx, (j + samples[k].y())/im.ny, 
							fsamples[k].x()/numSamp, fsamples[k].y()/numSamp);
					
					// loop over list of shapes
					for (Surface shape : scene) {
						if (shape.hit(ray, .00001f, tmax, 0, record)) {
							tmax = record.t;
							isHit = true;
						}
					}
					
					if (isHit) color.sadd(record.hitTex.value(null, record.hitPoint));
					else color.sadd(bgColor);
				}

				im.set(i, j, color.div(numsSampSq));
			}
		}

	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		// geometry
		Collection<Surface> scene = initScene();
		
		Image im = new Image(640, 480);
		Rgb bgColor = new Rgb(.2f, .2f, .2f);		
		final int numSamp = 10;
		
		Camera cam = initCamera();

		render(im, scene, bgColor, numSamp, cam);
		
		im.writePNG(new FileOutputStream("renders/result-nrooks-multix2-100-aperture1.png"));
		System.exit(0);
	}

	private static Camera initCamera() {
		Vec3 e = new Vec3(1, -.75f, 2f);
		Vec3 g = new Vec3(-.25f, 0, -2);
		Vec3 vup = new Vec3(0, 1, 0);
		float s = 2;
		float aperture = 1;
		Camera cam = new Camera(e, g, vup, aperture, -2f, 2f, -2f, 2f, s);
		return cam;
	}
	
	private static Collection<Surface> initScene() {
		Collection<Surface> shapes = new Vector<Surface>();
		shapes.add(new Triangle(new Vec3(1, 0, 1), new Vec3(-1, 0, 1), new Vec3(0, -1, 1), new SimpleTexture(new Rgb(1, .5f, .5f))));
		shapes.add(new Triangle(new Vec3(1, 0, 0), new Vec3(-1, 0, 0), new Vec3(0, -1, 0), new SimpleTexture(new Rgb(.5f, 1, .5f))));
		shapes.add(new Triangle(new Vec3(1, 0, -1), new Vec3(-1, 0, -1), new Vec3(0, -1, -1), new SimpleTexture(new Rgb(.5f, .5f, 1))));

		return shapes;
	}
}
