package htracer;

import static htracer.utility.Constants.black;
import htracer.geometric.Function;
import htracer.samplers.Jittered;
import htracer.samplers.MultiJittered;
import htracer.tracers.MultipleObjects;
import htracer.world.World;

import java.io.IOException;

public class Ch5World extends World {
	static String fileName;

	@Override
	public void build() {
		int res = 512;
		int n = 7;
		
		fileName = "chap5..mj."+n+"."+res+".png";
		
		vp.hres = res;
		vp.vres = res;
		vp.s = 8f/res;
		vp.setSampler(new MultiJittered(n*n));
		
		backgroundColor.set(black);
		
		tracer = new MultipleObjects(this);  
		
		addObject(new Function());
	}

	public static void main(String[] args) {
		World world = new Ch5World();
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
