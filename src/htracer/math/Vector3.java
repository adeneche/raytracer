package htracer.math;

import htracer.utility.Normal;

public class Vector3 {

	public float x;
	public float y;
	public float z;
	
	public Vector3() {
		this(0, 0, 0);
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(Vector3 v) {
		this(v.x, v.y, v.z);
	}
	
	public void set(Normal n) {
		x = n.x;
		y = n.y;
		z = n.z;
	}
	
	public void set(Vector3 v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3 add(Vector3 v) {
		return new Vector3(x + v.x, y + v.y, z + v.z);
	}

	public Vector3 sub(Vector3 v) {
		return new Vector3(x - v.x, y - v.y, z - v.z);
	}

	public Vector3 sub(Normal n) {
		return new Vector3(x - n.x, y - n.y, z - n.z);
	}
	
	public Vector3 mul(float a) {
		return new Vector3(x*a, y*a, z*a);
	}
	
	public Vector3 div(float a) {
		return new Vector3(x/a, y/a, z/a);
	}
	
	public Vector3 neg() {
		return new Vector3(-x, -y, -z);
	}
	
	public Vector3 sadd(Vector3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		
		return this;
	}
	
	public float lenSq() {
		return x*x + y*y + z*z;
	}
	
	public float len() {
		return (float)Math.sqrt(lenSq());
	}
	
	public void normalize() {
		float len = len();
		x /= len;
		y /= len;
		z /= len;
	}

	public Point3 toPoint() {
		return new Point3(x, y, z);
	}

	public float dot(Vector3 v) {
		return dot(this, v);
	}
	
	public static float dot(Vector3 u, Vector3 v) {
		return u.x*v.x + u.y*v.y + u.z*v.z;
	}
	
	public static Vector3 cross(Vector3 u, Vector3 v) {
		Vector3 res = new Vector3();
		return cross(u, v, res);
	}
	
	public static Vector3 cross(Vector3 u, Vector3 v, Vector3 res) {
		res.x = u.y*v.z - u.z*v.y;
		res.y = u.z*v.x - u.x*v.z;
		res.z = u.x*v.y - u.y*v.x;
		
		return res;
	}

	public Vector3 hat() {
		normalize();
		return this;
	}
	

}
