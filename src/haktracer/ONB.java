package haktracer;

public class ONB {
	
	private static final float ONB_EPSILON = 0.01f;
	private Vec3 U = new Vec3();
	private Vec3 V = new Vec3();
	private Vec3 W = new Vec3();
	
	public Vec3 u() { return U; }
	public Vec3 v() { return V; }
	public Vec3 w() { return W; }
	
	public ONB() {}
	
	public ONB(Vec3 a, Vec3 b, Vec3 c) {
		U.set(a);
		V.set(b);
		W.set(c);
	}
	
	public void initFromU(Vec3 u) {
//		   Vector3 n(1.0f, 0.0f, 0.0f);
//		   Vector3 m(0.0f, 1.0f, 0.0f);
//		   
//		   U = unitVector(u);
//		   V = cross(U, n);
//		   if (V.length() < ONB_EPSILON)
//		      V = cross(U, m); 
//		   W = cross( U, V );
		Vec3 n = Vec3.X;
		Vec3 m = Vec3.Y;
		
		Vec3.normalize(u, U);
		Vec3.cross(U, n, V);
		if (V.len() < ONB_EPSILON) Vec3.cross(this.U, m, V);
		Vec3.cross(U, V, W);
	}
	
	public void initFromV(Vec3 v) {
//		   Vector3 n(1.0f, 0.0f, 0.0f);
//		   Vector3 m(0.0f, 1.0f, 0.0f);
//		   
//		   V = unitVector(v);
//		   U = cross(V, n);
//		   if (U.squaredLength() < ONB_EPSILON)
//		      U = cross(V, m); 
//		   W = cross(U, V);
		Vec3 n = Vec3.X;
		Vec3 m = Vec3.Y;
		
		Vec3.normalize(v, V);
		Vec3.cross(V, n, U);
		if (U.lenSq() < ONB_EPSILON) Vec3.cross(V, m, U);
		Vec3.cross(U, V, W);
	}
	
	public void initFromW(Vec3 w) {
//		   Vector3 n(1.0f, 0.0f, 0.0f);
//		   Vector3 m(0.0f, 1.0f, 0.0f);
//		   
//		   W = unitVector(w);
//		   U = cross(W, n);
//		   if (U.length() < ONB_EPSILON)
//		      U = cross(W, m); 
//		   V = cross(W, U);
		Vec3 n = Vec3.X;
		Vec3 m = Vec3.Y;
		
		Vec3.normalize(w, W);
		Vec3.cross(W, n, U);
		if (U.len() < ONB_EPSILON) Vec3.cross(W, m, U);
		Vec3.cross(W, U, V);
	}
	
	public void set(Vec3 a, Vec3 b, Vec3 c) {
		U.set(a);
		V.set(b);
		W.set(c);
	}
	
	// calculate the ONB from two vectors
	// The first one is the fixed vector (it is just normalized)
	// the second is normalized and it's direction can be adjusted
	public void initFromUV(Vec3 u, Vec3 v) {
//		   U = unitVector( u );
//		   W = unitVector( cross(u, v) );
//		   V = cross( W, U);
		Vec3.normalize(u, U);
		Vec3.cross(u, v, W).normalize();
		Vec3.cross(W, U, V);
	}
	
	public void initFromVU(Vec3 v, Vec3 u) {
//		   V = unitVector( v );
//		   W = unitVector( cross(u, v) );
//		   U = cross( V, W );
		Vec3.normalize(v, V);
		Vec3.cross(u,  v, W).normalize();
		Vec3.cross(V, W, U);
	}
	
	public void initFromUW(Vec3 u, Vec3 w) {
//		   U = unitVector( u );
//		   V = unitVector( cross(w, u) );
//		   W = cross( U, V );
		Vec3.normalize(u, U);
		Vec3.cross(w, u, V).normalize();
		Vec3.cross(U, V, W);
	}
	
	public void initFromWU(Vec3 w, Vec3 u) {
//		   W = unitVector( w );
//		   V = unitVector( cross(w, u) );
//		   U = cross(V, W);
		Vec3.cross(w, W);
		Vec3.cross(w, u, V).normalize();
		Vec3.cross(V, W, U);
	}
	
	public void initFromVW(Vec3 v, Vec3 w) {
//		   V = unitVector( v );
//		   U = unitVector( cross(v, w) );
//		   W = cross( U, V );
		Vec3.cross(v, V);
		Vec3.cross(v, w, U).normalize();
		Vec3.cross(U, V, W);
	}
	
	public void initFromWV(Vec3 w, Vec3 v) {
//		   W = unitVector( w );
//		   U = unitVector( cross(v, w) );
//		   V = cross( W, U );
		Vec3.normalize(w, W);
		Vec3.cross(v, w, U).normalize();
		Vec3.cross(W, U, V);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || !(obj instanceof ONB)) return false;
		
		ONB onb = (ONB)obj;
		
		return U.equals(onb.U) && V.equals(onb.V) && W.equals(onb.W);
	}

	@Override
	public int hashCode() {
		return U.hashCode() + V.hashCode() + W.hashCode();
	}
	
	
}
