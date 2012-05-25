package haktracer.materials;

import haktracer.Rgb;
import haktracer.Vec2;
import haktracer.Vec3;

public abstract class Texture {

	public abstract Rgb value(Vec2 coord, Vec3 hitPoint); 
}
