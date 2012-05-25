package htracer.utility;

import htracer.Matrix;

public class Normal {

	public float x;
	public float y;
	public float z;
	
	public Normal() {
		this(0, 0, 0);
	}
	
	public Normal(float a) {
		this(a, a, a);
	}
	
	public Normal(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Normal(Normal n) {
		this(n.x, n.y, n.z);
	}

	public Normal(Vector3 v) {
		this(v.x, v.y, v.z);
	}
	
	public Normal set(Normal v) {
		x = v.x;
		y = v.y;
		z = v.z;
		
		return this;
	}
	
	public void set(Vector3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public void set(Point3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Normal neg() {
		return new Normal(-x, -y, -z);
	}

	public Normal add(Normal v) {
		return new Normal(x + v.x, y + v.y, z + v.z);
	}

	public Normal add(Vector3 v) {
		return new Normal(x + v.x, y + v.y, z + v.z);
	}
	
	public void sadd(Normal v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public float dot(Vector3 v) {
		return x*v.x + y*v.y + z*v.z;
	}
	
	public Normal mul(float a) {
		return new Normal(x*a, y*a, z*a);
	}

	public void normalize() {
		float len = (float)Math.sqrt(x*x + y*y + z*z);
		x /= len;
		y /= len;
		z /= len;
	}

	public Normal mul(Matrix m) {
//		return (Normal(	mat.m[0][0] * n.x + mat.m[1][0] * n.y + mat.m[2][0] * n.z,
//				mat.m[0][1] * n.x + mat.m[1][1] * n.y + mat.m[2][1] * n.z,
//				mat.m[0][2] * n.x + mat.m[1][2] * n.y + mat.m[2][2] * n.z));
		throw new RuntimeException("Not Yet Implemented");
	}

}
