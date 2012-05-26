package htracer;

import static htracer.utility.Constants.black;
import htracer.geometric.Function;
import htracer.geometric.Sphere;
import htracer.tracers.MultipleObjects;
import htracer.utility.Point3;
import htracer.utility.RGBColor;
import htracer.world.World;

import java.io.IOException;

public class Ch4World extends World {
	static String fileName;

	@Override
	public void build() {
		int res = 20;
		int n = 4;
		
		fileName = "chap4."+n+"."+res+".png";
		
		vp.hres = res;
		vp.vres = res;
		vp.s = 2f;
		vp.numSamples = n*n;
		
		backgroundColor.set(black);
		
		tracer = new MultipleObjects(this);  
		
		addObject(new Sphere(new Point3(0, 0, -60), 18, new RGBColor(1)));
	}

	public static void main(String[] args) {
		World world = new Ch4World();
		world.build();
		world.renderScene();
		
		try {
			world.saveImage(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
