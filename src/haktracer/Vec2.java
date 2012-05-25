package haktracer;
import java.util.Arrays;


public class Vec2 {
	public static final Vec2 X = new Vec2(1,0);
	public static final Vec2 Y = new Vec2(0,1);
	
	public final float[] es = new float[2];
	
	public float x() { return es[0]; }
	public float y() { return es[1]; }
	
	public float element(int ind) {
		return es[ind];
	}
	
	public Vec2() {
		es[0] = 0;
		es[1] = 0;
	}
	
	public Vec2(Vec2 v) {
		es[0] = v.es[0];
		es[1] = v.es[1];
	}
	
	public Vec2(float x, float y) {
		es[0] = x;
		es[1] = y;
	}
	
	public void normalize() {
		sdiv(len());
	}	
	
	public float len() {
		return (float) Math.sqrt(es[0]*es[0] + es[1]*es[1]);
	}
	
	public float lenSq() {
		return es[0]*es[0] + es[1]*es[1];
	}
	
	public Vec2 neg() {
		return new Vec2(-es[0], -es[1]);
	}
	
	public Vec2 mul(float k) {
		return new Vec2(k*es[0], k*es[1]);
	}
	
	public Vec2 div(float k) {
		return new Vec2(es[0]/k, es[1]/k);
	}
	
	public Vec2 add(Vec2 v) {
		return new Vec2(es[0]+v.es[0], es[1]+v.es[1]);
	}
	
	public Vec2 sub(Vec2 v) {
		return new Vec2(es[0]-v.es[0], es[1]-v.es[1]);
	}

	public void sadd(Vec2 v) {
		es[0] += v.es[0];
		es[1] += v.es[1];
	}

	public void ssub(Vec2 v) {
		es[0] -= v.es[0];
		es[1] -= v.es[1];
	}

	public void smul(float k) {
		es[0] *= k;
		es[1] *= k;
	}

	public void sdiv(float k) {
		es[0] /= k;
		es[1] /= k;
	}

	public float minComp() {
		float min = es[0];
		if (es[1] < min) min = es[1];
		return min; 
	}

	public float indMinComp() {
		int min = 0;
		if (es[1] < es[min]) min = 1;
		return min; 
	}

	public float maxComp() {
		float max = es[0];
		if (es[1] > max) max = es[1];
		return max; 
	}
	
	public float indMaxComp() {
		int max = 0;
		if (es[1] > es[max]) max = 1;
		return max; 
	}

	public float minAbsComp() {
		float min = Math.abs(es[0]);
		if (Math.abs(es[1]) < min) min = Math.abs(es[1]);
		return min; 
	}

	public float indMinAbsComp() {
		int min = 0;
		if (Math.abs(es[1]) < Math.abs(es[min])) min = 1;
		return min; 
	}

	public float maxAbsComp() {
		float max = Math.abs(es[0]);
		if (Math.abs(es[1]) > max) max = Math.abs(es[1]);
		return max; 
	}

	public float indMaxAbsComp() {
		int max = 0;
		if (Math.abs(es[1]) > Math.abs(es[max])) max = 1;
		return max; 
	}
	
	public static float dot(Vec2 v1, Vec2 v2) {
		return v1.es[0]*v2.es[0] + v1.es[1]*v2.es[1];
	}
	
	public static Vec2 normalize(Vec2 v, Vec2 res) {
		res.set(v);
		res.sdiv(v.len());
		return res;
	}
	
	public static Vec2 normalize(Vec2 v) {
		return normalize(v, new Vec2());
	}
	
	public static Vec2 minVec(Vec2 v1, Vec2 v2) {
		Vec2 v = new Vec2(v1);
		if (v2.es[0] < v1.es[0]) v.es[0] = v2.es[0];
		if (v2.es[1] < v1.es[1]) v.es[1] = v2.es[1];
		return v;
	}
	
	public static Vec2 maxVec(Vec2 v1, Vec2 v2) {
		Vec2 v = new Vec2(v1);
		if (v2.es[0] > v1.es[0]) v.es[0] = v2.es[0];
		if (v2.es[1] > v1.es[1]) v.es[1] = v2.es[1];
		return v;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || !(obj instanceof Vec2)) return false;
		
		Vec2 v = (Vec2)obj;
		
		return Arrays.equals(es, v.es);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(es);
	}

	public Vec2 set(Vec2 a) {
		es[0] = a.es[0];
		es[1] = a.es[1];
		
		return this;
	}

}
