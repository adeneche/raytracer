package htracer.tracers;

import htracer.utility.RGBColor;
import htracer.utility.Ray;
import htracer.utility.ShadeRec;
import htracer.utility.Vector3;
import htracer.world.World;

public class MultipleObjects extends Tracer {

	public Vector3 lightDir;
	public float ambiant;
	
	public MultipleObjects(World wr) {
		super(wr);
		lightDir = new Vector3(0,1,0);
		ambiant = .5f;
	}
	
	@Override
	public RGBColor traceRay(Ray ray) {
		ShadeRec sr = world.hitBareBonesObjects(ray);
		
		if (sr.hitAnObject) {
			return sr.color.mul( Math.max(0, sr.normal.neg().dot(lightDir))).add(sr.color.mul(ambiant));
		} else {
			return world.backgroundColor;
		}
	}

}
