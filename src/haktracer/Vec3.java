package haktracer;
import java.util.Arrays;


public class Vec3 {
	public static final Vec3 X = new Vec3(1,0,0);
	public static final Vec3 Y = new Vec3(0,1,0);
	public static final Vec3 Z = new Vec3(0,0,1);
	
	public final float[] es = new float[3];
	
	public float x() { return es[0]; }
	public float y() { return es[1]; }
	public float z() { return es[2]; }
	
	public float element(int ind) {
		return es[ind];
	}
	
	public Vec3() {
		es[0] = 0;
		es[1] = 0;
		es[2] = 0;
	}
	
	public Vec3(Vec3 v) {
		es[0] = v.es[0];
		es[1] = v.es[1];
		es[2] = v.es[2];
	}
	
	public Vec3(float x, float y, float z) {
		es[0] = x;
		es[1] = y;
		es[2] = z;
	}
	
	public void normalize() {
		sdiv(len());
	}	
	
	public float len() {
		return (float) Math.sqrt(es[0]*es[0] + es[1]*es[1] + es[2]*es[2]);
	}
	
	public float lenSq() {
		return es[0]*es[0] + es[1]*es[1] + es[2]*es[2];
	}
	
	public Vec3 neg() {
		return new Vec3(-es[0], -es[1], -es[2]);
	}
	
	public Vec3 mul(float k) {
		return new Vec3(k*es[0], k*es[1], k*es[2]);
	}
	
	public Vec3 div(float k) {
		return new Vec3(es[0]/k, es[1]/k, es[2]/k);
	}
	
	public Vec3 add(Vec3 v) {
		return new Vec3(es[0]+v.es[0], es[1]+v.es[1], es[2]+v.es[2]);
	}
	
	public Vec3 sub(Vec3 v) {
		return new Vec3(es[0]-v.es[0], es[1]-v.es[1], es[2]-v.es[2]);
	}

	public void sadd(Vec3 v) {
		es[0] += v.es[0];
		es[1] += v.es[1];
		es[2] += v.es[2];
	}

	public void ssub(Vec3 v) {
		es[0] -= v.es[0];
		es[1] -= v.es[1];
		es[2] -= v.es[2];
	}

	public void smul(float k) {
		es[0] *= k;
		es[1] *= k;
		es[2] *= k;
	}

	public void sdiv(float k) {
		es[0] /= k;
		es[1] /= k;
		es[2] /= k;
	}

	public float minComp() {
		float min = es[0];
		if (es[1] < min) min = es[1];
		if (es[2] < min) min = es[2];
		return min; 
	}

	public float indMinComp() {
		int min = 0;
		if (es[1] < es[min]) min = 1;
		if (es[2] < es[min]) min = 2;
		return min; 
	}

	public float maxComp() {
		float max = es[0];
		if (es[1] > max) max = es[1];
		if (es[2] > max) max = es[2];
		return max; 
	}
	
	public float indMaxComp() {
		int max = 0;
		if (es[1] > es[max]) max = 1;
		if (es[2] > es[max]) max = 2;
		return max; 
	}

	public float minAbsComp() {
		float min = Math.abs(es[0]);
		if (Math.abs(es[1]) < min) min = Math.abs(es[1]);
		if (Math.abs(es[2]) < min) min = Math.abs(es[2]);
		return min; 
	}

	public float indMinAbsComp() {
		int min = 0;
		if (Math.abs(es[1]) < Math.abs(es[min])) min = 1;
		if (Math.abs(es[2]) < Math.abs(es[min])) min = 2;
		return min; 
	}

	public float maxAbsComp() {
		float max = Math.abs(es[0]);
		if (Math.abs(es[1]) > max) max = Math.abs(es[1]);
		if (Math.abs(es[2]) > max) max = Math.abs(es[2]);
		return max; 
	}

	public float indMaxAbsComp() {
		int max = 0;
		if (Math.abs(es[1]) > Math.abs(es[max])) max = 1;
		if (Math.abs(es[2]) > Math.abs(es[max])) max = 2;
		return max; 
	}
	
	public static float dot(Vec3 v1, Vec3 v2) {
		return v1.es[0]*v2.es[0] + v1.es[1]*v2.es[1] + v1.es[2]*v2.es[2];
	}
	
	public static Vec3 cross(Vec3 v1, Vec3 v2, Vec3 res) {
		res.es[0] = v1.y()*v2.z() - v1.z()*v2.y();
		res.es[1] = v1.z()*v2.x() - v1.x()*v2.z();
		res.es[2] = v1.x()*v2.y() - v1.y()*v2.x();
		return res;
	}
	
	public static Vec3 cross(Vec3 v1, Vec3 v2) {
		return cross(v1, v2, new Vec3());
	}
	
	public static float tripleProduct(Vec3 v1, Vec3 v2, Vec3 v3) {
		return dot(cross(v1, v2), v3);
	}
	
	public static Vec3 normalize(Vec3 v, Vec3 res) {
		res.set(v);
		res.sdiv(v.len());
		return res;
	}
	
	public static Vec3 normalize(Vec3 v) {
		return normalize(v, new Vec3());
	}
	
	public static Vec3 minVec(Vec3 v1, Vec3 v2) {
		Vec3 v = new Vec3(v1);
		if (v2.es[0] < v1.es[0]) v.es[0] = v2.es[0];
		if (v2.es[1] < v1.es[1]) v.es[1] = v2.es[1];
		if (v2.es[2] < v1.es[2]) v.es[2] = v2.es[2];
		return v;
	}
	
	public static Vec3 maxVec(Vec3 v1, Vec3 v2) {
		Vec3 v = new Vec3(v1);
		if (v2.es[0] > v1.es[0]) v.es[0] = v2.es[0];
		if (v2.es[1] > v1.es[1]) v.es[1] = v2.es[1];
		if (v2.es[2] > v1.es[2]) v.es[2] = v2.es[2];
		return v;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || !(obj instanceof Vec3)) return false;
		
		Vec3 v = (Vec3)obj;
		
		return Arrays.equals(es, v.es);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(es);
	}
	public Vec3 set(Vec3 a) {
		es[0] = a.es[0];
		es[1] = a.es[1];
		es[2] = a.es[2];
		
		return this;
	}

}
