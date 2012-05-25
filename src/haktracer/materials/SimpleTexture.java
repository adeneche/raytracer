package haktracer.materials;

import haktracer.Rgb;
import haktracer.Vec2;
import haktracer.Vec3;

public class SimpleTexture extends Texture {

	private final Rgb color;
	
	public SimpleTexture(Rgb color) {
		this.color = color;
	}
	
	@Override
	public Rgb value(Vec2 coord, Vec3 hitPoint) {
		return color;
	}

}
