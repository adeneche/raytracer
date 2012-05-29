package htracer.utility;

import htracer.Matrix;

public class Point3 {

	public float x;
	public float y;
	public float z;
	
	public Point3() {
		this(0);
	}
	
	public Point3(float a) {
		x = y = z = a;
	}
	
	public Point3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3(Point3 b) {
		set(b);
	}
	
	public void set(Point3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public void set(Vector3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Point3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public Vector3 asVector() {
		return new Vector3(x, y, z);
	}
	
	public Point3 neg() {
		return new Point3(-x, -y, -z);
	}
	
	public Vector3 sub(Point3 b) {
		return new Vector3(x - b.x, y - b.y, z - b.z);
	}
	
	public Point3 add(Vector3 v) {
		return new Point3(x + v.x, y + v.y, z + v.z);
	}

	public Point3 sub(Vector3 v) {
		return new Point3(x - v.x, y - v.y, z - v.z);
	}

	public Point3 mul(float a) {
		return new Point3(x*a, y*a, z*a);
	}
	
	public float lenSq(Point3 b) {
		return x*x + y*y + z*z;
	}
	
	public float len(Point3 b) {
		return (float)Math.sqrt(lenSq(b));
	}

	public Point3 mul(Matrix m) {
//		return (Point3D(mat.m[0][0] * p.x + mat.m[0][1] * p.y + mat.m[0][2] * p.z + mat.m[0][3],
//				mat.m[1][0] * p.x + mat.m[1][1] * p.y + mat.m[1][2] * p.z + mat.m[1][3],
//				mat.m[2][0] * p.x + mat.m[2][1] * p.y + mat.m[2][2] * p.z + mat.m[2][3]));
		throw new RuntimeException("Not Yet Implemented");
	}

}
